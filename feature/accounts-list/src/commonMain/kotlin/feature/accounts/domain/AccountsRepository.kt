package feature.accounts.domain

import feature.accounts.domain.models.Account

interface AccountsRepository {
    suspend fun getAccounts(): List<Account>
}