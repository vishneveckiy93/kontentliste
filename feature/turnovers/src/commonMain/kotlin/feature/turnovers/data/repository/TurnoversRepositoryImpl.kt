package feature.turnovers.data.repository

import feature.turnovers.data.mapping.toDomain
import feature.turnovers.data.remote.api.TurnoversApi
import feature.turnovers.domain.TurnoversRepository
import feature.turnovers.domain.models.Turnover
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class TurnoversRepositoryImpl(private val api: TurnoversApi) : TurnoversRepository {
    override suspend fun getForAccount(accountId: Int): Flow<List<Turnover>> =
        flow { emit(api.getTurnovers()) }
            .flowOn(Dispatchers.IO)
            .map { all ->
                all.filter { it.accountId == accountId }
                    .sortedByDescending { it.timestamp }
                    .map { it.toDomain() }
            }
            .flowOn(Dispatchers.Default)
}