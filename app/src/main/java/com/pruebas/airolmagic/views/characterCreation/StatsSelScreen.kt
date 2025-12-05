package com.pruebas.airolmagic.views.characterCreation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pruebas.airolmagic.R
import com.pruebas.airolmagic.ui.theme.Lora
import com.pruebas.airolmagic.ui.theme.MedievalSharp
import com.pruebas.airolmagic.viewModels.CharacterViewModel
import com.pruebas.airolmagic.views.MenuWithMiddleContent
import kotlin.math.floor

@Composable
fun StatsSelView(
    onBackClicked: () -> Unit,
    characterViewModel: CharacterViewModel,
    onNextClicked: () -> Unit
){
    val backgroundId by remember { mutableStateOf(characterViewModel.getBackgroundId()) }
    val clenAttributes by remember { mutableStateOf(characterViewModel.getCleanAttributes()) }
    var totalPoints by remember { mutableStateOf(27) }
    var extraPoints by remember { mutableStateOf(3) }
    var strValue by remember { mutableStateOf(8) }
    var desValue by remember { mutableStateOf(8) }
    var conValue by remember { mutableStateOf(8) }
    var intValue by remember { mutableStateOf(8) }
    var sabValue by remember { mutableStateOf(8) }
    var carValue by remember { mutableStateOf(8) }
    var extraV1 by remember { mutableStateOf(0) }
    var extraV2 by remember { mutableStateOf(0) }
    var extraV3 by remember { mutableStateOf(0) }
    var extraN1 by remember { mutableStateOf(R.string.placeholder_text) }
    var extraN2 by remember { mutableStateOf(R.string.placeholder_text) }
    var extraN3 by remember { mutableStateOf(R.string.placeholder_text) }

    if(clenAttributes != null){
        strValue = clenAttributes!!.strength
        desValue = clenAttributes!!.dexterity
        conValue = clenAttributes!!.constitution
        intValue = clenAttributes!!.intelligence
        sabValue = clenAttributes!!.wisdom
        carValue = clenAttributes!!.charisma
        totalPoints = 0
    }

    if(backgroundId != 0){
        when(backgroundId){
            R.string.background_acolyte -> {
                extraN1 = R.string.skill_intelligence
                extraN2 = R.string.skill_wisdom
                extraN3 = R.string.skill_charisma
            }
            R.string.background_criminal -> {
                extraN1 = R.string.skill_dexterity
                extraN2 = R.string.skill_constitution
                extraN3 = R.string.skill_intelligence
            }
            R.string.background_sage -> {
                extraN1 = R.string.skill_constitution
                extraN2 = R.string.skill_intelligence
                extraN3 = R.string.skill_wisdom
            }
            R.string.background_soldier -> {
                extraN1 = R.string.skill_dexterity
                extraN2 = R.string.skill_strength
                extraN3 = R.string.skill_constitution
            }
        }
    }

    fun changeValue(value: Int, skillValue: Int): Int{
        var newValue = 0
        if(value == 1 && skillValue < 18 && totalPoints > 0) {
            newValue = 1
            totalPoints -= 1
        }
        if(value == -1 && skillValue > 8 && totalPoints < 27) {
            newValue = -1
            totalPoints += 1
        }

        return newValue
    }

    fun validate(value: Int, skill: Int){
        when(skill){
            R.string.skill_strength -> { strValue += changeValue(value, strValue) }
            R.string.skill_dexterity -> { desValue += changeValue(value, desValue) }
            R.string.skill_constitution -> { conValue += changeValue(value, conValue) }
            R.string.skill_intelligence -> { intValue += changeValue(value, intValue) }
            R.string.skill_wisdom -> { sabValue += changeValue(value, sabValue) }
            R.string.skill_charisma -> { carValue += changeValue(value, carValue) }
        }
    }

    val offset = remember { mutableStateOf(0f) }
    MenuWithMiddleContent(
        backButton = true,
        pagTitle = "${stringResource(R.string.cc_step)} 4 ${stringResource(R.string.cc_of_step)} 8",
        title = stringResource(R.string.cc_stats),
        subtitle = stringResource(R.string.cc_define_stats),
        onBackClicked = { onBackClicked() },
        onNextClicked = {
            if(totalPoints == 0 && extraPoints == 0){
                characterViewModel.setStatsData(strValue, desValue, conValue, intValue, sabValue, carValue, extraV1, extraV2, extraV3)
                onNextClicked()
            }
        }
    ){
        Column(
            modifier = Modifier.fillMaxWidth().verticalScroll(rememberScrollState())
        ){
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Absolute.Right) {
                Text(text = "${totalPoints}", fontFamily = MedievalSharp, color = colorResource(R.color.semi_white), fontSize = 22.sp)
                Text(text = "/27 pts", fontFamily = Lora, color = Color.Gray, fontSize = 16.sp)
            }
            Column(Modifier.fillMaxWidth()){
                SetStatsBox(label = R.string.skill_strength, value = strValue, onClickedBtn = { new -> validate(new, R.string.skill_strength) })
                Spacer(modifier = Modifier.height(10.dp))
                SetStatsBox(label = R.string.skill_dexterity, value = desValue, onClickedBtn = { new -> validate(new, R.string.skill_dexterity) })
                Spacer(modifier = Modifier.height(10.dp))
                SetStatsBox(label = R.string.skill_constitution, value = conValue, onClickedBtn = { new -> validate(new, R.string.skill_constitution) })
                Spacer(modifier = Modifier.height(10.dp))
                SetStatsBox(label = R.string.skill_intelligence, value = intValue, onClickedBtn = { new -> validate(new, R.string.skill_intelligence) })
                Spacer(modifier = Modifier.height(10.dp))
                SetStatsBox(label = R.string.skill_wisdom, value = sabValue, onClickedBtn = { new -> validate(new, R.string.skill_wisdom) })
                Spacer(modifier = Modifier.height(10.dp))
                SetStatsBox(label = R.string.skill_charisma, value = carValue, onClickedBtn = { new -> validate(new, R.string.skill_charisma) })
            }
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Text(text = stringResource(R.string.cc_extra_stats), fontFamily = Lora, color = Color.Gray, fontSize = 20.sp)
                Row{
                    Text(text = "${extraPoints}", fontFamily = MedievalSharp, color = colorResource(R.color.semi_white), fontSize = 22.sp)
                    Text(text = "/3 pts", fontFamily = Lora, color = Color.Gray, fontSize = 16.sp)
                }
            }
            Column(Modifier.fillMaxWidth()){
                SetStatsBox(counter = false,label = extraN1, value = extraV1, onClickedBtn = { new ->
                    if(new > 0 && extraPoints > 0){ extraV1 += 1; extraPoints -= 1 }
                    if(new < 0 && extraV1 > 0){ extraV1 -= 1; extraPoints += 1 }
                })
                Spacer(modifier = Modifier.height(10.dp))
                SetStatsBox(counter = false,label = extraN2, value = extraV2, onClickedBtn = { new ->
                    if(new > 0 && extraPoints > 0){ extraV2 += 1; extraPoints -= 1 }
                    if(new < 0 && extraV2 > 0){ extraV2 -= 1; extraPoints += 1 }
                })
                Spacer(modifier = Modifier.height(10.dp))
                SetStatsBox(counter = false,label = extraN3, value = extraV3, onClickedBtn = { new ->
                    if(new > 0 && extraPoints > 0){ extraV3 += 1; extraPoints -= 1 }
                    if(new < 0 && extraV3 > 0){ extraV3 -= 1; extraPoints += 1 }
                })
            }
        }
    }
}

