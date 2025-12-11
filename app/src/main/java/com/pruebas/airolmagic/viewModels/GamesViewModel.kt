package com.pruebas.airolmagic.viewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.pruebas.airolmagic.R
import com.pruebas.airolmagic.data.CharacterProfile
import com.pruebas.airolmagic.data.GameData
import com.pruebas.airolmagic.data.PlayersCharacters
import com.pruebas.airolmagic.data.database.GameRepository
import com.pruebas.airolmagic.data.database.GeneralRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GamesViewModel(
    application: Application,
    private val gameRepository: GameRepository,
    private val generalRepository: GeneralRepository
): AndroidViewModel(application) {
    private var _playerLobbyJob: Job? = null
    private val _players = MutableStateFlow<List<PlayersCharacters>>(emptyList())
    private val _roomCode = MutableStateFlow<String?>(null)
    private val _gamesList = MutableStateFlow<List<GameData>>(emptyList())
    private val _selectedGame = MutableStateFlow<GameData?>(null)
    var selectedGame: StateFlow<GameData?> = _selectedGame.asStateFlow()
    val gamesList: StateFlow<List<GameData>> = _gamesList.asStateFlow()
    val players: StateFlow<List<PlayersCharacters>> = _players.asStateFlow()

    fun getGamesList(userId: String){
        viewModelScope.launch {
            Log.d("MyLogs", "userId: $userId")
            _gamesList.value = gameRepository.getGamesList(userId)
        }
    }

    fun setSelectedGame(game: GameData){
        _selectedGame.value = game
        if(_players.value.isEmpty()) observePlayersCall(game.id)
    }

    fun createNewGame(userId: String, gameName: String, onFinished: (Boolean) -> Unit){
        viewModelScope.launch {
            val userName: String = generalRepository.getUserName(userId)
            _roomCode.value = gameRepository.getUniqueCode()

            val gameData = GameData(
                name = gameName,
                status = 0,
                hostId = userId,
                hostName = userName,
                playerIds = listOf(userId),
                joinCode = _roomCode.value!!
            )

            val character = PlayersCharacters(
                userId = userId,
                userName = userName,
                character = CharacterProfile()
            )

            val state: Result<String> = gameRepository.saveGameToFirebase(game = gameData, host =character)
            state.onSuccess { gameId ->
                onFinished(true)
                observePlayersCall(gameId)
            }
            state.onFailure {
                onFinished(false)
            }
        }
    }

    private fun observePlayersCall(roomId: String){
        _playerLobbyJob?.cancel()

        _playerLobbyJob = viewModelScope.launch {
            _players.value = emptyList()

            gameRepository.observePlayers(roomId).collect { result ->
                result.onSuccess { _players.value = it }
                result.onFailure { error -> Log.e("MyLogs", "Error al obtener jugadores (viewModel)", error) }
            }
        }
    }

    fun joinToGame(userId: String, roomCode: String, onFinished: (Int) -> Unit){
        viewModelScope.launch {
            val userName: String = generalRepository.getUserName(userId)
            _roomCode.value = roomCode

            val character = PlayersCharacters(
                userId = userId,
                userName = userName,
                character = CharacterProfile()
            )

            val code = gameRepository.joinToGame(roomCode, character)
            if(code.isSuccess) onFinished(code.getOrDefault(0))
            else onFinished(R.string.err_joining_game)
        }
    }

    fun joinCharacter(userId: String, character: CharacterProfile, onFinished: (Int) -> Unit){
        viewModelScope.launch {
            val result: Result<Int> = gameRepository.joinCharacter(roomCode = _roomCode.value!!, userId = userId, character = character)

            if(result.isSuccess) onFinished(result.getOrDefault(0))
            else onFinished(R.string.err_joining_game)
        }
    }
}