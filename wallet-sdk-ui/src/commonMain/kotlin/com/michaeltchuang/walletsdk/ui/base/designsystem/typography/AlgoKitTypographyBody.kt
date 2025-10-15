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
internal fun getAlgoKitTypographyBody(): AlgoKitTypography.Body =
    AlgoKitTypography.Body(
        regular = getAlgoKitTypographyBodyRegular(),
        large = getAlgoKitTypographyBodyLarge(),
    )

@Composable
private fun getAlgoKitTypographyBodyRegular(): AlgoKitTypography.Body.BodyRegular {
    val body =
        TextStyle(
            fontSize = 15.sp,
            lineHeight = 24.sp,
        )
    return AlgoKitTypography.Body.BodyRegular(
        sans =
            body.copy(
                fontFamily = FontFamily(Font(Res.font.dmsans_regular, FontWeight.Normal)),
            ),
        sansMedium =
            body.copy(
                fontFamily = FontFamily(Font(Res.font.dmsans_medium, FontWeight.Medium)),
            ),
        sansBold =
            body.copy(
                fontFamily = FontFamily(Font(Res.font.dmsans_bold, FontWeight.Bold)),
            ),
        mono =
            body.copy(
                fontFamily = FontFamily(Font(Res.font.dmmono_regular, FontWeight.Normal)),
            ),
        monoMedium =
            body.copy(
                fontFamily = FontFamily(Font(Res.font.dmmono_medium, FontWeight.Medium)),
            ),
    )
}

@Composable
private fun getAlgoKitTypographyBodyLarge(): AlgoKitTypography.Body.BodyLarge {
    val body =
        TextStyle(
            fontSize = 19.sp,
            lineHeight = 28.sp,
        )
    return AlgoKitTypography.Body.BodyLarge(
        sans =
            body.copy(
                fontFamily = FontFamily(Font(Res.font.dmsans_regular, FontWeight.Normal)),
            ),
        sansMedium =
            body.copy(
                fontFamily = FontFamily(Font(Res.font.dmsans_medium, FontWeight.Medium)),
            ),
        mono =
            body.copy(
                fontFamily = FontFamily(Font(Res.font.dmmono_regular, FontWeight.Normal)),
            ),
    )
}
