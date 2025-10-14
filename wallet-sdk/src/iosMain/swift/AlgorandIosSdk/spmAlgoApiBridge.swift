import Foundation
import x_hd_wallet_api
import MnemonicSwift
import AlgoSDK

@objcMembers public class spmAlgoApiBridge: NSObject {

    public func getHdPublicKey(mnemonic: String, account: Int, change: Int, keyIndex: Int) -> String {

        do {
            let seed = try Mnemonic.deterministicSeedString(from: mnemonic)

            guard let wallet = XHDWalletAPI(seed: seed) else {
                print("Failed to create wallet")
                return ""
            }

            let publicKey = try wallet.keyGen(
                context: .Address,
                account: UInt32(account),
                change: UInt32(change),
                keyIndex: UInt32(keyIndex)
            )
            print("Public Key: \(publicKey)")
            return publicKey.base64EncodedString()

        } catch {
            print("Failed to generate seed or key: \(error)")
            return ""
        }
    }

    public func getHdPrivateKey(mnemonic: String, account: Int, change: Int, keyIndex: Int) -> String {

        do {
            let seed = try Mnemonic.deterministicSeedString(from: mnemonic)
            guard let nonOptionalSeedData = Data(base64Encoded: seed) else {
                throw NSError(domain: "WalletError", code: 1, userInfo: [NSLocalizedDescriptionKey: "Invalid Base64 seed string"])
            }

            guard let wallet = XHDWalletAPI(seed: seed) else {
                print("Failed to create wallet")
                return ""
            }

            let account: UInt32 = UInt32(account)
            let change: UInt32 = UInt32(change)
            let keyIndex: UInt32 = UInt32(keyIndex)

            let bip44Path = wallet.getBIP44PathFromContext(
                context: .Address,
                account: account,
                change: change,
                keyIndex: keyIndex
            )

            let privateKey = try wallet.deriveKey(
                rootKey: wallet.fromSeed(nonOptionalSeedData),
                bip44Path: bip44Path,
                isPrivate: false,
                derivationType: BIP32DerivationType.Peikert
            )
            print("Private Key: \(privateKey)")
            return privateKey.base64EncodedString()

        } catch {
            print("Failed to generate seed or key: \(error)")
            return ""
        }
    }

    public func getAlgo25SecretKey(mnemonic: String?) -> String {
        if mnemonic != nil {
            // Mnemonic is not null, so convert it to a secret key
            var error: NSError?
            guard let secretKeyData = AlgoSDK.AlgoSdkMnemonicToPrivateKey(mnemonic, &error) else {
                print("Failed to convert mnemonic to secret key.")
                return ""
            }
            return secretKeyData.base64EncodedString()
        } else {
            // Mnemonic is null, so generate a new secret key
            guard let newSecretKey = AlgoSDK.AlgoSdkGenerateSK() else {
                print("Failed to generate new secret key.")
                return ""
            }
            return newSecretKey.base64EncodedString()
        }
    }

    public func getAlgo25MnemonicFromSecretKey(secretKey: Data) -> String {
        var error: NSError?
        let mnemonic = AlgoSDK.AlgoSdkMnemonicFromPrivateKey(secretKey, &error)

        if let error = error {
            print("Error generating mnemonic: \(error)")
            return ""
        }

        return mnemonic
    }

    public func generateAddressFromPublicKey(publicKey: String) -> String {
        guard
            !publicKey.isEmpty,
            let data = Data(base64Encoded: publicKey)
        else {
            return ""
        }

        return AlgoSDK.AlgoSdkGenerateAddressFromPublicKey(data, nil)
    }

    public func generateAddressFromSK(secretKey: String) -> String {
        guard
            !secretKey.isEmpty,
            let data = Data(base64Encoded: secretKey)
        else {
            return ""
        }

        return AlgoSDK.AlgoSdkGenerateAddressFromSK(data, nil)
    }

