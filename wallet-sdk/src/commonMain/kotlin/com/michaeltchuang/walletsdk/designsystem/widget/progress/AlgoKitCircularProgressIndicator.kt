package com.michaeltchuang.walletsdk.designsystem.widget.progress

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.michaeltchuang.walletsdk.designsystem.theme.AlgoKitTheme

@Composable
fun PeraCircularProgressIndicator(
    modifier: Modifier = Modifier,
    color: Color = AlgoKitTheme.colors.linkPrimary,
    strokeWidth: Dp = 3.dp
) {
    CircularProgressIndicator(
        modifier = modifier
            .width(24.dp)
            .height(24.dp),
        color = color,
        strokeWidth = strokeWidth
    )
}
