package com.michaeltchuang.walletsdk.ui.typography

import algokit_walletsdk_kmp.wallet_sdk.generated.resources.Res
import algokit_walletsdk_kmp.wallet_sdk.generated.resources.dmmono_medium
import algokit_walletsdk_kmp.wallet_sdk.generated.resources.dmmono_regular
import algokit_walletsdk_kmp.wallet_sdk.generated.resources.dmsans_bold
import algokit_walletsdk_kmp.wallet_sdk.generated.resources.dmsans_medium
import algokit_walletsdk_kmp.wallet_sdk.generated.resources.dmsans_regular
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.Font

@Composable
internal fun getPeraTypographyTitle(): PeraTypography.Title {
    return PeraTypography.Title(
        regular = getPeraTypographyTitleRegular(),
        large = getPeraTypographyTitleLarge(),
        small = getPeraTypographyTitleSmall()
    )
}

@Composable
private fun getPeraTypographyTitleRegular(): PeraTypography.Title.TitleRegular {
    val titleStyle = TextStyle(
        fontSize = 32.sp,
        lineHeight = 40.sp,
    )
    return PeraTypography.Title.TitleRegular(
        sans = titleStyle.copy(
            fontFamily = FontFamily(Font(Res.font.dmsans_regular, FontWeight.Normal))
        ),
        sansMedium = titleStyle.copy(
            fontFamily = FontFamily(Font(Res.font.dmsans_medium, FontWeight.Medium))
        ),
        sansBold = titleStyle.copy(
            fontFamily = FontFamily(Font(Res.font.dmsans_bold, FontWeight.Bold))
        )
    )
}

@Composable
private fun getPeraTypographyTitleLarge(): PeraTypography.Title.TitleLarge {
    val titleStyle = TextStyle(
        fontSize = 36.sp,
        lineHeight = 48.sp,
    )
    return PeraTypography.Title.TitleLarge(
        sans = titleStyle.copy(
            fontFamily = FontFamily(Font(Res.font.dmsans_regular, FontWeight.Normal))
        ),
        sansMedium = titleStyle.copy(
            fontFamily = FontFamily(Font(Res.font.dmsans_medium, FontWeight.Medium))
        ),
        mono = titleStyle.copy(
            fontFamily = FontFamily(Font(Res.font.dmmono_regular, FontWeight.Normal))
        ),
        monoMedium = titleStyle.copy(
            fontFamily = FontFamily(Font(Res.font.dmmono_medium, FontWeight.Medium))
        )
    )
}

@Composable
private fun getPeraTypographyTitleSmall(): PeraTypography.Title.TitleSmall {
    val titleStyle = TextStyle(
        fontSize = 28.sp,
        lineHeight = 32.sp,
    )
    return PeraTypography.Title.TitleSmall(
        sans = titleStyle.copy(
            fontFamily = FontFamily(Font(Res.font.dmsans_regular, FontWeight.Normal))
        ),
        sansMedium = titleStyle.copy(
            fontFamily = FontFamily(Font(Res.font.dmsans_medium, FontWeight.Medium))
        )
    )
}
