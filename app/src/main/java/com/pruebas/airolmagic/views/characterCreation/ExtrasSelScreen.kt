package com.pruebas.airolmagic.views.characterCreation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.pruebas.airolmagic.data.alignItems
import com.pruebas.airolmagic.data.descriptionOptions
import com.pruebas.airolmagic.data.languageItems
import com.pruebas.airolmagic.data.moralityItems
import com.pruebas.airolmagic.ui.theme.Lora
import com.pruebas.airolmagic.viewModels.CharacterViewModel
import com.pruebas.airolmagic.viewModels.SessionViewModel
import com.pruebas.airolmagic.views.ColoredTextField
import com.pruebas.airolmagic.views.DropdownBox
import com.pruebas.airolmagic.views.ErrorDialog
import com.pruebas.airolmagic.views.LoadingDialog
import com.pruebas.airolmagic.views.MenuWithMiddleContent
import com.pruebas.airolmagic.views.TextSubtitleWhite

@Composable
fun ExtrasSelView(
    characterViewModel: CharacterViewModel,
    onBackClicked: () -> Unit,
    onNextClicked: () -> Unit,
    sessionViewModel: SessionViewModel,
    isMagicClass: Boolean,
    onFailedToCreateCharacter: () -> Unit
) {
    var char_name by remember { mutableStateOf(characterViewModel.getCharacterName()) }
    var alignDrop by remember { mutableStateOf(false) }
    var moralityDrop by remember { mutableStateOf(false) }
    var languageDrop by remember { mutableStateOf(false) }
    var align by remember { mutableStateOf(characterViewModel.getAlignment()) }
    var morality by remember { mutableStateOf(characterViewModel.getMorality()) }
    var language by remember { mutableStateOf(characterViewModel.getExtraLanguage()) }
    var text_align by remember { mutableStateOf("") }
    var text_language by remember { mutableStateOf("") }
    var showLoadingDialog by remember { mutableStateOf(false) }
    var showErrorDialog by remember { mutableStateOf(false) }

    if(showLoadingDialog) LoadingDialog()
    if(showErrorDialog) ErrorDialog(texto = stringResource(R.string.err_saving_character), onDismissRequest = { showErrorDialog = false; onFailedToCreateCharacter() })

    text_align = stringResource(align) + " - " + stringResource(morality)
    text_language = stringResource(R.string.language_common) + ", " + stringResource(language)
    MenuWithMiddleContent(
        backButton = true,
        isLastPage = !isMagicClass,
        pagTitle = "${stringResource(R.string.cc_step)} 5",
        title = stringResource(R.string.cc_details),
        onBackClicked = { onBackClicked() },
        onNextClicked = {
            if(char_name.isNotEmpty()){
                characterViewModel.setCharacterExtras(
                    userId = sessionViewModel.getUserId(),
                    name = char_name,
                    alignment = text_align,
                    languages = text_language,
                    alignmentId = align,
                    moralityId = morality,
                    languagesId = language
                )
                if(isMagicClass) onNextClicked()
                else{
                    showLoadingDialog = true
                    characterViewModel.saveUserData(
                        onSuccess = { onNextClicked(); showLoadingDialog = false },
                        onError = { showErrorDialog = true }
                    )
                }
            }
        }
    ){
        Column(Modifier.fillMaxSize().background(color = colorResource(R.color.bg_black_purple))){
            TextSubtitleWhite(stringResource(R.string.cc_character_name))
            Spacer(modifier = Modifier.height(5.dp))
            ColoredTextField(
                textValue = char_name,
                onValueChange = { newValue -> char_name = newValue }
            )
            Spacer(modifier = Modifier.height(15.dp))

            TextSubtitleWhite(stringResource(R.string.cc_alignment))
            Spacer(modifier = Modifier.height(5.dp))
            DropdownBox(
                selected = align,
                items = alignItems,
                expanded = alignDrop,
                onClick = { alignDrop = true },
                onDismissRequest = { alignDrop = false },
                onItemSelected = { new ->
                    align = new
                    alignDrop = false
                }
            )
            Spacer(modifier = Modifier.height(10.dp))

            TextSubtitleWhite(stringResource(R.string.cc_morality))
            Spacer(modifier = Modifier.height(5.dp))
            DropdownBox(
                selected = morality,
                items = moralityItems,
                expanded = moralityDrop,
                onClick = { moralityDrop = true },
                onDismissRequest = { moralityDrop = false },
                onItemSelected = { new ->
                    morality = new
                    moralityDrop = false
                }
            )
            Spacer(modifier = Modifier.height(10.dp))

            Box(
                modifier = Modifier.fillMaxWidth().border(width = 1.dp, color = colorResource(R.color.btn_sel_blue), shape = RoundedCornerShape(8.dp))
            ){
                Column(Modifier.padding(15.dp)) {
                    TextSubtitleWhite(stringResource(R.string.cc_languages))
                    Spacer(modifier = Modifier.height(5.dp))
                    Row{
                        Box(
                            modifier = Modifier
                                .height(45.dp).background(color = Color.Black)
                                .border(width = 1.dp, color = colorResource(R.color.btn_sel_blue), shape = RoundedCornerShape(6.dp)),
                            contentAlignment = Alignment.Center
                        ){
                            Text(
                                text = stringResource(R.string.language_common),
                                color = Color.Gray, fontFamily = Lora, fontSize = 12.sp,
                                modifier = Modifier.padding(horizontal = 10.dp)
                            )
                        }
                        Box(
                            modifier = Modifier.height(45.dp).width(40.dp),
                            contentAlignment = Alignment.Center
                        ){Text(text = " + ", color = Color.Gray, fontSize = 15.sp)}
                        DropdownBox(
                            selected = language,
                            items = languageItems,
                            expanded = languageDrop,
                            height = 45,
                            onClick = { languageDrop = true },
                            onDismissRequest = { languageDrop = false },
                            onItemSelected = { new ->
                                language = new
                                languageDrop = false
                            }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(10.dp))

            Box(Modifier.border(width = 1.dp, color = colorResource(R.color.btn_sel_blue), shape = RoundedCornerShape(8.dp))){
                Column(Modifier.padding(15.dp)) {
                    TextSubtitleWhite(stringResource(R.string.cc_align_morality_desc))
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = stringResource(descriptionOptions(align, morality)),
                        color = Color.LightGray, fontFamily = Lora
                    )
                }
            }
        }
    }
}