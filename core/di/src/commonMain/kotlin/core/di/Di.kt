package core.di

import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module
import provideHttpClient

val networkModule: Module = module {
    single { provideHttpClient() }
}

object Di {
    private var started = false
    fun init() {
        if (started) return
        started = true
        startKoin { modules(listOf(networkModule)) }
    }
}