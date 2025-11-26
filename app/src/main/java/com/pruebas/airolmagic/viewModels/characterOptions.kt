package com.pruebas.airolmagic.viewModels

import com.pruebas.airolmagic.R
import com.pruebas.airolmagic.data.BackgroundData

val speciesItems = listOf(
    R.string.species_human,
    R.string.species_dragonborn,
    R.string.species_dwarf,
    R.string.species_elf,
    R.string.species_orc,
    R.string.species_gnome,
    R.string.species_goliath,
    R.string.species_half_elf,
    R.string.species_half_orc,
    R.string.species_halfling,
    R.string.species_tiefling
)
val alignItems = listOf(
    R.string.align_lawful,
    R.string.align_neutral,
    R.string.align_chaotic
)
val moralityItems = listOf(
    R.string.morality_good,
    R.string.morality_neutral,
    R.string.morality_bad
)
val classItems = listOf(
    R.string.class_barbarian,
    R.string.class_bard,
    R.string.class_cleric,
    R.string.class_druid,
    R.string.class_fighter,
    R.string.class_monk,
    R.string.class_paladin,
    R.string.class_ranger,
    R.string.class_rogue,
    R.string.class_sorcerer,
    R.string.class_warlock,
    R.string.class_wizard
)

val backgroundItems = listOf(
    R.string.background_acolyte,
    R.string.background_criminal,
    R.string.background_sage,
    R.string.background_soldier
)

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
            feat = R.string.feat_magic_initiate,
            skills = listOf(R.string.skill_insight, R.string.skill_religion),
            toolProficiencies = R.string.equipment_calligrapher_supplies,
            equipment = R.string.equipment_acolyte
        ),
        BackgroundData(
            id = 2,
            backgroundName = R.string.background_criminal,
            abilityScores = listOf(
                R.string.skill_dexterity,
                R.string.skill_constitution,
                R.string.skill_intelligence
            ),
            feat = R.string.feat_alert,
            skills = listOf(R.string.skill_sleight_of_hand, R.string.skill_stealth),
            toolProficiencies = R.string.equipment_thieves_tools,
            equipment = R.string.equipment_criminal
        ),
        BackgroundData(
            id = 3,
            backgroundName = R.string.background_sage,
            abilityScores = listOf(
                R.string.skill_constitution,
                R.string.skill_intelligence,
                R.string.skill_wisdom
            ),
            feat = R.string.feat_magic_initiate,
            skills = listOf(R.string.skill_arcana, R.string.skill_history),
            toolProficiencies = R.string.equipment_calligrapher_supplies,
            equipment = R.string.equipment_sage
        ),
        BackgroundData(
            id = 4,
            backgroundName = R.string.background_soldier,
            abilityScores = listOf(
                R.string.skill_dexterity,
                R.string.skill_strength,
                R.string.skill_constitution
            ),
            feat = R.string.feat_savage_attacker,
            skills = listOf(R.string.skill_athletics, R.string.skill_intimidation),
            toolProficiencies = R.string.choose_gaming_set,
            equipment = R.string.equipment_soldier
        ),
    )
}

val gamingSetItems = listOf(
    R.string.equipment_dice_set,
    R.string.equipment_dragonchess_set,
    R.string.equipment_playing_card_set,
    R.string.equipment_three_dragon_ante_set
)

fun descriptionOptions(alignment: Int, morality: Int): Int {
    var desc = 0

    if(alignment == R.string.align_lawful && morality == R.string.morality_good) desc = R.string.am_description_lg
    else if(alignment == R.string.align_lawful && morality == R.string.morality_neutral) desc = R.string.am_description_ln
    else if(alignment == R.string.align_lawful && morality == R.string.morality_bad) desc = R.string.am_description_le
    else if(alignment == R.string.align_neutral && morality == R.string.morality_good) desc = R.string.am_description_ng
    else if(alignment == R.string.align_neutral && morality == R.string.morality_neutral) desc = R.string.am_description_n
    else if(alignment == R.string.align_neutral && morality == R.string.morality_bad) desc = R.string.am_description_ne
    else if(alignment == R.string.align_chaotic && morality == R.string.morality_good) desc = R.string.am_description_cg
    else if(alignment == R.string.align_chaotic && morality == R.string.morality_neutral) desc = R.string.am_description_cn
    else if(alignment == R.string.align_chaotic && morality == R.string.morality_bad) desc = R.string.am_description_ce

    return desc
}