import Foundation
import CryptoKit
import Security
import LocalAuthentication

/// Errors that can occur during encryption operations
public enum EncryptionError: Error {
    case keyNotInitialized
    case encryptionFailed(String)
    case decryptionFailed(String)
    case dataConversionFailed
    case secureEnclaveNotAvailable
    case keyCreationFailed(String)
    case keychainError(OSStatus)
}

@available(iOS 13.0, *)
@objcMembers public class IosEncryptionManager: NSObject {

    private static let keyAlias = "com.michaeltchuang.walletsdk.aes_key"
    private static let secureEnclaveKeyAlias = "com.michaeltchuang.walletsdk.se_key"
    private static let wrappedKeyAlias = "com.michaeltchuang.walletsdk.wrapped_aes_key"

    private var symmetricKey: SymmetricKey?
    private let queue = DispatchQueue(label: "com.michaeltchuang.walletsdk.encryption", attributes: .concurrent)

    public override init() {
        super.init()
        do {
            self.symmetricKey = try Self.getOrCreateKey()
        } catch {
            print("Warning: Failed to initialize encryption key: \(error)")
        }
    }

    // MARK: - Key Management

    /// Gets an existing key from the Keychain or creates a new one
    private static func getOrCreateKey() throws -> SymmetricKey {
        // Try to retrieve existing key (Secure Enclave wrapped or regular)
        if let existingKey = try? retrieveKey() {
            return existingKey
        }

        // Create new key with Secure Enclave if available
        if isSecureEnclaveAvailable() {
            do {
                return try createSecureEnclaveProtectedKey()
            } catch {
                print("Warning: Secure Enclave key creation failed, falling back to regular keychain: \(error)")
            }
        }

        // Fallback to regular Keychain
        return try createRegularKey()
    }

    /// Checks if Secure Enclave is available on this device
    private static func isSecureEnclaveAvailable() -> Bool {
        // Try to create a test key to verify Secure Enclave availability
        let testTag = "com.michaeltchuang.walletsdk.test_se_key".data(using: .utf8)!

        let access = SecAccessControlCreateWithFlags(
            nil,
            kSecAttrAccessibleWhenUnlockedThisDeviceOnly,
            .privateKeyUsage,
            nil
        )

        guard let access = access else { return false }

        let attributes: [String: Any] = [
            kSecAttrKeyType as String: kSecAttrKeyTypeECSECPrimeRandom,
            kSecAttrKeySizeInBits as String: 256,
            kSecAttrTokenID as String: kSecAttrTokenIDSecureEnclave,
            kSecPrivateKeyAttrs as String: [
                kSecAttrIsPermanent as String: false,
                kSecAttrApplicationTag as String: testTag,
                kSecAttrAccessControl as String: access
            ]
        ]

        var error: Unmanaged<CFError>?
        if let testKey = SecKeyCreateRandomKey(attributes as CFDictionary, &error) {
            // Clean up test key
            let query: [String: Any] = [
                kSecClass as String: kSecClassKey,
                kSecAttrApplicationTag as String: testTag,
                kSecAttrKeyType as String: kSecAttrKeyTypeECSECPrimeRandom
            ]
            SecItemDelete(query as CFDictionary)

            return true
        }

        return false
    }

