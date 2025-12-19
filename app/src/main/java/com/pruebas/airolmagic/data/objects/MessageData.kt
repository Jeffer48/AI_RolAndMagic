package com.pruebas.airolmagic.data.objects

import com.google.firebase.firestore.ServerTimestamp
import com.pruebas.airolmagic.data.DateSerializer
import kotlinx.serialization.Serializable
import java.util.Date

data class MessageData(
    var userType: String = "",
    var role: String = "",
    var content: String = "",
    var userId: String = "",
    var userName: String = "",
    var characterName: String = "",
    @ServerTimestamp
    @Serializable(with = DateSerializer::class)
    val createdAt: Date? = null,
)