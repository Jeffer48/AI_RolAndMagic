package com.pruebas.airolmagic.data

data class BackgroundData(
    val id: Int,
    val backgroundName: Int,
    val abilityScores: List<Int>,
    val feat: Int,
    val skills: List<Int>,
    val toolProficiencies: Int,
    val equipment: Int
)