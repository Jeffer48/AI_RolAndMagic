package com.pruebas.airolmagic.views.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pruebas.airolmagic.R
import com.pruebas.airolmagic.ui.theme.Lora
import com.pruebas.airolmagic.viewModels.ChatViewModel

@Composable
fun ChatView(
    userId: String,
    chatViewModel: ChatViewModel
) {
    val chatMessages by chatViewModel.chatMessages.collectAsState()
    val selectedGame by chatViewModel.selectedGame.collectAsState()
    var message: String by remember { mutableStateOf("") }

    LaunchedEffect(chatViewModel) { chatViewModel.watcherMessages() }
    DisposableEffect(chatViewModel){ onDispose { chatViewModel.cancelObservePlayers() } }

    Column(Modifier.fillMaxSize().padding(vertical = 5.dp)){
        LazyColumn(
            modifier = Modifier.fillMaxWidth().weight(1f).padding(horizontal = 5.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ){
            items(chatMessages.size){index ->
                val data = chatMessages[index]
                val isMe = userId == data.userId
                if(data.role == "user") RoleplayUserBubble(isMe = isMe, characterName = data.characterName, message = data.content, timestamp = data.createdAt.toString(), accentColor = Color(0xFFC084FC))
                if(data.role == "assistant") NarratorBox(text = data.content)
            }
        }
        Spacer(modifier = Modifier.height(1.dp).fillMaxWidth().background(colorResource(R.color.btn_unsel_border)))
        Box(Modifier.fillMaxWidth().background(color = colorResource(R.color.bg_black_purple))){
            TextZone(
                message = message,
                onUpdateMessage = { new -> message = new },
                onSendClicked = { chatViewModel.sendMessage(message = message, userId = userId); message = "" }
            )
        }
    }
}

@Composable
fun TextZone(message: String, onUpdateMessage: (String) -> Unit = {}, onSendClicked: () -> Unit = {}){
    Box(
        modifier = Modifier.fillMaxWidth().background(colorResource(R.color.btn_unsel_darkblue)).padding(all = 15.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Surface(
                modifier = Modifier.weight(1f).heightIn(min = 55.dp, max = 150.dp),
                shape = RoundedCornerShape(25.dp),
                color = Color(0xFF0D1117)
            ) {
                TextField(
                    modifier = Modifier.fillMaxWidth().border(width = 1.dp, color = colorResource(R.color.btn_unsel_border), shape = RoundedCornerShape(25.dp)),
                    value = message,
                    placeholder = { Text(text = "") },
                    onValueChange = { onUpdateMessage(it) },
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = colorResource(R.color.semi_white),
                        unfocusedTextColor = colorResource(R.color.semi_white),
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        focusedPlaceholderColor = Color.Gray,
                        unfocusedPlaceholderColor = Color.Gray,
                    ),
                )
            }
            Spacer(modifier = Modifier.size(10.dp))
            Box(
                modifier = Modifier.size(55.dp)
                    .background(color = Color(0xFFAE8200), shape = RoundedCornerShape(10.dp))
                    .clickable(onClick = { onSendClicked() }),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    modifier = Modifier.fillMaxSize(0.5f),
                    painter = painterResource(R.drawable.ico_send_outlined),
                    contentDescription = stringResource(R.string.send_button),
                    tint = colorResource(R.color.semi_white)
                )
            }
        }
    }
}

@Composable
fun NarratorBox(text: String){
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ){
        Box(
            modifier = Modifier.fillMaxWidth(0.9f)
                .border(width = 1.dp, color = Color(0xFF5c450e), shape = RoundedCornerShape(5.dp))
                .background(color = colorResource(R.color.bg_bb_narrator))
        ){
            Column(
                modifier = Modifier.fillMaxWidth().padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = text,
                    fontFamily = Lora,
                    fontStyle = FontStyle.Italic,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    color = colorResource(R.color.narrator_font_color)
                )
                Spacer(modifier = Modifier.height(5.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(R.string.narrator),
                        fontFamily = Lora,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(R.color.yellow_font),
                        fontSize = 10.sp
                    )
                }
            }
        }
    }
}

@Composable
fun RoleplayUserBubble(
    isMe: Boolean,
    characterName: String,
    message: String,
    timestamp: String,
    accentColor: Color,
    modifier: Modifier = Modifier
) {
    val bubbleShape = RoundedCornerShape(
        topStart = if(isMe) 12.dp else 0.dp,
        topEnd = if(isMe) 0.dp else 12.dp,
        bottomEnd = 12.dp,
        bottomStart = 12.dp
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if(isMe) Arrangement.End else Arrangement.Start
    ) {
        Surface(
            modifier = modifier.widthIn(max = 320.dp).padding(vertical = 4.dp),
            shape = bubbleShape,
            color = colorResource(R.color.bg_bb_user),
            shadowElevation = 2.dp
        ) {
            Row(Modifier.height(IntrinsicSize.Min)) {
                if (!isMe) Box(Modifier.fillMaxHeight().width(4.dp).background(accentColor))
                Column(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                        .weight(1f, fill = false)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = characterName.uppercase(),
                            color = accentColor,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            letterSpacing = 1.sp
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = timestamp,
                            color = Color.Gray,
                            fontSize = 10.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = message,
                        color = colorResource(R.color.semi_white),
                        fontSize = 14.sp,
                        lineHeight = 20.sp
                    )
                }
                if (isMe) Box(Modifier.fillMaxHeight().width(4.dp).background(accentColor))
            }
        }
    }
}