    /// Creates a symmetric key protected by Secure Enclave
    private static func createSecureEnclaveProtectedKey() throws -> SymmetricKey {
        // Step 1: Create an ECC key pair in Secure Enclave for wrapping
        let seKeyTag = secureEnclaveKeyAlias.data(using: .utf8)!

        guard let access = SecAccessControlCreateWithFlags(
            nil,
            kSecAttrAccessibleWhenUnlockedThisDeviceOnly,
            .privateKeyUsage,
            nil
        ) else {
            throw EncryptionError.keyCreationFailed("Failed to create access control")
        }

        let seAttributes: [String: Any] = [
            kSecAttrKeyType as String: kSecAttrKeyTypeECSECPrimeRandom,
            kSecAttrKeySizeInBits as String: 256,
            kSecAttrTokenID as String: kSecAttrTokenIDSecureEnclave,
            kSecPrivateKeyAttrs as String: [
                kSecAttrIsPermanent as String: true,
                kSecAttrApplicationTag as String: seKeyTag,
                kSecAttrAccessControl as String: access
            ]
        ]

        var error: Unmanaged<CFError>?
        guard let sePrivateKey = SecKeyCreateRandomKey(seAttributes as CFDictionary, &error) else {
            if let error = error {
                throw EncryptionError.keyCreationFailed("Secure Enclave key creation failed: \(error.takeRetainedValue())")
            }
            throw EncryptionError.keyCreationFailed("Unknown Secure Enclave error")
        }

        // Step 2: Generate a symmetric key for AES encryption
        let symmetricKey = SymmetricKey(size: .bits256)

        // Step 3: Wrap the symmetric key using the Secure Enclave public key
        guard let publicKey = SecKeyCopyPublicKey(sePrivateKey) else {
            throw EncryptionError.keyCreationFailed("Failed to get public key from Secure Enclave key")
        }

        let symmetricKeyData = symmetricKey.withUnsafeBytes { Data($0) }

        guard let wrappedKey = SecKeyCreateEncryptedData(
            publicKey,
            .eciesEncryptionCofactorX963SHA256AESGCM,
            symmetricKeyData as CFData,
            &error
        ) as Data? else {
            if let error = error {
                throw EncryptionError.keyCreationFailed("Failed to wrap symmetric key: \(error.takeRetainedValue())")
            }
            throw EncryptionError.keyCreationFailed("Unknown wrapping error")
        }

        // Step 4: Store the wrapped key in regular Keychain
        let wrappedKeyQuery: [String: Any] = [
            kSecClass as String: kSecClassGenericPassword,
            kSecAttrAccount as String: wrappedKeyAlias,
            kSecValueData as String: wrappedKey,
            kSecAttrAccessible as String: kSecAttrAccessibleAfterFirstUnlockThisDeviceOnly
        ]

        // Delete any existing wrapped key
        SecItemDelete(wrappedKeyQuery as CFDictionary)

        let status = SecItemAdd(wrappedKeyQuery as CFDictionary, nil)
        guard status == errSecSuccess else {
            throw EncryptionError.keychainError(status)
        }

        // Mark that we're using Secure Enclave
        UserDefaults.standard.set(true, forKey: "useSecureEnclave")

        return symmetricKey
    }

    /// Creates a regular symmetric key stored in Keychain
    private static func createRegularKey() throws -> SymmetricKey {
        let symmetricKey = SymmetricKey(size: .bits256)

        let keyData = symmetricKey.withUnsafeBytes { Data($0) }

        let query: [String: Any] = [
            kSecClass as String: kSecClassGenericPassword,
            kSecAttrAccount as String: keyAlias,
            kSecValueData as String: keyData,
            kSecAttrAccessible as String: kSecAttrAccessibleAfterFirstUnlockThisDeviceOnly
        ]

        // Delete any existing key
        SecItemDelete(query as CFDictionary)

        let status = SecItemAdd(query as CFDictionary, nil)
        guard status == errSecSuccess else {
            throw EncryptionError.keychainError(status)
        }

        UserDefaults.standard.set(false, forKey: "useSecureEnclave")

        return symmetricKey
    }

    /// Retrieves the symmetric key (unwrapping if necessary)
    private static func retrieveKey() throws -> SymmetricKey? {
        let isUsingSecureEnclave = UserDefaults.standard.bool(forKey: "useSecureEnclave")

        if isUsingSecureEnclave {
            return try retrieveSecureEnclaveProtectedKey()
        } else {
            return try retrieveRegularKey()
        }
    }

