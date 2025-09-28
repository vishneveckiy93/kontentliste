package feature.turnovers

import feature.turnovers.data.remote.api.TurnoversApi
import feature.turnovers.data.repository.TurnoversRepositoryImpl
import feature.turnovers.domain.TurnoversRepository
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.HttpHeaders
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class TurnoversRepositoryTest {
    @Test
    fun repo_returns_domain_list() = runTest {
        val engine = MockEngine {
            respond(
                """[
           { "id": "e4b6c9a2d8f1e5a7b3c8d9e0f2a4b6c8", "account_id": 1, "amount": 500.25, "sender_name": "Company X", "sender_iban": "DE12345678901234567890", "reference": "Payment for services rendered.", "timestamp": 1735689600000 },
           { "id": "a1b2c3d4e5f6a7b8c9d0e1f2a3b4c5d6", "account_id": 1, "amount": -75.50, "sender_name": "Online Store", "sender_iban": "DE22334455667788990011", "reference": "Order #54321.", "timestamp": 1740960000000 }
        ]""",
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }
        val client = HttpClient(engine) { install(ContentNegotiation) { json() } }
        val api = TurnoversApi(client)

        val repo: TurnoversRepository = TurnoversRepositoryImpl(api)
        val res = repo.getForAccount(1).first()
        assertEquals(2, res.size)
        assertEquals("a1b2c3d4e5f6a7b8c9d0e1f2a3b4c5d6", res.first().id)
    }
}