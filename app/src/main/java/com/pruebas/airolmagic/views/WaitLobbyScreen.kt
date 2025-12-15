package com.pruebas.airolmagic.views

import android.content.ClipData
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ClipEntry
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle.Companion.Italic
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pruebas.airolmagic.R
import com.pruebas.airolmagic.data.objects.CharacterProfile
import com.pruebas.airolmagic.data.objects.GameData
import com.pruebas.airolmagic.data.objects.PlayersCharacters
import com.pruebas.airolmagic.ui.theme.Lora
import com.pruebas.airolmagic.ui.theme.MedievalSharp
import com.pruebas.airolmagic.viewModels.GamesViewModel
import com.pruebas.airolmagic.viewModels.WatchersViewModel
import kotlinx.coroutines.launch

@Composable
fun WaitLobbyView(
    userId: String,
    gamesViewModel: GamesViewModel,
    watchersViewModel: WatchersViewModel,
    onNonSelectedCharacter: () -> Unit,
){
    val context = LocalContext.current
    val clipboardManager = LocalClipboard.current
    val scope = rememberCoroutineScope()
    val textCopied = stringResource(R.string.text_copied)
    val playersList: List<PlayersCharacters> by watchersViewModel.players.collectAsStateWithLifecycle()
    val game: GameData? by gamesViewModel.selectedGame.collectAsStateWithLifecycle()
    val playerData: PlayersCharacters? = playersList.find { it.userId == userId }
    val gameName = if(game == null) "" else game!!.name
    val gameCode = if(game == null) "" else game!!.joinCode
    val hostId = if(game == null) "" else game!!.hostId

    LaunchedEffect(game){
        game?.let {
            watchersViewModel.observePlayersCall(game!!.id)
        }
    }
    DisposableEffect(watchersViewModel){ onDispose { watchersViewModel.cancelObservePlayers() } }

    if(playerData != null && playerData.character == CharacterProfile()){
        val roomCode = game?.joinCode
        if(roomCode != null) gamesViewModel.setRoomCode(roomCode)
        onNonSelectedCharacter()
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Column(
            modifier = Modifier.fillMaxWidth().weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(
                text = gameName,
                color = colorResource(R.color.yellow_font),
                fontFamily = MedievalSharp,
                fontSize = 40.sp,
                lineHeight = 40.sp,
                textAlign = TextAlign.Center,
            )
            Spacer(Modifier.height(50.dp))
            Text(
                text = stringResource(R.string.invitation_code),
                color = colorResource(R.color.semi_white),
                fontFamily = Lora,
                fontSize = 20.sp,
                fontWeight = SemiBold
            )
            Spacer(Modifier.height(20.dp))
            Row(verticalAlignment = Alignment.CenterVertically){
                Text(
                    text = gameCode,
                    color = colorResource(R.color.semi_white),
                    fontFamily = Lora,
                    fontSize = 25.sp,
                    fontWeight = Bold,
                    letterSpacing = 10.sp
                )
                IconButton(onClick = {
                    scope.launch {
                        val clipData = ClipData.newPlainText("text", gameCode)
                        val clipEntry = ClipEntry(clipData)
                        clipboardManager.setClipEntry(clipEntry)
                        Toast.makeText(context, textCopied, Toast.LENGTH_SHORT).show()
                    }
                }
                ){
                    Icon(
                        painter = painterResource(R.drawable.outline_content_copy_24),
                        contentDescription = stringResource(R.string.copy_invitation_code),
                        tint = colorResource(R.color.semi_white)
                    )
                }
            }
            Spacer(Modifier.height(20.dp))
            Text(
                text = stringResource(R.string.joined_players),
                color = colorResource(R.color.semi_white),
                fontFamily = Lora,
                fontSize = 20.sp,
                fontWeight = SemiBold
            )
            Spacer(Modifier.height(10.dp))
            LazyColumn(
                modifier = Modifier.fillMaxWidth().weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
            ){
                items(playersList.size){ index ->
                    PlayerBox(player = playersList[index])
                }
            }
        }
        if(hostId == userId) {
            Row(
                modifier = Modifier.fillMaxWidth().height(80.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                YellowButton(
                    modifier = Modifier.height(50.dp),
                    text = stringResource(R.string.start_game),
                    onClicked = {}
                )
            }
        }
    }
}

@Composable
fun PlayerBox(player: PlayersCharacters){
    val textCharName = stringResource(R.string.cc_character_name)
    val textClass = stringResource(R.string.cc_class)
    val textSpecies = stringResource(R.string.cc_species)
    val characterName = player.character.name.ifEmpty { textCharName }
    val characterClass = player.character.classN.name.ifEmpty { textClass }
    val characterSpecies = player.character.race.name.ifEmpty { textSpecies }

    Box(
        modifier = Modifier.fillMaxWidth()
            .background(color = colorResource(R.color.btn_unsel_darkblue))
            .border(width = 1.dp, color = colorResource(R.color.btn_unsel_border), shape = RoundedCornerShape(10.dp))
    ){
        Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text(
                    text = characterName,
                    color = colorResource(R.color.yellow_font),
                    fontFamily = MedievalSharp,
                    fontSize = 20.sp,
                )
                Text(
                    text = player.userName,
                    color = colorResource(R.color.semi_white),
                    fontFamily = Lora,
                    fontSize = 15.sp,
                    fontWeight = SemiBold,
                    fontStyle = Italic
                )
            }
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text(
                    text = characterClass,
                    color = colorResource(R.color.semi_white),
                    fontFamily = Lora,
                    fontSize = 15.sp,
                    fontWeight = SemiBold
                )
                Text(
                    text = characterSpecies,
                    color = colorResource(R.color.semi_white),
                    fontFamily = Lora,
                    fontSize = 15.sp,
                    fontWeight = SemiBold
                )
            }
        }
    }
}