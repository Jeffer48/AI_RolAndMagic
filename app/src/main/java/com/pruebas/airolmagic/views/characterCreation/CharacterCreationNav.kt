package com.pruebas.airolmagic.views.characterCreation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.pruebas.airolmagic.viewModels.CharacterViewModel
import com.pruebas.airolmagic.views.MainScaffold

@Composable
fun CharacterCreationNavigation(characterViewModel: CharacterViewModel){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "class"){
        composable("class"){
            MainScaffold(navController) { ClassSelView(characterViewModel = characterViewModel, onNextClicked = { navController.navigate("background") }) }
        }

        composable("background"){
            MainScaffold(navController) { BackgroundSelView(
                characterViewModel = characterViewModel,
                onBackClicked = { navController.navigate("class") },
                onNextClicked = { navController.navigate("species") }
            )}
        }

        composable("species") {
            MainScaffold(navController) { SpeciesSelView(
                characterViewModel = characterViewModel,
                onBackClicked = { navController.navigate("background") },
                onNextClicked = { navController.navigate("stats") }
            ) }
        }

        composable("stats"){
            MainScaffold(navController) { StatsSelView(
                characterViewModel = characterViewModel,
                onBackClicked = { navController.navigate("species") },
                onNextClicked = { navController.navigate("extras") }
            )}
        }

        composable("extras"){
            MainScaffold(navController) { ExtrasSelView( characterViewModel = characterViewModel, onBackClicked = { navController.navigate("species") }, ) }
        }
    }
}