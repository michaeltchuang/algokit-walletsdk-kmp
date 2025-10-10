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
            let seedData = Data(base64Encoded: seed)

            guard let wallet = XHDWalletAPI(seed: seed) else {
                print("Failed to create wallet")
                return ""
            }

            let privateKey = try wallet.keyGen(
                context: .Address,
                account: UInt32(account),
                change: UInt32(change),
                keyIndex: UInt32(keyIndex)
            )

           // let account: UInt32 = UInt32(account)
           // let change: UInt32 = UInt32(change)
           // let keyIndex: UInt32 = UInt32(keyIndex)
           // let bip44Path = wallet.fromSeed(Data).getBIP44PathFromContext(
           //     context: .Address,
           //     account: account,
           //     change: change,
           //     keyIndex: keyIndex
           // )
           //
           // let privateKey = wallet.deriveKey(
           //     rootKey: wallet.fromSeed(seed: seedData),
           //     bip44Path: bip44Path,
           //     isPrivate: false,
           //     derivationType: BIP32DerivationType.Peikert
           // )
           print("Private Key: \(privateKey)")
           return privateKey.base64EncodedString()

        } catch {
            print("Failed to generate seed or key: \(error)")
            return ""
        }
    }

    public func getAlgo25SecretKey(mnemonic: String?) -> String {
        if let knownMnemonic = mnemonic {
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

        return algorandKeyInfo.publicKey
    }

    public func getFalconPrivateKeyFromMnemonic(passphrase: String) -> String {
        var error: NSError?
        guard let algorandKeyInfo = AlgoSdkDeriveFromBIP39(passphrase, &error) else {
            if let error = error {
                print("Error deriving from BIP39: \(error)")
            }
            return ""
        }

        return algorandKeyInfo.privateKey
    }
}
