package app.di

import core.di.networkModule
import feature.accounts.di.accountsModule
import org.koin.core.context.startKoin

object AppDI {
    private var started = false
    fun init() {
        if (started) return
        started = true
        startKoin { modules(listOf(networkModule, accountsModule())) }
    }
}