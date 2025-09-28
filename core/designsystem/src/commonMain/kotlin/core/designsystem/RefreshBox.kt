package core.designsystem

import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlin.math.max

@Composable
fun RefreshBox(
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    indicator: @Composable () -> Unit = { CircularProgressIndicator() },
    content: @Composable (topInsetPx: Float) -> Unit
) {
    var dragOffset by remember { mutableStateOf(0f) }
    val threshold = 120f
    val maxPull = 200f

    LaunchedEffect(isRefreshing) {
        if (!isRefreshing) {
            delay(120)
            dragOffset = 0f
        }
    }

    Box(
        Modifier
            .fillMaxSize()
            .pointerInput(isRefreshing) {
                detectVerticalDragGestures(
                    onVerticalDrag = { _, delta ->
                        if (!isRefreshing) {
                            dragOffset = (dragOffset + delta).coerceIn(0f, maxPull)
                        }
                    },
                    onDragEnd = {
                        if (dragOffset >= threshold && !isRefreshing) onRefresh()
                    }
                )
            }
    ) {
        if (isRefreshing || dragOffset > 0f) {
            Box(Modifier.padding(top = max(0f, dragOffset - 24f).dp).fillMaxWidth()) {
                Box(Modifier.padding(16.dp)) { indicator() }
            }
        }
        content(dragOffset)
    }
}