package com.michaeltchuang.walletsdk.ui.typography

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle

data class AlgoKitTypography(
    val title: Title,
    val body: Body,
    val footnote: Footnote,
    val caption: Caption,
) {
    data class Title(
        val regular: TitleRegular,
        val large: TitleLarge,
        val small: TitleSmall,
    ) {
        data class TitleRegular(
            val sans: TextStyle,
            val sansMedium: TextStyle,
            val sansBold: TextStyle,
        )

        data class TitleLarge(
            val sans: TextStyle,
            val sansMedium: TextStyle,
            val mono: TextStyle,
            val monoMedium: TextStyle,
        )

        data class TitleSmall(
            val sans: TextStyle,
            val sansMedium: TextStyle,
        )
    }

    data class Body(
        val regular: BodyRegular,
        val large: BodyLarge,
    ) {
        data class BodyRegular(
            val sans: TextStyle,
            val sansMedium: TextStyle,
            val sansBold: TextStyle,
            val mono: TextStyle,
            val monoMedium: TextStyle,
        )

        data class BodyLarge(
            val sans: TextStyle,
            val sansMedium: TextStyle,
            val mono: TextStyle,
        )
    }

    data class Footnote(
        val sans: TextStyle,
        val sansBold: TextStyle,
        val sansMedium: TextStyle,
        val mono: TextStyle,
        val monoMedium: TextStyle,
    )

    data class Caption(
        val sans: TextStyle,
        val sansBold: TextStyle,
        val sansMedium: TextStyle,
        val mono: TextStyle,
        val monoMedium: TextStyle,
    )
}

@Composable
fun AlgoKitTypography() =
    AlgoKitTypography(
        title = getAlgoKitTypographyTitle(),
        body = getAlgoKitTypographyBody(),
        footnote = getAlgoKitTypographyFootnote(),
        caption = getAlgoKitTypographyCaption(),
    )