    public func signTransactionWithBase64(skBase64: String, encodedTxBase64: String) -> String {

        // Decode Base64 strings to Data
        guard let skData = Data(base64Encoded: skBase64) else {
            print("Error: Failed to decode secret key from Base64")
            return ""
        }

        guard let encodedTxData = Data(base64Encoded: encodedTxBase64) else {
            print("Error: Failed to decode transaction from Base64")
            return ""
        }

        // Validate secret key length
        guard skData.count == 64 else {
            print("Error signing transaction: Secret key (sk) must be 64 bytes long, but received \(skData.count) bytes.")
            return ""
        }

        guard !encodedTxData.isEmpty else {
            print("Error signing transaction: Unsigned transaction data (encodedTx) is empty.")
            return ""
        }

        print("Swift: About to call AlgoSdkSignTransaction")
        print("Swift: sk size = \(skData.count), encodedTx size = \(encodedTxData.count)")

        // Use withUnsafeBytes to keep the Data alive during the SDK call
        let result = skData.withUnsafeBytes { (skPtr: UnsafeRawBufferPointer) -> String in
            let sk = Data(bytes: skPtr.baseAddress!, count: skData.count)

            return encodedTxData.withUnsafeBytes { (txPtr: UnsafeRawBufferPointer) -> String in
                let encodedTx = Data(bytes: txPtr.baseAddress!, count: encodedTxData.count)

                var error: NSError?
                guard let signedTxn = AlgoSDK.AlgoSdkSignTransaction(sk, encodedTx, &error) else {
                    if let error = error {
                        print("Error signing transaction (SDK failed): \(error.localizedDescription)")
                    } else {
                        print("Failed to sign transaction (SDK failed): unknown error.")
                    }
                    return ""
                }

                print("Swift: Signing completed successfully")
                return signedTxn.base64EncodedString()
            }
        }

        return result
    }

    public func signTransactionWithSk(sk: Data, encodedTx: Data) -> Data {

        guard sk.count == 64 else {
            print("Error signing transaction: Secret key (sk) must be 64 bytes long, but received \(sk.count) bytes.")
            return Data()
        }

        guard !encodedTx.isEmpty else {
            print("Error signing transaction: Unsigned transaction data (encodedTx) is empty.")
            return Data()
        }

        var error: NSError?
        guard let signedTxn = AlgoSDK.AlgoSdkSignTransaction(sk, encodedTx, &error) else {
            if let error = error {
                print("Error signing transaction (SDK failed): \(error.localizedDescription)")
            } else {
                print("Failed to sign transaction (SDK failed): unknown error.")
            }
            return Data()
        }
        return signedTxn
    }

    public func getFalconAddressFromMnemonic(passphrase: String) -> String {
        var error: NSError?
        guard let algorandKeyInfo = AlgoSdkDeriveFromBIP39(passphrase, &error) else {
            if let error = error {
                print("Error deriving from BIP39: \(error)")
            } else {
                print("Failed to derive from BIP39 - no key info returned")
            }
            return ""
        }

        return algorandKeyInfo.algorandAddress
    }

    public func getFalconPublicKeyFromMnemonic(passphrase: String) -> String {
        var error: NSError?
        guard let algorandKeyInfo = AlgoSdkDeriveFromBIP39(passphrase, &error) else {
            if let error = error {
                print("Error deriving from BIP39: \(error)")
            }
            return ""
        }

        guard let publicKeyData = algorandKeyInfo.publicKey else {
            print("Public key data is nil")
            return ""
        }
        return publicKeyData.base64EncodedString()
    }

    public func getFalconPrivateKeyFromMnemonic(passphrase: String) -> String {
        var error: NSError?
        guard let algorandKeyInfo = AlgoSdkDeriveFromBIP39(passphrase, &error) else {
            if let error = error {
                print("Error deriving from BIP39: \(error)")
            }
            return ""
        }

        guard let privateKeyData = algorandKeyInfo.privateKey else {
            print("Private key data is nil")
            return ""
        }
        return privateKeyData.base64EncodedString()
    }

    public func signFalconTransaction(
        transactionBytes: Data,
        publicKeyBase64: String,
        privateKeyBase64: String
    ) -> Data? {
        guard let publicKeyData = Data(base64Encoded: publicKeyBase64),
              let privateKeyData = Data(base64Encoded: privateKeyBase64) else {
            print("Failed to decode base64 keys")
            return nil
        }

        var error: NSError?
        guard let signedBytes = AlgoSdkSignFalconTransaction(
            transactionBytes,
            publicKeyData,
            privateKeyData,
            &error
        ) else {
            if let error = error {
                print("Error signing Falcon transaction: \(error)")
            }
            return nil
        }

        return signedBytes
    }

