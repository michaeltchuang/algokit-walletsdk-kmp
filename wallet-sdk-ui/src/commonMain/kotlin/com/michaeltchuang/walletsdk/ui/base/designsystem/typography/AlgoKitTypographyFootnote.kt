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
fun getAlgoKitTypographyFootnote(): AlgoKitTypography.Footnote {
    val footnote =
        TextStyle(
            fontSize = 13.sp,
            lineHeight = 20.sp,
        )
    return AlgoKitTypography.Footnote(
        sans =
            footnote.copy(
                fontFamily = FontFamily(Font(Res.font.dmsans_regular, FontWeight.Normal)),
            ),
        sansBold =
            footnote.copy(
                fontFamily = FontFamily(Font(Res.font.dmsans_bold, FontWeight.Bold)),
            ),
        sansMedium =
            footnote.copy(
                fontFamily = FontFamily(Font(Res.font.dmsans_medium, FontWeight.Medium)),
            ),
        mono =
            footnote.copy(
                fontFamily = FontFamily(Font(Res.font.dmmono_regular, FontWeight.Normal)),
            ),
        monoMedium =
            footnote.copy(
                fontFamily = FontFamily(Font(Res.font.dmmono_medium, FontWeight.Medium)),
            ),
    )
}
