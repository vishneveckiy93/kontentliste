package feature.accounts.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import feature.accounts.domain.AccountsRepository
import feature.accounts.domain.models.Account
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

data class AccountsState(
    val isLoading: Boolean = false,
    val items: List<Account> = emptyList(),
    val error: String? = null
)

sealed interface AccountsEvent {
    data object Load : AccountsEvent
}

class AccountsViewModel(): ViewModel(), KoinComponent {

    private val repo: AccountsRepository by inject()

    private val _state = MutableStateFlow(AccountsState())
    val state: StateFlow<AccountsState> = _state

    fun onEvent(event: AccountsEvent) {
        when (event) {
            AccountsEvent.Load -> refresh()
        }
    }

    fun load() {
        if (_state.value.isLoading) return

        _state.update { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            runCatching { repo.getAccounts() }
                .onSuccess { list -> _state.update { it.copy(isLoading = false, items = list) } }
                .onFailure { e -> _state.update { it.copy(isLoading = false, error = e.message) } }
        }
    }

    private fun refresh() = load()


}