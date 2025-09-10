import Foundation
import CryptoSwift
import x_hd_wallet_api

@objcMembers public class xHdWalletApiBridge: NSObject {
    public func toMD5(value: String) -> String {
        return value.md5()
    }
}
