package co.algorand.app.ui.components

import algokit_walletsdk_kmp.composetestapp.generated.resources.Res
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import io.github.alexzhirkevich.compottie.LottieAnimation
import io.github.alexzhirkevich.compottie.LottieCompositionSpec
import io.github.alexzhirkevich.compottie.animateLottieCompositionAsState
import io.github.alexzhirkevich.compottie.rememberLottieComposition
import org.jetbrains.compose.resources.ExperimentalResourceApi

@OptIn(ExperimentalResourceApi::class)
@Composable
fun LottieConfetti(modifier: Modifier = Modifier) {
    var animationData by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        animationData = Res.readBytes("files/confetti_animation.json").decodeToString()
    }

    val composition by rememberLottieComposition(
        LottieCompositionSpec.JsonString(animationData),
    )
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = 1,
        speed = 1.5f,
    )
    LottieAnimation(
        composition = composition,
        progress = { progress },
        modifier = modifier,
    )
}
