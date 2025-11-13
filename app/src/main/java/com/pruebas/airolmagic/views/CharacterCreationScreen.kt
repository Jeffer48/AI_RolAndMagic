package com.pruebas.airolmagic.views

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pruebas.airolmagic.R
import com.pruebas.airolmagic.ui.theme.AIRolMagicTheme
import com.pruebas.airolmagic.data.alignItems
import com.pruebas.airolmagic.data.classItems
import com.pruebas.airolmagic.data.speciesItems
import com.pruebas.airolmagic.data.moralityItems
import com.pruebas.airolmagic.data.descriptionOptions

@Preview(showBackground = true)
@Composable
fun CharacterCreationPreview() {
    AIRolMagicTheme {
        CharacterCreationView()
    }
}

@Composable
fun CharacterCreationView(){
    var character_name_sel by remember { mutableStateOf("") }
    var sel_species by remember { mutableStateOf(R.string.cc_species) }
    var sel_alignment by remember { mutableStateOf(R.string.cc_alignment) }
    var sel_morality by remember { mutableStateOf(R.string.cc_morality) }
    var sel_class by remember { mutableStateOf(R.string.cc_class) }
    var description by remember { mutableStateOf(0) }

    Column(Modifier.fillMaxSize().background(colorResource(R.color.bg_black_purple))){
        TransparentTextField(label = R.string.cc_character_name, value_txt = character_name_sel, onValueChange = { newText -> character_name_sel = newText })
        Spacer(modifier = Modifier.height(20.dp))

        DropdownButton(label = stringResource(R.string.cc_species), items = speciesItems, selectedItem = sel_species, onItemSelected = { newItem -> sel_species = newItem })
        DropdownButton(label = stringResource(R.string.cc_alignment), items = alignItems, selectedItem = sel_alignment, onItemSelected = { newItem -> sel_alignment = newItem; description = descriptionOptions(sel_alignment, sel_morality) })
        DropdownButton(label = stringResource(R.string.cc_morality), items = moralityItems, selectedItem = sel_morality, onItemSelected = { newItem -> sel_morality = newItem; description = descriptionOptions(sel_alignment, sel_morality) })
        DropdownButton(label = stringResource(R.string.cc_class), items = classItems, selectedItem = sel_class, onItemSelected = { newItem -> sel_class = newItem })
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = stringResource(R.string.cc_align_morality_desc),
            color = colorResource(R.color.white),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Box(Modifier.border(width = 1.dp, color = colorResource(R.color.semi_white))){
            Text(
                text = if(description != 0) stringResource(description) else "",
                color = colorResource(R.color.white),
                modifier = Modifier.background(Color.Transparent).padding(10.dp).fillMaxWidth().height(150.dp)
            )
        }
    }
}