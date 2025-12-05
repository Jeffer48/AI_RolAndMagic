package com.pruebas.airolmagic.data

import com.pruebas.airolmagic.R
import kotlin.math.floor

class ManaCalculator {
    fun calculateMana(classId: Int, intelligence: Int, level: Int): Int {
        val intBase: Int = baseStats(intelligence)
        val halfCasterPoints: Int = floor(level.toDouble() / 2).toInt() + 1

        val manaPoints = when (classId) {
            R.string.class_bard -> intBase + level
            R.string.class_cleric -> intBase + level
            R.string.class_druid -> intBase + level
            R.string.class_sorcerer -> intBase + level
            R.string.class_warlock -> intBase + level
            R.string.class_wizard -> intBase + level
            R.string.class_paladin -> if(halfCasterPoints < 2) 2 else halfCasterPoints
            R.string.class_ranger -> if(halfCasterPoints < 2) 2 else halfCasterPoints
            else -> 0
        }

        return manaPoints
    }

    fun baseStats(stat: Int): Int{
        val statPoints: Double = floor((stat.toDouble() - 10) / 2)

        return statPoints.toInt()
    }
}