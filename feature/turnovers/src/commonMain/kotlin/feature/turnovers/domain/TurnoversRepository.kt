package feature.turnovers.domain

import feature.turnovers.domain.models.Turnover
import kotlinx.coroutines.flow.Flow

interface TurnoversRepository {
    suspend fun getForAccount(accountId: Int): Flow<List<Turnover>>
}