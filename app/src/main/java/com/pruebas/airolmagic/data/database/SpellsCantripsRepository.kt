package com.pruebas.airolmagic.data.database

import com.pruebas.airolmagic.data.objects.InventoryItem
import kotlinx.coroutines.tasks.await

class SpellsCantripsRepository(private val dataSource: DataSources){
    private var spellsCatalogCache: Map<String, InventoryItem>? = null
    private var cantripsCatalogCache: Map<String, InventoryItem>? = null

    suspend fun loadSpellsCatalog(): Map<String, InventoryItem> {
        if(spellsCatalogCache != null) return spellsCatalogCache!!

        val snapshot = dataSource.getAllSpells().await()
        val catalogMap = snapshot.documents.mapNotNull { documentSnapshot ->
            val spellItem = documentSnapshot.toObject(InventoryItem::class.java)
            spellItem?.let {it.catalogId to it}
        }.toMap()

        spellsCatalogCache = catalogMap
        return catalogMap
    }

    suspend fun loadCantripsCatalog(): Map<String, InventoryItem> {
        if(cantripsCatalogCache != null) return cantripsCatalogCache!!

        val snapshot = dataSource.getAllCantrips().await()
        val catalogMap = snapshot.documents.mapNotNull { documentSnapshot ->
            val spellItem = documentSnapshot.toObject(InventoryItem::class.java)
            spellItem?.let {it.catalogId to it}
        }.toMap()

        cantripsCatalogCache = catalogMap
        return catalogMap
    }
}