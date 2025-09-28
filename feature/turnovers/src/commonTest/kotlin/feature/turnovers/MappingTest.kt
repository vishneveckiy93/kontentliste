package feature.turnovers

import feature.turnovers.data.mapping.toDomain
import feature.turnovers.data.remote.dto.TurnoverDto
import feature.turnovers.domain.models.Turnover
import kotlin.test.Test
import kotlin.test.assertEquals

class MappingTest {

    @Test
    fun dto_maps_to_domain() {
        val dto = TurnoverDto(
            id = "e4b6c9a2d8f1e5a7b3c8d9e0f2a4b6c8",
            accountId = 1,
            amount = 500.25,
            senderName = "Company X",
            senderIban = "DE12345678901234567890",
            reference = "Payment for services rendered",
            timestamp = 1735689600000
        )
        val model: Turnover = dto.toDomain()

        assertEquals("e4b6c9a2d8f1e5a7b3c8d9e0f2a4b6c8", model.id)
        assertEquals(1, model.accountId)
        assertEquals(500.25, model.amount)
        assertEquals("Payment for services rendered", model.reference)
        assertEquals("Company X", model.senderName)
        assertEquals("DE12345678901234567890", model.senderIban)
        assertEquals(1735689600000, model.timestamp)
    }

}