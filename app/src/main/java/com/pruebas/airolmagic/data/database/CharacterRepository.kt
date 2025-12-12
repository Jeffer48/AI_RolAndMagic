package com.pruebas.airolmagic.data.database

import com.google.firebase.firestore.FirebaseFirestore
import com.pruebas.airolmagic.data.objects.CharacterProfile
import kotlinx.coroutines.tasks.await
import kotlin.collections.emptyList

class CharacterRepository {
    private val db = FirebaseFirestore.getInstance()
    private val collectionRef = db.collection("personajes")

    suspend fun saveCharacterToFirebase(character: CharacterProfile): Result<Boolean>{
        return try {
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

    suspend fun getCharactersByUserId(userId: String): List<CharacterProfile>{
        return try {
            val snapshot = collectionRef.whereEqualTo("userId", userId).get().await()

            val lista = snapshot.documents.mapNotNull { doc ->
                val character = doc.toObject(CharacterProfile::class.java)
                character?.id = doc.id
                character
            }

            lista
        }catch(e: Exception){
            e.printStackTrace()
            emptyList()
        }
    }
}