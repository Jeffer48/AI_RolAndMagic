package com.pruebas.airolmagic.views

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pruebas.airolmagic.R
import com.pruebas.airolmagic.data.RegisterViewModel
import com.pruebas.airolmagic.data.RegistrationState
import com.pruebas.airolmagic.ui.theme.Lora

@Composable
fun RegisterView(onNavigateToLogin: () -> Unit) {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passConfirm by remember { mutableStateOf("") }
    val context = LocalContext.current
    val viewModel: RegisterViewModel = viewModel()
    val registrationState by viewModel.registrationState.collectAsState()
    var errorMessage by remember { mutableStateOf("Error Desconocido") }
    var showErrorDialog by remember { mutableStateOf(false) }
    var showSuccessDialog by remember { mutableStateOf(false) }
    var showLoadingDialog by remember { mutableStateOf(false) }

    if(showLoadingDialog) LoadingDialog()
    if(showErrorDialog) ErrorDialog(texto = errorMessage, onDismissRequest = { showErrorDialog = false })
    if(showSuccessDialog) SuccessDialog(texto = stringResource(R.string.success_register), onDismissRequest = { onNavigateToLogin(); showSuccessDialog = false })

    LaunchedEffect(registrationState) {
        when (val state = registrationState){
            is RegistrationState.Success -> {
                viewModel.resetState()
                showLoadingDialog = false
                showSuccessDialog = true
            }
            is RegistrationState.Error -> {
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
                errorMessage = state.message
                viewModel.resetState()
                showLoadingDialog = false
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
    ){
        Text(
            text = stringResource(R.string.create_account),
            color = colorResource(R.color.semi_white),
            style = MaterialTheme.typography.headlineLarge
        )
        Spacer(modifier = Modifier.height(20.dp))
        TransparentTextField(label = R.string.user_name, value_txt = username, onValueChange = { newValue -> username = newValue })

        Spacer(modifier = Modifier.height(10.dp))
        TransparentTextField(label = R.string.email, value_txt = email, isError = emailError, onValueChange = {
            newValue -> email = newValue
            emailError = !android.util.Patterns.EMAIL_ADDRESS.matcher(newValue).matches()
        })
        if(emailError) Text(text = stringResource(R.string.err_email_format), color = colorResource(R.color.error), fontFamily = Lora, fontStyle = FontStyle.Italic)

        Spacer(modifier = Modifier.height(10.dp))
        TransparentTextField(label = R.string.password, value_txt = password, isError = passwordError, onValueChange = {
            newValue -> password = newValue
            passwordError = newValue.length < 6
            confirmPassError = newValue != passConfirm && passConfirm.isNotEmpty()
        })
        if(passwordError) Text(text = stringResource(R.string.err_pass_long), color = colorResource(R.color.error), fontFamily = Lora, fontStyle = FontStyle.Italic)

        Spacer(modifier = Modifier.height(10.dp))
        TransparentTextField(label = R.string.confirm_pass, value_txt = passConfirm, isError = confirmPassError, onValueChange = {
            newValue -> passConfirm = newValue
            confirmPassError = newValue != password
        })
        if(confirmPassError) Text(text = stringResource(R.string.err_pass_confirm), color = colorResource(R.color.error), fontFamily = Lora, fontStyle = FontStyle.Italic)

        Spacer(modifier = Modifier.height(10.dp))
        OutlinedButton(
            enabled = registrationState !is RegistrationState.Loading,
            onClick = {
                if(!emailError && !passwordError && !confirmPassError && username.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()){
                    viewModel.registerUser(username, email, password)
                    print(registrationState)
                    showLoadingDialog = true
                }
            }
        ) {
            Text(
                text = stringResource(R.string.sign_up),
                fontFamily = Lora,
                color = colorResource(R.color.semi_white)
            )
        }
    }
}