package com.pruebas.airolmagic.views.characterCreation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.pruebas.airolmagic.R
import com.pruebas.airolmagic.data.speciesItems
import com.pruebas.airolmagic.viewModels.CharacterViewModel
import com.pruebas.airolmagic.views.ButtonOptions
import com.pruebas.airolmagic.views.MenuWithMiddleContent

@Composable
fun SpeciesSelView(
    onBackClicked: () -> Unit,
    onNextClicked: () -> Unit,
    characterViewModel: CharacterViewModel
){
    val speciesOptions = remember { speciesItems }
    var selectedSpeciesId by remember { mutableStateOf(characterViewModel.getSpeciesData()) }
    var selectedSpeciesName by remember { mutableStateOf("") }
    if(selectedSpeciesId != 0) selectedSpeciesName = stringResource(selectedSpeciesId)

    MenuWithMiddleContent(
        backButton = true,
        pagTitle = "${stringResource(R.string.cc_step)} 3 ${stringResource(R.string.cc_of_step)} 8",
        title = stringResource(R.string.cc_species),
        subtitle = stringResource(R.string.cc_define_species),
        onBackClicked = { onBackClicked() },
        onNextClicked = {
            if(selectedSpeciesId != 0){
                characterViewModel.setSpeciesData(selectedSpeciesName, selectedSpeciesId)
                onNextClicked()
            }
        }
    ){
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ){
            items(speciesOptions){ species ->
                ButtonOptions(
                    text = species,
                    selected = (selectedSpeciesId == species),
                    onSelected = { selectedSpeciesId = species }
                )
            }
        }
    }
}