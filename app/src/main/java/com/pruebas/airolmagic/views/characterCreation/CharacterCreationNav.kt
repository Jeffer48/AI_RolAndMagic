package com.pruebas.airolmagic.views.characterCreation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.pruebas.airolmagic.data.lists.isMagicClass
import com.pruebas.airolmagic.viewModels.CharacterViewModel
import com.pruebas.airolmagic.viewModels.GamesViewModel
import com.pruebas.airolmagic.viewModels.SessionViewModel
import com.pruebas.airolmagic.views.MainScaffold

@Composable
fun CharacterCreationNavigation(
    userId: String,
    characterViewModel: CharacterViewModel,
    onNavigateToWaitLobby: () -> Unit,
    onFailedToCreateCharacter: () -> Unit,
    gamesViewModel: GamesViewModel,
    sessionViewModel: SessionViewModel
){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "class"){
        composable("class"){
            MainScaffold(navController, sessionViewModel) { ClassSelView(characterViewModel = characterViewModel, onNextClicked = { navController.navigate("background") }) }
        }

        composable("background"){
            MainScaffold(navController, sessionViewModel) { BackgroundSelView(
                characterViewModel = characterViewModel,
                onBackClicked = { navController.navigate("class") },
                onNextClicked = { navController.navigate("species") }
            )}
        }

        composable("species") {
            MainScaffold(navController, sessionViewModel) { SpeciesSelView(
                characterViewModel = characterViewModel,
                onBackClicked = { navController.navigate("background") },
                onNextClicked = { navController.navigate("stats") }
            ) }
        }

        composable("stats"){
            MainScaffold(navController, sessionViewModel) { StatsSelView(
                characterViewModel = characterViewModel,
                onBackClicked = { navController.navigate("species") },
                onNextClicked = { navController.navigate("extras") }
            )}
        }

        composable("extras"){
            val isMagic = isMagicClass(characterViewModel.getClassData())
            MainScaffold(navController, sessionViewModel) { ExtrasSelView(
                userId = userId,
                characterViewModel = characterViewModel,
                gamesViewModel = gamesViewModel,
                isMagicClass = isMagic,
                onBackClicked = { navController.navigate("stats") },
                onNextClicked = { if(isMagic) navController.navigate("spells") else onNavigateToWaitLobby() },
                onFailedToCreateCharacter = { onFailedToCreateCharacter() }
            )}
        }

        composable("spells"){
            MainScaffold(navController, sessionViewModel) { SpellsSelView(
                userId = userId,
                gamesViewModel = gamesViewModel,
                characterViewModel = characterViewModel,
                onBackClicked = { navController.navigate("extras") },
                onNextClicked = { onNavigateToWaitLobby() },
                onFailedToCreateCharacter = { onFailedToCreateCharacter() }
            )}
        }
    }
}