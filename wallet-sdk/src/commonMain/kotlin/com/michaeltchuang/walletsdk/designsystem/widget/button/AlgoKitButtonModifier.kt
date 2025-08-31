package com.michaeltchuang.walletsdk.designsystem.widget.button

import androidx.compose.foundation.layout.Arrangement.Horizontal
import androidx.compose.material3.ButtonColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class AlgoKitButtonModifier(
    val modifier: Modifier = Modifier,
    val onClick: () -> Unit,
    val text: String,
    val textStyle: TextStyle,
    val colors: ButtonColors,
    val horizontalArrangement: Horizontal,
    val state: AlgoKitButtonState = AlgoKitButtonState.ENABLED,
    val leftIcon: @Composable (() -> Unit)? = null,
    val rightIcon: @Composable (() -> Unit)? = null,
    val cornerRadius: Dp = 4.dp
)
