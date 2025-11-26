package com.pruebas.airolmagic.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pruebas.airolmagic.R
import com.pruebas.airolmagic.viewModels.LoginState
import com.pruebas.airolmagic.viewModels.LoginViewModel
import com.pruebas.airolmagic.viewModels.SessionViewModel
import com.pruebas.airolmagic.ui.theme.Lora

@Composable
fun LoginView(
    onNavigateToGames: () -> Unit,
    onNavigateToRegister: () -> Unit,
    sessionViewModel: SessionViewModel
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("Error Desconocido") }
    val viewModel: LoginViewModel = viewModel()
    val loginState by viewModel.loginState.collectAsState()
    var showErrorDialog by remember { mutableStateOf(false) }
    var showLoadingDialog by remember { mutableStateOf(false) }

    if(showLoadingDialog) LoadingDialog()
    if(showErrorDialog) ErrorDialog(texto = errorMessage, onDismissRequest = { showErrorDialog = false; viewModel.resetState()})

    LaunchedEffect(loginState) {
        when(val state = loginState){
            is LoginState.Success -> {
                sessionViewModel.onUserLoggedIn(state.user)
                showLoadingDialog = false
                onNavigateToGames()
                viewModel.resetState()
            }
            is LoginState.Error -> {
                errorMessage = state.message
                showLoadingDialog = false
                showErrorDialog = true
            }
            else -> {}
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Column(
            modifier = Modifier.weight(1f).fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "AI Rol&Magic",
                color = colorResource(R.color.semi_white),
                style = MaterialTheme.typography.headlineLarge
            )
            Spacer(modifier = Modifier.height(20.dp))
            TransparentTextField(label = R.string.email, value_txt = email, onValueChange = { newValue -> email = newValue })
            Spacer(modifier = Modifier.height(10.dp))
            TransparentTextField(label = R.string.password, value_txt = password, onValueChange = { newValue -> password = newValue })
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedButton(
                onClick = {
                    if(email.isNotEmpty() && password.isNotEmpty()){
                        showLoadingDialog = true
                        viewModel.login(email, password)
                    }
                },
                enabled = loginState != LoginState.Loading,
            ) {
                Text(
                    text = stringResource(R.string.login),
                    fontFamily = Lora,
                    color = colorResource(R.color.semi_white)
                )
            }
        }
        Row(
            modifier = Modifier.height(70.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            Box(
                modifier = Modifier.fillMaxHeight().weight(1f),
                contentAlignment = Alignment.Center
            ){OptionButton(text = R.string.create_account, onClick = onNavigateToRegister)}
            Box(
                modifier = Modifier.fillMaxHeight().weight(1f),
                contentAlignment = Alignment.Center
            ){OptionButton(R.string.reset_pass, {})}
        }
    }
}