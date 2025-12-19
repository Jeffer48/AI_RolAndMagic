package com.pruebas.airolmagic.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.pruebas.airolmagic.R
import com.pruebas.airolmagic.data.objects.CharacterProfile
import com.pruebas.airolmagic.data.objects.GameData
import com.pruebas.airolmagic.data.objects.PlayersCharacters
import com.pruebas.airolmagic.data.database.GameRepository
import com.pruebas.airolmagic.data.database.GeneralRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GamesViewModel @Inject constructor(
    application: Application,
    private val gameRepository: GameRepository,
    private val generalRepository: GeneralRepository
): AndroidViewModel(application) {
    private val _roomCode = MutableStateFlow<String?>(null)
    private val _gamesList = MutableStateFlow<List<GameData>>(emptyList())
    private val _selectedGame = MutableStateFlow<GameData?>(null)
    var selectedGame: StateFlow<GameData?> = _selectedGame.asStateFlow()
    val gamesList: StateFlow<List<GameData>> = _gamesList.asStateFlow()

    fun getGamesList(userId: String){
        viewModelScope.launch {
            _gamesList.value = gameRepository.getGamesList(userId)
        }
    }

    fun setSelectedGame(game: GameData){ _selectedGame.value = game }
    fun setRoomCode(code: String){ _roomCode.value = code }

    fun createNewGame(userId: String, gameName: String, language: String, onFinished: (Boolean) -> Unit){
        viewModelScope.launch {
            val userName: String = generalRepository.getUserName(userId)
            _roomCode.value = gameRepository.getUniqueCode()

            val gameData = GameData(
                name = gameName,
                status = 0,
                hostId = userId,
                hostName = userName,
                playerIds = listOf(userId),
                joinCode = _roomCode.value!!,
                language = language
            )

            val character = PlayersCharacters(
                userId = userId,
                userName = userName,
                character = CharacterProfile()
            )

            val state: Result<String> = gameRepository.saveGameToFirebase(game = gameData, host =character)
            state.onSuccess { gameId ->
                setSelectedGame(gameData)
                onFinished(true)
            }
            state.onFailure {
                onFinished(false)
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