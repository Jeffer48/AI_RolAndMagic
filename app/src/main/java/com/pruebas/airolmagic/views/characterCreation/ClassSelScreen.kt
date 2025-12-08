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
import com.pruebas.airolmagic.data.classItems
import com.pruebas.airolmagic.viewModels.CharacterViewModel
import com.pruebas.airolmagic.views.ButtonOptions
import com.pruebas.airolmagic.views.MenuWithMiddleContent

@Composable
fun ClassSelView(onNextClicked: () -> Unit, characterViewModel: CharacterViewModel) {
    val classOptions = remember { classItems }
    var selectedClassId by remember { mutableStateOf(characterViewModel.getClassData()) }
    var selectedClassName by remember { mutableStateOf("") }

    if(selectedClassId != 0) selectedClassName = stringResource(selectedClassId)

    MenuWithMiddleContent(
        pagTitle = "${stringResource(R.string.cc_step)} 1",
        title = stringResource(R.string.cc_class),
        subtitle = stringResource(R.string.cc_combat_style),
        onBackClicked = {  },
        onNextClicked = {
            if(selectedClassId != 0) {
                characterViewModel.setClassData(selectedClassName, selectedClassId)
                onNextClicked()
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ){
            items(classOptions){ classItem ->
                ButtonOptions(
                    text = classItem,
                    selected = (selectedClassId == classItem),
                    onSelected = { selectedClassId = classItem }
                )
            }
        }
    }
}