    public func createOfflineKeyRegTransaction(
        senderAddress: String,
        noteBase64: String?,
        fee: UInt64,
        flatFee: Bool,
        firstRound: UInt64,
        lastRound: UInt64,
        genesisHashBase64: String,
        genesisID: String
    ) -> Data {

        guard let genesisHashData = Data(base64Encoded: genesisHashBase64) else {
            print("Error creating Offline KeyReg Tx: Failed to decode genesisHash.")
            return Data()
        }

        let params = AlgoSdkSuggestedParams()
        params.fee = Int64(truncatingIfNeeded: fee)
        params.flatFee = flatFee
        params.firstRoundValid = Int64(truncatingIfNeeded: firstRound)
        params.lastRoundValid = Int64(truncatingIfNeeded: lastRound)
        params.genesisHash = genesisHashData
        params.genesisID = genesisID

        let noteData = noteBase64.flatMap { Data(base64Encoded: $0) }
        var error: NSError?
        guard let encodedTx = AlgoSDK.AlgoSdkMakeKeyRegTxnWithStateProofKey(
            senderAddress,
            noteData,
            params,
            "",
            "",
            "",
            nil,
            nil,
            nil,
            false,
            &error
        ) else {
            if let error = error {
                print("Error creating Offline KeyReg Tx (SDK failed): \(error.localizedDescription)")
            } else {
                print("Failed to create Offline KeyReg Tx: unknown SDK error.")
            }
            return Data()
        }

        return encodedTx
    }

    public func createOnlineKeyRegTransaction(
        senderAddress: String,
        noteBase64: String?,
        fee: UInt64,
        flatFee: Bool,
        firstRound: UInt64,
        lastRound: UInt64,
        genesisHashBase64: String,
        genesisID: String,
        voteKeyBase64: String,
        selectionKeyBase64: String,
        stateProofKeyBase64: String,
        voteFirstRound: UInt64,
        voteLastRound: UInt64,
        voteKeyDilution: UInt64
    ) -> Data {

        func convertToStandardBase64(_ urlSafeBase64: String) -> String {
            var standard = urlSafeBase64
                .replacingOccurrences(of: "-", with: "+")
                .replacingOccurrences(of: "_", with: "/")

            let padding = (4 - (standard.count % 4)) % 4
            if padding > 0 {
                standard += String(repeating: "=", count: padding)
            }

            return standard
        }

        guard let genesisHashData = Data(base64Encoded: genesisHashBase64) else {
            print("Error creating Online KeyReg Tx: Failed to decode genesisHash.")
            return Data()
        }

        let voteKeyStandard = convertToStandardBase64(voteKeyBase64)
        let selectionKeyStandard = convertToStandardBase64(selectionKeyBase64)
        let stateProofKeyStandard = convertToStandardBase64(stateProofKeyBase64)

        let params = AlgoSdkSuggestedParams()
        params.fee = Int64(truncatingIfNeeded: fee)
        params.flatFee = flatFee
        params.firstRoundValid = Int64(truncatingIfNeeded: firstRound)
        params.lastRoundValid = Int64(truncatingIfNeeded: lastRound)
        params.genesisHash = genesisHashData
        params.genesisID = genesisID

        let noteData = noteBase64.flatMap { Data(base64Encoded: $0) }

        let voteFirstRoundWrapper = AlgoSdkUint64()
        voteFirstRoundWrapper.upper = Int64(voteFirstRound >> 32)
        voteFirstRoundWrapper.lower = Int64(voteFirstRound & 0xFFFFFFFF)

        let voteLastRoundWrapper = AlgoSdkUint64()
        voteLastRoundWrapper.upper = Int64(voteLastRound >> 32)
        voteLastRoundWrapper.lower = Int64(voteLastRound & 0xFFFFFFFF)

        let voteKeyDilutionWrapper = AlgoSdkUint64()
        voteKeyDilutionWrapper.upper = Int64(voteKeyDilution >> 32)
        voteKeyDilutionWrapper.lower = Int64(voteKeyDilution & 0xFFFFFFFF)

        var error: NSError?

        guard let encodedTx = AlgoSDK.AlgoSdkMakeKeyRegTxnWithStateProofKey(
            senderAddress,
            noteData,
            params,
            voteKeyStandard,
            selectionKeyStandard,
            stateProofKeyStandard,
            voteFirstRoundWrapper,
            voteLastRoundWrapper,
            voteKeyDilutionWrapper,
            false,
            &error
        ) else {
            if let error = error {
                print("Error creating Online KeyReg Tx (SDK failed): \(error.localizedDescription)")
            } else {
                print("Failed to create Online KeyReg Tx: unknown SDK error.")
            }
            return Data()
        }

        return encodedTx
    }
}