package com.pruebas.airolmagic

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.serialization.RouteEncoder
import com.pruebas.airolmagic.data.SessionState
import com.pruebas.airolmagic.data.SessionViewModel
import com.pruebas.airolmagic.views.*

@Composable
fun AppNavigation(navController: NavHostController, sessionViewModel: SessionViewModel){
    val sessionState by sessionViewModel.sessionState.collectAsState()

    when(sessionState) {
        is SessionState.Loading -> {  }
        is SessionState.LoggedOut -> { navController.navigate(LoginScreen){ popUpTo(LoadingScreen) { inclusive = true } }  }
        is SessionState.LoggedIn -> { navController.navigate(GamesListScreen){ popUpTo(LoadingScreen) { inclusive = true } }   }
    }

    NavHost(navController, startDestination = LoadingScreen){
        composable<LoadingScreen>{
            MainScaffold(navController) { LoadingView() }
        }

        composable<LoginScreen>{
            MainScaffold(navController) {LoginView(
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
            MainScaffold(navController) {RegisterView(onNavigateToLogin = { navController.navigate(LoginScreen) })}
        }

        composable<GamesListScreen>{
            MainScaffold(navController) {GamesListView(
                onNavigateToJoinLobby = { navController.navigate(JoinGameScreen) },
                onNavigateToCreateLobby = { navController.navigate(CreateLobbyScreen) }
            )}
        }

        composable<CreateLobbyScreen>{
            MainScaffold(navController) {CreateLobbyView(onNavigateToCreateCharacter = { navController.navigate(CharacterCreationScreen) })}
        }

        composable<JoinGameScreen>{
            MainScaffold(navController) {JoinGameView(onNavigateToCreateCharacter = { navController.navigate(CharacterCreationScreen) })}
        }

        composable<CharacterCreationScreen>{
            MainScaffold(navController) {CharacterCreationView()}
        }

        composable<WaitLobbyScreen>{
            MainScaffold(navController) {WaitLobbyView()}
        }
    }
}