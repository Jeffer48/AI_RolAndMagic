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
import androidx.compose.material3.Icon
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pruebas.airolmagic.R
import com.pruebas.airolmagic.data.objects.GameData
import com.pruebas.airolmagic.ui.theme.Lora
import com.pruebas.airolmagic.ui.theme.MedievalSharp
import com.pruebas.airolmagic.viewModels.ChatViewModel
import com.pruebas.airolmagic.viewModels.GamesViewModel

@Composable
fun GamesListView(
    userId: String,
    gamesViewModel: GamesViewModel,
    chatViewModel: ChatViewModel,
    onNavigateToLobby: () -> Unit,
    onNavigateToJoinLobby: () -> Unit,
    onNavigateToCreateLobby: () -> Unit,
    onNavigateToGame: () -> Unit
){
    val gamesList: List<GameData> by gamesViewModel.gamesList.collectAsState()
    val showErrorDialog = remember { mutableStateOf(false) }

    if(showErrorDialog.value) ErrorDialog(texto = stringResource(R.string.err_deleting_game), onDismissRequest = { showErrorDialog.value = false })

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
                items(count = gamesList.size){ index ->
                    val game = gamesList[index]
                    GameButton(
                        game = game,
                        onSelected = {
                            if(game.status == 0) {
                                gamesViewModel.setSelectedGame(game)
                                onNavigateToLobby()
                            }else{
                                chatViewModel.setSelectedGame(game)
                                chatViewModel.getAndSetPlayersList(game.id)
                                onNavigateToGame()
                            }
                        },
                        onRemove = { gamesViewModel.deleteGame(game.id, userId, onError = { showErrorDialog.value = true }) }
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
fun GameButton(game: GameData, onSelected: () -> Unit, onRemove: () -> Unit){
    val showWarningDialog = remember { mutableStateOf(false) }

    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = { value ->
            if(value == SwipeToDismissBoxValue.StartToEnd){
                showWarningDialog.value = true
                false
            }else false
        }
    )

    if(showWarningDialog.value){
        ConfirmDialog(
            texto = stringResource(R.string.delete_game_desc),
            txtHead = stringResource(R.string.delete_game),
            onConfirm = { onRemove(); showWarningDialog.value = false },
            onCancel = { showWarningDialog.value = false }
        )
    }

    SwipeToDismissBox(
        state = dismissState,
        backgroundContent = {
            val color = when (dismissState.dismissDirection) {
                SwipeToDismissBoxValue.StartToEnd -> Color.Red
                else -> Color.Transparent
            }

            Box(
                modifier = Modifier.fillMaxSize().background(color).padding(horizontal = 20.dp).border(width = 1.dp, color = color, shape = RoundedCornerShape(10.dp)),
                contentAlignment = Alignment.CenterStart
            ){
                Icon(
                    painter = painterResource(R.drawable.outline_delete_24),
                    contentDescription = "Eliminar sala",
                    tint = Color.White
                )
            }
        }
    ) {
        Box(
            modifier = Modifier.fillMaxWidth().height(70.dp)
                .border(
                    width = 1.dp,
                    color = colorResource(R.color.btn_unsel_border),
                    shape = RoundedCornerShape(10.dp)
                )
                .background(color = colorResource(R.color.btn_unsel_darkblue))
                .clickable(onClick = {
                    onSelected()
                }),
            contentAlignment = Alignment.Center
        ) {
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
                ) {
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
}

fun gameStatus(code: Int): Int{
    return when(code){
        0 -> R.string.game_status_waiting
        1 -> R.string.game_status_ongoing
        2 -> R.string.game_status_finished
        else -> R.string.game_status_waiting
    }
}