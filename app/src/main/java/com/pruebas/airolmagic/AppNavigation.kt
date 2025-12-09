package com.pruebas.airolmagic

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.pruebas.airolmagic.viewModels.CharacterViewModel
import com.pruebas.airolmagic.viewModels.CharactersListViewModel
import com.pruebas.airolmagic.viewModels.SessionState
import com.pruebas.airolmagic.viewModels.SessionViewModel
import com.pruebas.airolmagic.views.*
import com.pruebas.airolmagic.views.characterCreation.CharacterCreationNavigation

@Composable
fun AppNavigation(
    navController: NavHostController,
    sessionViewModel: SessionViewModel,
    characterViewModel: CharacterViewModel,
    charactersListViewModel: CharactersListViewModel
){
    val sessionState by sessionViewModel.sessionState.collectAsState()

    when(sessionState) {
        is SessionState.Loading -> {  }
        is SessionState.LoggedOut -> { navController.navigate(LoginScreen){ popUpTo(LoadingScreen) { inclusive = true } } }
        is SessionState.LoggedIn -> { navController.navigate(GamesListScreen){ popUpTo(LoadingScreen) { inclusive = true } } }
    }

    NavHost(navController, startDestination = LoadingScreen){
        composable<LoadingScreen>{
            MainScaffold(navController, sessionViewModel) { LoadingView() }
        }

        composable<LoginScreen>{
            MainScaffold(navController, sessionViewModel) {LoginView(
                onNavigateToGames = {
                    navController.navigate(GamesListScreen){
                        popUpTo(LoginScreen) { inclusive = true }
                    }
                },
                onNavigateToRegister = { navController.navigate(RegisterScreen) },
                sessionViewModel = sessionViewModel
            )}
        }

        composable<RegisterScreen> {
            MainScaffold(navController,sessionViewModel) {RegisterView(onNavigateToLogin = { navController.navigate(LoginScreen) })}
        }

        composable<GamesListScreen>{
            MainScaffold(navController) {GamesListView(
                onNavigateToJoinLobby = { navController.navigate(JoinGameScreen) },
                onNavigateToCreateLobby = { navController.navigate(CreateLobbyScreen) }
            )}
        }

        composable<CreateLobbyScreen>{
            MainScaffold(navController) {CreateLobbyView(onNavigateToCreateCharacter = { navController.navigate(MyCharactersScreen) })}
        }

        composable<JoinGameScreen>{
            MainScaffold(navController) {JoinGameView(onNavigateToCreateCharacter = { navController.navigate(MyCharactersScreen) })}
        }

        composable<MyCharactersScreen>{
            MainScaffold(navController) {MyCharactersView(
                charactersListViewModel = charactersListViewModel,
                sessionViewModel = sessionViewModel,
                onCharacterSelected = { character -> navController.navigate(WaitLobbyScreen) },
                onNewCharacterClicked = { navController.navigate(CharacterCreationScreen) }
            )}
        }

        composable<CharacterCreationScreen>{
            CharacterCreationNavigation(
                characterViewModel = characterViewModel,
                sessionViewModel = sessionViewModel,
                onFailedToCreateCharacter = { navController.navigate(GamesListScreen) },
                onNavigateToWaitLobby = { navController.navigate(WaitLobbyScreen){
                    popUpTo(CharacterCreationScreen) { inclusive = true }
                } }
            )
        }

        composable<WaitLobbyScreen>{
            MainScaffold(navController) {WaitLobbyView()}
        }
    }
}