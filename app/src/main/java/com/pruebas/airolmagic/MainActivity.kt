package com.pruebas.airolmagic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.pruebas.airolmagic.data.database.*
import com.pruebas.airolmagic.viewModels.SessionViewModel
import com.pruebas.airolmagic.ui.theme.AIRolMagicTheme
import com.pruebas.airolmagic.viewModels.CharacterViewModel
import com.pruebas.airolmagic.viewModels.CharactersListViewModel
import com.pruebas.airolmagic.viewModels.GamesViewModel
import com.pruebas.airolmagic.viewModels.WatchersViewModel

class MainActivity : ComponentActivity() {
    val dataSources = DataSources()
    val gameRepository = GameRepository()
    val generalRepository = GeneralRepository()
    val watchersRepository = WatchersRepository()
    val characterRepository = CharacterRepository()
    val repository = SpellsCantripsRepository(dataSources)
    val itemsRepository = ItemsRepository(dataSources)

    private val session: SessionViewModel by viewModels()
    private val characterList: CharactersListViewModel by viewModels(){
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return CharactersListViewModel(
                    application,
                    characterRepository = characterRepository
                ) as T
            }
        }
    }
    private val gamesViewModel: GamesViewModel by viewModels{
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return GamesViewModel(
                    application,
                    gameRepository = gameRepository,
                    generalRepository = generalRepository
                ) as T
            }
        }
    }
    private val watchersViewModel: WatchersViewModel by viewModels{
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return WatchersViewModel(
                    application,
                    watchersRepository = watchersRepository,
                ) as T
            }
        }
    }
    private val character: CharacterViewModel by viewModels{
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return CharacterViewModel(
                    application,
                    scRepository = repository,
                    itemsRepository = itemsRepository,
                    characterRepository = characterRepository
                ) as T
            }
        }
    }

    //Inicio de la aplicación -------------------------------------------------------------------------------------------------------------
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