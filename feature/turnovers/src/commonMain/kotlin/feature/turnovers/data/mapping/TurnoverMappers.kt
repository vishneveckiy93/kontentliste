package feature.turnovers.data.mapping

import feature.turnovers.data.remote.dto.TurnoverDto
import feature.turnovers.domain.models.Turnover

internal fun TurnoverDto.toDomain() = Turnover(
    id = this.id,
    accountId = this.accountId,
    amount = this.amount,
    senderName = this.senderName,
    senderIban = this.senderIban,
    reference = this.reference,
    timestamp = this.timestamp
)