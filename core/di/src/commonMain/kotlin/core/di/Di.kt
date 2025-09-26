package core.di

import AccountsApi
import TurnoversApi
import org.koin.core.KoinApplication
import org.koin.core.context.KoinContext
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module
import provideHttpClient

val networkModule: Module = module {
    single { provideHttpClient() }
    single { AccountsApi(get()) }
    single { TurnoversApi(get()) }
}

object Di {
    private var started = false
    fun init(extra: List<Module> = emptyList()): KoinApplication? {
        if (started) return null
        started = true
        return startKoin { modules(listOf(networkModule) + extra) }
    }
}