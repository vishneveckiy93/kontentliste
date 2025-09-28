package feature.turnovers.data.repository

import feature.turnovers.data.mapping.toDomain
import feature.turnovers.data.remote.api.TurnoversApi
import feature.turnovers.domain.TurnoversRepository
import feature.turnovers.domain.models.Turnover

class TurnoversRepositoryImpl(private val api: TurnoversApi) : TurnoversRepository {
    override suspend fun getForAccount(accountId: Int): List<Turnover> {
        return api.getTurnovers().map { it.toDomain() }
    }
}