package com.pruebas.airolmagic.views.characterCreation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.pruebas.airolmagic.views.MainScaffold

@Composable
fun CharacterCreationNavigation(){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "class"){
        composable("class"){
            MainScaffold(navController) { ClassSelView(onNextClicked = { navController.navigate("background") }) }
        }

        composable("background"){
            MainScaffold(navController) { BackgroundSelView(onBackClicked = { navController.navigate("class") }) }
        }
    }
}