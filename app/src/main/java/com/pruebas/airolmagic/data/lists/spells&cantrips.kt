package com.pruebas.airolmagic.data.lists

import com.pruebas.airolmagic.R

fun getClassCantrips(classId: Int): List<Int>? {
    return when (classId) {
        R.string.class_wizard -> wizardAndSorcererCantrips
        R.string.class_sorcerer -> wizardAndSorcererCantrips
        R.string.class_warlock -> warlockCantrips
        R.string.class_cleric -> clericCantrips
        R.string.class_bard -> bardCantrips
        R.string.class_druid -> druidCantrips
        else -> null
    }
}

fun getClassSpells(classId: Int): List<Int>? {
    return when (classId) {
        R.string.class_wizard -> wizardAndSorcererSpells
        R.string.class_sorcerer -> wizardAndSorcererSpells
        R.string.class_warlock -> warlockSpells
        R.string.class_cleric -> clericSpells
        R.string.class_bard -> bardSpells
        R.string.class_druid -> druidSpells
        R.string.class_paladin -> paladinSpells
        R.string.class_ranger -> rangerSpells
        else -> null
    }
}

fun getSpellsAmount(classId: Int): Int {
    return when (classId) {
        R.string.class_wizard -> 2
        R.string.class_sorcerer -> 2
        R.string.class_warlock -> 2
        R.string.class_cleric -> 2
        R.string.class_bard -> 3
        R.string.class_druid -> 2
        R.string.class_paladin -> 1
        R.string.class_ranger -> 1
        else -> 0
    }
}

fun getCantripsAmount(classId: Int): Int {
    return when (classId) {
        R.string.class_wizard -> 3
        R.string.class_sorcerer -> 4
        R.string.class_warlock -> 2
        R.string.class_cleric -> 3
        R.string.class_bard -> 2
        R.string.class_druid -> 2
        else -> 0
    }
}

fun isMagicClass(classId: Int): Boolean {
    return when (classId) {
        R.string.class_wizard -> true
        R.string.class_sorcerer -> true
        R.string.class_warlock -> true
        R.string.class_cleric -> true
        R.string.class_bard -> true
        R.string.class_druid -> true
        R.string.class_paladin -> true
        R.string.class_ranger -> true
        else -> false
    }
}

// -------------------------------------------------------- TRUCOS ----------------------------------------------------
val cantripsItems = listOf(
    R.string.cantrips_fire_bolt,
    R.string.cantrips_light,
    R.string.cantrips_ray_of_frost,
    R.string.cantrips_eldritch_blast,
    R.string.cantrips_sacred_flame,
    R.string.cantrips_mage_hand,
    R.string.cantrips_minor_illusion,
    R.string.cantrips_vicious_mockery
)

val wizardAndSorcererCantrips = listOf(
    R.string.cantrips_fire_bolt,
    R.string.cantrips_light,
    R.string.cantrips_ray_of_frost,
    R.string.cantrips_mage_hand,
    R.string.cantrips_minor_illusion,
)

val warlockCantrips = listOf(
    R.string.cantrips_eldritch_blast,
    R.string.cantrips_mage_hand,
    R.string.cantrips_minor_illusion
)

val clericCantrips = listOf(
    R.string.cantrips_sacred_flame,
    R.string.cantrips_light
)

val bardCantrips = listOf(
    R.string.cantrips_vicious_mockery,
    R.string.cantrips_minor_illusion,
    R.string.cantrips_mage_hand,
    R.string.cantrips_light
)

val druidCantrips = listOf(
    R.string.cantrips_ray_of_frost,
    R.string.cantrips_light
)

// -------------------------------------------------------- HECHIZOS ----------------------------------------------------
val spellsItems = listOf(
    R.string.spells_magic_missile,
    R.string.spells_thunderwave,
    R.string.spells_fireball,
    R.string.spells_lightning_bolt,
    R.string.spells_cure_wounds,
    R.string.spells_healing_word,
    R.string.spells_shield,
    R.string.spells_mage_armor,
    R.string.spells_revivify,
    R.string.spells_sleep,
    R.string.spells_invisibility,
    R.string.spells_fly,
    R.string.spells_detect_magic,
    R.string.spells_hold_person
)

val wizardAndSorcererSpells = listOf(
    R.string.spells_magic_missile,
    R.string.spells_thunderwave,
    R.string.spells_fireball,
    R.string.spells_lightning_bolt,
    R.string.spells_shield,
    R.string.spells_mage_armor,
    R.string.spells_invisibility,
    R.string.spells_fly,
    R.string.spells_detect_magic,
    R.string.spells_hold_person
)

val warlockSpells = listOf(
    R.string.spells_hold_person,
    R.string.spells_thunderwave,
    R.string.spells_fly,
    R.string.spells_mage_armor,
    R.string.spells_invisibility
)

val clericSpells = listOf(
    R.string.spells_cure_wounds,
    R.string.spells_healing_word,
    R.string.spells_revivify,
    R.string.spells_shield,
    R.string.spells_detect_magic,
    R.string.spells_hold_person
)

val bardSpells = listOf(
    R.string.spells_healing_word,
    R.string.spells_cure_wounds,
    R.string.spells_sleep,
    R.string.spells_invisibility,
    R.string.spells_thunderwave,
    R.string.spells_detect_magic,
    R.string.spells_hold_person
)

val druidSpells = listOf(
    R.string.spells_cure_wounds,
    R.string.spells_healing_word,
    R.string.spells_thunderwave,
    R.string.spells_detect_magic,
    R.string.spells_hold_person
)

val paladinSpells = listOf(
    R.string.spells_cure_wounds,
    R.string.spells_shield,
    R.string.spells_revivify,
    R.string.spells_detect_magic,
)

val rangerSpells = listOf(
    R.string.spells_cure_wounds,
    R.string.spells_detect_magic,
    R.string.spells_invisibility
)