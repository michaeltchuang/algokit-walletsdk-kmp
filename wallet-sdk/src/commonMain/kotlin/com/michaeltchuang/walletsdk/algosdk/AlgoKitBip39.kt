package com.michaeltchuang.walletsdk.algosdk

import com.michaeltchuang.walletsdk.algosdk.utils.PassphraseKeywordUtils
import fr.acinq.bitcoin.MnemonicCode
import kotlin.random.Random

object AlgoKitBip39 {
    fun getSeedFromEntropy(entropy: ByteArray): ByteArray {
        val mnemonic = MnemonicCode.toMnemonics(entropy)
        val passphrase = ""
        val seed = MnemonicCode.toSeed(mnemonic, passphrase)
        return seed
    }

    @OptIn(ExperimentalStdlibApi::class)
    fun getEntropyFromMnemonic(mnemonic: String): String {
        // First validate the mnemonic
//    if (!MnemonicCode.validate(mnemonic)) {
//        throw IllegalArgumentException("Invalid mnemonic")
//    }

        val words = mnemonic.trim().split("\\s+".toRegex())
        if (words.size != 24) {
            throw IllegalArgumentException("Expected 24 words, got ${words.size}")
        }

        val bip39WordList = PassphraseKeywordUtils.predefinedWords

        // Convert words to indices
        val indices =
            words.map { word ->
                val index = bip39WordList.indexOf(word.lowercase())
                if (index == -1) {
                    throw IllegalArgumentException("Word '$word' not found in BIP39 wordlist")
                }
                index
            }

        // Convert indices to 11-bit binary and concatenate
        val binaryString =
            indices.joinToString("") { index ->
                index.toString(2).padStart(11, '0')
            }

        // Split into entropy and checksum (for 24 words: 256 bits entropy + 8 bits checksum)
        val entropyBits = binaryString.substring(0, 256)
        val checksumBits = binaryString.substring(256)

        // Convert entropy bits to bytes
        val entropyBytes =
            entropyBits.chunked(8).map { byte ->
                byte.toInt(2).toByte()
            }.toByteArray()

        // Verify checksum (optional but recommended)
        // val computedChecksum = sha256(entropyBytes).first().toString(2).padStart(8, '0')
        // if (checksumBits != computedChecksum) {
        //     throw IllegalArgumentException("Invalid checksum")
        // }
//
//        println("Extracted entropy: ${entropyBytes.toHexString()}")
        return entropyBytes.toHexString()
    }

    fun getMnemonicFromEntropy(entropy: ByteArray): String {
        val mnemonic = MnemonicCode.toMnemonics(entropy)
        return mnemonic.joinToString(" ")
    }

    fun generate24WordMnemonic(): String {
        val entropy = ByteArray(32)
        Random.Default.nextBytes(entropy)
        val mnemonic = MnemonicCode.toMnemonics(entropy).joinToString(" ")
        println("mnemonic: $mnemonic")
        return mnemonic
    }
}
