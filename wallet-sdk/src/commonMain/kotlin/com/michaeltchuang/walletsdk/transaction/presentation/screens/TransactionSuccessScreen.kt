package com.michaeltchuang.walletsdk.transaction.presentation.screens

import algokit_walletsdk_kmp.wallet_sdk.generated.resources.Res
import algokit_walletsdk_kmp.wallet_sdk.generated.resources.done
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.michaeltchuang.walletsdk.designsystem.theme.AlgoKitTheme
import com.michaeltchuang.walletsdk.designsystem.theme.AlgoKitTheme.typography
import com.michaeltchuang.walletsdk.designsystem.widget.button.AlgoKitPrimaryButton
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
fun TransactionSuccessScreen(onDoneClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AlgoKitTheme.colors.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .padding(bottom = 100.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Checkmark Icon
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Success",
                tint = Color(0xFF22D3EE), // Cyan color
                modifier = Modifier
                    .size(72.dp)
                    .background(Color.Transparent)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Success Message
            Text(
                text = "Operation completed",
                color = AlgoKitTheme.colors.textMain,
                style = typography.body.regular.sansMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Subtext
            Text(
                text = "Your transaction was successfully\nreceived by the Algorand network.",
                color = AlgoKitTheme.colors.textGrayLighter,
                style = typography.body.regular.sansMedium
            )

            Spacer(modifier = Modifier.height(40.dp))

        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        ) {
            Text(
                text = "View Transaction detail in Pera Explorer",
                color = AlgoKitTheme.colors.textMain,
                style = typography.footnote.sansMedium,
                modifier = Modifier.clickable { }
            )
            AlgoKitPrimaryButton(
                onClick = {
                    onDoneClick()
                },
                text = stringResource(Res.string.done),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            )
        }

    }
}

@Preview
@Composable
fun TransactionSuccessScreenPreview() {
    AlgoKitTheme {
        TransactionSuccessScreen(){}
    }

}
