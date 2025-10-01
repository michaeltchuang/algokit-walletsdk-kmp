# AlgoKit Wallet SDK

This mobile utils library project provides common wallet UI components and screens out of the box, allowing native developers to skip building standard wallet functionality and focus more on unique, value-added features for their mobile applications.

AlgoKit Wallet SDK currently uses UI theming inspired by [Pera Android](https://github.com/perawallet/pera-android) as a placeholder until official Algorand Foundation branding guidelines are available.

```mermaid
---
title: AlgoKit Wallet SDK High Level Overview
---
graph TD
    subgraph " "
        App["Algorand App"]
    end

    subgraph " "
        SDK["AlgoKit Wallet SDK<br/>(UI Components & Screens)"]
    end

    subgraph "Algorand SDKs"
        Core["AlgoKit-Core SDK"]
        xHD["Algo xHD SDK"]
        JavaSDK["Algo Java SDK"]
        GoSDK["Algo Go SDK"]
    end

    App <--> SDK
    SDK <--> Core
    SDK <--> xHD
    SDK <--> JavaSDK
    SDK <--> GoSDK
```

The sample apps (Android/iOS) demonstrate `wallet-sdk` usage through a simplified "Pera-lite" wallet application. Current and planned features include:

- Create and recover accounts (Algo25, HD)
- Theme customization
- Network switching (Mainnet/Testnet)
- QR code scanning for account imports and keyreg transactions
- Algo-native experience
- Account detail screens
- Passphrase management
- Localization

## Project structure

The project has the following modules:

- **composeSampleApp**: A [Compose Multiplatform](https://github.com/JetBrains/compose-multiplatform) sample wallet app that demonstrates `wallet-sdk` usage.
- **iosSampleApp**: The iOS app for `composeSampleApp`. Open this module in Xcode if needed.
- **wallet-sdk**: The AlgoKit Wallet SDK - a UI layer built with [Kotlin Multiplatform](https://developer.android.com/kotlin/multiplatform) on top of [AlgoKit-Core SDK](https://github.com/algorandfoundation/algokit-core), [Algo xHD SDK](https://github.com/algorandfoundation/xHD-Wallet-API-kt), [Algo Java SDK](https://github.com/algorand/java-algorand-sdk), and [Algo Go SDK](https://github.com/perawallet/algorand-go-mobile-sdk) projects. 

This project is developed using [Android Studio](https://developer.android.com/studio) stable version and the [Kotlin Multiplatform Plugin](https://plugins.jetbrains.com/plugin/14936-kotlin-multiplatform).

## Screenshots - Sample App

#### Accounts List Screen

<img width="200" alt="Accounts List" src="https://github.com/user-attachments/assets/e7c48e52-89b9-4107-a530-decb41e8658e" />

### Wallet-SDK Screens - Onboarding

#### No Accounts Onboarding Flow

<img width="200" alt="Image" src="https://github.com/user-attachments/assets/cb4563d7-e34e-4df1-9a4d-e7748d8f7afe" /> 
<img width="200" alt="Image" src="https://github.com/user-attachments/assets/25b215b6-e502-436e-9110-a02e278e0d06" />

#### Create HD Wallet Flow

<img width="200" alt="Image" src="https://github.com/user-attachments/assets/1fcba333-5d68-45e8-93a3-0da893135696" /> 
<img width="200" alt="Image" src="https://github.com/user-attachments/assets/77da2564-a81b-4172-8784-6f8fbfdfedda" />

#### Add HD Account To Existing Wallet Flow

<img width="200" alt="Image" src="https://github.com/user-attachments/assets/87f01910-8301-41fa-9bd3-b2d6aca22783" /> 
<img width="200" alt="Image" src="https://github.com/user-attachments/assets/0872fc82-5abb-4806-a2db-61c90441dfbd" /> 
<img width="200" alt="Image" src="https://github.com/user-attachments/assets/992add95-3d16-4b00-a1c5-8ada8ef302b4" />

#### Recover Algo25 Account Flow

<img width="200" alt="Image" src="https://github.com/user-attachments/assets/1653a674-a532-4f6d-b81e-246c10d39616" /> 
<img width="200" alt="Image" src="https://github.com/user-attachments/assets/877eb324-2a22-4f57-8532-3a521879eecc" /> 
<img width="200" alt="Image" src="https://github.com/user-attachments/assets/25a361fe-0410-49ad-9178-7b5baaa9bdad" /> 
<img width="200" alt="Image" src="https://github.com/user-attachments/assets/6c021283-7050-4b33-a11f-aacf2774cfa3" />

#### Recover Algo25 Account with QR Code Flow

<img width="200" alt="Image" src="https://github.com/user-attachments/assets/8d386947-3a7a-4f08-be63-929982956fb4" /> 
<img width="200" alt="Image" src="https://github.com/user-attachments/assets/4ec7bcdd-fa74-4ed7-a095-90705b049ebb" /> 
<img width="200" alt="Image" src="https://github.com/user-attachments/assets/6c021283-7050-4b33-a11f-aacf2774cfa3" />

#### Recover HD Account with QR Code Flow

Coming Soon

### Wallet-SDK Screens - Account Details

#### View Passphrase Flow

<img width="200" alt="Image" src="https://github.com/user-attachments/assets/b0fe4ff5-73fa-4dab-8c35-97c16cce2fa1" /> 
<img width="200" alt="Image" src="https://github.com/user-attachments/assets/56dbc88b-a35d-409f-9acd-1ff211e3c902" /> 
<img width="200" alt="Image" src="https://github.com/user-attachments/assets/d0cf983f-5349-4230-b742-16a4896cd0a3" /> 
<img width="200" alt="Image" src="https://github.com/user-attachments/assets/3f85712f-7248-40f3-9c53-9458019a7d28" />

### Wallet-SDK Screens - Settings

#### Theme Picker Flow

<img width="200" alt="Settings Screen" src="https://github.com/user-attachments/assets/a79f7d70-f070-45ae-83a0-a45cb9789dbb" /> <img width="200" alt="Theme Picker" src="https://github.com/user-attachments/assets/636ed2db-a1e8-410b-9a07-15ec08a22e32" />

#### Network Switcher Flow

<img width="200" alt="Settings Screen" src="https://github.com/user-attachments/assets/a79f7d70-f070-45ae-83a0-a45cb9789dbb" /> <img width="200" alt="Network Switcher 1" src="https://github.com/user-attachments/assets/7f0ece09-f0c5-4c02-8b07-5da2d1910a46" /> <img width="200" alt="Network Switcher 2" src="https://github.com/user-attachments/assets/cb8e96ff-6e97-456f-a576-1309c0dfd0a6" />

#### Create Algo25 Account Flow

<img width="200" alt="Settings Screen" src="https://github.com/user-attachments/assets/a79f7d70-f070-45ae-83a0-a45cb9789dbb" /> <img width="200" alt="Algo25 Create 1" src="https://github.com/user-attachments/assets/7f0ece09-f0c5-4c02-8b07-5da2d1910a46" /> <img width="200" alt="Algo25 Create 2" src="https://github.com/user-attachments/assets/5ad40b83-43d6-4750-98eb-b39667d4d290" />

### Wallet-SDK Screens - Transactions

#### KeyReg

Coming Soon

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
```

## Contributing
Development happens in this open source repo for the AlgoKit Wallet SDK. Algorand community is always welcome to contribute by reviewing or opening new pull requests.
