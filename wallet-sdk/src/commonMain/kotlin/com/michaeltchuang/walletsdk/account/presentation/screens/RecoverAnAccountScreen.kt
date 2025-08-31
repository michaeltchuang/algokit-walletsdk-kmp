package com.michaeltchuang.walletsdk.account.presentation.screens

import algokit_walletsdk_kmp.wallet_sdk.generated.resources.Res
import algokit_walletsdk_kmp.wallet_sdk.generated.resources.bottom_sheet_mnemonic_type_title
import algokit_walletsdk_kmp.wallet_sdk.generated.resources.legacy_text
import algokit_walletsdk_kmp.wallet_sdk.generated.resources.mnemonic_type_algo25_description
import algokit_walletsdk_kmp.wallet_sdk.generated.resources.mnemonic_type_algo25_footer
import algokit_walletsdk_kmp.wallet_sdk.generated.resources.mnemonic_type_algo25_title
import algokit_walletsdk_kmp.wallet_sdk.generated.resources.mnemonic_type_universal_description
import algokit_walletsdk_kmp.wallet_sdk.generated.resources.mnemonic_type_universal_footer
import algokit_walletsdk_kmp.wallet_sdk.generated.resources.mnemonic_type_universal_title
import algokit_walletsdk_kmp.wallet_sdk.generated.resources.new_text
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.michaeltchuang.walletsdk.account.presentation.components.AlgoKitScreens
import com.michaeltchuang.walletsdk.designsystem.theme.AlgoKitTheme
import com.michaeltchuang.walletsdk.designsystem.widget.AlgoKitTopBar
import com.michaeltchuang.walletsdk.designsystem.widget.PeraCard
import com.michaeltchuang.walletsdk.designsystem.widget.text.AlgoKitHighlightedGrayText
import com.michaeltchuang.walletsdk.designsystem.widget.text.AlgoKitHighlightedGreenText
import com.michaeltchuang.walletsdk.designsystem.widget.text.AlgoKitTitleText
import com.michaeltchuang.walletsdk.utils.WalletSdkConstants
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
fun RecoverAnAccountScreen(
    navController: NavController,
    onClick: (message: String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(16.dp))
            .background(AlgoKitTheme.colors.background)
    ) {
        AlgoKitTopBar(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .wrapContentSize(),
            onClick = { navController.popBackStack() }
        )

        AlgoKitTitleText(
            modifier = Modifier.padding(horizontal = 24.dp),
            text = stringResource(Res.string.bottom_sheet_mnemonic_type_title)
        )
        Spacer(Modifier.height(24.dp))
        PeraCard(
            title = stringResource(Res.string.mnemonic_type_universal_title),
            description = stringResource(Res.string.mnemonic_type_universal_description),
            footer = stringResource(Res.string.mnemonic_type_universal_footer),
            highlightContent = {
                AlgoKitHighlightedGreenText(
                    text = stringResource(Res.string.new_text)
                )
            },
            onClick = {
                onClick(WalletSdkConstants.FEATURE_NOT_SUPPORTED_YET)
            }
        )

        PeraCard(
            title = stringResource(Res.string.mnemonic_type_algo25_title),
            description = stringResource(Res.string.mnemonic_type_algo25_description),
            footer = stringResource(Res.string.mnemonic_type_algo25_footer),
            highlightContent = {
                AlgoKitHighlightedGrayText(
                    text = stringResource(Res.string.legacy_text)
                )
            },
            onClick = {
                navController.navigate(AlgoKitScreens.RECOVER_PHRASE_SCREEN.name + "/")
            }
        )
    }
}

@Preview
@Composable
fun RecoverAnAccountScreenPreview() {
    AlgoKitTheme {
        RecoverAnAccountScreen(navController = rememberNavController()) {

        }
    }
}
