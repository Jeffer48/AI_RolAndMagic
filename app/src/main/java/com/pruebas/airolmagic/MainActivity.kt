package com.pruebas.airolmagic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.navigation.compose.rememberNavController
import com.pruebas.airolmagic.viewModels.SessionViewModel
import com.pruebas.airolmagic.ui.theme.AIRolMagicTheme

class MainActivity : ComponentActivity() {
    private val session: SessionViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AIRolMagicTheme {
                val navController = rememberNavController()
                AppNavigation(navController = navController, sessionViewModel = session)
            }
        }
    }
}