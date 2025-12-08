package com.pruebas.airolmagic.data.database

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

class DataSources {
    private val firestore = FirebaseFirestore.getInstance()
    private val catalogPath = firestore.collection("game_data").document("catalogs")
    private val spellsCollection = catalogPath.collection("spells")
    private val cantripsCollection = catalogPath.collection("cantrips")
    private val consumablesCollection = catalogPath.collection("consumables")
    private val weaponsCollection = catalogPath.collection("weapons")
    private val armorCollection = catalogPath.collection("armors")
    private val itemsCollection = catalogPath.collection("items")
    private val ammoCollection = catalogPath.collection("ammo")

    fun getAllSpells(): Task<QuerySnapshot> { return spellsCollection.get() }

    fun getAllCantrips(): Task<QuerySnapshot> { return cantripsCollection.get() }

    fun getAllConsumables(): Task<QuerySnapshot> { return consumablesCollection.get() }

    fun getAllWeapons(): Task<QuerySnapshot> { return weaponsCollection.get() }

    fun getAllArmor(): Task<QuerySnapshot> { return armorCollection.get() }

    fun getAllItems(): Task<QuerySnapshot> { return itemsCollection.get() }

    fun getAllAmmo(): Task<QuerySnapshot> { return ammoCollection.get() }
}