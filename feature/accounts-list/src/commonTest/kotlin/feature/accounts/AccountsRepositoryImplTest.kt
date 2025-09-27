package feature.accounts

import feature.accounts.data.remote.api.AccountsApi
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.engine.mock.respondBadRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.HttpHeaders
import io.ktor.http.fullPath
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class AccountsRepositoryImplTest {
    @Test
    fun repo_returns_domain() = runTest {
        val engine = MockEngine {
            if (it.url.fullPath.endsWith("/accounts")) {
                respond(
                    content = """[{ "id": 1, "name": "Checking Account", "ownerName": "John Doe", "balance": 1250.75, "currency": "${'$'}", "iban": "US12345678901234567890" }]""",
                    headers = headersOf(HttpHeaders.ContentType, "application/json")
                )
            } else respondBadRequest()
        }
        val client = HttpClient(engine) { install(ContentNegotiation) { json() } }
        val api  = AccountsApi(client)

        val repo = AccountsRepositoryImpl(api)
        val res = repo.getAccounts()

        assertEquals("Checking Account", res.first().name)
        assertEquals(1, res.size)
    }
}