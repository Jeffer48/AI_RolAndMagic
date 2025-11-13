package com.pruebas.airolmagic.data

import com.pruebas.airolmagic.R

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