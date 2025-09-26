import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.Serializable

object ApiConfig {
    const val BASE_URL = "https://testapi.io/api/jpt/v1"
}

expect fun defaultEngineFactory(): HttpClientEngineFactory<*>

fun provideHttpClient(): HttpClient =
    HttpClient(defaultEngineFactory()) {
        install(ContentNegotiation) { json() }
        install(Logging) { level = LogLevel.INFO }
    }

/* --- temporarily: DTO + API in core:network (we'll move it to features later) --- */

@Serializable
data class AccountDto(
    val id: Int,
    val name: String,
    val ownerName: String,
    val balance: Double,
    val currency: String,
    val iban: String
)

@Serializable
data class TurnoverDto(
    val id: String,
    val accountId: String,
    val amount: Double,
    val bookedAt: String
)

class AccountsApi(private val client: HttpClient) {
    suspend fun getAccounts(): List<AccountDto> =
        client.get("${ApiConfig.BASE_URL}/accounts").body()
}

class TurnoversApi(private val client: HttpClient) {
    suspend fun getTurnovers(): List<TurnoverDto> =
        client.get("${ApiConfig.BASE_URL}/turnovers").body()
}