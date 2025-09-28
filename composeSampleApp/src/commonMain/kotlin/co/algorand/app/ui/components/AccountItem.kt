package co.algorand.app.ui.components

import algokit_walletsdk_kmp.composesampleapp.generated.resources.Res
import algokit_walletsdk_kmp.composesampleapp.generated.resources.ic_hd_wallet
import algokit_walletsdk_kmp.composesampleapp.generated.resources.ic_wallet
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.michaeltchuang.walletsdk.account.domain.model.core.AccountRegistrationType
import com.michaeltchuang.walletsdk.account.domain.model.custom.AccountLite
import com.michaeltchuang.walletsdk.designsystem.theme.AlgoKitTheme
import com.michaeltchuang.walletsdk.designsystem.theme.AlgoKitTheme.typography
import com.michaeltchuang.walletsdk.designsystem.widget.icon.AlgoKitIconRoundShape
import com.michaeltchuang.walletsdk.utils.toShortenedAddress
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun AccountItem(
    account: AccountLite,
    onAccountItemClick: (address: String) -> Unit,
) {
    Card(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AlgoKitIconRoundShape(
                modifier = Modifier,
                imageVector = vectorResource(getWalletIcon(account.registrationType)),
                contentDescription = "Wallet Icon",
            )
            Column(
                modifier =
                    Modifier
                        .fillMaxWidth(.8f)
                        .padding(horizontal = 8.dp),
            ) {
                Text(
                    modifier =
                        Modifier.clickable(onClick = {
                            onAccountItemClick(account.address)
                        }),
                    text = account.customName.ifEmpty { account.address.toShortenedAddress() },
                    style = typography.body.large.sansMedium,
                )
                Text(
                    text = getAccountType(account.registrationType),
                    style = typography.footnote.mono,
                )
            }

            Text(text = ("\u00A6") + "***", fontSize = 16.sp, style = typography.footnote.sansMedium)
        }
    }
}

fun getWalletIcon(localAccount: AccountRegistrationType): DrawableResource =
    when (localAccount) {
        is AccountRegistrationType.HdKey -> {
            Res.drawable.ic_hd_wallet
        }

        is AccountRegistrationType.Algo25 -> {
            Res.drawable.ic_wallet
        }

        else -> {
            Res.drawable.ic_wallet
        }
    }

fun getAccountType(localAccount: AccountRegistrationType): String =
    when (localAccount) {
        is AccountRegistrationType.HdKey -> {
            "HD"
        }

        is AccountRegistrationType.Algo25 -> {
            "Algo25"
        }

        is AccountRegistrationType.NoAuth -> {
            "Watch"
        }

        is AccountRegistrationType.LedgerBle -> {
            "Ledger"
        }
    } + " Account"

@Preview()
@Composable
fun AccountItemPreview() {
    // Create a sample account for preview
    val sampleAccount =
        AccountLite(
            address = "ASDFGHJKLASDFGHJKL",
            customName = "Sample Account",
            registrationType = AccountRegistrationType.Algo25,
        )

    AlgoKitTheme {
        AccountItem(
            account = sampleAccount,
            onAccountItemClick = { /* Preview - no action */ },
        )
    }
}
