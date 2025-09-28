package feature.turnovers.helper

import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.math.absoluteValue
import kotlin.math.roundToLong
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

internal fun formatAmount(amount: Double): String {
    val cents = (amount * 100).roundToLong()
    val sign = if (cents < 0) "-" else ""
    val abs = cents.absoluteValue
    val major = abs / 100
    val minor = (abs % 100).toString().padStart(2, '0')
    return "$sign$major.$minor"
}

@OptIn(ExperimentalTime::class)
internal fun formatDateTime(ts: Long): String {
    val dt = Instant.fromEpochMilliseconds(ts).toLocalDateTime(TimeZone.currentSystemDefault())
    val hh = dt.time.hour.toString().padStart(2,'0')
    val mm = dt.time.minute.toString().padStart(2,'0')
    return "${dt.date} $hh:$mm"
}

internal fun maskIban(iban: String): String =
    if (iban.length <= 8) iban
    else iban.take(4) + " •••• •••• •••• " + iban.takeLast(4)