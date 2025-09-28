import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import app.ui.SplashScreen
import feature.accounts.presentation.AccountsViewModel
import feature.accounts.ui.AccountsListScreen
import feature.turnovers.presentation.TurnoversViewModel
import feature.turnovers.ui.TurnoversScreen
import routes.AccountTurnoversRoute
import routes.AccountsListRoute
import routes.SplashRoute

@Composable
fun AppNavHost() {
    val nav = rememberNavController()

    NavHost(
        navController = nav,
        startDestination = SplashRoute,
    ) {
        composable<SplashRoute>(
            exitTransition = { fadeOut(animationSpec = tween(durationMillis = 900)) }
        ) {
            SplashScreen {
                nav.navigate(AccountsListRoute) {
                    popUpTo<SplashRoute> { inclusive = true }
                }
            }
        }

        composable<AccountsListRoute>(
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { it/ 3 },
                    animationSpec = tween(durationMillis = 900, easing = LinearOutSlowInEasing)
                ) + fadeIn(animationSpec = tween(450))
            },
            popExitTransition = {
                fadeOut(animationSpec = tween(250))
            }
        ) {
            val vm: AccountsViewModel = viewModel { AccountsViewModel() }
            AccountsListScreen(vm = vm) { id ->
                nav.navigate(AccountTurnoversRoute(id))
            }
        }

        composable<AccountTurnoversRoute> { backStackEntry ->
            val args: AccountTurnoversRoute = backStackEntry.toRoute()

            val vm: TurnoversViewModel = viewModel { TurnoversViewModel(accountId = args.id) }
            TurnoversScreen(vm)
        }
    }
}