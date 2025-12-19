package com.pruebas.airolmagic.data.objects

import com.google.firebase.firestore.ServerTimestamp
import com.pruebas.airolmagic.data.DateSerializer
import kotlinx.serialization.Serializable
import java.util.Date

@Serializable
data class GameData(
    var id: String = "",
    var name: String = "",
    var status: Int = 0,
    var hostId: String = "",
    var hostName: String = "",
    var joinCode: String = "",
    var currentTurn: String = "",
    var playerIds: List<String> = emptyList(),
    val language: String = "Español",
    @ServerTimestamp
    @Serializable(with = DateSerializer::class)
    val createdAt: Date? = null,
)

@Serializable
data class PlayersCharacters(
    var userId: String = "",
    var userName: String = "",
    var character: CharacterProfile = CharacterProfile(),
)