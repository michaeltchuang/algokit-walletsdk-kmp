package com.michaeltchuang.walletsdk.settings.presentation.components

import algokit_walletsdk_kmp.wallet_sdk.generated.resources.Res
import algokit_walletsdk_kmp.wallet_sdk.generated.resources.next
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.michaeltchuang.walletsdk.foundation.designsystem.theme.AlgoKitTheme
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun SettingsItem(
    icon: DrawableResource,
    title: String,
    onClick: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier =
            Modifier
                .fillMaxWidth()
                .clickable { onClick() }
                .padding(vertical = 12.dp),
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = title,
            tint = AlgoKitTheme.colors.textMain,
            modifier = Modifier.size(24.dp),
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = title,
            color = AlgoKitTheme.colors.textMain,
            modifier = Modifier.weight(1f),
            style = AlgoKitTheme.typography.body.regular.sansMedium,
        )
        Icon(
            Icons.Default.KeyboardArrowRight,
            tint = AlgoKitTheme.colors.textMain,
            contentDescription = stringResource(Res.string.next),
        )
    }
}
