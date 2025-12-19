package com.pruebas.airolmagic.data.database

import com.google.firebase.firestore.FirebaseFirestore
import com.pruebas.airolmagic.data.objects.MessageData
import com.pruebas.airolmagic.data.objects.PlayersCharacters

class ChatRepository {
    private val db = FirebaseFirestore.getInstance()
    private val collectionRef = db.collection("partidas")

    fun updateGameStatus(gameId: String, newStatus: Int) {
        collectionRef.document(gameId).update("status", newStatus)
    }

    fun sendMessage(gameId: String, message: MessageData) {
        collectionRef.document(gameId).collection("mensajes").add(message)
    }

    fun getPlayersList(gameId: String): List<PlayersCharacters> {
        val playersList: MutableList<PlayersCharacters> = mutableListOf()
        collectionRef.document(gameId).collection("jugadores").get().addOnSuccessListener { result ->
            for (document in result) {
                val player = document.toObject(PlayersCharacters::class.java)
                playersList += player
            }
        }
        return playersList
    }
}