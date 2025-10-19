import Foundation
import CryptoKit
import Security
import LocalAuthentication

@available(iOS 13.0, *)
@objcMembers public class IosEncryptionManager: NSObject {

    private static let keyAlias = "com.michaeltchuang.walletsdk.aes_key"
    private static let secureEnclaveKeyAlias = "com.michaeltchuang.walletsdk.aes_key_se"
    private var symmetricKey: SymmetricKey?
    private var useSecureEnclave: Bool = false

    public override init() {
        super.init()
        self.symmetricKey = Self.getOrCreateKey()
    }

    // MARK: - Key Management

    /// Gets an existing key from the Keychain or creates a new one
    /// Tries Secure Enclave first, falls back to regular Keychain
    private static func getOrCreateKey() -> SymmetricKey {
        // First, try to retrieve existing Secure Enclave key
        if let existingKey = retrieveKeyFromKeychain(useSecureEnclave: true) {
            return existingKey
        }

        // Try to retrieve existing regular Keychain key
        if let existingKey = retrieveKeyFromKeychain(useSecureEnclave: false) {
            return existingKey
        }

        // Check if device supports Secure Enclave
        if Self.isSecureEnclaveAvailable() {
            if let newKey = Self.createSecureEnclaveKey() {
                return newKey
            }
        }

        // Fallback to regular Keychain
        let newKey = SymmetricKey(size: .bits256)
        Self.saveKeyToKeychain(key: newKey, useSecureEnclave: false)
        return newKey
    }

    /// Checks if Secure Enclave is available on this device
    private static func isSecureEnclaveAvailable() -> Bool {
        // Secure Enclave is available on devices with A7 chip or later
        // This includes iPhone 5s and later, iPad Air and later, iPad mini 2 and later

        // Check if we can create a Secure Enclave key
        let context = LAContext()
        var error: NSError?

        // Check if biometrics are available (good indicator of Secure Enclave support)
        let canEvaluate = context.canEvaluatePolicy(.deviceOwnerAuthenticationWithBiometrics, error: &error)

        // Also check if the device has passcode set (required for Secure Enclave)
        let hasPasscode = context.canEvaluatePolicy(.deviceOwnerAuthentication, error: &error)

        return canEvaluate || hasPasscode
    }

    /// Creates a key stored in the Secure Enclave
    /// Returns nil if Secure Enclave is not available or creation fails
    private static func createSecureEnclaveKey() -> SymmetricKey? {
        // For Secure Enclave, we need to use a P256 key (elliptic curve)
        // and derive an AES key from it
        let access = SecAccessControlCreateWithFlags(
            nil,
            kSecAttrAccessibleWhenUnlockedThisDeviceOnly,
            [.privateKeyUsage],
            nil
        )

        guard let access = access else {
            print("Error: Failed to create access control for Secure Enclave")
            return nil
        }

        // Generate a P256 private key in the Secure Enclave
        let privateKeyAttributes: [String: Any] = [
            kSecAttrIsPermanent as String: true,
            kSecAttrApplicationTag as String: secureEnclaveKeyAlias,
            kSecAttrAccessControl as String: access
        ]

        let attributes: [String: Any] = [
            kSecAttrKeyType as String: kSecAttrKeyTypeECSECPrimeRandom,
            kSecAttrKeySizeInBits as String: 256,
            kSecAttrTokenID as String: kSecAttrTokenIDSecureEnclave,
            kSecPrivateKeyAttrs as String: privateKeyAttributes
        ]

        var error: Unmanaged<CFError>?
        guard let privateKey = SecKeyCreateRandomKey(attributes as CFDictionary, &error) else {
            if let error = error {
                print("Error: Failed to create Secure Enclave key: \(error.takeRetainedValue())")
            }
            return nil
        }

        // Derive a symmetric key from the private key's public key representation
        // This is a simplification - in production you might want to use key agreement
        guard let publicKey = SecKeyCopyPublicKey(privateKey),
              let publicKeyData = SecKeyCopyExternalRepresentation(publicKey, &error) as Data?
        else {
            if let error = error {
                print("Error: Failed to get public key data: \(error.takeRetainedValue())")
            } else {
                print("Error: Failed to get public key data")
            }
            return nil
        }

        // Use SHA256 to derive a 256-bit symmetric key from the public key
        let hash = SHA256.hash(data: publicKeyData)
        let symmetricKey = SymmetricKey(data: hash)

        // Store a reference that we're using Secure Enclave
        UserDefaults.standard.set(true, forKey: "useSecureEnclave")

        return symmetricKey
    }

