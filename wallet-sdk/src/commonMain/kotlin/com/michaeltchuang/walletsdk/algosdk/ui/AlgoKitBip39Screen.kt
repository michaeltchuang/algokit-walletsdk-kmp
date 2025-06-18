package com.michaeltchuang.walletsdk.algosdk.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.michaeltchuang.walletsdk.algosdk.getEntropyFromMnemonic

@Composable
fun AlgoKitBip39Screen(text: String) {
    Box(
        Modifier.fillMaxHeight(0.5f).fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier.wrapContentSize(),
            text = getEntropyFromMnemonic(text),
            color = Color.Black,
            fontSize = 18.sp
        )
    }
}
