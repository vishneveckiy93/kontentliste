package feature.accounts.domain

import feature.accounts.domain.models.Account
import kotlinx.coroutines.flow.Flow

interface AccountsRepository {
    fun getAccounts(): Flow<List<Account>>
}