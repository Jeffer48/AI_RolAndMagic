package com.pruebas.airolmagic.data.lists

import com.pruebas.airolmagic.R
import com.pruebas.airolmagic.data.objects.BackgroundData

object BackgroundRepository{
    val list = listOf<BackgroundData>(
        BackgroundData(
            id = 1,
            backgroundName = R.string.background_acolyte,
            abilityScores = listOf(
                R.string.skill_intelligence,
                R.string.skill_wisdom,
                R.string.skill_charisma
            ),
            skills = listOf(R.string.skill_insight, R.string.skill_religion),
            equipment = listOf(
                R.string.wpn_mace,
                R.string.armor_leather,
                R.string.item_holy_symbol,
                R.string.consumable_healing_basic
            ),
            gold = 5,
            tagId = R.string.background_tag_acolyte,
            tagDescriptionId = R.string.background_desc_acolyte
        ),
        BackgroundData(
            id = 2,
            backgroundName = R.string.background_criminal,
            abilityScores = listOf(
                R.string.skill_dexterity,
                R.string.skill_constitution,
                R.string.skill_intelligence
            ),
            skills = listOf(R.string.skill_sleight_of_hand, R.string.skill_stealth),
            equipment = listOf(
                R.string.wpn_dagger,
                R.string.armor_leather,
                R.string.item_thieves_tools
            ),
            gold = 15,
            tagId = R.string.background_tag_criminal,
            tagDescriptionId = R.string.background_desc_criminal
        ),
        BackgroundData(
            id = 3,
            backgroundName = R.string.background_sage,
            abilityScores = listOf(
                R.string.skill_constitution,
                R.string.skill_intelligence,
                R.string.skill_wisdom
            ),
            skills = listOf(R.string.skill_arcana, R.string.skill_history),
            equipment = listOf(
                R.string.item_writing_kit,
                R.string.item_lore_book,
                R.string.wpn_dagger
            ),
            gold = 10,
            tagId = R.string.background_tag_sage,
            tagDescriptionId = R.string.background_desc_sage
        ),
        BackgroundData(
            id = 4,
            backgroundName = R.string.background_soldier,
            abilityScores = listOf(
                R.string.skill_dexterity,
                R.string.skill_strength,
                R.string.skill_constitution
            ),
            skills = listOf(R.string.skill_athletics, R.string.skill_intimidation),
            equipment = listOf(
                R.string.armor_chainmail,
                R.string.wpn_longsword,
                R.string.armor_shield
            ),
            gold = 10,
            tagId = R.string.background_tag_soldier,
            tagDescriptionId = R.string.background_desc_soldier
        ),
    )
}