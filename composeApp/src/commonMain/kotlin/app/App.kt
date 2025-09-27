package app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import feature.accounts.presentation.AccountsEvent
import feature.accounts.presentation.AccountsViewModel
import feature.accounts.ui.AccountsListScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(vm: AccountsViewModel) {
    LaunchedEffect(Unit) { vm.onEvent(AccountsEvent.Refresh) }
    AccountsListScreen(vm)
}