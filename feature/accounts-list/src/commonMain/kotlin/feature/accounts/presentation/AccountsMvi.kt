package feature.accounts.presentation

import feature.accounts.domain.AccountsRepository
import feature.accounts.domain.models.Account
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class AccountsState(
    val isLoading: Boolean = false,
    val items: List<Account> = emptyList(),
    val error: String? = null
)

sealed interface AccountsEvent {
    data object Refresh : AccountsEvent
}

class AccountsViewModel(
    private val repo: AccountsRepository,
    private val scope: CoroutineScope
) {

    private val _state = MutableStateFlow(AccountsState())
    val state: StateFlow<AccountsState> = _state

    fun onEvent(event: AccountsEvent) {
        when (event) {
            AccountsEvent.Refresh -> refresh()
        }
    }

    private fun refresh() {
        _state.update { it.copy(isLoading = true, error = null) }
        scope.launch {
            runCatching { repo.getAccounts() }
                .onSuccess { list -> _state.update { it.copy(isLoading = false, items = list) } }
                .onFailure { e -> _state.update { it.copy(isLoading = false, error = e.message) } }
        }
    }


}