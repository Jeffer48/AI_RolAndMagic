package com.pruebas.airolmagic.data

import kotlinx.serialization.Serializable

@Serializable
class CharacterProfile(
    val id: String = "",
    val userId: String = "",
    val name: String = "",
    val level: Int = 1,
    val experience: Int = 0,
    val mana_points: Int = 0,
    val max_mana_points: Int = 0,
    val bio: String = "",
    val characteristics: String = "",
    val alignment: String = "",
    val languages: String = "",

    val race: RaceData = RaceData(),
    val classN: ClassData = ClassData(), //classN es class, pero class es palabra reservada
    val stats: StatsData = StatsData(),
    val wallet: WalletData = WalletData(),
    val inventory: InventoryData = InventoryData(),
)