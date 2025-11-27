package com.pruebas.airolmagic.viewModels

import androidx.lifecycle.ViewModel
import com.pruebas.airolmagic.data.BackgroundData
import com.pruebas.airolmagic.data.ClassData
import com.pruebas.airolmagic.data.RaceData
import kotlinx.coroutines.flow.MutableStateFlow
import org.w3c.dom.CharacterData
import com.pruebas.airolmagic.R
import com.pruebas.airolmagic.data.AttributesData
import com.pruebas.airolmagic.data.StatsData

class CharacterViewModel: ViewModel() {
    private val _characterData = MutableStateFlow<CharacterData?>(null)
    private val _classData = MutableStateFlow<ClassData?>(null)
    private var _backgroundData = MutableStateFlow<BackgroundData?>(null)
    private val _speciesData = MutableStateFlow<RaceData?>(null)
    private val _statsData = MutableStateFlow<StatsData?>(null)
    private val _attributesData = MutableStateFlow<AttributesData?>(null)

    fun setClassData(className: String, classId: Int){
        _classData.value = ClassData(
            id = classId,
            name = className,
        )
    }

    fun setBackgroundData(background: BackgroundData){
        _backgroundData = MutableStateFlow(background)
    }

    fun setSpeciesData(speciesName: String, speciesId: Int){
        var speed = ""
        var size = ""

        when(speciesId){
            R.string.species_human -> {speed = "normal"; size = "medium"}
            R.string.species_dragonborn -> {speed = "normal"; size = "medium"}
            R.string.species_dwarf -> {speed = "slow"; size = "medium"}
            R.string.species_elf -> {speed = "normal"; size = "medium"}
            R.string.species_orc -> {speed = "normal"; size = "medium"}
            R.string.species_gnome -> {speed = "slow"; size = "small"}
            R.string.species_goliath -> {speed = "normal"; size = "medium"}
            R.string.species_half_elf -> {speed = "normal"; size = "medium"}
            R.string.species_half_orc -> {speed = "normal"; size = "medium"}
            R.string.species_halfling -> {speed = "slow"; size = "small"}
            R.string.species_tiefling -> {speed = "normal"; size = "medium"}
        }

        _speciesData.value = RaceData(
            id = speciesId,
            name = speciesName,
            speed = speed,
            size = size
        )
    }

    fun setStatsData(str: Int, des: Int, con: Int, int: Int, sab: Int, car: Int, ext1: Int, ext2: Int, ext3: Int){
        val backgroundId = _backgroundData.value?.backgroundName
        var str = str
        var des = des
        var con = con
        var int = int
        var sab = sab
        var car = car

        when(backgroundId){
            R.string.background_acolyte -> {
                int += ext1
                sab += ext2
                car += ext3
            }
            R.string.background_criminal -> {
                des += ext1
                con += ext2
                int += ext3
            }
            R.string.background_sage -> {
                con += ext1
                int += ext2
                sab += ext3
            }
            R.string.background_soldier -> {
                des += ext1
                str += ext2
                con += ext3
            }
        }

        _attributesData.value = AttributesData(
            strength = str,
            dexterity = des,
            constitution = con,
            intelligence = int,
            wisdom = sab,
            charisma = car
        )

        _statsData.value = StatsData(
            attributes = _attributesData.value!!
        )
    }


    fun getClassData(): Int {
        val classId = _classData.value?.id

        return if(classId == null) 0 else classId
    }

    fun getBackgroundData(): Int {
        val backgroundId = _backgroundData.value?.id

        return if(backgroundId == null) 0 else backgroundId
    }

    fun getBackgroundId(): Int {
        val backgroundId = _backgroundData.value?.backgroundName

        return if(backgroundId == null) 0 else backgroundId
    }

    fun getSpeciesData(): Int {
        val speciesId = _speciesData.value?.id

        return if(speciesId == null) 0 else speciesId
    }
}