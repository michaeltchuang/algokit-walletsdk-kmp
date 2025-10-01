import SwiftUI
import AlgoSDK

// A simple model to hold the generated data
struct WalletData {
    var hdMnemonic: String
    var hdPublicKey: String
    var hdPrivateKey: String
    var hdAddress: String
    var algo25Mnemonic: String
    var algo25SecretKey: String
    var algo25Address: String
    var algo25PublicKey: String
}

struct ContentView: View {
    // Use @State to store the data, so the view updates when it's set
    @State private var walletData: WalletData?

    // This method handles all the logic
    private func generateKeysAndAddresses() {
        let mnemonicHd = "borrow among leopard smooth trade cake profit proud matrix bottom goose charge oxygen shine punch hotel era monitor fossil violin tip notice any visit"
        
        let api = iosApiBridge()

        // Generating address for HdKey
        let publicHdKey = api.getHdPublicKey(mnemonic: mnemonicHd)
        let dataPublicKey = Data(base64Encoded: publicHdKey)
        let hdKeyAddress = AlgoSDK.AlgoSdkGenerateAddressFromPublicKey(dataPublicKey, nil)
        let privateHdKey = api.getHdPrivateKey(mnemonic: mnemonicHd)

        // Generating address for Algo25
        let mnemonicAlgo25 = "define claw hungry wave umbrella boost blind never muscle also grab gaze fluid echo predict describe turkey unaware dash phone urge crunch eyebrow abstract team"
        
        let algo25SecretKey = api.getAlgo25SecretKey(mnemonic: mnemonicAlgo25)
        let dataSecretKey = Data(base64Encoded: algo25SecretKey) ?? Data()
        let algo25Address = AlgoSDK.AlgoSdkGenerateAddressFromSK(dataSecretKey, nil)
        let algo25PublicKey = api.generatePublicKeyFromAddress(address: algo25Address)

        // Set the state property, which will cause the view to refresh
        self.walletData = WalletData(
            hdMnemonic: mnemonicHd,
            hdPublicKey: publicHdKey,
            hdPrivateKey: privateHdKey,
            hdAddress: hdKeyAddress,
            algo25Mnemonic: mnemonicAlgo25,
            algo25SecretKey: algo25SecretKey,
            algo25Address: algo25Address,
            algo25PublicKey: algo25PublicKey
        )
    }
    
    // The main view body
    var body: some View {
        VStack(spacing: 20) {
            Text("Wallet Information")
                .font(.largeTitle)
                .fontWeight(.bold)
            
            // Show a progress view while data is being generated
            if let data = walletData {
                WalletInfoView(data: data)
            } else {
                ProgressView("Generating keys...")
            }
        }
        .padding()
        // Call the generation logic when the view appears
        .onAppear {
            generateKeysAndAddresses()
        }
    }
}

// A separate, reusable view to display the wallet info
struct WalletInfoView: View {
    let data: WalletData

    var body: some View {
        ScrollView {
            VStack(alignment: .leading, spacing: 15) {
                // Section for HDKey
                GroupBox(label: Text("HDKey").font(.headline)) {
                    VStack(alignment: .leading, spacing: 10) {
                        Text("Mnemonic:")
                            .font(.subheadline)
                        Text(data.hdMnemonic)
                            .font(.system(.body, design: .monospaced))
                            .textSelection(.enabled) // Allows users to copy the key
                            .lineLimit(nil)
                        
                        Divider()
                        
                        Text("Public Key:")
                            .font(.subheadline)
                        Text(data.hdPublicKey)
                            .font(.system(.body, design: .monospaced))
                            .textSelection(.enabled) // Allows users to copy the key
                            .lineLimit(nil)
                        
                        Divider()
                        
                        Text("Private Key:")
                            .font(.subheadline)
                        Text(data.hdPrivateKey)
                            .font(.system(.body, design: .monospaced))
                            .textSelection(.enabled) // Allows users to copy the key
                            .lineLimit(nil)

                        Divider()

                        Text("Address:")
                            .font(.subheadline)
                        Text(data.hdAddress)
                            .font(.system(.body, design: .monospaced))
                            .textSelection(.enabled)
                            .lineLimit(nil)
                    }
                    .padding()
                }

                // Section for Algo25
                GroupBox(label: Text("Algo25").font(.headline)) {
                    VStack(alignment: .leading, spacing: 10) {
                        Text("Mnemonic:")
                            .font(.subheadline)
                        Text(data.algo25Mnemonic)
                            .font(.system(.body, design: .monospaced))
                            .textSelection(.enabled)
                            .lineLimit(nil)

                        Divider()
                        
                        Text("Address:")
                            .font(.subheadline)
                        Text(data.algo25Address)
                            .font(.system(.body, design: .monospaced))
                            .textSelection(.enabled)
                            .lineLimit(nil)

                        Divider()

                        Text("Secret Key:")
                            .font(.subheadline)
                        Text(data.algo25SecretKey)
                            .font(.system(.body, design: .monospaced))
                            .textSelection(.enabled)
                            .lineLimit(nil)
                        
                        Divider()

                        Text("Public Key:")
                            .font(.subheadline)
                        Text(data.algo25PublicKey)
                            .font(.system(.body, design: .monospaced))
                            .textSelection(.enabled)
                            .lineLimit(nil)
                    }
                    .padding()
                }
            }
        }
    }
}

#Preview {
    ContentView()
}
