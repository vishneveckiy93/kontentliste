package feature.accounts.data.repository

import feature.accounts.data.mapping.toDomain
import feature.accounts.data.remote.api.AccountsApi
import feature.accounts.domain.AccountsRepository
import feature.accounts.domain.models.Account

class AccountsRepositoryImpl(private val api: AccountsApi) : AccountsRepository {
    override suspend fun getAccounts(): List<Account> =
        api.getAccounts().map { it.toDomain() }
}