package com.pruebas.airolmagic

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.pruebas.airolmagic.viewModels.CharacterViewModel
import com.pruebas.airolmagic.viewModels.CharactersListViewModel
import com.pruebas.airolmagic.viewModels.GamesViewModel
import com.pruebas.airolmagic.viewModels.SessionState
import com.pruebas.airolmagic.viewModels.SessionViewModel
import com.pruebas.airolmagic.views.*
import com.pruebas.airolmagic.views.characterCreation.CharacterCreationNavigation

@Composable
fun AppNavigation(
    navController: NavHostController,
    sessionViewModel: SessionViewModel,
    characterViewModel: CharacterViewModel,
    charactersListViewModel: CharactersListViewModel,
    gamesViewModel: GamesViewModel
){
    val sessionState by sessionViewModel.sessionState.collectAsState()
    var userId by remember { mutableStateOf("") }

    when(sessionState) {
        is SessionState.Loading -> {  }
        is SessionState.LoggedOut -> {
            navController.navigate(LoginScreen){ popUpTo(LoadingScreen) { inclusive = true } }
            userId = ""
        }
        is SessionState.LoggedIn -> {
            navController.navigate(GamesListScreen){ popUpTo(LoadingScreen) { inclusive = true } }
            userId = sessionViewModel.getUserId()
        }
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
            MainScaffold(navController) {CreateLobbyView(
                onNavigateToSelCharacter = { navController.navigate(MyCharactersScreen) },
                onNavigateToHome = {
                    navController.navigate(GamesListScreen){
                        popUpTo(0) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                gamesViewModel = gamesViewModel,
                userId = userId
            )}
        }

        composable<JoinGameScreen>{
            MainScaffold(navController) {JoinGameView(
                userId = userId,
                gamesViewModel = gamesViewModel,
                onNavigateToCreateCharacter = { navController.navigate(MyCharactersScreen) },
                onNavigateToHome = {
                    navController.navigate(GamesListScreen){
                        popUpTo(0) { inclusive = true }
                        launchSingleTop = true
                    }
                },
            )}
        }

        composable<MyCharactersScreen>{
            MainScaffold(navController) {MyCharactersView(
                userId = userId,
                charactersListViewModel = charactersListViewModel,
                gamesViewModel = gamesViewModel,
                onCharacterSelected = {
                    navController.navigate(WaitLobbyScreen){
                        popUpTo(0) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                onNewCharacterClicked = { navController.navigate(CharacterCreationScreen) },
                onNavigateToHome = {
                    navController.navigate(GamesListScreen){
                        popUpTo(0) { inclusive = true }
                        launchSingleTop = true
                    }
                },
            )}
        }

        composable<CharacterCreationScreen>{
            CharacterCreationNavigation(
                userId = userId,
                characterViewModel = characterViewModel,
                gamesViewModel = gamesViewModel,
                onFailedToCreateCharacter = {
                    navController.navigate(GamesListScreen){
                        popUpTo(0) { inclusive = true }
                        launchSingleTop = true
                    }
                },
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