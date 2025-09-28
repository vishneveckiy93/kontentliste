import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import app.ui.SplashScreen
import feature.accounts.ui.AccountsListRouteScreen
import feature.turnovers.ui.TurnoversRouteScreen
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
            AccountsListRouteScreen { id ->
                nav.navigate(AccountTurnoversRoute(id))
            }
        }

        composable<AccountTurnoversRoute> { backStackEntry ->
            val args: AccountTurnoversRoute = backStackEntry.toRoute()

            TurnoversRouteScreen(args.id)
        }
    }
}