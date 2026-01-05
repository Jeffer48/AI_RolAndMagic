package com.pruebas.airolmagic.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import com.pruebas.airolmagic.R
import com.pruebas.airolmagic.viewModels.RegisterViewModel
import com.pruebas.airolmagic.viewModels.RegistrationState
import com.pruebas.airolmagic.ui.theme.Lora

@Composable
fun RegisterView(
    registerViewModel: RegisterViewModel,
    onNavigateToLogin: () -> Unit
) {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passConfirm by remember { mutableStateOf("") }

    val registrationState by registerViewModel.registrationState.collectAsState()

    var errorMessage by remember { mutableStateOf("Error Desconocido") }
    var showErrorDialog by remember { mutableStateOf(false) }

    if (registrationState is RegistrationState.Loading) {
        LoadingDialog()
    }

    if (showErrorDialog) {
        ErrorDialog(
            texto = errorMessage,
            onDismissRequest = {
                showErrorDialog = false
                registerViewModel.resetState()
            }
        )
    }

    LaunchedEffect(registrationState) {
        when (val state = registrationState) {
            is RegistrationState.Error -> {
                errorMessage = state.message
                showErrorDialog = true
            }
            else -> {}
        }
    }

    var emailError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }
    var confirmPassError by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.create_account),
            color = colorResource(R.color.semi_white),
            style = MaterialTheme.typography.headlineLarge
        )
        Spacer(modifier = Modifier.height(20.dp))

        TransparentTextField(
            label = R.string.user_name,
            value_txt = username,
            onValueChange = { username = it }
        )

        Spacer(modifier = Modifier.height(10.dp))
        TransparentTextField(
            label = R.string.email,
            value_txt = email,
            isError = emailError,
            onValueChange = { newValue ->
                email = newValue
                emailError = !android.util.Patterns.EMAIL_ADDRESS.matcher(newValue).matches()
            }
        )
        if (emailError) {
            Text(
                text = stringResource(R.string.err_email_format),
                color = colorResource(R.color.error),
                fontFamily = Lora,
                fontStyle = FontStyle.Italic
            )
        }

        Spacer(modifier = Modifier.height(10.dp))
        TransparentTextField(
            label = R.string.password,
            value_txt = password,
            isError = passwordError,
            isPassword = true,
            onValueChange = { newValue ->
                password = newValue
                passwordError = newValue.length < 6
                confirmPassError = newValue != passConfirm && passConfirm.isNotEmpty()
            }
        )
        if (passwordError) {
            Text(
                text = stringResource(R.string.err_pass_long),
                color = colorResource(R.color.error),
                fontFamily = Lora,
                fontStyle = FontStyle.Italic
            )
        }

        Spacer(modifier = Modifier.height(10.dp))
        TransparentTextField(
            label = R.string.confirm_pass,
            value_txt = passConfirm,
            isError = confirmPassError,
            isPassword = true,
            onValueChange = { newValue ->
                passConfirm = newValue
                confirmPassError = newValue != password
            }
        )
        if (confirmPassError) {
            Text(
                text = stringResource(R.string.err_pass_confirm),
                color = colorResource(R.color.error),
                fontFamily = Lora,
                fontStyle = FontStyle.Italic
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedButton(
            enabled = registrationState !is RegistrationState.Loading,
            onClick = {
                if (!emailError && !passwordError && !confirmPassError &&
                    username.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()
                ) {
                    registerViewModel.registerUser(username, email, password)
                }
            }
        ) {
            Text(
                text = stringResource(R.string.sign_up),
                fontFamily = Lora,
                color = colorResource(R.color.semi_white)
            )
        }

        Spacer(modifier = Modifier.height(15.dp))

        Text(
            text = "¿Ya tienes cuenta? Inicia sesión",
            color = colorResource(R.color.semi_white),
            fontFamily = Lora,
            modifier = Modifier.clickable { onNavigateToLogin() }
        )
    }
}