package com.pruebas.airolmagic.views

import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pruebas.airolmagic.R
import com.pruebas.airolmagic.ui.theme.Lora
import com.pruebas.airolmagic.ui.theme.MedievalSharp

@Composable
fun GamesListView(
    onNavigateToJoinLobby: () -> Unit,
    onNavigateToCreateLobby: () -> Unit
){
    Column(Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxWidth().weight(1f)
        ){ GameButton("Partida 1", "Jugador") }
        Row(
            modifier = Modifier.height(70.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            Box(
                modifier = Modifier.fillMaxHeight().weight(1f),
                contentAlignment = Alignment.Center
            ){OptionButton(text = R.string.create_lobby, onClick = onNavigateToCreateLobby)}
            Box(
                modifier = Modifier.fillMaxHeight().weight(1f),
                contentAlignment = Alignment.Center
            ){OptionButton(text = R.string.join_lobby, onClick = onNavigateToJoinLobby)}
        }
    }
}

@Composable
fun GameButton(gameName: String, hostName: String){
    Column(
        modifier = Modifier.fillMaxWidth().clickable(onClick = {  })
    ){
        Text(
            text = gameName,
            fontFamily = Lora,
            fontSize = 20.sp,
            color = colorResource(R.color.semi_white)
        )
        Text(
            text = "Anfitrion: $hostName",
            fontFamily = Lora,
            fontSize = 13.sp,
            color = colorResource(R.color.gray_font)
        )
    }
}