package feature.turnovers.data.remote.api

import feature.turnovers.data.remote.dto.TurnoverDto
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json

class TurnoversApi(private val client: HttpClient) {
    suspend fun getTurnovers(): List<TurnoverDto> {
        val resp = client.get("${ApiConfig.BASE_URL}/turnovers")
        val raw = resp.bodyAsText()
        return Json.decodeFromString(raw)
    }
}