import Foundation
import x_hd_wallet_api
import MnemonicSwift
import AlgoSDK
import AlgoKitTransact

@objcMembers public class iosApiBridge: NSObject {
    
    public func getHdPublicKey(mnemonic: String) -> String {
        
        do {
            // Generate deterministic seed
            let seed = try Mnemonic.deterministicSeedString(from: mnemonic)
            
            // Create wallet
            guard let wallet = XHDWalletAPI(seed: seed) else {
                print("Failed to create wallet")
                return ""
            }
            
            // Generate key
            let publicKey = try wallet.keyGen(context: .Address, account: 0, change: 0, keyIndex: 0)
            print("Public Key: \(publicKey)")
            return publicKey.base64EncodedString()
            
        } catch {
            print("Failed to generate seed or key: \(error)")
            return ""
        }
    }
    
    public func getHdPrivateKey(mnemonic: String) -> String {
        
        do {
            // Generate deterministic seed
            let seed = try Mnemonic.deterministicSeedString(from: mnemonic)
            let seedData = Data(base64Encoded: seed)
            
            // Create wallet
            guard let wallet = XHDWalletAPI(seed: seed) else {
                print("Failed to create wallet")
                return ""
            }
            
            // return public key for private as placeholder for now
            let publicKey = try wallet.keyGen(context: .Address, account: 0, change: 0, keyIndex: 0)
            print("Private Key: \(publicKey)")
            return publicKey.base64EncodedString()
            
//            let account: UInt32 = 0
//            let change: UInt32 = 0
//            let keyIndex: UInt32 = 0
//            let bip44Path = wallet.fromSeed(Data) .getBIP44PathFromContext(
//                context: .Address,
//                account: account,
//                change: change,
//                keyIndex: keyIndex
//            )
//            
//            // Generate key
//            let privateKey = wallet.deriveKey(
//                rootKey: wallet.fromSeed(seed: seedData),
//                bip44Path: bip44Path,
//                isPrivate: false,
//                derivationType: BIP32DerivationType.Peikert
//            )
//            print("Private Key: \(privateKey)")
//            return privateKey.base64EncodedString()
            
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
    
    public func generateAddressFromSK(secretKey: String) -> String {
        guard
            !secretKey.isEmpty,
            let data = Data(base64Encoded: secretKey)
        else {
            return ""
        }
        
        return AlgoSDK.AlgoSdkGenerateAddressFromSK(data, nil)
    }

    public func generatePublicKeyFromAddress(address: String) -> String {
        do {
            return try AlgoKitTransact.publicKeyFromAddress(address: address).base64EncodedString()
        } catch {
            print("Error converting address: \(error)")
            return ""
        }
    }
}
