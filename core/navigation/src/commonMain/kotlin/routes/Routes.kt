package routes

import kotlinx.serialization.Serializable

@Serializable object SplashRoute
@Serializable object AccountsListRoute
@Serializable data class AccountDetailRoute(val id: Int)