@Composable
fun SetStatsBox(counter: Boolean = true,label: Int,value: Int, onClickedBtn: (Int) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(colorResource(R.color.bg_black_purple))
            .border(width = 1.dp, color = colorResource(R.color.btn_unsel_border), shape = RoundedCornerShape(10.dp))
            .padding(10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){
        Box(Modifier.weight(1f)){
            Text(
                text = stringResource(label).uppercase(),
                fontFamily = Lora,
                fontSize = 16.sp,
                color = Color.LightGray
            )
        }
        Row(Modifier.width(110.dp)){
            AddRemoveStatButton(symbol = "-", onClickedBtn = onClickedBtn)
            Spacer(modifier = Modifier.width(8.dp))
            Box(
                modifier = Modifier.height(25.dp).width(30.dp),
                contentAlignment = Alignment.Center
            ){
                Text(
                    text = "${value}",
                    fontFamily = Lora,
                    fontSize = 20.sp,
                    color = colorResource(R.color.semi_white)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            AddRemoveStatButton(symbol = "+", onClickedBtn = onClickedBtn)
        }
        if(counter) {
            Box(
                modifier = Modifier.height(30.dp).width(35.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "${floor(((value.toDouble() - 10) / 2)).toInt()}",
                    fontFamily = Lora,
                    fontSize = 20.sp,
                    color = colorResource(R.color.semi_white)
                )
            }
        }
    }
}

@Composable
fun AddRemoveStatButton(symbol: String, onClickedBtn: (Int) -> Unit){
    val addValue = if (symbol == "+") 1 else -1

    Box(
        modifier = Modifier.size(30.dp)
            .background(colorResource(R.color.btn_unsel_darkblue))
            .border(1.dp, colorResource(R.color.yellow_font), shape = RoundedCornerShape(4.dp))
            .clickable(
                onClick = { onClickedBtn(addValue) }
            ),
        contentAlignment = Alignment.Center
    ){ Text(text = symbol, color = colorResource(R.color.yellow_font)) }
}