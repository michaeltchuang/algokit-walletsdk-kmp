package com.michaeltchuang.walletsdk.ui.signing.screens

import algokit_walletsdk_kmp.wallet_sdk_ui.generated.resources.Res
import algokit_walletsdk_kmp.wallet_sdk_ui.generated.resources.add_note
import algokit_walletsdk_kmp.wallet_sdk_ui.generated.resources.done
import algokit_walletsdk_kmp.wallet_sdk_ui.generated.resources.edit_note
import algokit_walletsdk_kmp.wallet_sdk_ui.generated.resources.enter_your_note
import algokit_walletsdk_kmp.wallet_sdk_ui.generated.resources.ic_algo_round
import algokit_walletsdk_kmp.wallet_sdk_ui.generated.resources.ic_asa_trusted
import algokit_walletsdk_kmp.wallet_sdk_ui.generated.resources.ic_delete
import algokit_walletsdk_kmp.wallet_sdk_ui.generated.resources.ic_wallet
import algokit_walletsdk_kmp.wallet_sdk_ui.generated.resources.max
import algokit_walletsdk_kmp.wallet_sdk_ui.generated.resources.next
import algokit_walletsdk_kmp.wallet_sdk_ui.generated.resources.send_algo
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.michaeltchuang.walletsdk.core.foundation.utils.toShortenedAddress
import com.michaeltchuang.walletsdk.core.utils.toAlgoAmount
import com.michaeltchuang.walletsdk.ui.base.designsystem.theme.AlgoKitTheme
import com.michaeltchuang.walletsdk.ui.base.designsystem.theme.AlgoKitTheme.typography
import com.michaeltchuang.walletsdk.ui.base.designsystem.widget.button.AlgoKitButtonState
import com.michaeltchuang.walletsdk.ui.base.designsystem.widget.button.AlgoKitPrimaryButton
import com.michaeltchuang.walletsdk.ui.base.designsystem.widget.icon.AlgoKitIcon
import com.michaeltchuang.walletsdk.ui.base.designsystem.widget.icon.AlgoKitIconRoundShapeSmall
import com.michaeltchuang.walletsdk.ui.base.navigation.AlgoKitScreens
import com.michaeltchuang.walletsdk.ui.signing.viewmodels.SendAlgoViewModel
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SendAlgoScreen(
    navController: NavController,
    senderAddress: String = "",
) {
    val viewModel: SendAlgoViewModel = koinViewModel()
    val viewState by viewModel.state.collectAsState()
    val events = viewModel.viewEvent.collectAsStateWithLifecycle(initialValue = null)
    val noteText = remember { mutableStateOf("") }

    LaunchedEffect(events.value) {
        events.value?.let { event ->
            when (event) {
                is SendAlgoViewModel.ViewEvent.NavigateNext -> {
                    navController.navigate(
                        "${AlgoKitScreens.SELECT_RECEIVER_SCREEN.name}?sender=$senderAddress&amount=${event.amount}&note=${noteText.value}",
                    )
                }
            }
        }
    }

    LaunchedEffect(senderAddress) {
        if (senderAddress.isNotEmpty()) {
            viewModel.fetchAccountBalance(senderAddress)
        }
    }

    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .background(AlgoKitTheme.colors.background),
    ) {
        Column {
            // Top Bar
            SendAlgoTopBar(
                senderAddress = senderAddress,
                onBackClick = { navController.popBackStack() },
                onInfoClick = {
                    navController.navigate(AlgoKitScreens.TRANSACTING_TIPS_SCREEN.name)
                },
            )

            // Content
            when (val state = viewState) {
                is SendAlgoViewModel.ViewState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text("Loading...", color = AlgoKitTheme.colors.textMain)
                    }
                }

                is SendAlgoViewModel.ViewState.Content -> {
                    SendAlgoContent(
                        state = state,
                        noteText = noteText,
                        onAmountChange = { digit -> viewModel.onDigitPressed(digit) },
                        onDeletePressed = { viewModel.onDeletePressed() },
                        onMaxPressed = { viewModel.onMaxPressed() },
                        onNextPressed = { viewModel.onNextPressed() },
                    )
                }

                is SendAlgoViewModel.ViewState.Error -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(state.message, color = AlgoKitTheme.colors.negative)
                    }
                }
            }
        }
    }
}

