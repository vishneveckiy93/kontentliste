package feature.turnovers.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import feature.turnovers.presentation.TurnoversState
import feature.turnovers.presentation.TurnoversViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TurnoversRouteScreen(accountId: Int) {
    val vm: TurnoversViewModel = viewModel { TurnoversViewModel(accountId) }
    LaunchedEffect(vm) { vm.load() }

    val state: TurnoversState by vm.state.collectAsState()
    val ptr = rememberPullToRefreshState()

    PullToRefreshBox(
        isRefreshing = state.isLoading,
        onRefresh = { vm.refresh() },
        state = ptr,
        modifier = Modifier.fillMaxSize(),
        indicator = {
            PullToRefreshDefaults.Indicator(
                modifier = Modifier.align(Alignment.TopCenter),
                isRefreshing = state.isLoading,
                state = ptr
            )
        }
    ) {
        TurnoversContent(state = state)
    }
}