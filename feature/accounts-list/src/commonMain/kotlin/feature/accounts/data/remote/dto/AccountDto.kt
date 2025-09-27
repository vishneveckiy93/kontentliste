package feature.accounts.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class AccountDto(
    val id: Int,
    val name: String,
    val ownerName: String,
    val balance: Double,
    val currency: String,
    val iban: String
)