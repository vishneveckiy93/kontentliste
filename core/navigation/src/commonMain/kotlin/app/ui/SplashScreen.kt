package app.ui

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onFinished: () -> Unit) {
    var started by remember { mutableStateOf(false) }
    val alpha by animateFloatAsState(
        targetValue = if (started) 1F else 0F,
        animationSpec = tween(durationMillis = 600, easing = LinearEasing),
        label = "splashAlpha"
    )

    LaunchedEffect(Unit) {
        started = true
        delay(900)
        onFinished()
    }

    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("KontenListe", style = MaterialTheme.typography.headlineLarge, modifier = Modifier.alpha(alpha))
            Spacer(Modifier.height(12.dp))
            CircularProgressIndicator(Modifier.alpha(alpha))
        }
    }
}