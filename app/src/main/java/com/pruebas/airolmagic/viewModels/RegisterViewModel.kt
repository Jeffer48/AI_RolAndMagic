package com.pruebas.airolmagic.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.userProfileChangeRequest // Importante
import com.google.firebase.functions.functions
import com.google.firebase.functions.getHttpsCallable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

sealed class RegistrationState{
    object Success: RegistrationState()
    object Loading: RegistrationState()
    object Idle: RegistrationState()
    data class Error(val message: String): RegistrationState()
}

class RegisterViewModel: ViewModel() {
    private val auth = FirebaseAuth.getInstance()

    private val _registrationState = MutableStateFlow<RegistrationState>(RegistrationState.Idle)
    val registrationState: StateFlow<RegistrationState> = _registrationState.asStateFlow()

    fun registerUser(username: String, email: String, password: String) {
        viewModelScope.launch {
            _registrationState.value = RegistrationState.Loading

            try{
                val authResult = auth.createUserWithEmailAndPassword(email, password).await()
                val user = authResult.user

                if(user != null){
                    val profileUpdates = userProfileChangeRequest {
                        displayName = username
                    }
                    user.updateProfile(profileUpdates).await()

                    callFirestoreFunction(username, email)

                    _registrationState.value = RegistrationState.Success
                } else {
                    throw Exception("Error al crear el usuario")
                }
            } catch (e: Exception){
                val errorMessage = e.message ?: "Error desconocido"
                _registrationState.value = RegistrationState.Error(errorMessage)
            }
        }
    }

    private suspend fun callFirestoreFunction(username: String, email: String){
        val data = hashMapOf("username" to username, "email" to email)

        Firebase.functions
            .getHttpsCallable("onCreateUser") { limitedUseAppCheckTokens = true }
            .call(data)
            .await()
    }

    fun resetState(){ _registrationState.value = RegistrationState.Idle }
}