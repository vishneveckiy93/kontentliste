package feature.turnovers.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class TurnoverDto(
    val id: String,
    @SerialName("account_id")
    val accountId: Int,
    val amount: Double,
    @SerialName("sender_name")
    val senderName: String,
    @SerialName("sender_iban")
    val senderIban: String,
    val reference: String,
    val timestamp: Long
)