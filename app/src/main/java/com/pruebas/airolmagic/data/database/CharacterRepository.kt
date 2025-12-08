package com.pruebas.airolmagic.data.database

import com.google.firebase.firestore.FirebaseFirestore
import com.pruebas.airolmagic.data.CharacterProfile
import kotlinx.coroutines.tasks.await

class CharacterRepository {
    private val db = FirebaseFirestore.getInstance()

    suspend fun saveCharacterToFirebase(character: CharacterProfile): Result<Boolean>{
        return try {
            val collectionRef = db.collection("personajes")

            val docRef = if (character.id != "") {
                collectionRef.document(character.id)
            } else {
                collectionRef.document()
            }

            character.id = docRef.id
            docRef.set(character).await()

            Result.success(true)
        }catch(e: Exception){
            e.printStackTrace()
            Result.failure(e)
        }
    }
}