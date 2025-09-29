package errorhandling

import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.plugins.ResponseException
import io.ktor.util.network.UnresolvedAddressException
import io.ktor.utils.io.errors.IOException

fun Throwable.toFriendly() = when {
    this is HttpRequestTimeoutException -> "Zeitüberschreitung der Anfrage"

    this is UnresolvedAddressException -> "Bitte Internetverbindung prüfen"

    this is ResponseException -> when (response.status.value) {
        in 500..599 -> "Serverfehler (${response.status.value})"
        in 400..499 -> "Anfragefehler (${response.status.value})"
        else -> "Netzwerkfehler (${response.status.value})"
    }

    this is kotlinx.io.IOException -> "Netzwerkfehler"

    else -> "Etwas ist schief gelaufen"

}