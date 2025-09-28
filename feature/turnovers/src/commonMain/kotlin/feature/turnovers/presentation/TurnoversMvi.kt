package feature.turnovers.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import feature.turnovers.domain.TurnoversRepository
import feature.turnovers.domain.models.Turnover
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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

    private val _state = MutableStateFlow(TurnoversState())
    val state: StateFlow<TurnoversState> = _state

    fun load() {
        if (_state.value.isLoading) return
        _state.update { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            runCatching { repo.getForAccount(accountId) }
                .onSuccess { list -> _state.update { it.copy(isLoading = false, items = list) } }
                .onFailure { e -> _state.update { it.copy(isLoading = false, error = e.message) } }
        }
    }

    fun refresh() = load()
}
