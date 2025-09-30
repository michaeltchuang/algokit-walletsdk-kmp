package com.michaeltchuang.walletsdk.transaction.presentation.screens

import algokit_walletsdk_kmp.wallet_sdk.generated.resources.Res
import algokit_walletsdk_kmp.wallet_sdk.generated.resources.add_note
import algokit_walletsdk_kmp.wallet_sdk.generated.resources.confirm_transaction
import algokit_walletsdk_kmp.wallet_sdk.generated.resources.done
import algokit_walletsdk_kmp.wallet_sdk.generated.resources.enter_your_note
import algokit_walletsdk_kmp.wallet_sdk.generated.resources.note
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.michaeltchuang.walletsdk.account.presentation.components.AlgoKitScreens
import com.michaeltchuang.walletsdk.deeplink.model.DeepLink
import com.michaeltchuang.walletsdk.designsystem.theme.AlgoKitTheme
import com.michaeltchuang.walletsdk.designsystem.theme.AlgoKitTheme.typography
import com.michaeltchuang.walletsdk.designsystem.widget.AlgoKitTopBar
import com.michaeltchuang.walletsdk.designsystem.widget.button.AlgoKitPrimaryButton
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun TransactionSignatureRequestScreen(
    navController: NavController,
    keyReg: DeepLink.KeyReg,
) {
    Box(
        modifier =
            Modifier
                .background(color = AlgoKitTheme.colors.background)
                .fillMaxSize()
                .padding(16.dp),
    ) {
        Column {
            AlgoKitTopBar(
                title = "Transaction Signature Request",
                onClick = { navController.popBackStack() },
            )
            ContentScreen(keyReg)
        }

        AlgoKitPrimaryButton(
            onClick = {
                navController.navigate(AlgoKitScreens.TRANSACTION_SUCCESS_SCREEN.name)
            },
            text = stringResource(Res.string.confirm_transaction),
            modifier =
                Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth(),
        )
    }
}

@Composable
fun ContentScreen(keyReg: DeepLink.KeyReg) {
    Column(
        modifier =
            Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .padding(bottom = 72.dp),
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        // Address Section
        LabeledText(
            label = "Address",
            value = keyReg.senderAddress,
        )

        // Fee
        LabeledText(label = "Fee", value = ("\u00A6") + (keyReg.fee ?: "0.001"))

        // Type
        LabeledText(label = "Type", value = keyReg.type)

        Divider()

        LabeledText(label = "Vote Key", value = keyReg.voteKey ?: "")
        LabeledText(label = "Selection Key", value = keyReg.selkey ?: "")
        LabeledText(label = "State Proof Key", value = "")
        LabeledText(label = "Valid First Round", value = "")
        LabeledText(label = "Valid Last Round", value = keyReg.fee ?: "")
        LabeledText(label = "Vote Key Dilution", value = keyReg.votelst ?: "")
        Spacer(modifier = Modifier.height(8.dp))
        Divider()
        AddNote()
    }
}

@Composable
fun Divider() {
    HorizontalDivider(
        modifier = Modifier.padding(vertical = 16.dp),
        thickness = DividerDefaults.Thickness,
        color = AlgoKitTheme.colors.layerGray,
    )
}

@Composable
fun LabeledText(
    label: String,
    value: String,
) {
    Row(modifier = Modifier.padding(vertical = 16.dp)) {
        Text(
            modifier = Modifier.fillMaxWidth(.25f),
            text = label,
            color = AlgoKitTheme.colors.textGray,
            style = typography.body.regular.sansMedium,
        )
        Text(
            text = value,
            color = AlgoKitTheme.colors.textMain,
            style = typography.body.regular.sansMedium,
        )
    }
}

@Composable
fun AddNote() {
    var isAddNoteEnabled by remember { mutableStateOf(false) }
    var noteText by remember { mutableStateOf("") }
    Column {
        if (isAddNoteEnabled) {
            AddNoteTextField(noteText, {
                noteText = it
            }, {
                noteText = ""
            }, {
                isAddNoteEnabled = false
            })
        } else {
            Row(
                modifier =
                    Modifier.clickable(onClick = {
                        isAddNoteEnabled = true
                    }),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                LabeledText(stringResource(Res.string.note), "")
                if (noteText.isNotEmpty()) {
                    Text(
                        style = typography.body.regular.sansMedium,
                        text = noteText,
                        color = AlgoKitTheme.colors.textMain,
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = stringResource(Res.string.add_note),
                        tint = AlgoKitTheme.colors.textMain,
                    )
                    Text(
                        style = typography.body.large.sansMedium,
                        text = stringResource(Res.string.add_note),
                        color = AlgoKitTheme.colors.textMain,
                    )
                }
            }
        }
    }
}

@Composable
fun AddNoteTextField(
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

        // Bottom Line
        HorizontalDivider(
            thickness = 1.dp,
            color = Color.Gray,
        )
    }
}

@Preview
@Composable
fun PreviewTransactionDetailsScreen() {
    val keyReg =
        DeepLink.KeyReg(
            "ASDFGHJKLQWERTYUIOPZXCVBNM",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
        )
    AlgoKitTheme {
        TransactionSignatureRequestScreen(rememberNavController(), keyReg)
    }
}
