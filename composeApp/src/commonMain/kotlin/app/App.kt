package app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cherryyar.kontentliste.Greeting
import feature.accounts.presentation.AccountsEvent
import feature.accounts.presentation.AccountsViewModel
import feature.accounts.ui.AccountsListScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    val vm: AccountsViewModel = viewModel { AccountsViewModel() }
    LaunchedEffect(Unit) { vm.onEvent(AccountsEvent.Refresh) }
    AccountsListScreen(vm)
}