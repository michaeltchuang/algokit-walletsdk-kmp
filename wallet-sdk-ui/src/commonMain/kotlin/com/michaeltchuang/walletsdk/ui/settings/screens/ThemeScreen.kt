package com.michaeltchuang.walletsdk.ui.settings.screens

import algokit_walletsdk_kmp.wallet_sdk_ui.generated.resources.Res
import algokit_walletsdk_kmp.wallet_sdk_ui.generated.resources.dark
import algokit_walletsdk_kmp.wallet_sdk_ui.generated.resources.light
import algokit_walletsdk_kmp.wallet_sdk_ui.generated.resources.system_default
import algokit_walletsdk_kmp.wallet_sdk_ui.generated.resources.theme
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
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
import com.michaeltchuang.walletsdk.ui.base.designsystem.theme.LocalThemeIsDark
import com.michaeltchuang.walletsdk.ui.base.designsystem.theme.ThemePreference
import com.michaeltchuang.walletsdk.ui.base.designsystem.theme.ThemePreferenceRepository
import com.michaeltchuang.walletsdk.ui.base.designsystem.theme.provideThemePreferenceRepository
import com.michaeltchuang.walletsdk.ui.base.designsystem.widget.AlgoKitTopBar
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ThemeScreen(
    navController: NavController,
    themeRepository: ThemePreferenceRepository = provideThemePreferenceRepository(),
) {
    val coroutineScope = rememberCoroutineScope()
    val currentThemePreference by themeRepository
        .getSavedThemePreferenceFlow()
        .collectAsState(initial = null)
    val systemDark = isSystemInDarkTheme()
    val isDark = LocalThemeIsDark.current

    @Composable
    fun ThemePreference.displayString() =
        when (this) {
            ThemePreference.LIGHT -> stringResource(Res.string.light)
            ThemePreference.DARK -> stringResource(Res.string.dark)
            ThemePreference.SYSTEM -> stringResource(Res.string.system_default)
        }

    fun resolveIsDark(
        theme: ThemePreference,
        systemDark: Boolean,
    ): Boolean =
        when (theme) {
            ThemePreference.LIGHT -> false
            ThemePreference.DARK -> true
            ThemePreference.SYSTEM -> systemDark
        }

    fun onThemeSelected(theme: ThemePreference) {
        if (theme != currentThemePreference) {
            coroutineScope.launch {
                themeRepository.saveThemePreference(theme)
                isDark.value = resolveIsDark(theme, systemDark)
            }
        }
    }

    val themeOptions =
        remember(currentThemePreference) {
            ThemePreference.entries.map { pref ->
                ThemeListItem(
                    theme = pref,
                    isSelected = pref == currentThemePreference,
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
        AlgoKitTopBar(title = stringResource(Res.string.theme)) { navController.popBackStack() }
        Spacer(modifier = Modifier.height(8.dp))
        themeOptions.forEach { theme ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .clickable { onThemeSelected(theme.theme) }
                        .padding(vertical = 16.dp),
            ) {
                Text(
                    text = theme.theme.displayString(),
                    color = AlgoKitTheme.colors.textMain,
                    modifier = Modifier.weight(1f),
                    style = AlgoKitTheme.typography.body.regular.sansMedium,
                )
                RadioButton(
                    selected = theme.isSelected,
                    onClick = { onThemeSelected(theme.theme) },
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

private data class ThemeListItem(
    val theme: ThemePreference,
    val isSelected: Boolean,
)

@Preview
@Composable
fun ThemeScreenPreview() {
    AlgoKitTheme {
        ThemeScreen(
            rememberNavController(),
            themeRepository = provideThemePreferenceRepository(),
        )
    }
}
