package feature.turnovers.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
class TurnoverDto(
    val id: String,
    val accountId: Int,
    val amount: Double,
    val senderName: String,
    val senderIban: String,
    val reference: String,
    val timestamp: Long
)