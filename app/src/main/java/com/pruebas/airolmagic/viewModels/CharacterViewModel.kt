package com.pruebas.airolmagic.viewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.pruebas.airolmagic.data.BackgroundData
import com.pruebas.airolmagic.data.ClassData
import com.pruebas.airolmagic.data.RaceData
import kotlinx.coroutines.flow.MutableStateFlow
import com.pruebas.airolmagic.R
import com.pruebas.airolmagic.data.AttributesData
import com.pruebas.airolmagic.data.CharacterProfile
import com.pruebas.airolmagic.data.InventoryData
import com.pruebas.airolmagic.data.InventoryItem
import com.pruebas.airolmagic.data.ItemData
import com.pruebas.airolmagic.data.ItemType
import com.pruebas.airolmagic.data.ManaCalculator
import com.pruebas.airolmagic.data.StatsData
import com.pruebas.airolmagic.data.WalletData
import com.pruebas.airolmagic.data.database.SpellsCantripsRepository
import kotlinx.coroutines.launch
import kotlin.math.floor

class CharacterViewModel(
    application: Application,
    private val scRepository: SpellsCantripsRepository
): AndroidViewModel(application) {
    private val manaCalculator = ManaCalculator()
    private val appContext = application.applicationContext
    private val _characterData = MutableStateFlow<CharacterProfile?>(null)
    private val _characterDataTemp = MutableStateFlow<CharacterProfile?>(null)
    private val _classData = MutableStateFlow<ClassData?>(null)
    private var _backgroundData = MutableStateFlow<BackgroundData?>(null)
    private val _speciesData = MutableStateFlow<RaceData?>(null)
    private val _statsData = MutableStateFlow<StatsData?>(null)
    private val _attributesData = MutableStateFlow<AttributesData?>(null)
    private val _attributesCleanData = MutableStateFlow<AttributesData?>(null)
    private val _inventoryData = MutableStateFlow<InventoryData?>(null)
    private val _inventoryItemsList = MutableStateFlow<List<InventoryItem>>(emptyList())

    fun saveUserData(){
        val intelligence: Int = if(_attributesData.value?.intelligence == null) 0 else _attributesData.value?.intelligence!!
        val classId: Int = if(_classData.value?.id == null) 0 else _classData.value?.id!!
        val manaPoints: Int = manaCalculator.calculateMana(classId = classId, intelligence = intelligence, level = 1)

        _inventoryData.value = InventoryData(
            items = _inventoryItemsList.value
        )

        _characterData.value = CharacterProfile(
            userId = _characterDataTemp.value?.userId ?: "",
            name = _characterDataTemp.value?.name ?: "",
            alignment = _characterDataTemp.value?.alignment ?: "",
            languages = _characterDataTemp.value?.languages ?: "",
            mana_points = manaPoints,
            max_mana_points = manaPoints,

            race = _speciesData.value!!,
            classN = _classData.value!!,
            stats = _statsData.value!!,
            wallet = WalletData(),
            inventory = _inventoryData.value!!
        )
    }

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

        _attributesCleanData.value = AttributesData(
            strength = str,
            dexterity = des,
            constitution = con,
            intelligence = int,
            wisdom = sab,
            charisma = car
        )

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

    private var _alignment = R.string.align_neutral
    private var _morality = R.string.morality_neutral
    private var _language = R.string.language_common_sign
    fun setCharacterExtras(userId: String,name: String, alignment: String, languages: String, alignmentId: Int, moralityId: Int, languagesId: Int){
        _alignment = alignmentId
        _morality = moralityId
        _language = languagesId

        _characterDataTemp.value = CharacterProfile(
            userId = userId,
            name = name,
            alignment = alignment,
            languages = languages,
        )
    }

    fun setSpellsAndCantrips(spells: List<Int>, cantrips: List<Int>) {
        viewModelScope.launch {
            val spellsCatalog: Map<String, InventoryItem>
            val cantripsCatalog: Map<String, InventoryItem>

            try{
                spellsCatalog = scRepository.loadSpellsCatalog()
                cantripsCatalog = scRepository.loadCantripsCatalog()
            }catch(e: Exception){
                e.printStackTrace()
                return@launch
            }

            val selectedSpells = spells.mapNotNull { resourceId ->
                val catalogId = getResourceKey(resourceId)
                val baseItem = spellsCatalog[catalogId]

                if(baseItem != null){
                    baseItem.copy(
                        instanceId = catalogId,
                        catalogId = catalogId,
                        name = appContext.getString(resourceId),
                    )
                }
                else{
                    Log.w("ViewModel", "El Hechizo con clave $resourceId no encontrado en Firebase.")
                    null
                }
            }
            if(selectedSpells.isNotEmpty()) _inventoryItemsList.value += selectedSpells

            val selectedCantrips = cantrips.mapNotNull { resourceId ->
                val catalogId = getResourceKey(resourceId)
                val baseItem = cantripsCatalog[catalogId]

                if(baseItem != null){
                    baseItem.copy(
                        instanceId = catalogId,
                        catalogId = catalogId,
                        name = appContext.getString(resourceId),
                    )
                }
                else{
                    Log.w("ViewModel", "El truco con clave $resourceId no encontrado en Firebase.")
                    null
                }
            }
            if(selectedCantrips.isNotEmpty()) _inventoryItemsList.value += selectedCantrips
        }
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

    fun getCleanAttributes(): AttributesData? {
        val cleanAttributes = _attributesCleanData.value
        return cleanAttributes
    }

    fun getAlignment(): Int {
        return _alignment
    }

    fun getMorality(): Int {
        return _morality
    }

    fun getCharacterName(): String {
        val characterName = _characterData.value?.name
        return characterName ?: ""
    }

    fun getExtraLanguage(): Int {
        return _language
    }

    fun getResourceKey(resourceId: Int): String{
        var resourceName = ""

        try{
            resourceName = appContext.resources.getResourceEntryName(resourceId)
        }catch(e: Exception){
            e.printStackTrace()
        }

        return resourceName
    }
}