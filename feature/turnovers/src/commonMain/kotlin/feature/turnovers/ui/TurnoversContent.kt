package feature.turnovers.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import feature.turnovers.presentation.TurnoversState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TurnoversContent(
    state: TurnoversState
) {
    Scaffold(topBar = { TopAppBar(title = { Text("Turnovers") }) }) { padding ->
        Box(Modifier.fillMaxSize().padding(padding)) {
            if (state.error != null) {
                Text("Error: ${state.error}", modifier = Modifier.padding(16.dp))
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(state.items) { t -> TurnoverItemCard(t) }
                }
            }
        }
    }
}