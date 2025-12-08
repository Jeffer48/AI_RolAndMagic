package com.pruebas.airolmagic.data

data class BackgroundData(
    val id: Int,
    val backgroundName: Int,
    val abilityScores: List<Int>,
    val skills: List<Int>,
    val equipment: List<Int>,
    val gold: Int,
    val tagId: Int,
    val tagDescriptionId: Int
)