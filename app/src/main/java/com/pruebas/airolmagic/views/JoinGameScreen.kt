package com.pruebas.airolmagic.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.pruebas.airolmagic.R
import com.pruebas.airolmagic.ui.theme.Lora
import com.pruebas.airolmagic.viewModels.GamesViewModel
import com.pruebas.airolmagic.viewModels.SessionViewModel

@Composable
fun JoinGameView(
    onNavigateToCreateCharacter: () -> Unit,
    onNavigateToHome: () -> Unit,
    gamesViewModel: GamesViewModel,
    userId: String
) {
    var gameCode: String by remember { mutableStateOf("") }
    val showLoadingDialog = remember { mutableStateOf(false) }
    val showErrorDialog = remember { mutableStateOf(false) }
    var errorMessage: Int by remember { mutableIntStateOf(R.string.err_joining_game) }

    if(showLoadingDialog.value) LoadingDialog()
    if(showErrorDialog.value){
        ErrorDialog(
            texto = stringResource(errorMessage),
            onDismissRequest = { showErrorDialog.value = false; onNavigateToHome() }
        )
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Text(
            text = stringResource(R.string.join_lobby),
            color = colorResource(R.color.semi_white),
            style = MaterialTheme.typography.headlineLarge
        )
        Spacer(modifier = Modifier.height(20.dp))
        ColoredTextField(
            singleLine = true,
            textValue = gameCode,
            placeholder = stringResource(R.string.game_code),
            onValueChange = { new ->
                if(new.length <= 5) gameCode = new.uppercase()
            }
        )
        Spacer(modifier = Modifier.height(20.dp))
        YellowButton(
            modifier = Modifier.height(50.dp).width(200.dp),
            text = stringResource(R.string.msg_continue),
            onClicked = {
                if(gameCode.isNotEmpty()){
                    showLoadingDialog.value = true
                    gamesViewModel.joinToGame(userId = userId,roomCode = "", onFinished = { code ->
                        showLoadingDialog.value = false
                        if(code == 0) onNavigateToCreateCharacter()
                        else {
                            errorMessage = code
                            showErrorDialog.value = true
                        }
                    })
                    onNavigateToCreateCharacter()
                }
            }
        )
    }
}