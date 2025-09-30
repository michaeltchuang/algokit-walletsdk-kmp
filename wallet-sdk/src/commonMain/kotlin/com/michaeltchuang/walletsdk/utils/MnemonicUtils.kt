package com.michaeltchuang.walletsdk.utils

const val MNEMONIC_DELIMITER_REGEX = "[, ]+"
const val MNEMONIC_SEPARATOR = " "

fun String.splitMnemonic(): List<String> = this.trim().split(Regex(MNEMONIC_DELIMITER_REGEX)).filter { it.isNotBlank() }

fun List<String>.joinMnemonics(): String = joinToString(MNEMONIC_SEPARATOR)
