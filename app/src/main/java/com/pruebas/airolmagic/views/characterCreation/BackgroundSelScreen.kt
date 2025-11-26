package com.pruebas.airolmagic.views.characterCreation

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pruebas.airolmagic.R
import com.pruebas.airolmagic.data.BackgroundRepository
import com.pruebas.airolmagic.data.gamingSetItems
import com.pruebas.airolmagic.ui.theme.Lora
import com.pruebas.airolmagic.views.ButtonOptions
import com.pruebas.airolmagic.views.DropdownButton
import com.pruebas.airolmagic.views.MenuWithMiddleContent

@Composable
fun BackgroundSelView(onBackClicked: () -> Unit){
    val backgroundOptions = remember { BackgroundRepository.list }
    var selectedBackground by remember { mutableStateOf(backgroundOptions.first()) }
    var selectedItem by remember { mutableStateOf(R.string.choose_gaming_set) }

    MenuWithMiddleContent(
        backButton = true,
        pagTitle = "${stringResource(R.string.cc_step)} 2 ${stringResource(R.string.cc_of_step)} 8",
        title = stringResource(R.string.cc_background),
        subtitle = stringResource(R.string.cc_combat_style),
        onBackClicked = { onBackClicked() },
        onNextClicked = {  }
    ){
        Column(Modifier.fillMaxSize()) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(bottom = 10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(backgroundOptions) { background ->
                    ButtonOptions(
                        text = background.backgroundName,
                        selected = (selectedBackground.id == background.id),
                        onSelected = { selectedBackground = background }
                    )
                }
            }
            if(selectedBackground.id == 4){
                DropdownButton(
                    label = stringResource(R.string.gaming_set),
                    items = gamingSetItems,
                    selectedItem = selectedItem,
                    onItemSelected = { newItem -> selectedItem = newItem }
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Column(
                modifier = Modifier.fillMaxWidth().border(
                    width = 2.dp,
                    color = colorResource(R.color.semi_white),
                    shape = RoundedCornerShape(10.dp)
                ).padding(10.dp)
            ) {
                TextWithLabels(
                    label = "${stringResource(R.string.bg_details_ability_scores)}: ",
                    description = "${stringResource(selectedBackground.abilityScores[0])}, ${stringResource(selectedBackground.abilityScores[1])}, ${stringResource(selectedBackground.abilityScores[2])}"
                )
                TextWithLabels(
                    label = "${stringResource(R.string.bg_details_feat)}: ",
                    description = stringResource(selectedBackground.feat)
                )
                TextWithLabels(
                    label = "${stringResource(R.string.bg_details_skills)}: ",
                    description = "${stringResource(selectedBackground.skills[0])}, ${stringResource(selectedBackground.skills[1])}"
                )
                TextWithLabels(
                    label = "${stringResource(R.string.bg_details_tool_proficiencies)}: ",
                    description = stringResource(selectedBackground.toolProficiencies)
                )
                TextWithLabels(
                    label = "${stringResource(R.string.bg_details_equipment)}: ",
                    description = stringResource(selectedBackground.equipment)
                )
            }
        }
    }
}

@Composable
fun TextWithLabels(label: String, description: String){
    Text(
        text = buildAnnotatedString {
            withStyle(
                SpanStyle(
                    fontFamily = Lora,
                    fontWeight = FontWeight.SemiBold,
                    color = colorResource(R.color.semi_white),
                    fontSize = 15.sp
                )
            ) {
                append(label)
            }

            withStyle(
                SpanStyle(
                    fontFamily = Lora,
                    color = colorResource(R.color.semi_white),
                    fontSize = 15.sp
                )
            ) {
                append(description)
            }
        }
    )
}