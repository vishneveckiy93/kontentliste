package feature.accounts.data.remote.api

import ApiConfig
import feature.accounts.data.remote.dto.AccountDto
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json

class AccountsApi(private val client: HttpClient) {
    suspend fun getAccounts(): List<AccountDto> {
        val resp = client.get("${ApiConfig.BASE_URL}/accounts")
        val raw = resp.bodyAsText()
        return Json.decodeFromString(raw)
    }

}