    /// Retrieves and unwraps a Secure Enclave protected key
    private static func retrieveSecureEnclaveProtectedKey() throws -> SymmetricKey? {
        // Step 1: Retrieve the Secure Enclave private key
        let seKeyTag = secureEnclaveKeyAlias.data(using: .utf8)!

        let seQuery: [String: Any] = [
            kSecClass as String: kSecClassKey,
            kSecAttrApplicationTag as String: seKeyTag,
            kSecAttrKeyType as String: kSecAttrKeyTypeECSECPrimeRandom,
            kSecReturnRef as String: true
        ]

        var seItem: CFTypeRef?
        let seStatus = SecItemCopyMatching(seQuery as CFDictionary, &seItem)

        guard seStatus == errSecSuccess,
              let sePrivateKey = seItem else {
            return nil
        }

        // Step 2: Retrieve the wrapped symmetric key
        let wrappedKeyQuery: [String: Any] = [
            kSecClass as String: kSecClassGenericPassword,
            kSecAttrAccount as String: wrappedKeyAlias,
            kSecReturnData as String: true
        ]

        var wrappedItem: CFTypeRef?
        let wrappedStatus = SecItemCopyMatching(wrappedKeyQuery as CFDictionary, &wrappedItem)

        guard wrappedStatus == errSecSuccess,
              let wrappedKeyData = wrappedItem as? Data else {
            return nil
        }

        // Step 3: Unwrap the symmetric key using the Secure Enclave private key
        var error: Unmanaged<CFError>?
        guard let unwrappedData = SecKeyCreateDecryptedData(
            sePrivateKey as! SecKey,
            .eciesEncryptionCofactorX963SHA256AESGCM,
            wrappedKeyData as CFData,
            &error
        ) as Data? else {
            if let error = error {
                throw EncryptionError.keyCreationFailed("Failed to unwrap key: \(error.takeRetainedValue())")
            }
            return nil
        }

        return SymmetricKey(data: unwrappedData)
    }

    /// Retrieves a regular symmetric key from Keychain
    private static func retrieveRegularKey() throws -> SymmetricKey? {
        let query: [String: Any] = [
            kSecClass as String: kSecClassGenericPassword,
            kSecAttrAccount as String: keyAlias,
            kSecReturnData as String: true
        ]

        var item: CFTypeRef?
        let status = SecItemCopyMatching(query as CFDictionary, &item)

        guard status == errSecSuccess,
              let keyData = item as? Data else {
            return nil
        }

        return SymmetricKey(data: keyData)
    }

    // MARK: - Public Methods for Objective-C/KMP Compatibility

    /// Encrypts a byte array using AES-GCM (Objective-C compatible)
    /// Returns nil on error for KMP compatibility
    @objc public func encryptByteArray(_ data: Data) -> Data? {
        do {
            return try encryptByteArrayThrows(data)
        } catch {
            print("Encryption error: \(error)")
            return nil
        }
    }

    /// Decrypts a byte array using AES-GCM (Objective-C compatible)
    /// Returns nil on error for KMP compatibility
    @objc public func decryptByteArray(_ encryptedData: Data) -> Data? {
        do {
            return try decryptByteArrayThrows(encryptedData)
        } catch {
            print("Decryption error: \(error)")
            return nil
        }
    }

    /// Encrypts a string and returns Base64 encoded result (Objective-C compatible)
    /// Returns empty string on error for KMP compatibility
    @objc public func encryptString(_ data: String) -> String {
        do {
            return try encryptStringThrows(data)
        } catch {
            print("String encryption error: \(error)")
            return ""
        }
    }

    /// Decrypts a Base64 encoded string (Objective-C compatible)
    /// Returns empty string on error for KMP compatibility
    @objc public func decryptString(_ encryptedData: String) -> String {
        do {
            return try decryptStringThrows(encryptedData)
        } catch {
            print("String decryption error: \(error)")
            return ""
        }
    }

    /// Checks if currently using Secure Enclave
    @objc public func isUsingSecureEnclave() -> Bool {
        return UserDefaults.standard.bool(forKey: "useSecureEnclave")
    }

    /// Re-initializes the encryption manager (Objective-C compatible)
    @objc public func initializeEncryptionManager() {
        do {
            try reinitialize()
        } catch {
            print("Reinitialization error: \(error)")
        }
    }

    /// Migrates to Secure Enclave if available (Objective-C compatible)
    @objc public func migrateToSecureEnclave() -> Bool {
        do {
            return try migrateToSecureEnclaveThrows()
        } catch {
            print("Migration error: \(error)")
            return false
        }
    }

    // MARK: - Swift Methods (Throwing versions)

