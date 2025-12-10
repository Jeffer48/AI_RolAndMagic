package com.pruebas.airolmagic.data.database

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class GeneralRepository {
    private val db = FirebaseFirestore.getInstance()
    private val usersPath = db.collection("usuarios")

    suspend fun getUserName(userId: String): String {
        return try {
            val snapshot = db.collection("usuarios").document(userId).get().await()
            val userName: String = snapshot.getString("username") ?: ""

            userName
        }catch(e: Exception){
            e.printStackTrace()
            ""
        }
    }
}