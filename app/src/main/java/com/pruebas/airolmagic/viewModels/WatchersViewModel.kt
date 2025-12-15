package com.pruebas.airolmagic.viewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.pruebas.airolmagic.data.objects.PlayersCharacters
import com.pruebas.airolmagic.data.database.WatchersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WatchersViewModel @Inject constructor(
    application: Application,
    private val watchersRepository: WatchersRepository,
): AndroidViewModel(application) {
    private var _playerLobbyJob: Job? = null
    private val _players = MutableStateFlow<List<PlayersCharacters>>(emptyList())
    val players: StateFlow<List<PlayersCharacters>> = _players.asStateFlow()

    fun observePlayersCall(roomId: String){
        _playerLobbyJob?.cancel()

        _playerLobbyJob = viewModelScope.launch {
            _players.value = emptyList()

            watchersRepository.observePlayers(roomId).collect { result ->
                result.onSuccess { _players.value = it }
                result.onFailure { error -> Log.e("MyLogs", "Error al obtener jugadores (viewModel)", error) }
            }
        }
    }

    fun cancelObservePlayers(){
        _playerLobbyJob?.cancel()
        _players.value = emptyList()
    }
}