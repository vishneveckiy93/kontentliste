package app

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.cherryyar.kontentliste.Greeting
import feature.accounts.presentation.AccountsEvent
import feature.accounts.presentation.AccountsViewModel
import feature.accounts.ui.AccountsListScreen
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import kontentliste.composeapp.generated.resources.Res
import kontentliste.composeapp.generated.resources.compose_multiplatform

@Composable
@Preview
fun App(vm: AccountsViewModel) {
    LaunchedEffect(Unit) { vm.onEvent(AccountsEvent.Refresh) }
    AccountsListScreen(vm)
}