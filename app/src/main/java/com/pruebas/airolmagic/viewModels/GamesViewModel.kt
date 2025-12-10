package com.pruebas.airolmagic.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.pruebas.airolmagic.R
import com.pruebas.airolmagic.data.CharacterProfile
import com.pruebas.airolmagic.data.GameData
import com.pruebas.airolmagic.data.PlayersCharacters
import com.pruebas.airolmagic.data.database.GameRepository
import com.pruebas.airolmagic.data.database.GeneralRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class GamesViewModel(
    application: Application,
    private val gameRepository: GameRepository,
    private val generalRepository: GeneralRepository
): AndroidViewModel(application) {
    private val _roomCode = MutableStateFlow<String?>(null)

    fun createNewGame(userId: String, gameName: String, onFinished: (Boolean) -> Unit){
        viewModelScope.launch {
            val userName: String = generalRepository.getUserName(userId)
            _roomCode.value = gameRepository.getUniqueCode()

            val gameData = GameData(
                name = gameName,
                status = 0,
                hostId = userId,
                joinCode = _roomCode.value!!
            )

            val character = PlayersCharacters(
                userId = userId,
                userName = userName,
                character = CharacterProfile()
            )

            val state: Result<Boolean> = gameRepository.saveGameToFirebase(game = gameData, host =character)
            if(state.isSuccess) onFinished(true) else onFinished(false)
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