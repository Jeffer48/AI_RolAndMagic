package com.pruebas.airolmagic.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.pruebas.airolmagic.data.objects.UserData
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

class SessionViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    private val _sessionState = MutableStateFlow<SessionState>(SessionState.Loading)
    val sessionState: StateFlow<SessionState> = _sessionState.asStateFlow()

    private val _isLoadingLogin = MutableStateFlow(false)
    val isLoadingLogin: StateFlow<Boolean> = _isLoadingLogin.asStateFlow()

    private val _loginError = MutableStateFlow<String?>(null)
    val loginError: StateFlow<String?> = _loginError.asStateFlow()

    init {
        auth.addAuthStateListener { firebaseAuth ->
            val firebaseUser = firebaseAuth.currentUser

            if (_sessionState.value is SessionState.LoggedIn && firebaseUser != null) {
                return@addAuthStateListener
            }

            if (firebaseUser != null) {
                val cachedName = firebaseUser.displayName
                val email = firebaseUser.email ?: ""

                if (!cachedName.isNullOrBlank()) _sessionState.value = SessionState.LoggedIn(UserData(cachedName, email))
                else fetchUserDataFallback(firebaseUser)
            } else {
                _sessionState.value = SessionState.LoggedOut
            }
        }
    }

    fun login(email: String, pass: String) {
        viewModelScope.launch {
            _isLoadingLogin.value = true
            _loginError.value = null
            try {
                auth.signInWithEmailAndPassword(email, pass).await()
            } catch (e: Exception) {
                _loginError.value = e.message ?: "Error al iniciar sesión"
            } finally {
                _isLoadingLogin.value = false
            }
        }
    }

    fun clearLoginError() { _loginError.value = null }

    fun onUserLoggedOut() {
        auth.signOut()
        _sessionState.value = SessionState.LoggedOut
    }

    fun getUserId(): String = auth.currentUser?.uid ?: ""

    private fun fetchUserDataFallback(user: FirebaseUser) {
        viewModelScope.launch {
            try {
                val doc = db.collection("usuarios").document(user.uid).get().await()
                val username = doc.getString("username") ?: "Usuario"

                val updates = userProfileChangeRequest { displayName = username }
                user.updateProfile(updates)

                _sessionState.value = SessionState.LoggedIn(UserData(username, user.email ?: ""))
            } catch (e: Exception) {
                _sessionState.value = SessionState.LoggedIn(UserData("Usuario", user.email ?: ""))
            }
        }
    }
}