package com.pruebas.airolmagic.views

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.annotations.concurrent.Background
import com.pruebas.airolmagic.R
import com.pruebas.airolmagic.data.CharacterProfile
import com.pruebas.airolmagic.ui.theme.Lora
import com.pruebas.airolmagic.ui.theme.MedievalSharp
import com.pruebas.airolmagic.viewModels.CharactersListViewModel
import com.pruebas.airolmagic.viewModels.SessionViewModel

@Composable
fun MyCharactersView(
    charactersListViewModel: CharactersListViewModel,
    sessionViewModel: SessionViewModel,
    onCharacterSelected: (CharacterProfile) -> Unit,
    onNewCharacterClicked: () -> Unit
) {
    val showLoadingDialog = remember { mutableStateOf(true) }
    val userId: String = sessionViewModel.getUserId()
    var charactersList: List<CharacterProfile> = remember { emptyList() }
    charactersListViewModel.setCharactersList(
        userId = userId,
        onSuccess = { showLoadingDialog.value = false; charactersList = charactersListViewModel.getCharactersList() }
    )

    if(showLoadingDialog.value) LoadingDialog()

    Column(Modifier.fillMaxSize()){
        Column(Modifier.fillMaxWidth()){
            Text(
                text = stringResource(R.string.my_characters).uppercase(),
                fontFamily = MedievalSharp,
                color = colorResource(R.color.semi_white),
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = stringResource(R.string.select_your_hero),
                fontFamily = Lora,
                color = Color.DarkGray,
            )
            Spacer(modifier = Modifier.height(20.dp))
            LazyColumn(
                modifier = Modifier.fillMaxWidth().weight(1f),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ){
                items(count = charactersList.size){ character ->
                    CharacterBox(
                        charName = charactersList[character].name,
                        charClass = charactersList[character].classN.name,
                        charSpecies = charactersList[character].race.name,
                        charBackground = charactersList[character].background,
                        charBackgroundDetails = charactersList[character].backgroundTagDetails,
                        onCharacterSelected = { onCharacterSelected(charactersList[character]) }
                    )
                }
            }
            Box(
                modifier = Modifier.fillMaxWidth().height(100.dp).padding(horizontal = 40.dp),
                contentAlignment = Alignment.Center
            ){
                YellowButton(text = stringResource(R.string.new_character), onClicked = { onNewCharacterClicked() })
            }
        }
    }
}

@Composable
fun CharacterBox(
    charName: String,
    charClass: String,
    charSpecies: String,
    charBackground: String,
    charBackgroundDetails: String,
    onCharacterSelected: () -> Unit
){
    Box(
        modifier = Modifier
            .fillMaxWidth().height(150.dp).background(color = Color(color = 0xFF151A30))
            .border(width = 1.dp, color = colorResource(R.color.btn_unsel_border), shape = RoundedCornerShape(8.dp))
            .clickable(onClick = { onCharacterSelected() }),
    ){
        Column(
            modifier = Modifier.fillMaxSize().padding(horizontal = 15.dp, vertical = 10.dp),
            verticalArrangement = Arrangement.SpaceAround
        ){
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.Center
                ){
                    Text(
                        text = charName.uppercase(),
                        fontFamily = MedievalSharp,
                        fontWeight = FontWeight.Bold,
                        fontSize = 25.sp,
                        color = colorResource(R.color.semi_white)
                    )
                }
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "$charSpecies  °  $charClass",
                        fontFamily = Lora,
                        fontSize = 15.sp,
                        color = Color(color = 0xFF84AABF)
                    )
                }
            }
            Spacer(modifier = Modifier.height(2.dp).fillMaxWidth().background(color = Color.Gray))
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
            ){
                Text(
                    text = charBackground,
                    fontFamily = Lora,
                    fontSize = 15.sp,
                    color = colorResource(R.color.semi_white)
                )
                Text(
                    text = charBackgroundDetails,
                    fontFamily = Lora,
                    fontStyle = FontStyle.Italic,
                    fontSize = 15.sp,
                    color = Color.Gray
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CharacterBoxPrev(){
    CharacterBox(
        charName = "Thorin",
        charClass = "Guerrero",
        charSpecies = "Humano",
        charBackground = "Acolito",
        charBackgroundDetails = "Puede recibir curación gratuita y alojamiento en templos de su fe. La gente religiosa confía en él automáticamente.",
        onCharacterSelected = {}
    )
}