@Composable
private fun SendAlgoTopBar(
    senderAddress: String,
    onBackClick: () -> Unit,
    onInfoClick: () -> Unit,
) {
    Box(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
    ) {
        IconButton(onClick = onBackClick) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = AlgoKitTheme.colors.textMain,
            )
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = stringResource(Res.string.send_algo),
                style = typography.body.regular.sansMedium,
                color = AlgoKitTheme.colors.textMain,
            )
            if (senderAddress.isNotEmpty()) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 4.dp),
                ) {
                    AlgoKitIconRoundShapeSmall(
                        imageVector = vectorResource(Res.drawable.ic_wallet),
                        contentDescription = "Wallet Icon",
                        backgroundColor = AlgoKitTheme.colors.wallet4,
                    )
                    Text(
                        text = senderAddress.toShortenedAddress(),
                        style = typography.caption.sansMedium,
                        color = AlgoKitTheme.colors.textGray,
                        modifier = Modifier.padding(start = 8.dp),
                    )
                }
            }
        }

        IconButton(onClick = onInfoClick, modifier = Modifier.align(Alignment.CenterEnd)) {
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = "Info",
                tint = AlgoKitTheme.colors.textMain,
            )
        }
    }
}

@Composable
private fun SendAlgoContent(
    state: SendAlgoViewModel.ViewState.Content,
    noteText: MutableState<String>,
    onAmountChange: (String) -> Unit,
    onDeletePressed: () -> Unit,
    onMaxPressed: () -> Unit,
    onNextPressed: () -> Unit,
) {
    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        // Amount Display
        AmountDisplaySection(
            noteText = noteText,
            amount = state.amount,
            usdValue = state.usdValue,
            showUSDAmount = state.showUSDAmount,
            onMaxPressed = onMaxPressed,
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Number Keypad
        NumberKeypad(
            onDigitPressed = onAmountChange,
            onDeletePressed = onDeletePressed,
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Asset Selection
        AssetSelectionCard(
            assetName = "Algo",
            assetSymbol = "ALGO",
            balance = state.balance,
            usdValue = state.usdValue,
            showUSDAmount = state.showUSDAmount,
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Next Button
        AlgoKitPrimaryButton(
            onClick = onNextPressed,
            text = stringResource(Res.string.next),
            modifier = Modifier.fillMaxWidth(),
            state =
                if (state.amount.isNotEmpty() && state.amount != "0") {
                    AlgoKitButtonState.ENABLED
                } else {
                    AlgoKitButtonState.DISABLED
                },
        )

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun AmountDisplaySection(
    noteText: MutableState<String>,
    amount: String,
    usdValue: String,
    showUSDAmount: Boolean,
    onMaxPressed: () -> Unit,
) {
    var isAddNoteEnabled by remember { mutableStateOf(false) }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth(),
    ) {
        // Main amount
        Text(
            text = amount.toAlgoAmount().ifEmpty { "0" },
            style =
                typography.title.large.sansMedium.copy(
                    fontSize = 48.sp,
                ),
            color = AlgoKitTheme.colors.textMain,
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(8.dp))

        // USD value
        if (showUSDAmount) {
            Text(
                text = usdValue,
                style = typography.body.large.sansMedium,
                color = AlgoKitTheme.colors.textGray,
                textAlign = TextAlign.Center,
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Action buttons row
        Row(
            horizontalArrangement = Arrangement.spacedBy(32.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (isAddNoteEnabled.not()) {
                Text(
                    text = if (noteText.value.isBlank()) stringResource(Res.string.add_note) else stringResource(Res.string.edit_note),
                    style = typography.body.regular.sansMedium,
                    color = AlgoKitTheme.colors.textGray,
                    modifier =
                        Modifier.clickable {
                            isAddNoteEnabled = true
                        },
                )
            }

            Text(
                text = stringResource(Res.string.max),
                style = typography.body.regular.sansMedium,
                color = AlgoKitTheme.colors.textGray,
                modifier = Modifier.clickable { onMaxPressed() },
            )
        }

        if (isAddNoteEnabled) {
            Spacer(modifier = Modifier.height(16.dp))
            SendAlgoAddNoteTextField(
                noteText.value,
                onValueChange = {
                    noteText.value = it
                },
                onClearClick = {
                    noteText.value = ""
                },
                onDoneClick = {
                    isAddNoteEnabled = false
                },
            )
        }
    }
}

@Composable
private fun NumberKeypad(
    onDigitPressed: (String) -> Unit,
    onDeletePressed: () -> Unit,
) {
    val keys =
        listOf(
            listOf("1", "2", "3"),
            listOf("4", "5", "6"),
            listOf("7", "8", "9"),
            listOf(".", "0", "10"),
        )

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        keys.forEach { row ->
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth(),
            ) {
                row.forEach { key ->
                    KeypadButton(
                        text = key,
                        icon =
                            if (key == "10") {
                                painterResource(Res.drawable.ic_delete)
                            } else {
                                null
                            },
                        onClick = {
                            when (key) {
                                "10" -> onDeletePressed()
                                else -> onDigitPressed(key)
                            }
                        },
                    )
                }
            }
        }
    }
}

@Composable
private fun KeypadButton(
    text: String,
    icon: Painter? = null,
    onClick: () -> Unit,
) {
    Surface(
        modifier =
            Modifier
                .size(75.dp)
                .clickable { onClick() },
        color = Color.Transparent,
    ) {
        Box(
            contentAlignment = Alignment.Center,
        ) {
            if (icon != null) {
                Icon(
                    painter = icon,
                    contentDescription = text,
                    tint = AlgoKitTheme.colors.textMain,
                )
            } else {
                Text(
                    text = text,
                    style =
                        typography.body.regular.sansMedium.copy(
                            fontSize = 20.sp,
                        ),
                    color = AlgoKitTheme.colors.textMain,
                )
            }
        }
    }
}

@Composable
private fun AssetSelectionCard(
    assetName: String,
    assetSymbol: String,
    balance: String?,
    usdValue: String?,
    showUSDAmount: Boolean,
) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            // Asset icon - using a simple circle with "A" for Algorand
            Box(
                modifier =
                    Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(AlgoKitTheme.colors.algoIconBg),
                contentAlignment = Alignment.Center,
            ) {
                AlgoKitIcon(
                    painter = painterResource(Res.drawable.ic_algo_round),
                    contentDescription = "Asset Icon",
                    modifier = Modifier,
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = assetName,
                        style = typography.body.regular.sansMedium,
                        color = AlgoKitTheme.colors.textMain,
                    )
                    Spacer(modifier = Modifier.width(4.dp))

                    AlgoKitIcon(
                        painter = painterResource(Res.drawable.ic_asa_trusted),
                        contentDescription = "Asset Icon",
                        modifier = Modifier,
                    )
                }
                Text(
                    text = assetSymbol,
                    style = typography.caption.sansMedium,
                    color = AlgoKitTheme.colors.textGray,
                )
            }
        }

        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = balance ?: "Loading...",
                style = typography.body.regular.sansMedium,
                color = AlgoKitTheme.colors.textMain,
            )
            if (showUSDAmount) {
                Text(
                    text = usdValue ?: "Loading...",
                    style = typography.caption.sansMedium,
                    color = AlgoKitTheme.colors.textGray,
                )
            }
        }
    }
}

