package com.michaeltchuang.walletsdk.ui.base.designsystem.widget.button

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.michaeltchuang.walletsdk.ui.base.designsystem.theme.AlgoKitTheme
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun AlgoKitButtonIcon(iconResId: DrawableResource) {
    Icon(
        modifier = Modifier.size(24.dp),
        painter = painterResource(iconResId),
        contentDescription = null,
        tint = AlgoKitTheme.colors.buttonPrimaryText,
    )
}
