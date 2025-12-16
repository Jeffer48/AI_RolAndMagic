package com.pruebas.airolmagic.data.database

import com.google.firebase.firestore.FirebaseFirestore

class ChatRepository {
    private val db = FirebaseFirestore.getInstance()
    private val collectionRef = db.collection("partidas")
}