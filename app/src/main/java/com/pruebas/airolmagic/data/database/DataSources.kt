package com.pruebas.airolmagic.data.database

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

class DataSources {
    private val firestore = FirebaseFirestore.getInstance()
    private val catalogPath = firestore.collection("game_data").document("catalogs")
    private val spellsCollection = catalogPath.collection("spells")
    private val cantripsCollection = catalogPath.collection("cantrips")

    fun getAllSpells(): Task<QuerySnapshot> {
        return spellsCollection.get()
    }

    fun getAllCantrips(): Task<QuerySnapshot> {
        return cantripsCollection.get()
    }
}