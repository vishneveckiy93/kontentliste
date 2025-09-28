package testing.coroutines

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain

object KmpMainDispatcher {
    private val dispatcher = StandardTestDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    fun install() { Dispatchers.setMain(dispatcher) }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun reset() { Dispatchers.resetMain() }
}