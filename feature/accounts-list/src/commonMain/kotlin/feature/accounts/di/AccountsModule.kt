package feature.accounts.di

import feature.accounts.data.remote.api.AccountsApi
import feature.accounts.data.repository.AccountsRepositoryImpl
import feature.accounts.domain.AccountsRepository
import org.koin.dsl.module

fun accountsModule() = module {
    single { AccountsApi(get()) }
    single<AccountsRepository> { AccountsRepositoryImpl(get()) }
}