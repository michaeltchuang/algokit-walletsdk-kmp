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
internal fun getPeraTypographyCaption(): PeraTypography.Caption {
    val caption = TextStyle(
        fontSize = 11.sp,
        lineHeight = 16.sp
    )
    return PeraTypography.Caption(
        sans = caption.copy(
            fontFamily = FontFamily(Font(Res.font.dmsans_regular, FontWeight.Normal)),
        ),
        sansMedium = caption.copy(
            fontFamily = FontFamily(Font(Res.font.dmsans_medium, FontWeight.Medium)),
        ),
        sansBold = caption.copy(
            fontFamily = FontFamily(Font(Res.font.dmsans_bold, FontWeight.Bold)),
        ),
        mono = caption.copy(
            fontFamily = FontFamily(Font(Res.font.dmmono_regular, FontWeight.Normal)),
        ),
        monoMedium = caption.copy(
            fontFamily = FontFamily(Font(Res.font.dmmono_medium, FontWeight.Medium)),
        )
    )
}
