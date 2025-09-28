package feature.turnovers.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import core.designsystem.RefreshBox
import feature.turnovers.presentation.TurnoversViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TurnoversScreen(vm: TurnoversViewModel) {
    LaunchedEffect(vm) { vm.load() }

    val state by vm.state.collectAsState()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Transactionen") }) }
    ) { padding ->
        Box(Modifier.fillMaxSize().padding(padding)) {
            RefreshBox(
                isRefreshing = state.isLoading,
                onRefresh = { vm.refresh() }
            ) {
                topInsetPx ->
                when {
                    state.error != null -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Error: ${state.error}")
                    }
                    else -> LazyColumn(
                        modifier = Modifier.fillMaxSize().padding(top = (topInsetPx / 2).dp),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(state.items) { t ->
                            TurnoverItemCard(t)
                        }
                    }
                }
            }
        }
    }
}