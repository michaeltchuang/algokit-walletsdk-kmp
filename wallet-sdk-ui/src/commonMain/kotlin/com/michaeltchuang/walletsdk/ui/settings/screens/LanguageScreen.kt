package com.michaeltchuang.walletsdk.ui.settings.screens

import algokit_walletsdk_kmp.wallet_sdk_ui.generated.resources.Res
import algokit_walletsdk_kmp.wallet_sdk_ui.generated.resources.english
import algokit_walletsdk_kmp.wallet_sdk_ui.generated.resources.italian
import algokit_walletsdk_kmp.wallet_sdk_ui.generated.resources.localization_screen_title
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.michaeltchuang.walletsdk.ui.base.designsystem.theme.AlgoKitTheme
import com.michaeltchuang.walletsdk.ui.base.designsystem.widget.AlgoKitTopBar
import com.michaeltchuang.walletsdk.ui.settings.domain.localization.LocalizationPreference
import com.michaeltchuang.walletsdk.ui.settings.domain.localization.LocalizationPreferenceRepository
import com.michaeltchuang.walletsdk.ui.settings.domain.localization.provideLocalizationPreferenceRepository
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun LanguageScreen(
    navController: NavController,
    localizationRepository: LocalizationPreferenceRepository = provideLocalizationPreferenceRepository(),
) {
    val coroutineScope = rememberCoroutineScope()
    val currentLocalizationPreference by localizationRepository
        .getSavedLocalizationPreferenceFlow()
        .collectAsState(initial = null)

    @Composable
    fun LocalizationPreference.displayString() =
        when (this) {
            LocalizationPreference.ITALIAN -> stringResource(Res.string.italian)
            LocalizationPreference.ENGLISH -> stringResource(Res.string.english)
        }

    fun onLanguageSelected(localization: LocalizationPreference) {
        if (localization != currentLocalizationPreference) {
            coroutineScope.launch {
                localizationRepository.saveLocalizationPreference(localization)
            }
        }
    }

    val localizationOptions =
        remember(currentLocalizationPreference) {
            LocalizationPreference.entries.map { pref ->
                LanguageListItem(
                    language = pref,
                    isSelected = pref == currentLocalizationPreference,
                )
            }
        }

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .background(color = AlgoKitTheme.colors.background)
                .padding(horizontal = 16.dp),
    ) {
        AlgoKitTopBar(title = stringResource(Res.string.localization_screen_title)) { navController.popBackStack() }
        Spacer(modifier = Modifier.height(8.dp))
        localizationOptions.forEach { localization ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .clickable { onLanguageSelected(localization.language) }
                        .padding(vertical = 16.dp),
            ) {
                Text(
                    text = localization.language.displayString(),
                    color = AlgoKitTheme.colors.textMain,
                    modifier = Modifier.weight(1f),
                    style = AlgoKitTheme.typography.body.regular.sansMedium,
                )
                RadioButton(
                    selected = localization.isSelected,
                    onClick = { onLanguageSelected(localization.language) },
                    colors =
                        RadioButtonDefaults.colors(
                            selectedColor = AlgoKitTheme.colors.positive,
                            unselectedColor = Color.LightGray,
                        ),
                )
            }
        }
    }
}

private data class LanguageListItem(
    val language: LocalizationPreference,
    val isSelected: Boolean,
)

@Preview
@Composable
fun LanguageScreenPreview() {
    AlgoKitTheme {
        LanguageScreen(
            rememberNavController(),
            localizationRepository = provideLocalizationPreferenceRepository(),
        )
    }
}
