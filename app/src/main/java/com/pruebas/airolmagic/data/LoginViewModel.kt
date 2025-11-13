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

sealed class LoginState{
    data class Success(val user: UserData): LoginState()
    object Loading: LoginState()
    object Idle: LoginState()
    data class Error(val message: String): LoginState()
}

class LoginViewModel: ViewModel() {
    private val auth = FirebaseAuth.getInstance()

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState.asStateFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading

            try{
                val authResult = auth.signInWithEmailAndPassword(email, password).await()

                if(authResult.user != null) callFirestoreFunction()
                else _loginState.value = LoginState.Error("Usuario no encontrado después del login.")
            }catch (e: Exception){
                val errorMessage = e.message ?: "Error desconocido"
                _loginState.value = LoginState.Error(errorMessage)
            }
        }
    }

    private suspend fun callFirestoreFunction(): UserData {
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

        val userData = UserData(username = username, email = email)

        _loginState.value = LoginState.Success(userData)
        return userData
    }

    fun resetState() {
        _loginState.value = LoginState.Idle
    }
}