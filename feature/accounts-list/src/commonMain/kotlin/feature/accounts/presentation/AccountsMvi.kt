package feature.accounts.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import errorhandling.toFriendly
import feature.accounts.domain.AccountsRepository
import feature.accounts.domain.models.Account
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

data class AccountsState(
    val isLoading: Boolean = false,
    val items: List<Account> = emptyList(),
    val error: String? = null
)

class AccountsViewModel(): ViewModel(), KoinComponent {

    private val repo: AccountsRepository by inject()

    private val reload = MutableSharedFlow<Unit>(replay = 1)

    @OptIn(ExperimentalCoroutinesApi::class)
    val state: StateFlow<AccountsState> =
        reload
            .flatMapLatest {
                repo.getAccounts()
                    .map { list ->
                        AccountsState(isLoading = false, items = list, error = null)
                    }
                    .onStart { emit(AccountsState(isLoading = true)) }
                    .catch { e -> emit(AccountsState(isLoading = false, error = e.toFriendly())) }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = AccountsState(isLoading = true)
            )


    fun load() = reload.tryEmit(Unit)


}