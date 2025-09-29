package feature.turnovers.data.remote.api

import feature.turnovers.data.remote.dto.TurnoverDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json

class TurnoversApi(private val client: HttpClient) {
    suspend fun getTurnovers(): List<TurnoverDto> =
        client.get("${ApiConfig.BASE_URL}/turnovers").body()
}