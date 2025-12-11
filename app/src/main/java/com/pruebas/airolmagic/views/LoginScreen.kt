package com.pruebas.airolmagic.views

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.pruebas.airolmagic.R
import com.pruebas.airolmagic.viewModels.SessionViewModel
import com.pruebas.airolmagic.ui.theme.Lora

@Composable
fun LoginView(
    onNavigateToRegister: () -> Unit,
    sessionViewModel: SessionViewModel
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val loginError by sessionViewModel.loginError.collectAsState()
    val isLoading by sessionViewModel.isLoadingLogin.collectAsState()

    if(isLoading) LoadingDialog()

    loginError?.let { msg ->
        ErrorDialog(
            texto = msg,
            onDismissRequest = { sessionViewModel.clearLoginError() }
        )
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

            TransparentTextField(
                label = R.string.email,
                value_txt = email,
                onValueChange = { email = it }
            )
            Spacer(modifier = Modifier.height(10.dp))

            TransparentTextField(
                label = R.string.password,
                value_txt = password,
                isPassword = true,
                onValueChange = { password = it }
            )
            Spacer(modifier = Modifier.height(10.dp))

            OutlinedButton(
                onClick = {
                    if(email.isNotEmpty() && password.isNotEmpty()){
                        sessionViewModel.login(email, password)
                    }
                },
                enabled = !isLoading,
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