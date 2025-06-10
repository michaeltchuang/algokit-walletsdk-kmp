package co.algorand.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.michaeltchuang.walletsdk.ui.typography.AlgoKitTypography
import kotlinx.serialization.Serializable

@Serializable
data object AlgoKitTypographyPreviewScreenNavigation

@Composable
fun AlgoKitTypographyPreviewScreen() {
    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        TitlePreview()
        BodyPreview()
        FootnotePreview()
        CaptionPreview()
    }
}

@Composable
private fun TitlePreview() {
    val typography = AlgoKitTypography()
    Column {
        SectionTitle("Title Small")
        Text(
            text = "Sans",
            style = typography.title.small.sans,
        )
        Text(
            text = "Sans Medium",
            style = typography.title.small.sansMedium,
        )
        SectionTitle("Title Regular")
        Text(
            text = "Sans",
            style = typography.title.regular.sans,
        )
        Text(
            text = "Sans Medium",
            style = typography.title.regular.sansMedium,
        )
        Text(
            text = "Sans Bold",
            style = typography.title.regular.sansBold,
        )
        SectionTitle("Title Large")
        Text(
            text = "Sans",
            style = typography.title.large.sans,
        )
        Text(
            text = "Sans Medium",
            style = typography.title.large.sansMedium,
        )
        Text(
            text = "Mono",
            style = typography.title.large.mono,
        )
        Text(
            text = "Medium",
            style = typography.title.large.monoMedium,
        )
    }
}

@Composable
private fun BodyPreview() {
    val typography = AlgoKitTypography()
    Column {
        SectionTitle("Body Regular")
        Text(
            text = "Sans",
            style = typography.body.regular.sans,
        )
        Text(
            text = "Sans Medium",
            style = typography.body.regular.sansMedium,
        )
        Text(
            text = "Sans Bold",
            style = typography.body.regular.sansBold,
        )
        Text(
            text = "Mono",
            style = typography.body.regular.mono,
        )
        Text(
            text = "Mono Medium",
            style = typography.body.regular.monoMedium,
        )
        SectionTitle("Body Large")
        Text(
            text = "Sans",
            style = typography.body.large.sans,
        )
        Text(
            text = "Sans Medium",
            style = typography.body.large.sansMedium,
        )
        Text(
            text = "Mono",
            style = typography.body.large.mono,
        )
    }
}

@Composable
private fun FootnotePreview() {
    val typography = AlgoKitTypography()
    Column {
        SectionTitle("Footnote")
        Text(
            text = "Sans",
            style = typography.footnote.sans,
        )
        Text(
            text = "Sans Bold",
            style = typography.footnote.sansBold,
        )
        Text(
            text = "Sans Medium",
            style = typography.footnote.sansMedium,
        )
        Text(
            text = "Mono",
            style = typography.footnote.mono,
        )
        Text(
            text = "Mono Medium",
            style = typography.footnote.monoMedium,
        )
    }
}

@Composable
private fun CaptionPreview() {
    val typography = AlgoKitTypography()
    Column {
        SectionTitle("Caption")
        Text(
            text = "Sans",
            style = typography.caption.sans,
        )
        Text(
            text = "Sans Bold",
            style = typography.caption.sansBold,
        )
        Text(
            text = "Sans Medium",
            style = typography.caption.sansMedium,
        )
        Text(
            text = "Mono",
            style = typography.caption.mono,
        )
        Text(
            text = "Mono Medium",
            style = typography.caption.monoMedium,
        )
    }
}

@Composable
private fun SectionTitle(title: String) {
    Text(
        modifier =
            Modifier
                .fillMaxWidth()
                .background(Color.LightGray, shape = RoundedCornerShape(16.dp))
                .padding(horizontal = 16.dp),
        text = title,
        style = AlgoKitTypography().body.large.sansMedium,
    )
}
