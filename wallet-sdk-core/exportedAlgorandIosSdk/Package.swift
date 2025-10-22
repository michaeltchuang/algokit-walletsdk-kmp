// swift-tools-version: 5.9
import PackageDescription

let package = Package(
  name: "exportedAlgorandIosSdk",
  platforms: [.iOS("16.2"), .macOS("10.13"), .tvOS("12.0"), .watchOS("4.0")],
  products: [
    .library(
      name: "exportedAlgorandIosSdk",
      type: .static,
      targets: ["exportedAlgorandIosSdk", "AlgoSDK"])
  ],
  dependencies: [],
  targets: [
    .target(
      name: "exportedAlgorandIosSdk",
      dependencies: [
        "AlgoSDK"
      ],
      path: "Sources"

    ), .binaryTarget(name: "AlgoSDK", path: "../src/iosMain/xcframeworks/AlgoSDK.xcframework.zip"),
  ]
)
