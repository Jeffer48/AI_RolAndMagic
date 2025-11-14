package com.pruebas.airolmagic.views.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.pruebas.airolmagic.R
import com.pruebas.airolmagic.data.SessionViewModel
import com.pruebas.airolmagic.ui.theme.Lora
import com.pruebas.airolmagic.views.GamesListScreen
import com.pruebas.airolmagic.views.LoadingScreen
import com.pruebas.airolmagic.views.LoginScreen

@Composable
fun SideBar(navController: NavHostController,sessionViewModel: SessionViewModel){
    val borderColor = colorResource(R.color.yellow_font)
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    ModalDrawerSheet(
        drawerContainerColor = colorResource(R.color.bg_black_purple),
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .drawWithContent{
                drawContent()
                val strokeWidth = 2.dp.toPx()
                val x = size.width - (strokeWidth / 2)

                drawLine(
                    color = borderColor,
                    start = Offset(x = x, y = 0f),
                    end = Offset(x = x, y = size.height),
                    strokeWidth = strokeWidth
                )
            }
    ){
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxSize()
            //.verticalScroll(rememberScrollState())
        ) {
            Row(
                modifier = Modifier.height(100.dp).fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ){
                Icon(
                    painter = painterResource(R.drawable.outline_account_circle_24),
                    modifier = Modifier.size(60.dp),
                    tint = colorResource(R.color.semi_white),
                    contentDescription = "Sidebar"
                )
            }
            Column(
                modifier = Modifier.weight(1f).fillMaxWidth(),
            ){
                Text("PRUEBA", color = colorResource(R.color.semi_white), fontFamily = Lora)
            }
            Row(
                modifier = Modifier.height(100.dp).fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ){
                Button(
                    onClick = {
                        sessionViewModel.onUserLoggedOut()
                        navController.navigate(LoginScreen){ popUpTo(currentRoute.toString()) { inclusive = true } }
                    },
                ) {
                    Text(text = stringResource(R.string.log_out), color = colorResource(R.color.semi_white), fontFamily = Lora)
                }
            }
        }
    }
}