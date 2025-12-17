package com.pruebas.airolmagic

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.pruebas.airolmagic.viewModels.CharacterViewModel
import com.pruebas.airolmagic.viewModels.CharactersListViewModel
import com.pruebas.airolmagic.viewModels.ChatViewModel
import com.pruebas.airolmagic.viewModels.GamesViewModel
import com.pruebas.airolmagic.viewModels.SessionState
import com.pruebas.airolmagic.viewModels.SessionViewModel
import com.pruebas.airolmagic.viewModels.WatchersViewModel
import com.pruebas.airolmagic.views.*
import com.pruebas.airolmagic.views.characterCreation.CharacterCreationNavigation
import com.pruebas.airolmagic.views.chat.ChatScaffold
import com.pruebas.airolmagic.views.chat.ChatView

@Composable
fun AppNavigation(
    navController: NavHostController,
    sessionViewModel: SessionViewModel,
    characterViewModel: CharacterViewModel,
    charactersListViewModel: CharactersListViewModel,
    gamesViewModel: GamesViewModel,
    watchersViewModel: WatchersViewModel,
    chatViewModel: ChatViewModel
){
    val context = LocalContext.current
    val exitMsg = stringResource(R.string.press_again_to_exit)
    var lastBackPressTime by remember { mutableLongStateOf(0L) }
    val sessionState by sessionViewModel.sessionState.collectAsState()
    var userId by remember { mutableStateOf("") }

    BackHandler(enabled = true) {
        if(navController.previousBackStackEntry == null){
            val currentTime = System.currentTimeMillis()
            if(currentTime - lastBackPressTime < 2000) (context as? MainActivity)?.finish()
            else {
                lastBackPressTime = currentTime
                Toast.makeText(context, exitMsg, Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun clearBackStack(route: Any){
        navController.navigate(route){
            if(route == GamesListScreen){
                popUpTo(0){ inclusive = true }
            }else{
                popUpTo<GamesListScreen> {
                    inclusive = false
                }
            }

            launchSingleTop = true
        }
    }

    LaunchedEffect(sessionState) {
        when(val state = sessionState) {
            is SessionState.Loading -> {}
            is SessionState.LoggedOut -> {
                clearBackStack(LoginScreen)
                userId = ""
            }
            is SessionState.LoggedIn -> {
                clearBackStack(GamesListScreen)
                userId = sessionViewModel.getUserId()
            }
        }
    }

    NavHost(navController, startDestination = LoadingScreen){

        composable<LoadingScreen>{
            MainScaffold(navController, sessionViewModel) { LoadingView() }
        }

        composable<LoginScreen>{
            MainScaffold(navController, sessionViewModel) {LoginView(
                onNavigateToRegister = { navController.navigate(RegisterScreen) },
                sessionViewModel = sessionViewModel
            )}
        }

        composable<RegisterScreen> {
            MainScaffold(navController, sessionViewModel) {RegisterView(onNavigateToLogin = { navController.navigate(LoginScreen) })}
        }

        composable<GamesListScreen>{
            MainScaffold(navController, sessionViewModel) {GamesListView(
                userId = userId,
                gamesViewModel = gamesViewModel,
                onNavigateToLobby = { navController.navigate(WaitLobbyScreen) },
                onNavigateToJoinLobby = { navController.navigate(JoinGameScreen) },
                onNavigateToCreateLobby = { navController.navigate(CreateLobbyScreen) }
            )}
        }

        composable<CreateLobbyScreen>{
            MainScaffold(navController, sessionViewModel) {CreateLobbyView(
                onNavigateToSelCharacter = { navController.navigate(MyCharactersScreen) },
                onNavigateToHome = { clearBackStack(GamesListScreen) },
                gamesViewModel = gamesViewModel,
                userId = userId
            )}
        }

        composable<JoinGameScreen>{
            MainScaffold(navController, sessionViewModel) {JoinGameView(
                userId = userId,
                gamesViewModel = gamesViewModel,
                onNavigateToCreateCharacter = { navController.navigate(MyCharactersScreen) },
                onNavigateToHome = { clearBackStack(GamesListScreen) },
            )}
        }

        composable<MyCharactersScreen>{
            MainScaffold(navController, sessionViewModel) {MyCharactersView(
                userId = userId,
                charactersListViewModel = charactersListViewModel,
                gamesViewModel = gamesViewModel,
                onCharacterSelected = { clearBackStack(WaitLobbyScreen) },
                onNewCharacterClicked = { navController.navigate(CharacterCreationScreen) },
                onNavigateToHome = { clearBackStack(GamesListScreen) },
            )}
        }

        composable<CharacterCreationScreen>{
            CharacterCreationNavigation(
                userId = userId,
                characterViewModel = characterViewModel,
                gamesViewModel = gamesViewModel,
                onFailedToCreateCharacter = { clearBackStack(GamesListScreen) },
                onNavigateToWaitLobby = { clearBackStack(WaitLobbyScreen) },
                sessionViewModel = sessionViewModel
            )
        }

        composable<WaitLobbyScreen>{
            MainScaffold(navController, sessionViewModel) {WaitLobbyView(
                userId = userId,
                chatViewModel = chatViewModel,
                gamesViewModel = gamesViewModel,
                watchersViewModel = watchersViewModel,
                onNonSelectedCharacter = { navController.navigate(MyCharactersScreen) },
                onStartGame = { clearBackStack(ChatScreen) }
            )}
        }

        composable<ChatScreen>{
            ChatScaffold(navController) { ChatView(chatViewModel = chatViewModel) }
        }
    }
}