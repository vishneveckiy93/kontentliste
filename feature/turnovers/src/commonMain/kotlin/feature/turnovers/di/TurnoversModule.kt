package feature.turnovers.di

import feature.turnovers.data.remote.api.TurnoversApi
import feature.turnovers.data.repository.TurnoversRepositoryImpl
import feature.turnovers.domain.TurnoversRepository
import org.koin.dsl.module

fun turnoversModule() = module {
    single { TurnoversApi(get()) }
    single<TurnoversRepository> { TurnoversRepositoryImpl(get()) }
}