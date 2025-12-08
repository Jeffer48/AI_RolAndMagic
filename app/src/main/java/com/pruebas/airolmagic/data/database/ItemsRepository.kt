package com.pruebas.airolmagic.data.database

import com.google.firebase.firestore.QuerySnapshot
import com.pruebas.airolmagic.data.InventoryItem
import kotlinx.coroutines.tasks.await

class ItemsRepository(private val dataSource: DataSources){
    private var armorCatalogCache: Map<String, InventoryItem>? = null
    private var weaponsCatalogCache: Map<String, InventoryItem>? = null
    private var itemsCatalogCache: Map<String, InventoryItem>? = null
    private var ammoCatalogCache: Map<String, InventoryItem>? = null
    private var consumablesCatalogCache: Map<String, InventoryItem>? = null

    suspend fun loadArmorCatalog(): Map<String, InventoryItem> {
        val catalogMap = loadCatalog(catalogCache = armorCatalogCache,snapshot = dataSource.getAllArmor().await())
        armorCatalogCache = catalogMap
        return catalogMap
    }

    suspend fun loadWeaponsCatalog(): Map<String, InventoryItem> {
        val catalogMap = loadCatalog(catalogCache = weaponsCatalogCache,snapshot = dataSource.getAllWeapons().await())
        weaponsCatalogCache = catalogMap
        return catalogMap
    }
    
    suspend fun loadItemsCatalog(): Map<String, InventoryItem> {
        val catalogMap = loadCatalog(catalogCache = itemsCatalogCache,snapshot = dataSource.getAllItems().await())
        itemsCatalogCache = catalogMap
        return catalogMap
    }
    
    suspend fun loadAmmoCatalog(): Map<String, InventoryItem> {
        val catalogMap = loadCatalog(catalogCache = ammoCatalogCache,snapshot = dataSource.getAllAmmo().await())
        ammoCatalogCache = catalogMap
        return catalogMap
    }
    
    suspend fun loadConsumablesCatalog(): Map<String, InventoryItem> {
        val catalogMap = loadCatalog(catalogCache = consumablesCatalogCache,snapshot = dataSource.getAllConsumables().await())
        consumablesCatalogCache = catalogMap
        return catalogMap
    }

    private fun loadCatalog(catalogCache: Map<String, InventoryItem>?,snapshot: QuerySnapshot): Map<String, InventoryItem>{
        if(catalogCache != null) return catalogCache

        val catalogMap = snapshot.documents.mapNotNull { documentSnapshot ->
            val item = documentSnapshot.toObject(InventoryItem::class.java)
            item?.let {it.catalogId to it}
        }.toMap()

        return catalogMap
    }
}