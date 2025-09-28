package feature.turnovers

import feature.turnovers.domain.TurnoversRepository
import feature.turnovers.domain.models.Turnover
import feature.turnovers.presentation.TurnoversViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import testing.coroutines.KmpMainDispatcher
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

private class FakeOk : TurnoversRepository {
    override suspend fun getForAccount(accountId: Int): List<Turnover> =
        listOf(
            Turnover(
                id = "e4b6c9a2d8f1e5a7b3c8d9e0f2a4b6c8",
                accountId = accountId,
                amount = 500.25,
                senderName = "Company X",
                senderIban = "DE12345678901234567890",
                reference = "Payment for services rendered",
                timestamp = 1735689600000
            ),
            Turnover(
                id = "b7c8d9e0f1a2b3c4d5e6f7a8b9c0d1e2",
                accountId = accountId,
                amount = 150.25,
                senderName = "Friend A",
                senderIban = "US09876543210987654321",
                reference = "Dinner reimbursement",
                timestamp = 1738281600000
            )
        )
}
private class FakeFail : TurnoversRepository {
    override suspend fun getForAccount(accountId: Int): List<Turnover> = error("boom")
}

@OptIn(ExperimentalCoroutinesApi::class)
class TurnoversViewModelTest {

    @BeforeTest
    fun setup() {
        KmpMainDispatcher.install()
        startKoin { modules(module { single<TurnoversRepository> { FakeOk() } }) }
    }
    @AfterTest
    fun tearDown() {
        stopKoin()
        KmpMainDispatcher.reset()
    }

    @Test
    fun load_success_returns_items() = runTest {
        val vm = TurnoversViewModel(accountId = 1)
        vm.load()
        advanceUntilIdle()
        val s = vm.state.value
        assertEquals(2, s.items.size)
        assertNull(s.error)
    }

    @Test
    fun load_failure_sets_error() = runTest {
        stopKoin()
        startKoin { modules(module { single<TurnoversRepository> { FakeFail() } }) }

        val vm = TurnoversViewModel(accountId = 1)
        vm.load()
        advanceUntilIdle()
        val s = vm.state.value
        assertTrue(s.items.isEmpty())
        assertNotNull(s.error)
    }

    @Test
    fun refresh_reloads() = runTest {
        val vm = TurnoversViewModel(accountId = 1)
        vm.load()
        advanceUntilIdle()
        val first = vm.state.value.items.size
        vm.refresh()
        advanceUntilIdle()
        val second = vm.state.value.items.size
        assertEquals(first, second)
    }

}