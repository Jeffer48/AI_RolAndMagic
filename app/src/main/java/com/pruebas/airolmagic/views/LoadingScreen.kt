package com.pruebas.airolmagic.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pruebas.airolmagic.R
import com.pruebas.airolmagic.ui.theme.MedievalSharp

@Composable
fun LoadingView(){
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Text(
            text = stringResource(R.string.app_name),
            fontFamily = MedievalSharp,
            fontSize = 50.sp,
            color = colorResource(R.color.yellow_font)
        )
        Spacer(Modifier.height(20.dp))
        CircularProgressIndicator(
            modifier = Modifier.size(64.dp),
            color = Color.Gray,
            trackColor = colorResource(R.color.yellow_font)
        )
    }
}