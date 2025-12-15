package com.pruebas.airolmagic.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.pruebas.airolmagic.R
import com.pruebas.airolmagic.viewModels.GamesViewModel

@Composable
fun CreateLobbyView(
    onNavigateToSelCharacter: () -> Unit,
    onNavigateToHome: () -> Unit,
    gamesViewModel: GamesViewModel,
    userId: String
) {
    var gameName: String by remember { mutableStateOf("") }
    val showLoadingDialog = remember { mutableStateOf(false) }
    val showErrorDialog = remember { mutableStateOf(false) }

    if(showLoadingDialog.value) LoadingDialog()
    if(showErrorDialog.value){
        ErrorDialog(
            texto = stringResource(R.string.err_creating_game),
            onDismissRequest = { showErrorDialog.value = false; onNavigateToHome()
        })
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().weight(1f).padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row(Modifier.height(50.dp)) {
                Text(
                    text = stringResource(R.string.create_lobby),
                    color = colorResource(R.color.semi_white),
                    style = MaterialTheme.typography.headlineLarge
                )
            }
            Spacer(modifier = Modifier.height(40.dp))
            ColoredTextField(
                singleLine = true,
                textValue = gameName,
                placeholder = stringResource(R.string.game_name),
                onValueChange = { new -> gameName = new }
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth().height(100.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ){
            YellowButton(
                modifier = Modifier.height(50.dp).width(200.dp),
                text = stringResource(R.string.msg_continue),
                onClicked = {
                    if(gameName.isNotEmpty()){
                        showLoadingDialog.value = true
                        gamesViewModel.createNewGame(
                            userId = userId,
                            gameName = gameName,
                            onFinished = { state ->
                                showLoadingDialog.value = false
                                if(state) onNavigateToSelCharacter()
                                else showErrorDialog.value = true
                            }
                        )
                    }
                }
            )
        }
    }
}