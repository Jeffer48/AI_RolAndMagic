package com.pruebas.airolmagic.views.characterCreation

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.pruebas.airolmagic.R
import com.pruebas.airolmagic.viewModels.CharacterViewModel
import com.pruebas.airolmagic.views.MenuWithMiddleContent

@Composable
fun ExtrasSelView(characterViewModel: CharacterViewModel, onBackClicked: () -> Unit) {
    MenuWithMiddleContent(
        backButton = true,
        pagTitle = "${stringResource(R.string.cc_step)} 4 ${stringResource(R.string.cc_of_step)} 8",
        title = stringResource(R.string.cc_details),
        onBackClicked = { onBackClicked() },
        onNextClicked = {  }
    ){}
}