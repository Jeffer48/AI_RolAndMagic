package com.pruebas.airolmagic.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.functions.functions
import com.google.firebase.functions.getHttpsCallable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

sealed class SessionState {
    object Loading : SessionState()
    object LoggedOut : SessionState()
    data class LoggedIn(val user: UserData) : SessionState()
}

class SessionViewModel: ViewModel() {
    private val auth = FirebaseAuth.getInstance()

    private val _sessionState = MutableStateFlow<SessionState>(SessionState.Loading)
    val sessionState: StateFlow<SessionState> = _sessionState.asStateFlow()

    init {
        auth.addAuthStateListener { firebaseAuth ->
            val firebaseUser = firebaseAuth.currentUser
            if(firebaseUser != null){
                viewModelScope.launch {
                    try{
                        val userData = callGetUserDataFunction()
                        _sessionState.value = SessionState.LoggedIn(userData)
                    }catch(e: Exception){
                        auth.signOut()
                        _sessionState.value = SessionState.LoggedOut
                    }
                }
            }else{
                _sessionState.value = SessionState.LoggedOut
            }
        }
    }

    fun onUserLoggedIn(user: UserData) {
        _sessionState.value = SessionState.LoggedIn(user)
    }

    fun onUserLoggedOut() {
        auth.signOut()
        _sessionState.value = SessionState.LoggedOut
    }

    private suspend fun callGetUserDataFunction(): UserData {
        val result = Firebase.functions
            .getHttpsCallable("onGetUserData") { limitedUseAppCheckTokens = true }
            .call()
            .await()

        val dataMap = result.data as? Map<String, Any>
            ?: throw Exception("La respuesta de la función estaba vacía o en un formato incorrecto.")

        val username = dataMap["username"] as? String
            ?: throw Exception("El campo 'username' no se encontró en la respuesta.")

        val email = dataMap["email"] as? String
            ?: throw Exception("El campo 'email' no se encontró en la respuesta")

        return UserData(username = username, email = email)
    }
}