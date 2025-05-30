package com.michaeltchuang.walletsdk.qr.presentation.view

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.unit.dp
import com.michaeltchuang.walletsdk.qr.presentation.view.CornerStrokePosition.BottomLeft
import com.michaeltchuang.walletsdk.qr.presentation.view.CornerStrokePosition.BottomRight
import com.michaeltchuang.walletsdk.qr.presentation.view.CornerStrokePosition.TopLeft
import com.michaeltchuang.walletsdk.qr.presentation.view.CornerStrokePosition.TopRight

private val TRANSPARENT_AREA_SIZE = 250.dp

@Composable
internal fun CameraOverlay(
    modifier: Modifier = Modifier,
) {
    Canvas(
        modifier = modifier.fillMaxSize()
    ) {
        drawIntoCanvas { it.saveLayer(Rect(Offset.Zero, size), Paint()) }
        drawRect(
            color = Color.Black.copy(alpha = 0.64f),
            size = size
        )

        val offsetX = size.width / 2 - 125.dp.toPx()
        val offsetY = size.height / 2 - 125.dp.toPx()
        val transparentAreaOffset = Offset(offsetX, offsetY)
        drawRoundRect(
            color = Color.Transparent,
            blendMode = BlendMode.Clear,
            cornerRadius = CornerRadius(46f, 46f),
            size = Size(TRANSPARENT_AREA_SIZE.toPx(), TRANSPARENT_AREA_SIZE.toPx()),
            topLeft = transparentAreaOffset
        )

        drawCornerStroke(transparentAreaOffset, TopLeft)
        drawCornerStroke(transparentAreaOffset, TopRight)
        drawCornerStroke(transparentAreaOffset, BottomLeft)
        drawCornerStroke(transparentAreaOffset, BottomRight)
    }
}

private fun DrawScope.drawCornerStroke(offset: Offset, position: CornerStrokePosition) {
    val strokeSize = 46.dp.toPx()
    drawArc(
        color = Color.White,
        startAngle = position.startAngle,
        sweepAngle = position.sweepAngle,
        useCenter = false,
        topLeft = offset + position.getOffset(Size(250.dp.toPx(), 250.dp.toPx()), strokeSize),
        size = Size(strokeSize, strokeSize),
        style = Stroke(width = 4.dp.toPx(), cap = StrokeCap.Round)
    )
}

sealed interface CornerStrokePosition {

    val startAngle: Float
    val sweepAngle: Float

    fun getOffset(canvasSize: Size, strokeSize: Float): Offset

    data object TopLeft : CornerStrokePosition {
        override val startAngle: Float = 180f
        override val sweepAngle: Float = 90f
        override fun getOffset(canvasSize: Size, strokeSize: Float): Offset = Offset(0f, 0f)
    }

    data object TopRight : CornerStrokePosition {
        override val startAngle: Float = 270f
        override val sweepAngle: Float = 90f
        override fun getOffset(canvasSize: Size, strokeSize: Float): Offset = Offset(canvasSize.width - strokeSize, 0f)
    }

    data object BottomLeft : CornerStrokePosition {
        override val startAngle: Float = 90f
        override val sweepAngle: Float = 90f
        override fun getOffset(canvasSize: Size, strokeSize: Float): Offset = Offset(0f, canvasSize.height - strokeSize)
    }

    data object BottomRight : CornerStrokePosition {
        override val startAngle: Float = 0f
        override val sweepAngle: Float = 90f
        override fun getOffset(canvasSize: Size, strokeSize: Float): Offset {
            return Offset(canvasSize.width - strokeSize, canvasSize.height - strokeSize)
        }
    }
}
