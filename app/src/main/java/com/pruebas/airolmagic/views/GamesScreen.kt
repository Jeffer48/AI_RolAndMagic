package com.pruebas.airolmagic.views

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pruebas.airolmagic.R
import com.pruebas.airolmagic.data.objects.GameData
import com.pruebas.airolmagic.ui.theme.Lora
import com.pruebas.airolmagic.ui.theme.MedievalSharp
import com.pruebas.airolmagic.viewModels.GamesViewModel

@Composable
fun GamesListView(
    userId: String,
    gamesViewModel: GamesViewModel,
    onNavigateToLobby: () -> Unit,
    onNavigateToJoinLobby: () -> Unit,
    onNavigateToCreateLobby: () -> Unit
){
    val gamesList: List<GameData> by gamesViewModel.gamesList.collectAsState()

    LaunchedEffect(Unit) {
        gamesViewModel.getGamesList(userId = userId)
    }

    Column(Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxWidth().weight(1f)
        ){
            LazyColumn(
                modifier = Modifier.fillMaxWidth().weight(1f),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ){
                items(count = gamesList.size){ game ->
                    GameButton(
                        game = gamesList[game],
                        onSelected = {
                            gamesViewModel.setSelectedGame(gamesList[game])
                            onNavigateToLobby()
                        }
                    )
                }
            }
        }
        Row(
            modifier = Modifier.height(70.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            Box(
                modifier = Modifier.fillMaxHeight().weight(1f),
                contentAlignment = Alignment.Center
            ){
                TransparentYellowButton(
                    textSize = 20,
                    text = stringResource(R.string.join_lobby),
                    onClicked = { onNavigateToJoinLobby() }
                )
            }
            Box(
                modifier = Modifier.fillMaxHeight().weight(1f),
                contentAlignment = Alignment.Center
            ){
                YellowButton(
                    textSize = 20,
                    text = stringResource(R.string.create_lobby),
                    onClicked = { onNavigateToCreateLobby() }
                )
            }
        }
    }
}

@Composable
fun GameButton(game: GameData, onSelected: () -> Unit){
    Box(
        modifier = Modifier.fillMaxWidth().height(70.dp)
            .border(width = 1.dp, color = colorResource(R.color.btn_unsel_border), shape = RoundedCornerShape(10.dp))
            .background(color = colorResource(R.color.btn_unsel_darkblue))
            .clickable(onClick = {
                onSelected()
            }),
        contentAlignment = Alignment.Center
    ){
        Row(
            modifier = Modifier.fillMaxSize().padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column() {
                Text(
                    text = game.name,
                    fontFamily = MedievalSharp,
                    fontSize = 25.sp,
                    color = colorResource(R.color.yellow_font),
                )
                Text(
                    text = "${stringResource(R.string.host)}: ${game.hostName}",
                    fontFamily = Lora,
                    fontSize = 15.sp,
                    color = Color.Gray,
                )
            }
            Box(
                modifier = Modifier.fillMaxHeight(),
                contentAlignment = Alignment.Center
            ){
                Text(
                    text = stringResource(gameStatus(game.status)),
                    fontFamily = Lora,
                    fontSize = 25.sp,
                    color = colorResource(R.color.semi_white),
                )
            }
        }
    }
}

fun gameStatus(code: Int): Int{
    return when(code){
        0 -> R.string.game_status_waiting
        1 -> R.string.game_status_ongoing
        2 -> R.string.game_status_finished
        else -> R.string.game_status_waiting
    }
}