package com.pruebas.airolmagic.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.pruebas.airolmagic.data.objects.CharacterProfile
import com.pruebas.airolmagic.data.database.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersListViewModel @Inject constructor(
    application: Application,
    private val characterRepository: CharacterRepository
): AndroidViewModel(application) {
    private val _charactersList: MutableStateFlow<List<CharacterProfile>> = MutableStateFlow(emptyList())
    val charactersList: StateFlow<List<CharacterProfile>> = _charactersList.asStateFlow()

    fun setCharactersList(userId: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            val list = characterRepository.getCharactersByUserId(userId)
            _charactersList.value = list
            onSuccess()
        }
    }
}