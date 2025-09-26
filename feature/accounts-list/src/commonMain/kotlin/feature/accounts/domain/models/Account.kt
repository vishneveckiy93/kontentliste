package feature.accounts.domain.models

data class Account(
    val id: Int,
    val name: String,
    val ownerName: String,
    val balance: Double,
    val currency: String,
    val iban: String
)