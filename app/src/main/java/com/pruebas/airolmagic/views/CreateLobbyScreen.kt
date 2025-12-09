package com.pruebas.airolmagic.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pruebas.airolmagic.R
import com.pruebas.airolmagic.ui.theme.AIRolMagicTheme
import com.pruebas.airolmagic.ui.theme.Lora

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CreateLobbyPrev(){
    AIRolMagicTheme {
        Box(Modifier.fillMaxSize().background(colorResource(R.color.bg_black_purple))){
            CreateLobbyView({})
        }
    }
}

@Composable
fun CreateLobbyView(onNavigateToCreateCharacter: () -> Unit) {
    var gameName: String by remember { mutableStateOf("") }
    var apiKey: String by remember { mutableStateOf("") }
    var baseUrl: String by remember { mutableStateOf("") }
    var modelName: String by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
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
            Spacer(modifier = Modifier.height(20.dp))
            Column {
                Text(
                    text = stringResource(R.string.opening_scene) + ":",
                    color = colorResource(R.color.semi_white),
                    fontFamily = Lora
                )
                TextField(
                    value = "",
                    onValueChange = {},
                    modifier = Modifier.height(100.dp).fillMaxWidth(),
                    placeholder = { Text("Texto de prueba") },
                )
            }
            Spacer(modifier = Modifier.height(40.dp))
            Text(
                text = stringResource(R.string.ai_data),
                color = colorResource(R.color.semi_white),
                fontFamily = Lora
            )
            Spacer(modifier = Modifier.height(20.dp))
            ColoredTextField(
                singleLine = true,
                textValue = apiKey,
                placeholder = stringResource(R.string.api_key),
                onValueChange = { new -> apiKey = new }
            )
            Spacer(modifier = Modifier.height(10.dp))
            ColoredTextField(
                singleLine = true,
                textValue = baseUrl,
                placeholder = stringResource(R.string.base_url),
                onValueChange = { new -> baseUrl = new }
            )
            Spacer(modifier = Modifier.height(10.dp))
            ColoredTextField(
                singleLine = true,
                textValue = modelName,
                placeholder = stringResource(R.string.model_name),
                onValueChange = { new -> modelName = new }
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth().height(100.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ){
            YellowButton(
                modifier = Modifier.height(50.dp).width(200.dp),
                text = stringResource(R.string.create),
                onClicked = { onNavigateToCreateCharacter() }
            )
        }
    }
}