package feature.turnovers.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import feature.turnovers.domain.TurnoversRepository
import feature.turnovers.domain.models.Turnover
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

data class TurnoversState(
    val isLoading: Boolean = false,
    val items: List<Turnover> = emptyList(),
    val error: String? = null
)

class TurnoversViewModel(
    private val accountId: Int
) : ViewModel(), KoinComponent {

    private val repo: TurnoversRepository by inject()

    private val reload = MutableSharedFlow<Unit>(replay = 1)

    val state: StateFlow<TurnoversState> =
        reload
            .flatMapLatest {
                repo.getForAccount(accountId)
                    .map<List<Turnover>, TurnoversState> { list ->
                        TurnoversState(isLoading = false, items = list, error = null)
                    }
                    .onStart { emit(TurnoversState(isLoading = true)) }
                    .catch { e -> emit(TurnoversState(isLoading = false, items = emptyList(), error = e.message)) }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = TurnoversState()
            )

    init {
        reload.tryEmit(Unit)
    }

    fun load() = reload.tryEmit(Unit)

    fun refresh() = reload.tryEmit(Unit)
}
