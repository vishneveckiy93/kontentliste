package feature.accounts.data

import AccountDto
import feature.accounts.domain.Account

internal fun AccountDto.toDomain() = Account(
    id = this.id,
    name = this.name,
    ownerName = this.ownerName,
    balance = this.balance,
    currency = this.currency,
    iban = this.iban
)