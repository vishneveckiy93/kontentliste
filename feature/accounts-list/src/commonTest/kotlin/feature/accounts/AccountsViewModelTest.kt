package feature.accounts

import feature.accounts.domain.AccountsRepository
import feature.accounts.domain.models.Account
import feature.accounts.presentation.AccountsViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
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
    override fun getAccounts() = flow {
        emit(
            listOf(
                Account(
                    id = 1,
                    name = "Checking Account",
                    ownerName = "John Doe",
                    balance = 1250.75,
                    currency = "$",
                    iban = "US12345678901234567890"
                )
            )
        )
    }
}

private class FakeFail : AccountsRepository {
    override fun getAccounts() = flow<List<Account>> {
        throw RuntimeException("boom")
    }
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
        val s = vm.state.first { !it.isLoading }
        assertTrue(s.items.isNotEmpty())
        assertNull(s.error)
    }

    @Test
    fun load_failure_sets_error() = runTest {
        stopKoin()
        startKoin { modules(module { single<AccountsRepository> { FakeFail() } }) }

        val vm = AccountsViewModel()
        vm.load()
        val s = vm.state.first { !it.isLoading }
        assertTrue(s.items.isEmpty())
        assertNotNull(s.error)
    }

}