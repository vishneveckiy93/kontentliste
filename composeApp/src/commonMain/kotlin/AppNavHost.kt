import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import feature.accounts.presentation.AccountsViewModel
import navigation.AccountDetailRoute
import navigation.AccountsListRoute

@Composable
fun AppNavHost() {
    val nav = rememberNavController()

    NavHost(navController = nav, startDestination = AccountsListRoute) {
        composable<AccountsListRoute> {
            val vm: AccountsViewModel = viewModel { AccountsViewModel() }
        }

        composable<AccountDetailRoute> { backStackEntry ->
            val args: AccountsListRoute = backStackEntry.toRoute()
            // TODO: AccountDetailsScreen(args.id)
        }
    }
}