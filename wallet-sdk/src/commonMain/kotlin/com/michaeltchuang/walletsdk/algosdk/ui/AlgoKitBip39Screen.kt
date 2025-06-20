package com.michaeltchuang.walletsdk.algosdk.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.michaeltchuang.walletsdk.algosdk.getEntropyFromMnemonic
import com.michaeltchuang.walletsdk.ui.theme.AlgoKitTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

private const val SAMPLE_HD_MNEMONIC =
    "borrow among leopard smooth trade cake profit proud matrix bottom goose charge oxygen shine punch hotel era monitor fossil violin tip notice any visit"

@Composable
fun AlgoKitBip39Screen(onAlgoKitEvent: (event: AlgoKitEvent) -> Unit) {
    Column(
        Modifier.fillMaxHeight(0.5f).fillMaxWidth()
            .background(color = AlgoKitTheme.colors.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var entropy by remember { mutableStateOf("") }

        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
            IconButton(onClick = { onAlgoKitEvent(AlgoKitEvent.CLOSE_BOTTOM_SHEET) }) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Icon Button",
                    tint = AlgoKitTheme.colors.textMain,
                    modifier = Modifier.size(48.dp).padding(8.dp)
                )
            }
        }
        Button(onClick = {
            entropy = getEntropyFromMnemonic(SAMPLE_HD_MNEMONIC)
            onAlgoKitEvent(AlgoKitEvent.HD_ACCOUNT_CREATED)
        }) {
            Text("Create HD Account")
        }
        Spacer(modifier = Modifier.size(16.dp))
        Text(
            modifier = Modifier.wrapContentSize(),
            text = entropy,
            color = AlgoKitTheme.colors.textMain ,
            fontSize = 18.sp
        )
    }
}

@Preview
@Composable
fun AlgoKitBip39ScreenPreview() {
    AlgoKitBip39Screen {}
}
