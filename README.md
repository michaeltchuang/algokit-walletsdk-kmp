# AlgoKit Wallet SDK

This mobile utils library project provides common wallet UI components and screens out of the box, allowing native developers to skip building standard wallet functionality and focus more on unique, value-added features for their mobile applications.

AlgoKit Wallet SDK currently uses UI theming inspired by [Pera Android](https://github.com/perawallet/pera-android) as a placeholder until official Algorand Foundation branding guidelines are available.

```mermaid
---
title: AlgoKit Wallet SDK High Level Overview
---
graph TD
    subgraph "Algorand Apps"
        App1["App 1"]
        App2["App 2"]
    end

    subgraph wallet["AlgoKit Wallet SDK"]
        SDK1["Wallet SDK UI<br/>(Embedded Wallet UI)"]
        SDK2["Wallet SDK Core<br/>(Headless Wallet Engine)"]
    end

    subgraph "Algorand SDKs"
        Core["AlgoKit-Core Rust SDK"]
        xHD["Algo xHD Kotlin/Swift SDK"]
        JavaSDK["Algo Java SDK"]
        GoSDK["Algo Go SDK"]
    end

    App1 <--> wallet
    App2 <--> wallet
    SDK1 <--> SDK2
    SDK2 <--> Core
    SDK2 <--> xHD
    SDK2 <--> JavaSDK
    SDK2 <--> GoSDK
```

The sample apps (Android/iOS) demonstrate `wallet-sdk` library usage through a simplified "Pera-lite" demo wallet application. Current and planned features include:

- Create and recover accounts (Algo25, Universal HD, Falcon24)
- Theme customization
- Network switching between Mainnet/Testnet (Code hasn't been audited, so use mainnet at your own risk)
- QR code scanning for account imports and keyreg transactions
- Algo-only experience for now (to swap memecoins...use Pera app, Haystack app, etc)
- Account detail screens
- Passphrase management
- Localization

```mermaid
---
config:
  theme: 'neutral'
---
timeline
    title AlgoKit Wallet SDK tentative roadmap

    section Completed ✅
    2025Q3  : ✅ Create sample KMP app ("Pera Lite")
            : ✅ Onboarding - Create Algo25/HD/Falcon24 wallet and account flow
            : ✅ Onboarding - Recover Algo25/Falcon24 account flow
            : ✅ Deeplink - Import Algo25/Falcon24 account using QR code flow
            : ✅ Settings - Theme picker
            : ✅ Onboarding - Embedded and external webview flow
            : ✅ Account Details - View passphrase flow
            : ✅ Settings - Network switcher flow

    section In Progress 🔄
    2025Q4  : ✅ Transaction - Sign KeyReg online/offline flow with QR code flow
            : ✅ Account Details - Add copy address button
            : ✅ Onboarding - Encrypt secret keys in DB
            : ✅ GitOps - Setup Maven Central for library releases
            : 🔄 Transaction - Send Algo flow (between accounts)
            : 🔄 Account Details - Add testnet dispenser link
            : ✅ Settings - Localization (Italian)
            : Settings - Localization (Hindi)
            : GitOps - Setup App / Play store for demo app releases
            : Testing - Setup unit test coverage foundation

    section Future
    2026Q1  : Transaction - Integrate new AlgoKit-Core Transact rust library
            : Onboarding - Passkeys / Liquid Auth
            : Onboarding - Rekey flow
            : Onboarding - Recover HD account flow
            : Onboarding - Ledger flow
            : Onboarding - Fun app splash screen
            : Account Details - Add more items in Account Detail screen
            : Testing - Add Screenshot Testing Infrastructure
            : Android - Wallet SDK as a background service integration
    2026Q2  : Research - React Native sample app that can use wallet-sdk (through bridging)
            : Research - Wallet-SDK supporting more platforms (e.g Desktop/Web)
            : Onboarding - Decentralized Identity
            : Account Details - Asset Inbox
            : Account Details - Swap
            : TBD
```

## Project structure

The project has the following modules:

- **composeDemoApp**: A [Compose Multiplatform](https://github.com/JetBrains/compose-multiplatform) sample wallet app that demonstrates `wallet-sdk` usage.
- **iosDemoApp**: The iOS app for `composeSampleApp`. Open this module in Xcode if needed.
- **wallet-sdk-core**: The AlgoKit Wallet SDK core module - a headless wallet utils library built with [Kotlin Multiplatform](https://developer.android.com/kotlin/multiplatform). It provides foundational wallet functionality and is built on top of [AlgoKit-Core SDK](https://github.com/algorandfoundation/algokit-core), [Algo xHD Swift SDK](https://github.com/algorandfoundation/xHD-Wallet-API-swift), [Algo xHD Kotlin SDK](https://github.com/algorandfoundation/xHD-Wallet-API-kt), [Algo Java SDK](https://github.com/algorand/java-algorand-sdk), and [Algo Go SDK](https://github.com/perawallet/algorand-go-mobile-sdk).
- **wallet-sdk-ui**: The AlgoKit Wallet SDK UI module - an embedded wallet utils library built with [Compose Multiplatform](https://github.com/JetBrains/compose-multiplatform). This module extends wallet-sdk-core and provides ready-to-use UI components for developers who want an integrated wallet interface in their applications.

This project is developed using [Android Studio](https://developer.android.com/studio) (stable version) and the [Kotlin Multiplatform Plugin](https://plugins.jetbrains.com/plugin/14936-kotlin-multiplatform). As a mobile development project, it is primarily developed on macOS, support for Windows and Linux is quite limited.  We also follow the [KMP compatibility guide](https://www.jetbrains.com/help/kotlin-multiplatform-dev/multiplatform-compatibility-guide.html).

## Screenshots

### Sample App - Accounts List

#### Fetching All Accounts Flow

<img width="200" alt="Image" src="https://github.com/user-attachments/assets/d86ffc6a-7fa2-4f9c-8c45-9603911efb3e" />

### Wallet-SDK Screens - Onboarding

#### No Accounts Onboarding Flow

<img width="200" alt="Image" src="https://github.com/user-attachments/assets/cb4563d7-e34e-4df1-9a4d-e7748d8f7afe" /> 
<img width="200" alt="Image" src="https://github.com/user-attachments/assets/25b215b6-e502-436e-9110-a02e278e0d06" />

#### Create Falcon24 Wallet Flow

<img width="200" alt="Image" src="https://github.com/user-attachments/assets/17745845-422a-436a-8a20-c26885f778af" /> 
<img width="200" alt="Image" src="https://github.com/user-attachments/assets/77da2564-a81b-4172-8784-6f8fbfdfedda" />

#### Add Falcon24 Account To Existing Wallet Flow

<img width="200" alt="Image" src="https://github.com/user-attachments/assets/17745845-422a-436a-8a20-c26885f778af" />
<img width="200" alt="Image" src="https://github.com/user-attachments/assets/7059079a-f078-4c69-8fb2-76ed0f90aec1" />
<img width="200" alt="Image" src="https://github.com/user-attachments/assets/25b215b6-e502-436e-9110-a02e278e0d06" />

#### Recover Falcon24/Algo25 Account Flow

<img width="200" alt="Image" src="https://github.com/user-attachments/assets/1653a674-a532-4f6d-b81e-246c10d39616" /> 
<img width="200" alt="Image" src="https://github.com/user-attachments/assets/3f5b80f4-b42f-4ecf-b1da-3bbdf83258ee" /> 
<img width="200" alt="Image" src="https://github.com/user-attachments/assets/25a361fe-0410-49ad-9178-7b5baaa9bdad" /> 
<img width="200" alt="Image" src="https://github.com/user-attachments/assets/6c021283-7050-4b33-a11f-aacf2774cfa3" />

#### Recover Falcon24/Algo25 Account with QR Code Flow

<img width="200" alt="Image" src="https://github.com/user-attachments/assets/8d386947-3a7a-4f08-be63-929982956fb4" /> 
<img width="200" alt="Image" src="https://github.com/user-attachments/assets/4ec7bcdd-fa74-4ed7-a095-90705b049ebb" /> 
<img width="200" alt="Image" src="https://github.com/user-attachments/assets/6c021283-7050-4b33-a11f-aacf2774cfa3" />

### Wallet-SDK Screens - Account Details

#### View Passphrase Flow

<img width="200" alt="Image" src="https://github.com/user-attachments/assets/b6378ca8-8179-4d99-acc6-243b62fb2dac" /> 
<img width="200" alt="Image" src="https://github.com/user-attachments/assets/9c50546c-20da-46ed-b4fb-4a77779de443" /> 
<img width="200" alt="Image" src="https://github.com/user-attachments/assets/48f6bd29-2b92-460c-8ef8-146bb07e3508" /> 
<img width="200" alt="Image" src="https://github.com/user-attachments/assets/3f85712f-7248-40f3-9c53-9458019a7d28" />

### Wallet-SDK Screens - Settings

#### Theme Picker Flow

<img width="200" alt="Settings Screen" src="https://github.com/user-attachments/assets/a79f7d70-f070-45ae-83a0-a45cb9789dbb" /> <img width="200" alt="Theme Picker" src="https://github.com/user-attachments/assets/636ed2db-a1e8-410b-9a07-15ec08a22e32" />

#### Network Switcher Flow

<img width="200" alt="Image" src="https://github.com/user-attachments/assets/a79f7d70-f070-45ae-83a0-a45cb9789dbb" /> <img width="200" alt="Network Switcher 1" src="https://github.com/user-attachments/assets/7f0ece09-f0c5-4c02-8b07-5da2d1910a46" /> <img width="200" alt="Network Switcher 2" src="https://github.com/user-attachments/assets/cb8e96ff-6e97-456f-a576-1309c0dfd0a6" /> <img width="200" alt="Image" src="https://github.com/user-attachments/assets/b80cae28-ade1-45e6-a0b1-f91bc0eb9654" />

#### Create Legacy Algo25 Account Flow

<img width="200" alt="Image" src="https://github.com/user-attachments/assets/a79f7d70-f070-45ae-83a0-a45cb9789dbb" /> 
<img width="200" alt="Image" src="https://github.com/user-attachments/assets/15cc6a7f-a1ed-498b-9ae6-ea8e9f28d586" /> 
<img width="200" alt="Image" src="https://github.com/user-attachments/assets/5ad40b83-43d6-4750-98eb-b39667d4d290" />

#### Create Legacy Universal HD Account Flow

<img width="200" alt="Image" src="https://github.com/user-attachments/assets/a79f7d70-f070-45ae-83a0-a45cb9789dbb" /> 
<img width="200" alt="Image" src="https://github.com/user-attachments/assets/15cc6a7f-a1ed-498b-9ae6-ea8e9f28d586" />
<img width="200" alt="Image" src="https://github.com/user-attachments/assets/0872fc82-5abb-4806-a2db-61c90441dfbd" /> 
<img width="200" alt="Image" src="https://github.com/user-attachments/assets/25b215b6-e502-436e-9110-a02e278e0d06" />

### Wallet-SDK Screens - Transactions

#### KeyReg Flow

<img width="200" alt="Image" src="https://github.com/user-attachments/assets/8d386947-3a7a-4f08-be63-929982956fb4" />   
<img width="200" alt="Image" src="https://github.com/user-attachments/assets/37d406cc-b584-44ae-9c95-94fed9c6baae" /> 
<img width="200" alt="Image" src="https://github.com/user-attachments/assets/a20d571d-ea2f-461b-a75f-0ddfc19bbd19" /> 
<img width="200" alt="Image" src="https://github.com/user-attachments/assets/e8db4235-3e1f-45c0-825c-29ad929a716d" />

## Architecture

# Database Schema

```mermaid
---
title: AlgoKitDatabase
---
erDiagram
    custom_account_info {
        String algo_address PK
        String custom_name
        Int order_index
        Boolean is_backed_up
    }
    custom_hd_seed_info {
        Int seed_id PK
        String entropy_custom_name
        Int order_index
        Boolean is_backed_up
    }
    algo_25 {
        String algo_address PK
        ByteArray encrypted_secret_key
    }
    ledger_ble {
        String algo_address PK
        String device_mac_address
        Int account_index_in_ledger
        String bluetooth_name
    }
    no_auth {
        String algo_address PK
    }
    hd_seeds {
        Int seed_id PK
        ByteArray encrypted_entropy UK
        ByteArray encrypted_seed UK
    }
    falcon_24 {
        String algo_address PK
        Int seed_id FK
        ByteArray public_key UK
        ByteArray encrypted_secret_key
    }
    hd_keys {
        String algo_address PK
        ByteArray public_key UK
        ByteArray encrypted_private_key
        Int seed_id FK
        Int account
        Int change
        Int key_index
        Int derivation_type
    }
    hd_keys ||--o{ hd_seeds : links
    falcon_24 ||--o{ hd_seeds : links
```

## Contributing
Development happens in this open source repo for the AlgoKit Wallet SDK. Algorand community is always welcome to contribute by reviewing or opening new pull requests.
