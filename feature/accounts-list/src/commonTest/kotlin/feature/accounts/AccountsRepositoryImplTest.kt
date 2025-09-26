package feature.accounts

import AccountDto
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

private class FakeRemote : AccountsRemoteDataSource {
    override suspend fun getAccountsDto(): List<AccountDto> =
        listOf(AccountDto(
            id = 1,
            name = "Checking Account",
            ownerName = "John Doe",
            balance = 100.0,
            currency = "$",
            iban = "US12345678901234567890"
        ))
}

class AccountsRepositoryImplTest {

    @Test
    fun repository_returns_mapped_accounts() = runTest {
        @Test fun repo_maps_remote_dto_to_domain() = runTest {
            val repo = AccountsRepositoryImpl(FakeRemote())
            val list = repo.getAccounts()
            assertEquals(1, list.size)
            assertEquals("Checking Account", list.first().name)
        }
    }
}