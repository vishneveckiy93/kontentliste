package feature.turnovers.domain

import feature.turnovers.domain.models.Turnover

interface TurnoversRepository {
    suspend fun getForAccount(accountId: Int): List<Turnover>
}