    /// Encrypts a byte array using AES-GCM (Swift version with proper error handling)
    public func encryptByteArrayThrows(_ data: Data) throws -> Data {
        try queue.sync {
            guard let key = symmetricKey else {
                throw EncryptionError.keyNotInitialized
            }

            do {
                let sealedBox = try AES.GCM.seal(data, using: key)

                guard let combined = sealedBox.combined else {
                    throw EncryptionError.encryptionFailed("Failed to get combined data from sealed box")
                }

                return combined
            } catch {
                throw EncryptionError.encryptionFailed(error.localizedDescription)
            }
        }
    }

    /// Decrypts a byte array using AES-GCM (Swift version with proper error handling)
    public func decryptByteArrayThrows(_ encryptedData: Data) throws -> Data {
        try queue.sync {
            guard let key = symmetricKey else {
                throw EncryptionError.keyNotInitialized
            }

            do {
                let sealedBox = try AES.GCM.SealedBox(combined: encryptedData)
                let decryptedData = try AES.GCM.open(sealedBox, using: key)
                return decryptedData
            } catch {
                throw EncryptionError.decryptionFailed(error.localizedDescription)
            }
        }
    }

    /// Encrypts a string and returns Base64 encoded result (Swift version)
    public func encryptStringThrows(_ data: String) throws -> String {
        guard let stringData = data.data(using: .utf8) else {
            throw EncryptionError.dataConversionFailed
        }

        let encryptedData = try encryptByteArrayThrows(stringData)
        return encryptedData.base64EncodedString()
    }

    /// Decrypts a Base64 encoded string (Swift version)
    public func decryptStringThrows(_ encryptedData: String) throws -> String {
        guard let encryptedBytes = Data(base64Encoded: encryptedData) else {
            throw EncryptionError.dataConversionFailed
        }

        let decryptedData = try decryptByteArrayThrows(encryptedBytes)

        guard let decryptedString = String(data: decryptedData, encoding: .utf8) else {
            throw EncryptionError.dataConversionFailed
        }

        return decryptedString
    }

    /// Re-initializes the encryption manager (Swift version)
    public func reinitialize() throws {
        try queue.sync(flags: .barrier) {
            symmetricKey = try Self.getOrCreateKey()
        }
    }

    /// Migrates from regular Keychain to Secure Enclave if available (Swift version)
    public func migrateToSecureEnclaveThrows() throws -> Bool {
        return try queue.sync(flags: .barrier) {
            guard Self.isSecureEnclaveAvailable() else {
                throw EncryptionError.secureEnclaveNotAvailable
            }

            guard !isUsingSecureEnclave() else {
                return true
            }

            // Create new Secure Enclave protected key
            let newKey = try Self.createSecureEnclaveProtectedKey()

            // Only delete old key after successful creation
            let query: [String: Any] = [
                kSecClass as String: kSecClassGenericPassword,
                kSecAttrAccount as String: Self.keyAlias
            ]
            SecItemDelete(query as CFDictionary)

            // Update current key
            self.symmetricKey = newKey

            return true
        }
    }

    /// Deletes all encryption keys (use with caution)
    @objc public func deleteAllKeys() {
        queue.sync(flags: .barrier) {
            // Delete regular key
            let regularQuery: [String: Any] = [
                kSecClass as String: kSecClassGenericPassword,
                kSecAttrAccount as String: Self.keyAlias
            ]
            SecItemDelete(regularQuery as CFDictionary)

            // Delete wrapped key
            let wrappedQuery: [String: Any] = [
                kSecClass as String: kSecClassGenericPassword,
                kSecAttrAccount as String: Self.wrappedKeyAlias
            ]
            SecItemDelete(wrappedQuery as CFDictionary)

            // Delete Secure Enclave key
            let seKeyTag = Self.secureEnclaveKeyAlias.data(using: .utf8)!
            let seQuery: [String: Any] = [
                kSecClass as String: kSecClassKey,
                kSecAttrApplicationTag as String: seKeyTag,
                kSecAttrKeyType as String: kSecAttrKeyTypeECSECPrimeRandom
            ]
            SecItemDelete(seQuery as CFDictionary)

            // Clear preferences
            UserDefaults.standard.removeObject(forKey: "useSecureEnclave")

            // Clear current key
            symmetricKey = nil
        }
    }
}
