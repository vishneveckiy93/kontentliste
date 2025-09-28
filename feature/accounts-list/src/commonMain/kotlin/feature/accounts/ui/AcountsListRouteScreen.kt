package feature.accounts.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import feature.accounts.presentation.AccountsState
import feature.accounts.presentation.AccountsViewModel

@Composable
fun AccountsListRouteScreen(
    onAccountClick: (Int) -> Unit
) {
    val vm: AccountsViewModel = viewModel { AccountsViewModel() }
    LaunchedEffect(vm) { vm.load() }

    val state: AccountsState by vm.state.collectAsState()

    AccountListContent(
        state = state,
        onAccountClick = onAccountClick
    )
}