@Composable
fun SendAlgoAddNoteTextField(
    value: String,
    onValueChange: (String) -> Unit,
    onClearClick: () -> Unit,
    onDoneClick: () -> Unit,
) {
    Column(
        Modifier.fillMaxWidth(),
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                style = typography.body.regular.sansMedium,
                color = AlgoKitTheme.colors.textMain,
                text = stringResource(Res.string.enter_your_note),
            )

            Text(
                modifier = Modifier.clickable(onClick = onDoneClick),
                style = typography.body.regular.sansMedium,
                color = AlgoKitTheme.colors.textMain,
                text = stringResource(Res.string.done),
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
        ) {
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                modifier =
                    Modifier
                        .weight(1f)
                        .padding(vertical = 8.dp),
                singleLine = true,
                textStyle =
                    LocalTextStyle.current.copy(
                        color = AlgoKitTheme.colors.textMain,
                        fontSize = 16.sp,
                    ),
            )
            IconButton(onClick = onClearClick) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Clear",
                    tint = Color.Gray,
                )
            }
        }
        HorizontalDivider(
            thickness = 1.dp,
            color = Color.Gray,
        )
    }
}

@Preview
@Composable
fun PreviewSendAlgoScreen() {
    AlgoKitTheme {
        Box(
            modifier =
                Modifier
                    .fillMaxSize()
                    .background(AlgoKitTheme.colors.background),
        ) {
            Column {
                // Top Bar Preview
                SendAlgoTopBar(
                    senderAddress = "QQVR...ZIXU",
                    onBackClick = { },
                    onInfoClick = { },
                )

                // Content Preview with mock state
                SendAlgoContent(
                    state = MockSendAlgoViewState(),
                    noteText = mutableStateOf(""),
                    onAmountChange = { },
                    onDeletePressed = { },
                    onMaxPressed = { },
                    onNextPressed = { },
                )
            }
        }
    }
}

@Composable
private fun MockSendAlgoViewState(): SendAlgoViewModel.ViewState.Content =
    SendAlgoViewModel.ViewState.Content(
        amount = "176238.12345678",
        usdValue = "$1.76",
        balance = "10.00",
        assetUsdValue = "$1.99",
    )
