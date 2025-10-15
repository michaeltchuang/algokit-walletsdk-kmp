package com.michaeltchuang.walletsdk.ui.base.designsystem.typography

import algokit_walletsdk_kmp.wallet_sdk_ui.generated.resources.Res
import algokit_walletsdk_kmp.wallet_sdk_ui.generated.resources.dmmono_medium
import algokit_walletsdk_kmp.wallet_sdk_ui.generated.resources.dmmono_regular
import algokit_walletsdk_kmp.wallet_sdk_ui.generated.resources.dmsans_bold
import algokit_walletsdk_kmp.wallet_sdk_ui.generated.resources.dmsans_medium
import algokit_walletsdk_kmp.wallet_sdk_ui.generated.resources.dmsans_regular
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.Font

@Composable
internal fun getAlgoKitTypographyTitle(): AlgoKitTypography.Title =
    AlgoKitTypography.Title(
        regular = getAlgoKitTypographyTitleRegular(),
        large = getAlgoKitTypographyTitleLarge(),
        small = getAlgoKitTypographyTitleSmall(),
    )

@Composable
private fun getAlgoKitTypographyTitleRegular(): AlgoKitTypography.Title.TitleRegular {
    val titleStyle =
        TextStyle(
            fontSize = 32.sp,
            lineHeight = 40.sp,
        )
    return AlgoKitTypography.Title.TitleRegular(
        sans =
            titleStyle.copy(
                fontFamily = FontFamily(Font(Res.font.dmsans_regular, FontWeight.Normal)),
            ),
        sansMedium =
            titleStyle.copy(
                fontFamily = FontFamily(Font(Res.font.dmsans_medium, FontWeight.Medium)),
            ),
        sansBold =
            titleStyle.copy(
                fontFamily = FontFamily(Font(Res.font.dmsans_bold, FontWeight.Bold)),
            ),
    )
}

@Composable
private fun getAlgoKitTypographyTitleLarge(): AlgoKitTypography.Title.TitleLarge {
    val titleStyle =
        TextStyle(
            fontSize = 36.sp,
            lineHeight = 48.sp,
        )
    return AlgoKitTypography.Title.TitleLarge(
        sans =
            titleStyle.copy(
                fontFamily = FontFamily(Font(Res.font.dmsans_regular, FontWeight.Normal)),
            ),
        sansMedium =
            titleStyle.copy(
                fontFamily = FontFamily(Font(Res.font.dmsans_medium, FontWeight.Medium)),
            ),
        mono =
            titleStyle.copy(
                fontFamily = FontFamily(Font(Res.font.dmmono_regular, FontWeight.Normal)),
            ),
        monoMedium =
            titleStyle.copy(
                fontFamily = FontFamily(Font(Res.font.dmmono_medium, FontWeight.Medium)),
            ),
    )
}

@Composable
private fun getAlgoKitTypographyTitleSmall(): AlgoKitTypography.Title.TitleSmall {
    val titleStyle =
        TextStyle(
            fontSize = 28.sp,
            lineHeight = 32.sp,
        )
    return AlgoKitTypography.Title.TitleSmall(
        sans =
            titleStyle.copy(
                fontFamily = FontFamily(Font(Res.font.dmsans_regular, FontWeight.Normal)),
            ),
        sansMedium =
            titleStyle.copy(
                fontFamily = FontFamily(Font(Res.font.dmsans_medium, FontWeight.Medium)),
            ),
    )
}
