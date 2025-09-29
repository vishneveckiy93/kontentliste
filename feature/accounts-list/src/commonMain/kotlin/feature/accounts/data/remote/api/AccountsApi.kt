package feature.accounts.data.remote.api

import ApiConfig
import feature.accounts.data.remote.dto.AccountDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json

class AccountsApi(private val client: HttpClient) {
    suspend fun getAccounts(): List<AccountDto> =
        client.get("${ApiConfig.BASE_URL}/accounts").body()

}