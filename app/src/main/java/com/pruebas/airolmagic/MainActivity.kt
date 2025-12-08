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

class MainActivity : ComponentActivity() {
    private val session: SessionViewModel by viewModels()
    private val character: CharacterViewModel by viewModels{
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val dataSources = DataSources()
                val repository = SpellsCantripsRepository(dataSources)
                val itemsRepository = ItemsRepository(dataSources)
                val characterRepository = CharacterRepository()
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AIRolMagicTheme {
                val navController = rememberNavController()
                AppNavigation(
                    navController = navController,
                    sessionViewModel = session,
                    characterViewModel = character
                )
            }
        }
    }
}