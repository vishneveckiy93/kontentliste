package feature.accounts

import feature.accounts.data.mapping.toDomain
import feature.accounts.data.remote.dto.AccountDto
import feature.accounts.domain.models.Account
import kotlin.test.Test
import kotlin.test.assertEquals

class MappingTest {
    @Test
    fun dto_is_mapped_to_domain_correctly() {
        val dto = AccountDto(
            id = 1,
            name = "Main",
            ownerName = "John Doe",
            balance = 100.0,
            currency = "EUR",
            iban = "DE00"
        )
        val model: Account = dto.toDomain()
        assertEquals(1, model.id)
        assertEquals("Main", model.name)
        assertEquals("DE00", model.iban)
        assertEquals(100.0, model.balance)
        assertEquals("EUR", model.currency)
        assertEquals("John Doe", model.ownerName)
    }
}