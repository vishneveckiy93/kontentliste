import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.KotlinxSerializationConverter
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object ApiConfig {
    const val BASE_URL = "https://testapi.io/api/jpt/v1"
}

expect fun defaultEngineFactory(): HttpClientEngineFactory<*>

fun provideHttpClient(): HttpClient =
    HttpClient(defaultEngineFactory()) {
        install(ContentNegotiation) {
            val jsonCfg = Json {
                isLenient = true
                ignoreUnknownKeys = true
                prettyPrint = true
            }
            json(jsonCfg)
            register(ContentType.Text.Html, KotlinxSerializationConverter(jsonCfg))
        }
        install(Logging) {
            logger = Logger.SIMPLE
            level = LogLevel.ALL
        }
    }

