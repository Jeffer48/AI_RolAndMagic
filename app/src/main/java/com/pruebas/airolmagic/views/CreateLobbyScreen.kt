package com.pruebas.airolmagic.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.pruebas.airolmagic.R
import com.pruebas.airolmagic.ui.theme.Lora

@Composable
fun CreateLobbyView(onNavigateToCreateCharacter: () -> Unit) {
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
            OutlinedTextField(
                value = "",
                onValueChange = {},
                label = { Text(text = stringResource(R.string.game_name)) },
                modifier = Modifier.fillMaxWidth()
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
            OutlinedTextField(
                value = "",
                onValueChange = {},
                label = { Text(text = stringResource(R.string.api_key)) },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = "",
                onValueChange = {},
                label = { Text(text = stringResource(R.string.base_url)) },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = "",
                onValueChange = {},
                label = { Text(text = stringResource(R.string.model_name)) },
                modifier = Modifier.fillMaxWidth()
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth().height(150.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ){
            OutlinedButton(
                onClick = onNavigateToCreateCharacter
            ) {
                Text(
                    text = stringResource(R.string.create),
                    fontFamily = Lora,
                    color = colorResource(R.color.semi_white)
                )
            }
        }
    }
}