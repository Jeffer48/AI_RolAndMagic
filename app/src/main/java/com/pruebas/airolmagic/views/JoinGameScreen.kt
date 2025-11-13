package com.pruebas.airolmagic.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.pruebas.airolmagic.R
import com.pruebas.airolmagic.ui.theme.Lora

@Composable
fun JoinGameView(onNavigateToCreateCharacter: () -> Unit) {
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
        OutlinedTextField(
            value = "",
            onValueChange = {},
            label = { Text(text = stringResource(R.string.game_code)) },
            modifier = Modifier
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedButton(
            onClick = onNavigateToCreateCharacter
        ) {
            Text(
                text = stringResource(R.string.join),
                fontFamily = Lora,
                color = colorResource(R.color.semi_white)
            )
        }
    }
}