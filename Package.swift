// swift-tools-version:5.7

import PackageDescription

let package = Package(
   name: "WalletSDK",
   platforms: [
     .iOS(.v14),
   ],
   products: [
      .library(name: "Common", targets: ["Common"])
   ],
   targets: [
      .binaryTarget(
         name: "Common",
         url:"https://github.com/mithiraj95/algokit-walletsdk-kmp/releases/download/v1.0.0-alpha/Common.xcframework.zip",
         checksum:"80a08ac8a81fc4021ec6ed2c3956793bc3ef60412b1d65c11b502cecafe25b06")
   ]
)
