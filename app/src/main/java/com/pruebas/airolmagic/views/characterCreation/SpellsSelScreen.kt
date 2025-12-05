package com.pruebas.airolmagic.views.characterCreation

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.pruebas.airolmagic.R
import com.pruebas.airolmagic.data.lists.getCantripsAmount
import com.pruebas.airolmagic.data.lists.getClassCantrips
import com.pruebas.airolmagic.data.lists.getClassSpells
import com.pruebas.airolmagic.data.lists.getSpellsAmount
import com.pruebas.airolmagic.viewModels.CharacterViewModel
import com.pruebas.airolmagic.views.ButtonOptions
import com.pruebas.airolmagic.views.MenuWithMiddleContent
import com.pruebas.airolmagic.views.TextSubtitleWhite

@Composable
fun SpellsSelView(characterViewModel: CharacterViewModel, onBackClicked: () -> Unit, onNextClicked: () -> Unit){
    val classId = characterViewModel.getClassData()
    val classSpells: List<Int>? = getClassSpells(classId)
    val classCantrips: List<Int>? = getClassCantrips(classId)
    var countSpells: Int by remember { mutableStateOf(getSpellsAmount(classId)) }
    var countCantrips: Int by remember { mutableStateOf(getCantripsAmount(classId)) }
    val selectedSpells: List<Int> = remember { mutableListOf() }
    val selectedCantrips: List<Int> = remember { mutableListOf() }

    MenuWithMiddleContent(
        backButton = true,
        pagTitle = "${stringResource(R.string.cc_step)} 4 ${stringResource(R.string.cc_of_step)} 8",
        title = stringResource(R.string.cc_spells_and_cantrips),
        onBackClicked = { onBackClicked() },
        onNextClicked = {
            if(countSpells == 0 && countCantrips == 0) {
                characterViewModel.setSpellsAndCantrips(spells = selectedSpells, cantrips = selectedCantrips)
                onNextClicked()
            }
        }
    ){
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ){
            if(classSpells != null) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth().weight(1f)
                        .border(
                            width = 1.dp,
                            color = colorResource(R.color.btn_unsel_border),
                            shape = RoundedCornerShape(15.dp)
                        )
                ) {
                    Column(Modifier.fillMaxSize().padding(horizontal = 15.dp, vertical = 10.dp)){
                        TextSubtitleWhite(stringResource(R.string.spells))
                        Spacer(Modifier.height(7.dp))
                        Column(
                            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ){

                            classSpells.forEach { spell ->
                                var selected by remember { mutableStateOf(false) }
                                ButtonOptions(
                                    text = spell,
                                    selected = selected,
                                    onSelected = {
                                        if (selected) {
                                            selected = false
                                            countSpells += 1
                                            selectedSpells.toMutableList().remove(spell)
                                        } else {
                                            if (countSpells > 0) {
                                                selected = true
                                                countSpells -= 1
                                                selectedSpells.toMutableList().add(spell)
                                            }
                                        }
                                    }
                                )
                            }
                        }
                    }
                }
            }
            if(classCantrips != null && classSpells != null) Spacer(Modifier.height(10.dp))
            if(classCantrips != null){
                Box(
                    modifier = Modifier
                        .fillMaxWidth().weight(1f)
                        .border(
                            width = 1.dp,
                            color = colorResource(R.color.btn_unsel_border),
                            shape = RoundedCornerShape(15.dp)
                        )
                ) {
                    Column(Modifier.fillMaxSize().padding(horizontal = 15.dp, vertical = 10.dp)){
                        TextSubtitleWhite(stringResource(R.string.cantrips))
                        Spacer(Modifier.height(7.dp))
                        Column(
                            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ){
                            classCantrips.forEach { cantrip ->
                                var selected by remember { mutableStateOf(false) }
                                ButtonOptions(
                                    text = cantrip,
                                    selected = selected,
                                    onSelected = {
                                        if (selected) { selected = false; countCantrips += 1; selectedCantrips.toMutableList().remove(cantrip) }
                                        else {
                                            if (countCantrips > 0) { selected = true; countCantrips -= 1; selectedCantrips.toMutableList().add(cantrip) }
                                        }
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}