package feature.accounts

import feature.accounts.domain.AccountsRepository
import feature.accounts.domain.models.Account
import feature.accounts.presentation.AccountsEvent
import feature.accounts.presentation.AccountsViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import testing.coroutines.KmpMainDispatcher
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertNotNull
import kotlin.test.assertNull
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

@OptIn(ExperimentalCoroutinesApi::class)
class AccountsViewModelTest {

    @BeforeTest
    fun setup() {
        KmpMainDispatcher.install()
        startKoin { modules(module { single<AccountsRepository> { FakeOk() } }) }
    }

    @AfterTest
    fun tearDown() {
        stopKoin()
        KmpMainDispatcher.reset()
    }

    @Test
    fun load_success_updates_state() = runTest {
        val vm = AccountsViewModel()
        vm.load()
        advanceUntilIdle()
        val s = vm.state.value
        assertTrue(s.items.isNotEmpty())
        assertNull(s.error)
    }

    @Test
    fun load_failure_sets_error() = runTest {
        stopKoin()
        startKoin { modules(module { single<AccountsRepository> { FakeFail() } }) }

        val vm = AccountsViewModel()
        vm.load()
        advanceUntilIdle()
        val s = vm.state.value
        assertTrue(s.items.isEmpty())
        assertNotNull(s.error)
    }

}