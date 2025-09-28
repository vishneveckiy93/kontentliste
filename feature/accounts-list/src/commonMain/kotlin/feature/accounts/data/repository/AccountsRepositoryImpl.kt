package feature.accounts.data.repository

import feature.accounts.data.mapping.toDomain
import feature.accounts.data.remote.api.AccountsApi
import feature.accounts.domain.AccountsRepository
import feature.accounts.domain.models.Account
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class AccountsRepositoryImpl(private val api: AccountsApi) : AccountsRepository {
    override suspend fun getAccounts(): Flow<List<Account>> =
        flow { emit(api.getAccounts()) }
            .flowOn(Dispatchers.IO)
            .map { dtos -> dtos.map { it.toDomain() } }
            .flowOn(Dispatchers.Default)
}