package com.pruebas.airolmagic.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.pruebas.airolmagic.data.database.ChatRepository
import com.pruebas.airolmagic.data.objects.GameData
import com.pruebas.airolmagic.data.objects.PlayersCharacters
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    application: Application,
    private val chatRepository: ChatRepository
): AndroidViewModel(application){
    private val _selectedGame = MutableStateFlow<GameData?>(null)
    private val _playersList = MutableStateFlow<List<PlayersCharacters>>(emptyList())

    fun setSelectedGame(game: GameData){ _selectedGame.value = game }
    fun setPlayersList(players: List<PlayersCharacters>){ _playersList.value = players }
}