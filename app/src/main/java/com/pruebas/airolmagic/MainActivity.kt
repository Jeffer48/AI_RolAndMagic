package com.pruebas.airolmagic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.navigation.compose.rememberNavController
import com.pruebas.airolmagic.viewModels.SessionViewModel
import com.pruebas.airolmagic.ui.theme.AIRolMagicTheme
import com.pruebas.airolmagic.viewModels.CharacterViewModel
import com.pruebas.airolmagic.viewModels.CharactersListViewModel
import com.pruebas.airolmagic.viewModels.GamesViewModel
import com.pruebas.airolmagic.viewModels.WatchersViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val session: SessionViewModel by viewModels()
    private val characterList: CharactersListViewModel by viewModels()
    private val gamesViewModel: GamesViewModel by viewModels()
    private val watchersViewModel: WatchersViewModel by viewModels()
    private val character: CharacterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AIRolMagicTheme {
                val navController = rememberNavController()
                AppNavigation(
                    navController = navController,
                    sessionViewModel = session,
                    characterViewModel = character,
                    charactersListViewModel = characterList,
                    gamesViewModel = gamesViewModel,
                    watchersViewModel = watchersViewModel
                )
            }
        }
    }
}