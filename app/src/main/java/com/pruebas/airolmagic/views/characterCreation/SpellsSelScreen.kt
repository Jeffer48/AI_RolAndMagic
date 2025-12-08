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
import androidx.compose.runtime.mutableStateListOf
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
import com.pruebas.airolmagic.views.ErrorDialog
import com.pruebas.airolmagic.views.LoadingDialog
import com.pruebas.airolmagic.views.MenuWithMiddleContent
import com.pruebas.airolmagic.views.TextSubtitleWhite

@Composable
fun SpellsSelView(
    characterViewModel: CharacterViewModel,
    onBackClicked: () -> Unit,
    onNextClicked: () -> Unit,
    onFailedToCreateCharacter: () -> Unit
){
    val classId = characterViewModel.getClassData()
    val classSpells: List<Int>? = getClassSpells(classId)
    val classCantrips: List<Int>? = getClassCantrips(classId)
    var countSpells: Int by remember { mutableStateOf(getSpellsAmount(classId)) }
    var countCantrips: Int by remember { mutableStateOf(getCantripsAmount(classId)) }
    val selectedSpells = remember { mutableStateListOf<Int>() }
    val selectedCantrips = remember { mutableStateListOf<Int>() }
    var showLoadingDialog by remember { mutableStateOf(false) }
    var showErrorDialog by remember { mutableStateOf(false) }

    if(showLoadingDialog) LoadingDialog()
    if(showErrorDialog) ErrorDialog(texto = stringResource(R.string.err_saving_character), onDismissRequest = { showErrorDialog = false; onFailedToCreateCharacter(); })

    MenuWithMiddleContent(
        backButton = true,
        isLastPage = true,
        pagTitle = "${stringResource(R.string.cc_step)} 6",
        title = stringResource(R.string.cc_spells_and_cantrips),
        onBackClicked = { onBackClicked() },
        onNextClicked = {
            if(countSpells == 0 && countCantrips == 0) {
                showLoadingDialog = true
                characterViewModel.setSpellsAndCantrips(spells = selectedSpells, cantrips = selectedCantrips)
                characterViewModel.saveUserData(
                    onSuccess = { onNextClicked(); showLoadingDialog = false },
                    onError = { showErrorDialog = true }
                )
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
                                val isSelected = selectedSpells.contains(spell)
                                ButtonOptions(
                                    text = spell,
                                    selected = isSelected,
                                    onSelected = {
                                        if (isSelected) {
                                            countSpells += 1
                                            selectedSpells.remove(spell)
                                        } else {
                                            if (countSpells > 0) {
                                                countSpells -= 1
                                                selectedSpells.add(spell)
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
                                val isSelected = selectedCantrips.contains(cantrip)
                                ButtonOptions(
                                    text = cantrip,
                                    selected = isSelected,
                                    onSelected = {
                                        if (isSelected) {
                                            countCantrips += 1
                                            selectedCantrips.remove(cantrip)
                                        } else {
                                            if (countCantrips > 0) {
                                                countCantrips -= 1
                                                selectedCantrips.add(cantrip)
                                            }
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