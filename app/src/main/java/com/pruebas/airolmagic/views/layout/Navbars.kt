package com.pruebas.airolmagic.views.layout

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pruebas.airolmagic.R
import com.pruebas.airolmagic.ui.theme.MedievalSharp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavBar(scope: CoroutineScope, drawerState: DrawerState, title: Int){
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(title),
                fontFamily = MedievalSharp,
                fontSize = 30.sp
            )
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color.Transparent,
            titleContentColor = Color.White,
            navigationIconContentColor = Color.White
        ),
        navigationIcon = {
            IconButton(
                modifier = Modifier.fillMaxHeight(),
                onClick = {
                    scope.launch {
                        drawerState.open()
                    }
                }
            ){
                Icon(
                    painter = painterResource(R.drawable.outline_account_circle_24),
                    modifier = Modifier.size(35.dp),
                    tint = colorResource(R.color.semi_white),
                    contentDescription = "Sidebar"
                )
            }
        }
    )
}