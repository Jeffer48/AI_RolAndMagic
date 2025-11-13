package com.pruebas.airolmagic.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pruebas.airolmagic.R
import com.pruebas.airolmagic.ui.theme.Lora
import com.pruebas.airolmagic.ui.theme.MedievalSharp

@Composable
fun WaitLobbyView(){
    Column(
        modifier = Modifier.fillMaxSize().background(colorResource(R.color.bg_black_purple)),
        verticalArrangement = Arrangement.SpaceBetween
    ){
        Column(horizontalAlignment = Alignment.CenterHorizontally){
            Text(
                text = "Nombre de la partida",
                color = colorResource(R.color.semi_white),
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
                    text = "A4F8K2",
                    color = colorResource(R.color.semi_white),
                    fontFamily = Lora,
                    fontSize = 25.sp,
                    fontWeight = Bold,
                    letterSpacing = 10.sp
                )
                IconButton(onClick = {}){
                    Icon(
                        painter = painterResource(R.drawable.outline_content_copy_24),
                        contentDescription = stringResource(R.string.copy_invitation_code),
                        tint = colorResource(R.color.semi_white)
                    )
                }
            }
            Spacer(Modifier.height(50.dp))
            Text(
                text = stringResource(R.string.joined_players),
                color = colorResource(R.color.semi_white),
                fontFamily = Lora,
                fontSize = 20.sp,
                fontWeight = SemiBold
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth().height(150.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ){
            OutlinedButton(onClick = {}) {
                Text(
                    text = stringResource(R.string.start_game),
                    color = colorResource(R.color.semi_white),
                    fontFamily = Lora,
                    fontSize = 15.sp,
                )
            }
        }
    }
}