package com.pruebas.airolmagic.data.objects

import com.google.firebase.firestore.ServerTimestamp
import com.pruebas.airolmagic.data.DateSerializer
import kotlinx.serialization.Serializable
import java.util.Date

data class MessageData(
    var role: String = "",
    var content: String = "",
    var userId: String = "",
    @ServerTimestamp
    @Serializable(with = DateSerializer::class)
    val createdAt: Date? = null,
)