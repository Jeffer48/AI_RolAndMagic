package com.pruebas.airolmagic.data

import kotlinx.serialization.Serializable

@Serializable
data class RaceData(
    val id: Int = 0,
    val name: String = "",
    val speed: String = "",
    val size: String = ""
)

@Serializable
data class ClassData(
    val id: Int = 0,
    val name: String = "",
    val hitDice: String = "1d8"
)

@Serializable
data class WalletData(
    val gold: Int = 0,
    val silver: Int = 0,
    val copper: Int = 0
)

@Serializable
data class StatsData(
    val hp: HpData = HpData(),
    val attributes: AttributesData = AttributesData()
)

@Serializable
data class HpData(
    val current: Int = 10,
    val max: Int = 10,
    val temp: Int = 0
)

@Serializable
data class AttributesData(
    val strength: Int = 8,
    val dexterity: Int = 8,
    val constitution: Int = 8,
    val intelligence: Int = 8,
    val wisdom: Int = 8,
    val charisma: Int = 8
)

@Serializable
data class InventoryData(
    val maxSlots: Int = 10,
    val usedSlots: Int = 0,
    val items: List<InventoryItem> = emptyList()
)

enum class ItemType {
    WEAPON, ARMOR, AMMO, CONSUMABLE, ITEM, SPELL
}

@Serializable
data class InventoryItem(
    val instanceId: String = "",
    val catalogId: String = "",
    val name: String = "",
    val type: ItemType = ItemType.ITEM,
    val slots: Int = 1,
    val quantity: Int = 1,
    val isStackable: Boolean = false,
    val maxStackSize: Int = 1,
    val data: ItemData = ItemData()
)

@Serializable
data class ItemData(
    // Campos de Armas / Munición
    val damage: String? = null,      // "1d8"
    val damageType: String? = null,  // "slashing"
    val properties: List<String>? = null, // ["versatile"]
    val cost: Int? = null,           // 1 Es para la magia

    // Campos de Armaduras
    val acBase: Int? = null,         // 16
    val minStrength: Int? = null,    // 13
    val stealthDisadvantage: Boolean? = null,

    // Campos de Consumibles
    val effect: String? = null,      // "heal"
    val value: Int? = null           // 8
)