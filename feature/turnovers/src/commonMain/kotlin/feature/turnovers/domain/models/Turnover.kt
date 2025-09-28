package feature.turnovers.domain.models

data class Turnover(
    val id: String,
    val accountId: Int,
    val amount: Double,
    val senderName: String,
    val senderIban: String,
    val reference: String,
    val timestamp: Long
)
