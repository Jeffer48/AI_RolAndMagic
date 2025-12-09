package com.pruebas.airolmagic.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.pruebas.airolmagic.data.CharacterProfile
import com.pruebas.airolmagic.data.database.CharacterRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class CharactersListViewModel(
    application: Application,
    private val characterRepository: CharacterRepository
): AndroidViewModel(application) {
    private val _charactersList: MutableStateFlow<List<CharacterProfile>> = MutableStateFlow(emptyList())

    fun setCharactersList(userId: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            val list = characterRepository.getCharactersByUserId(userId)
            _charactersList.value = list
            onSuccess()
        }
    }

    fun getCharactersList(): List<CharacterProfile> {
        return _charactersList.value
    }
}