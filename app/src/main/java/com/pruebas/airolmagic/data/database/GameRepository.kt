package com.pruebas.airolmagic.data.database

import android.util.Log
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.pruebas.airolmagic.R
import com.pruebas.airolmagic.data.CharacterProfile
import com.pruebas.airolmagic.data.GameData
import com.pruebas.airolmagic.data.PlayersCharacters
import com.pruebas.airolmagic.data.generateRoomCode
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.tasks.await
import kotlin.collections.emptyList

class GameRepository {
    private val db = FirebaseFirestore.getInstance()
    private val collectionRef = db.collection("partidas")

    suspend fun getGamesList(userId: String): List<GameData> {
        return try {
            val snapshot = collectionRef.whereArrayContains("playerIds", userId).get().await()

            snapshot.documents.mapNotNull { doc ->
                val game = doc.toObject(GameData::class.java)
                game?.apply { id = doc.id }
            }
        }catch(e: Exception){
            e.printStackTrace()
            emptyList()
        }
    }

    suspend fun saveGameToFirebase(game: GameData, host: PlayersCharacters): Result<Boolean> {
        return try {
            val newGameRef = collectionRef.document()
            val gameId = newGameRef.id
            game.id = gameId

            val hostRef = newGameRef.collection("jugadores").document(host.userId)

            val batch = db.batch()
            batch.set(newGameRef, game)
            batch.set(hostRef, host)

            batch.commit().await()

            Result.success(true)
        }catch(e: Exception){
            e.printStackTrace()
            Result.failure(e)
        }
    }

    suspend fun joinToGame(roomCode: String, player: PlayersCharacters): Result<Int> {
        return try {
            val snapshot = collectionRef.whereEqualTo("joinCode", roomCode).limit(1).get().await()
            if(snapshot.isEmpty) return Result.success(R.string.err_room_not_found)

            val gameRef = snapshot.documents.first()
            val playersRef = gameRef.reference.collection("jugadores")
            val playersSnapshot = playersRef.get().await()

            if(playersSnapshot.size() >= 8) return Result.success(R.string.err_max_players)

            val alreadyJoined = playersSnapshot.documents.any { doc ->
                doc.getString("userId") == player.userId
            }

            if(alreadyJoined) return Result.success(R.string.err_duplicate_user_game)

            playersRef.document(player.userId).set(player).await()
            gameRef.reference.update("playerIds", FieldValue.arrayUnion(player.userId)).await()
            Result.success(0)
        }catch(e: Exception){
            e.printStackTrace()
            Result.failure(e)
        }
    }

    suspend fun joinCharacter(roomCode: String, userId: String, character: CharacterProfile): Result<Int>{
        return try {
            val snapshot = collectionRef.whereEqualTo("joinCode", roomCode).limit(1).get().await()
            if(snapshot.isEmpty) return Result.success(R.string.err_room_not_found)

            val playersRef = snapshot.documents.first().reference.collection("jugadores")
            val playerSnapshot = playersRef.whereEqualTo("userId", userId).limit(1).get().await()
            if(playerSnapshot.isEmpty) return Result.success(R.string.err_user_not_found)

            val playerDoc = playerSnapshot.documents.first()
            playerDoc.reference.update("character", character).await()

            Result.success(0)
        }catch(e: Exception){
            e.printStackTrace()
            Result.failure(e)
        }
    }

    suspend fun getUniqueCode(): String {
        var isCodeUnique = false
        var code = ""

        try {
            var attempts = 0
            while (!isCodeUnique && attempts < 10) {
                val testCode = generateRoomCode()
                val snapshot = collectionRef.whereEqualTo("joinCode", testCode).get().await()

                if(snapshot.isEmpty){
                    isCodeUnique = true
                    code = testCode
                }
                attempts++
            }

            return code
        }catch(e: Exception){
            e.printStackTrace()
            return ""
        }
    }
}