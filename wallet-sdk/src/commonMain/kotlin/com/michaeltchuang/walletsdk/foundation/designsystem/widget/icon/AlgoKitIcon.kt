package com.michaeltchuang.walletsdk.foundation.designsystem.widget.icon

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ContentScale.Companion.Fit
import androidx.compose.ui.unit.dp
import com.michaeltchuang.walletsdk.foundation.designsystem.theme.AlgoKitTheme

@Composable
private fun AlgoKitCoreIcon(
    modifier: Modifier = Modifier,
    painter: Painter,
    contentDescription: String,
    tintColor: Color?,
    contentScale: ContentScale,
) {
    Image(
        modifier = modifier,
        painter = painter,
        contentDescription = contentDescription,
        colorFilter = tintColor?.let { ColorFilter.tint(it) },
        contentScale = contentScale,
    )
}

@Composable
fun AlgoKitIcon(
    modifier: Modifier = Modifier,
    painter: Painter,
    contentDescription: String,
    tintColor: Color? = null,
    contentScale: ContentScale = Fit,
) {
    AlgoKitCoreIcon(
        modifier = modifier,
        painter = painter,
        contentDescription = contentDescription,
        tintColor = tintColor,
        contentScale = contentScale,
    )
}

@Composable
fun AlgoKitIconRoundShape(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    contentDescription: String,
    backgroundColor: Color = AlgoKitTheme.colors.wallet4,
    tintColor: Color = AlgoKitTheme.colors.wallet4Icon,
) {
    Box(
        modifier =
            modifier
                .padding(start = 10.dp)
                .size(40.dp)
                .clip(shape = CircleShape)
                .background(color = backgroundColor),
    ) {
        Icon(
            modifier = Modifier.align(Alignment.Center),
            imageVector = imageVector,
            tint = tintColor,
            contentDescription = contentDescription,
        )
    }
}

@Composable
fun AlgoKitIconRoundShapeBig(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    contentDescription: String,
) {
    Box(
        modifier =
            modifier
                .padding(start = 10.dp)
                .size(64.dp)
                .clip(shape = CircleShape)
                .background(color = AlgoKitTheme.colors.layerGrayLighter),
    ) {
        Icon(
            modifier =
                Modifier
                    .align(Alignment.Center)
                    .height(40.dp)
                    .width(40.dp),
            imageVector = imageVector,
            tint = AlgoKitTheme.colors.textMain,
            contentDescription = contentDescription,
        )
    }
}
