package com.pruebas.airolmagic.data.database

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.pruebas.airolmagic.data.objects.MessageData
import com.pruebas.airolmagic.data.objects.PlayersCharacters
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class WatchersRepository {
    private val db = FirebaseFirestore.getInstance()

    fun observePlayers(roomId: String): Flow<Result<List<PlayersCharacters>>> = callbackFlow {
        val playersRef = db.collection("partidas").document(roomId).collection("jugadores")
        val listener = playersRef.addSnapshotListener { snapshot, error ->
            if(error != null){
                Log.e("MyLogs", "Error al escuchar jugadores", error)
                trySend(Result.failure(error))
                return@addSnapshotListener
            }

            if(snapshot != null) {
                try{
                    val players = snapshot.toObjects(PlayersCharacters::class.java)
                    trySend(Result.success(players))
                }catch(e: Exception) {
                    trySend(Result.failure(e))
                }
            }
        }

        awaitClose {
            listener.remove()
        }
    }

    fun observeMessages(roomId: String): Flow<Result<List<MessageData>>> = callbackFlow {
        val messagesRef = db.collection("partidas").document(roomId).collection("mensajes")
        val listener = messagesRef.addSnapshotListener { snapshot, error ->
            if(error != null){
                Log.e("MyLogs", "Error al escuchar mensajes", error)
                trySend(Result.failure(error))
                return@addSnapshotListener
            }

            if(snapshot != null) {
                try{
                    val messages = snapshot.toObjects(MessageData::class.java)
                    trySend(Result.success(messages))
                }catch(e: Exception) {
                    trySend(Result.failure(e))
                }
            }
        }

        awaitClose {
            listener.remove()
        }
    }
}