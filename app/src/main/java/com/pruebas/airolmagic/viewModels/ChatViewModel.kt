package com.pruebas.airolmagic.viewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.pruebas.airolmagic.data.database.ChatRepository
import com.pruebas.airolmagic.data.database.WatchersRepository
import com.pruebas.airolmagic.data.objects.GameData
import com.pruebas.airolmagic.data.objects.MessageData
import com.pruebas.airolmagic.data.objects.PlayersCharacters
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    application: Application,
    private val chatRepository: ChatRepository,
    private val watchersRepository: WatchersRepository
): AndroidViewModel(application){
    private var _messagesWatcherJob: Job? = null
    private val _isLoading = MutableStateFlow(false)
    private val _selectedGame = MutableStateFlow<GameData?>(null)
    private val _playersList = MutableStateFlow<List<PlayersCharacters>>(emptyList())
    private val _chatMessages = MutableStateFlow<List<MessageData>>(emptyList())
    val chatMessages: StateFlow<List<MessageData>> = _chatMessages.asStateFlow()
    val selectedGame: StateFlow<GameData?> = _selectedGame.asStateFlow()
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun setSelectedGame(game: GameData){ _selectedGame.value = game }
    fun setPlayersList(players: List<PlayersCharacters>){ _playersList.value = players }
    fun getAndSetPlayersList(gameId: String){
        viewModelScope.launch {
            _playersList.value = chatRepository.getPlayersList(gameId)
        }
    }

    fun startGame(onFinish: (Int) -> Unit){
        val game: GameData = _selectedGame.value ?: return

        viewModelScope.launch {
            _isLoading.value = true
            try{
                chatRepository.updateGameStatus(gameId = game.id, newStatus = 1)
                _selectedGame.value = game.copy(status = 1)
            }catch (e: Exception){
                e.printStackTrace()
                onFinish(0)
            }finally {
                _isLoading.value = false
                onFinish(1)
            }
        }
    }

    fun sendMessage(message: String, userId: String){
        val gameId: String = _selectedGame.value?.id ?: return
        val playerItem = _playersList.value.find { it.userId == userId }
        val characterName = playerItem?.character?.name ?: ""
        val userName = playerItem?.userName ?: ""

        val newMessage = MessageData(
            userType = "user",
            role = "user",
            content = message,
            userId = userId,
            userName = userName,
            characterName = characterName
        )

        viewModelScope.launch {
            chatRepository.sendMessage(gameId = gameId, message = newMessage)
        }
    }

    fun watcherMessages(){
        val game: GameData = _selectedGame.value ?: return
        val roomId: String = game.id
        _messagesWatcherJob?.cancel()

        _messagesWatcherJob = viewModelScope.launch {
            _chatMessages.value = emptyList()

            watchersRepository.observeMessages(roomId).collect { result ->
                result.onSuccess { _chatMessages.value = it }
                result.onFailure { error -> Log.e("MyLogs", "Error al obtener los mensajes (viewModel)", error) }
            }
        }
    }

    fun cancelObservePlayers(){
        _messagesWatcherJob?.cancel()
        _chatMessages.value = emptyList()
    }
}