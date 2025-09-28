package feature.turnovers

import feature.turnovers.data.remote.api.TurnoversApi
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

class TurnoversApiTest {

    @Test
    fun api_parses_json() = runTest {
        val engine = MockEngine { req ->
            if (req.url.fullPath.endsWith("/turnovers")) {
                respond(
                    """[{ "id": "e4b6c9a2d8f1e5a7b3c8d9e0f2a4b6c8", "account_id": 1, "amount": 500.25, "sender_name": "Company X", "sender_iban": "DE12345678901234567890", "reference": "Payment for services rendered.", "timestamp": 1735689600000 }]""",
                    headers = headersOf(HttpHeaders.ContentType, "application/json")
                )
            } else respondBadRequest()
        }
        val client = HttpClient(engine) {
            install(ContentNegotiation) { json() }
        }
        val api = TurnoversApi(client)

        val list = api.getTurnovers()

        assertEquals(1, list.size)
        assertEquals(1, list.first().accountId)
    }

}