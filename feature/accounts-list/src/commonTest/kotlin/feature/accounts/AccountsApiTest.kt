package feature.accounts

import feature.accounts.data.remote.api.AccountsApi
import feature.accounts.data.remote.dto.AccountDto
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.engine.mock.respondBadRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.fullPath
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.KotlinxSerializationConverter
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

class AccountsApiTest {

    @Test
    fun api_parses_accounts_json() = runTest {
        val engine = MockEngine {
            if (it.url.fullPath.endsWith("/accounts")) {
                respond(content = """[{ "id": 1, "name": "Checking Account", "ownerName": "John Doe", "balance": 1250.75, "currency": "${'$'}", "iban": "US12345678901234567890" }]""",
                    headers = headersOf(HttpHeaders.ContentType, "application/json"))
            } else respondBadRequest()
        }
        val client = HttpClient(engine) { install(ContentNegotiation) { json() } }
        val api = AccountsApi(client)

        val list: List<AccountDto> = api.getAccounts()
        assertEquals("Checking Account", list.first().name)
        assertEquals(1, list.size)
    }

    @Test
    fun html_is_parsed_as_json() = runTest {
        val engine = MockEngine {
            if (it.url.fullPath.endsWith("/accounts")) {
                respond(content = """[{ "id": 1, "name": "Checking Account", "ownerName": "John Doe", "balance": 1250.75, "currency": "${'$'}", "iban": "US12345678901234567890" }]""",
                    headers = headersOf(HttpHeaders.ContentType, "text/html"))
            } else respondBadRequest()
        }
        val jsonCfg = Json { ignoreUnknownKeys = true; isLenient = true }
        val client = HttpClient(engine) {
            install(ContentNegotiation) {
                json(jsonCfg)
                register(ContentType.Text.Html, KotlinxSerializationConverter(jsonCfg))
            }
        }
        val api = AccountsApi(client)
        val list: List<AccountDto> = api.getAccounts()
        assertEquals(1, list.size)
    }

}