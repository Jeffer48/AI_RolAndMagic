package com.pruebas.airolmagic.views

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle.Companion.Italic
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.PopupProperties
import com.pruebas.airolmagic.R
import com.pruebas.airolmagic.ui.theme.AIRolMagicTheme
import com.pruebas.airolmagic.ui.theme.Lora
import com.pruebas.airolmagic.ui.theme.MedievalSharp

@Composable
fun OptionButton(text: Int, onClick: () -> Unit){
    TextButton(onClick = onClick){
        Text(
            text = stringResource(text),
            fontFamily = MedievalSharp,
            color = colorResource(R.color.semi_white),
            fontSize = 15.sp
        )
    }
}

@Composable
fun TransparentTextField(label: Int, value_txt: String, isError: Boolean = false, onValueChange: (String) -> Unit){
    OutlinedTextField(
        value = value_txt,
        isError = isError,
        onValueChange = { onValueChange(it) },
        label = { Text(text = stringResource(label)) },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        colors = TextFieldDefaults.colors(
            focusedTextColor = colorResource(R.color.semi_white),
            unfocusedTextColor = colorResource(R.color.semi_white),
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            errorLabelColor = Color.Red,
            errorTrailingIconColor = Color.Red,
            errorContainerColor = Color.Transparent,
            errorTextColor = colorResource(R.color.semi_white),
        )
    )
}

@Composable
fun ColoredTextField(singleLine: Boolean = true, textValue: String, onValueChange: (String) -> Unit){
    TextField(
        modifier = Modifier.fillMaxWidth().height(55.dp)
            .border(width = 1.dp, color = colorResource(R.color.btn_unsel_border), shape = RoundedCornerShape(8.dp)),
        value = textValue,
        singleLine = singleLine,
        placeholder = { Text(text = "Ej. Thorin...") },
        onValueChange = { onValueChange(it) },
        colors = TextFieldDefaults.colors(
            focusedTextColor = colorResource(R.color.semi_white),
            unfocusedTextColor = colorResource(R.color.semi_white),
            focusedContainerColor = colorResource(R.color.btn_unsel_darkblue),
            unfocusedContainerColor = colorResource(R.color.btn_unsel_darkblue),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            focusedPlaceholderColor = Color.Gray,
            unfocusedPlaceholderColor = Color.Gray,
        ),
    )
}

