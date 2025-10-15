plugins {
    alias(libs.plugins.multiplatform)

    id("com.android.library")
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.compose)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.ktlint)
}

kotlin {
    androidTarget {
        compilations.all {
            compileTaskProvider {
                compilerOptions {
                    jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21)
                    freeCompilerArgs.add("-Xjdk-release=${JavaVersion.VERSION_21}")
                }
            }
        }
    }

    listOf(
        iosArm64(),
        iosSimulatorArm64(),
        iosX64(),
    ).forEach {
        it.binaries.framework {
            baseName = "walletSDKUi"
            isStatic = true
        }
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(libs.kotlin.stdlib)
                api(project(":wallet-sdk-core"))

                api(libs.napier)

                implementation(compose.animation)
                implementation(compose.components.resources)
                implementation(compose.components.uiToolingPreview)
                implementation(compose.foundation)
                implementation(compose.material)
                implementation(compose.material3)
                implementation(compose.runtime)
                implementation(libs.bitcoin.kmp)
                implementation(libs.coil.compose)
                implementation(project.dependencies.platform(libs.koin.bom))
                implementation(libs.koin.core)
                implementation(libs.koin.compose)
                implementation(libs.koin.compose.viewmodel)
                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.kotlinx.serialization.json)
                implementation(libs.ktor.client.logging)
                implementation(libs.ktor.client.content.negotiation)
                implementation(libs.ktor.kotlinx.serialization)
                implementation(libs.ktor.client.core)
                implementation(libs.ktor.utils)
                implementation(libs.room.runtime)
                implementation(libs.sqlite.bundled)
                implementation(libs.kotlinx.datetime)
                implementation(libs.webview.multiplatform.mobile)
                implementation(libs.compose.webview.multiplatform)
                implementation(libs.qr.kit)
                implementation(libs.navigation.compose)
                implementation(libs.datastore)
                implementation(libs.datastore.preferences)
                implementation(libs.room.runtime)
                implementation(libs.sqlite.bundled)
                implementation(libs.bignum)
                implementation(libs.compottie)
            }
        }

        commonTest {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }

        androidMain {
            dependencies {
                implementation(libs.xhdwalletapi)
                implementation(libs.kotlin.bip39)
                implementation(libs.androidx.activityCompose)
                implementation(libs.androidx.compose.foundation)
                implementation(libs.androidx.lifecycle.runtime.compose)
                implementation(libs.androidx.lifecycle.viewmodel.compose)
                implementation(libs.androidx.lifecycle.viewmodel.savedstate)
                implementation(libs.kotlinx.coroutines.android)
                implementation(libs.kotlinfixture)
                implementation(libs.ktor.client.android)
                implementation(libs.ktor.client.okhttp)
                implementation(compose.uiTooling)
                implementation(compose.components.uiToolingPreview)
                implementation(project.dependencies.platform(libs.firebase.bom))
                implementation(libs.firebase.analytics)
                implementation(libs.firebase.config)
                implementation(libs.koin.android)
            }
        }

        iosMain {
            dependencies {}
        }
    }
}

android {
    namespace = "com.michaeltchuang.walletsdk.ui"
    compileSdk = 36

    defaultConfig {
        minSdk = 24
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    sourceSets["main"].res.srcDirs("src/commonMain/composeResources", "src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/composeResources")
}
