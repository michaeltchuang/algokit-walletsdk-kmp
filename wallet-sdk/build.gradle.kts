import org.gradle.kotlin.dsl.android
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getting
import org.gradle.kotlin.dsl.invoke
import org.gradle.kotlin.dsl.kotlin
import org.gradle.kotlin.dsl.libs
import org.gradle.kotlin.dsl.room
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion


plugins {
    // this needs to be first in list
    alias(libs.plugins.multiplatform)

    id("com.android.library")
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.compose)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.ktlint)
    alias(libs.plugins.room)
    id("io.github.frankois944.spmForKmp") version "1.0.0-Beta05"
}

kotlin {

    androidTarget {
        compilations.all {
            compileTaskProvider {
                compilerOptions {
                    jvmTarget.set(JvmTarget.JVM_21)
                    freeCompilerArgs.add("-Xjdk-release=${JavaVersion.VERSION_21}")
                }
            }
        }
    }

    @OptIn(ExperimentalKotlinGradlePluginApi::class)
    compilerOptions {
        languageVersion.set(KotlinVersion.KOTLIN_2_1)
        freeCompilerArgs.add("-Xexpect-actual-classes")
    }

    listOf(
        iosArm64(),
        iosSimulatorArm64(),
        iosX64(),
    ).forEach { iosTarget ->
        iosTarget.compilations {
            val main by getting {
                cinterops {
                    create("AlgorandIosSdk")
                }
            }
        }
    }

    swiftPackageConfig {
        create("AlgorandIosSdk") {
            customPackageSourcePath = "${layout.projectDirectory.asFile.path}/src/iosMain/swift"
            minIos = "16.2"
            dependency {
                localBinary(
                    path = "${layout.projectDirectory.asFile.path}/src/iosMain/xcframeworks/AlgoSDK.xcframework.zip",
                    packageName = "AlgoSDK",
                    exportToKotlin = false,
                )
                remotePackageVersion(
                    url = uri("https://github.com/Electric-Coin-Company/MnemonicSwift.git"),
                    products = {
                        add("MnemonicSwift")
                    },
                    version = "2.2.5",
                )
                remotePackageBranch(
                    url = uri("https://github.com/michaeltchuang/algorand-devrel-xHD-Wallet-API-swift.git"),
                    products = {
                        add("x-hd-wallet-api")
                    },
                    branch = "fix/swiftlint",
                )
            }
        }
    }

    sourceSets {
        androidMain.dependencies {
            // will turn this to implementation when app module references are moved to common
            api(libs.algosdk)
            api(libs.algorand.go.mobile)

            // toml files don't support aar files yet
            implementation("net.java.dev.jna:jna:5.17.0@aar")
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
        commonMain.dependencies {
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
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
        androidUnitTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.kotlinx.coroutines.test)
            implementation(libs.mockk)
        }
    }
}

android {
    namespace = "com.michaeltchuang.walletsdk"
    compileSdk =
        libs.versions.android.compileSdk
            .get()
            .toInt()
    defaultConfig {
        minSdk =
            libs.versions.android.minSdk
                .get()
                .toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    buildFeatures {
        // enables a Compose tooling support in the AndroidStudio
        compose = true
    }

    lint {
        // Disable problematic rules for KMP
        disable.addAll(
            listOf(
                "NullSafeMutableLiveData",
                "UnusedResources",
                "MissingTranslation",
                "Instantiatable",
                "InvalidPackage",
                "TypographyFractions",
                "TypographyQuotes",
                "TrustAllX509TrustManager",
                "UseTomlInstead",
                "AndroidGradlePluginVersion",
                "GradleDependency",
            ),
        )

        // Continue on lint errors instead of failing the build
        abortOnError = false

        // Skip lint for release builds to speed up builds
        checkReleaseBuilds = false

        // Only run lint on changed files
        checkDependencies = false
    }

    sourceSets["main"].res.srcDirs("src/commonMain/composeResources", "src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/composeResources")
}

room {
    schemaDirectory("$projectDir/schemas")
}

dependencies {
    add("kspAndroid", libs.room.compiler)
    add("kspIosSimulatorArm64", libs.room.compiler)
    add("kspIosArm64", libs.room.compiler)
    add("kspIosX64", libs.room.compiler)
    add("kspCommonMainMetadata", libs.room.compiler)
}