@Composable
fun DropdownBox(
    selected: Int,
    items: List<Int>,
    expanded: Boolean,
    height: Int = 55,
    onClick: () -> Unit,
    onDismissRequest: () -> Unit,
    onItemSelected: (Int) -> Unit
){
    Box(
        modifier = Modifier
            .fillMaxWidth().clickable(onClick = { onClick() }).height(height.dp)
            .background(color = colorResource(R.color.btn_unsel_darkblue))
            .border(width = 1.dp, color = colorResource(R.color.btn_unsel_border), shape = RoundedCornerShape(8.dp))
    ){
        Row(
            modifier = Modifier.fillMaxSize().padding(horizontal = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = stringResource(selected),
                fontFamily = Lora,
                color = colorResource(R.color.semi_white)
            )
            Icon(
                painter = painterResource(R.drawable.ic_dropdown),
                contentDescription = stringResource(R.string.btn_dropdown),
                tint = colorResource(R.color.semi_white)
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { onDismissRequest() },
            containerColor = colorResource(R.color.btn_sel_blue),
            properties = PopupProperties(focusable = false),
        ){
            items.forEach { item ->
                DropdownBoxItem(item, onItemSelected)
            }
        }
    }
}

@Composable
fun DropdownBoxItem(item: Int, onItemSelected: (Int) -> Unit){
    DropdownMenuItem(
        text = { Text(text = stringResource(item), color = colorResource(R.color.semi_white)) },
        onClick = { onItemSelected(item) }
    )
}

@Composable
fun DropdownButton(
    label: String,
    items: List<Int>,
    selectedItem: Int,
    onItemSelected: (Int) -> Unit
){
    var expanded by remember { mutableStateOf(false) }
    var selected by remember { mutableIntStateOf(R.string.select_option) }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(text=label, color = colorResource(R.color.semi_white))
        OutlinedButton(
            onClick = { expanded = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(selectedItem),
                color = colorResource(R.color.semi_white),
                fontFamily = Lora
            )
            Icon(
                painter = painterResource(R.drawable.ic_dropdown),
                contentDescription = stringResource(R.string.btn_dropdown),
                tint = colorResource(R.color.semi_white)
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.fillMaxWidth().heightIn(max = 500.dp).background(colorResource(R.color.bg_black_purple)),
                border = BorderStroke(width = 1.dp, color = colorResource(R.color.semi_white)),
                offset = DpOffset(0.dp, 10.dp)
            ) {
                items.forEach {item ->
                    DropdownMenuItem(
                        text = { Text(text = stringResource(item), color = colorResource(R.color.semi_white)) },
                        onClick = {
                            onItemSelected(item)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun ErrorDialog(texto: String,onDismissRequest: () -> Unit) {
    PreDialog(texto, "Error!", R.color.error, onDismissRequest)
}

@Composable
fun WarningDialog(texto: String,onDismissRequest: () -> Unit) {
    PreDialog(texto, stringResource(R.string.err_warning), R.color.yellow_font, onDismissRequest)
}

@Composable
fun SuccessDialog(texto: String,onDismissRequest: () -> Unit) {
    PreDialog(texto, stringResource(R.string.msg_success), R.color.success, onDismissRequest)
}

@Composable
fun LoadingDialog(){
    Dialog(onDismissRequest = {}){
        Card(
            modifier = Modifier.width(500.dp).height(220.dp),
            shape = RoundedCornerShape(10.dp),
            border = BorderStroke(width = 2.dp, color = colorResource(R.color.yellow_font)),
            colors = CardDefaults.cardColors(
                containerColor = colorResource(R.color.bg_black_purple)
            )
        ){
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ){
                CircularProgressIndicator(
                    modifier = Modifier.size(64.dp),
                    color = Color.Gray,
                    trackColor = colorResource(R.color.yellow_font)
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = stringResource(R.string.alert_loadding),
                    fontFamily = Lora,
                    color = colorResource(R.color.yellow_font),
                    fontSize = 15.sp
                )
            }
        }
    }
}

@Composable
fun PreDialog(texto: String, txtHead: String, color: Int,onDismissRequest: () -> Unit){
    Dialog(onDismissRequest = {onDismissRequest}){
        Card(
            modifier = Modifier.width(500.dp).height(220.dp),
            shape = RoundedCornerShape(10.dp),
            border = BorderStroke(width = 2.dp, color = colorResource(color)),
            colors = CardDefaults.cardColors(
                containerColor = colorResource(R.color.bg_black_purple)
            )
        ){
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ){
                Text(
                    text = txtHead,
                    textAlign = TextAlign.Center,
                    color = colorResource(color),
                    fontFamily = MedievalSharp,
                    fontSize = 30.sp,
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = texto,
                    textAlign = TextAlign.Center,
                    color = colorResource(R.color.semi_white),
                    fontFamily = Lora,
                    fontSize = 15.sp
                )
                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    modifier = Modifier.width(100.dp),
                    onClick = { onDismissRequest() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(color)
                    )
                ){
                    Text(
                        text = stringResource(R.string.close),
                        fontFamily = Lora,
                        color = colorResource(R.color.semi_white),
                        fontSize = 15.sp
                    )
                }
            }
        }
    }
}

@Composable
fun TextDescription(text: String, modifier: Modifier = Modifier){
    Text(
        modifier = modifier,
        text = text,
        color = Color.Gray,
        fontFamily = Lora,
        fontStyle = Italic,
        textAlign = TextAlign.Center,
    )
}

@Composable
fun ButtonOptions(text: Int, selected: Boolean = false, onSelected: () -> Unit = {}){
    val colorBackground = if(selected) colorResource(R.color.btn_sel_blue) else colorResource(R.color.btn_unsel_darkblue)
    val colorBorder = if(selected) colorResource(R.color.btn_sel_border) else colorResource(R.color.btn_unsel_border)

    Box(
        modifier = Modifier
            .fillMaxWidth().height(75.dp).background(color = colorBackground)
            .border(width = 1.dp, color = colorBorder, shape = RoundedCornerShape(10.dp))
            .clickable(onClick = { onSelected() }),
        contentAlignment = Alignment.Center
    ){
        Text(
            text = stringResource(text),
            fontFamily = MedievalSharp,
            fontSize = 25.sp,
            color = colorResource(R.color.semi_white),
        )
    }
}

@Composable
fun MenuWithMiddleContent(
    pagTitle: String = "",
    title: String,
    subtitle: String = "",
    isLastPage: Boolean = false,
    backButton: Boolean = false,
    onBackClicked: () -> Unit,
    onNextClicked: () -> Unit,
    content: @Composable () -> Unit
){
    val rightButton = if(isLastPage) stringResource(R.string.save) else stringResource(R.string.next)

    Column(Modifier.fillMaxSize()){
        Column(Modifier.fillMaxWidth()){
            if(pagTitle != "") {
                Text(
                    text = pagTitle,
                    fontFamily = Lora,
                    fontSize = 15.sp,
                    color = Color.DarkGray
                )
            }
            Text(
                text = title,
                fontFamily = MedievalSharp,
                fontSize = 40.sp,
                color = colorResource(R.color.semi_white)
            )
            if(subtitle != "") {
                Text(
                    text = subtitle,
                    fontFamily = Lora,
                    fontSize = 15.sp,
                    color = Color.Gray
                )
            }
        }
        Spacer(modifier = Modifier.height(10.dp))

        Box(Modifier.fillMaxWidth().weight(1f)){ content() }

        Row(
            modifier = Modifier.fillMaxWidth().height(70.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ){
            if(backButton) {
                TransparentYellowButton(
                    modifier = Modifier.weight(1f).height(50.dp),
                    text = stringResource(R.string.back),
                    onClicked = { onBackClicked() }
                )
                Spacer(modifier = Modifier.width(10.dp))
            }
            YellowButton(
                modifier = Modifier.weight(1f).height(50.dp),
                text = rightButton,
                onClicked = { onNextClicked() }
            )
        }
    }
}

@Composable
fun YellowButton(modifier: Modifier = Modifier, text: String, onClicked: () -> Unit){
    Button(
        onClick = { onClicked() },
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(R.color.yellow_font)
        )
    ){
        Text(
            text = text,
            fontFamily = MedievalSharp,
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp,
            color = colorResource(R.color.black)
        )
    }
}

@Composable
fun TransparentYellowButton(modifier: Modifier = Modifier, text: String, onClicked: () -> Unit){
    Button(
        onClick = { onClicked() },
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
        ),
        border = BorderStroke(width = 1.dp, color = colorResource(R.color.yellow_font))
    ) {
        Text(
            text = text,
            fontFamily = MedievalSharp,
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp,
            color = colorResource(R.color.yellow_font)
        )
    }
}

//Componentes para titulos
@Composable
fun TextSubtitleWhite(text: String){
    Text(
        text = text,
        color = colorResource(R.color.semi_white),
        fontFamily = Lora,
        fontWeight = FontWeight.SemiBold
    )
}