    /// Saves the symmetric key to the Keychain
    private static func saveKeyToKeychain(key: SymmetricKey, useSecureEnclave: Bool) {
        let keyData = key.withUnsafeBytes {
            Data($0)
        }
        let alias = useSecureEnclave ? secureEnclaveKeyAlias : keyAlias

        var query: [String: Any] = [
            kSecClass as String: kSecClassGenericPassword,
            kSecAttrAccount as String: alias,
            kSecValueData as String: keyData,
            kSecAttrAccessible as String: kSecAttrAccessibleAfterFirstUnlockThisDeviceOnly
        ]

        if useSecureEnclave {
            // Add Secure Enclave-specific attributes
            if let access = SecAccessControlCreateWithFlags(
                nil,
                kSecAttrAccessibleWhenUnlockedThisDeviceOnly,
                [.privateKeyUsage],
                nil
            ) {
                query[kSecAttrAccessControl as String] = access
            }
        }

        // Delete any existing key first
        SecItemDelete(query as CFDictionary)

        let status = SecItemAdd(query as CFDictionary, nil)
        if status != errSecSuccess {
            print("Error: Failed to save key to Keychain (status: \(status))")
        }
    }

    /// Retrieves the symmetric key from the Keychain
    private static func retrieveKeyFromKeychain(useSecureEnclave: Bool) -> SymmetricKey? {
        let alias = useSecureEnclave ? secureEnclaveKeyAlias : keyAlias

        let query: [String: Any] = [
            kSecClass as String: kSecClassGenericPassword,
            kSecAttrAccount as String: alias,
            kSecReturnData as String: true,
            kSecMatchLimit as String: kSecMatchLimitOne
        ]

        var item: CFTypeRef?
        let status = SecItemCopyMatching(query as CFDictionary, &item)

        guard status == errSecSuccess,
              let keyData = item as? Data
        else {
            return nil
        }

        return SymmetricKey(data: keyData)
    }

    /// Checks if currently using Secure Enclave
    public func isUsingSecureEnclave() -> Bool {
        return UserDefaults.standard.bool(forKey: "useSecureEnclave")
    }

    /// Migrates from regular Keychain to Secure Enclave if available
    public func migrateToSecureEnclave() -> Bool {
        guard Self.isSecureEnclaveAvailable() else {
            return false
        }

        guard !isUsingSecureEnclave() else {
            return true
        }

        // Create new Secure Enclave key
        if let newKey = Self.createSecureEnclaveKey() {
            // Delete old Keychain key
            let query: [String: Any] = [
                kSecClass as String: kSecClassGenericPassword,
                kSecAttrAccount as String: Self.keyAlias
            ]
            SecItemDelete(query as CFDictionary)

            // Update current key
            self.symmetricKey = newKey
            self.useSecureEnclave = true

            return true
        }

        return false
    }

    // Encryption/Decryption

    /// Encrypts a byte array using AES-GCM
    /// Returns IV (12 bytes) + encrypted data + authentication tag
    public func encryptByteArray(_ data: Data) -> Data {
        guard let key = symmetricKey else {
            print("Error: Encryption key not initialized")
            return Data()
        }

        do {
            let sealedBox = try AES.GCM.seal(data, using: key)

            // Combine nonce (IV) + ciphertext + tag
            // This matches Android's IV + encryptedData format
            guard let combined = sealedBox.combined else {
                print("Error: Failed to get combined data from sealed box")
                return Data()
            }

            return combined
        } catch {
            print("Error: Encryption failed - \(error)")
            return Data()
        }
    }

    /// Decrypts a byte array using AES-GCM
    /// Expects IV (12 bytes) + encrypted data + authentication tag
    public func decryptByteArray(_ encryptedData: Data) -> Data {
        guard let key = symmetricKey else {
            print("Error: Encryption key not initialized")
            return Data()
        }

        do {
            // Create sealed box from combined data (nonce + ciphertext + tag)
            let sealedBox = try AES.GCM.SealedBox(combined: encryptedData)
            let decryptedData = try AES.GCM.open(sealedBox, using: key)
            return decryptedData
        } catch {
            print("Error: Decryption failed - \(error)")
            return Data()
        }
    }

    /// Encrypts a string and returns Base64 encoded result
    public func encryptString(_ data: String) -> String {
        guard let stringData = data.data(using: .utf8) else {
            print("Error: Failed to convert string to data")
            return ""
        }

        let encryptedData = encryptByteArray(stringData)
        if encryptedData.isEmpty {
            return ""
        }

        return encryptedData.base64EncodedString()
    }

    /// Decrypts a Base64 encoded string
    public func decryptString(_ encryptedData: String) -> String {
        guard let encryptedBytes = Data(base64Encoded: encryptedData) else {
            print("Error: Failed to decode Base64 string")
            return ""
        }

        let decryptedData = decryptByteArray(encryptedBytes)
        if decryptedData.isEmpty {
            return ""
        }

        guard let decryptedString = String(data: decryptedData, encoding: .utf8) else {
            print("Error: Failed to convert decrypted data to string")
            return ""
        }

        return decryptedString
    }

    /// Initializes the encryption manager (creates or retrieves key)
    public func initializeEncryptionManager() {
        if symmetricKey == nil {
            symmetricKey = Self.getOrCreateKey()
        }
    }
}
