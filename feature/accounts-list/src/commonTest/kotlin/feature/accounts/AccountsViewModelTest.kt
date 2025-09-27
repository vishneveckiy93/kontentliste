package feature.accounts

import feature.accounts.domain.AccountsRepository
import feature.accounts.domain.models.Account
import feature.accounts.presentation.AccountsEvent
import feature.accounts.presentation.AccountsViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

private class FakeOk : AccountsRepository {
    override suspend fun getAccounts(): List<Account> = listOf(Account(
        id = 1,
        name = "Main",
        ownerName = "John Doe",
        balance = 100.0,
        currency = "EUR",
        iban = "DE00"
    ))
}

private class FakeFail : AccountsRepository {
    override suspend fun getAccounts(): List<Account> = error("boom")
}

class AccountsViewModelTest {

    @Test
    fun refresh_emits_items() = runTest {
        val vm = AccountsViewModel(FakeOk(), this)
        vm.onEvent(AccountsEvent.Refresh)
        val state = vm.state.first { state -> !state.isLoading }
        assertTrue(state.items.isNotEmpty())
    }

    @Test
    fun refresh_sets_error() = runTest {
        val vm = AccountsViewModel(FakeFail(), this)
        vm.onEvent(AccountsEvent.Refresh)
        val state = vm.state.first { !it.isLoading }
        assertTrue(state.items.isEmpty())
        assertNotNull(state.error)

    }

}