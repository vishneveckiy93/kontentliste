package feature.turnovers.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import feature.turnovers.domain.models.Turnover
import feature.turnovers.helper.formatAmount
import feature.turnovers.helper.formatDateTime
import feature.turnovers.helper.maskIban
import kotlin.math.absoluteValue

@Composable
fun TurnoverItemCard(
    item: Turnover,
    modifier: Modifier = Modifier
) {
    ElevatedCard(modifier.fillMaxWidth()) {
        Column(modifier.padding(16.dp)) {
            Row(modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                val isPlus = item.amount >+ 0
                Text(
                    (if (isPlus) "+" else "-") + formatAmount(item.amount.absoluteValue),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = if (isPlus) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
                )
                Text(formatDateTime(item.timestamp), style = MaterialTheme.typography.bodyMedium)
            }
            Spacer(modifier.height(8.dp))

            Text(item.senderName, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Medium)
            Text(maskIban(item.senderIban), style = MaterialTheme.typography.bodySmall)
            Spacer(modifier.height(8.dp))

            Text(item.reference, style = MaterialTheme.typography.bodyMedium)
        }
    }
}