package feature.accounts.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import feature.accounts.presentation.AccountsEvent
import feature.accounts.presentation.AccountsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountsListScreen(
    vm: AccountsViewModel,
    onAccountClick: (id: Int) -> Unit = {}
) {

    LaunchedEffect(vm) { vm.onEvent(AccountsEvent.Load) }

    val state by vm.state.collectAsState()

    Scaffold(topBar = { TopAppBar(title = { Text("Accounts") }) }) { padding ->
       Box(Modifier.fillMaxSize().padding(padding)) {
           when {
               state.isLoading -> CircularProgressIndicator(Modifier.align(Alignment.Center))
               state.error != null -> Text("Error: ${state.error}", Modifier.align(Alignment.Center))
               else -> LazyColumn(Modifier.fillMaxSize(), contentPadding = PaddingValues(16.dp)) {
                   items(state.items) { a ->
                       Text(
                           "${a.name} • ${a.iban}",
                           style = MaterialTheme.typography.bodyLarge,
                           modifier = Modifier
                               .fillMaxWidth()
                               .padding(vertical = 8.dp)
                               .clickable { onAccountClick(a.id)}
                           )
                       Spacer(Modifier.height(12.dp))
                   }
               }
           }
       }
    }
}