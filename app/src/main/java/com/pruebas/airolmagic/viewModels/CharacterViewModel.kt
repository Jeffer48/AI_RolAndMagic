package com.pruebas.airolmagic.viewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.pruebas.airolmagic.data.objects.BackgroundData
import com.pruebas.airolmagic.data.objects.ClassData
import com.pruebas.airolmagic.data.objects.RaceData
import kotlinx.coroutines.flow.MutableStateFlow
import com.pruebas.airolmagic.R
import com.pruebas.airolmagic.data.objects.AttributesData
import com.pruebas.airolmagic.data.objects.CharacterProfile
import com.pruebas.airolmagic.data.objects.InventoryData
import com.pruebas.airolmagic.data.objects.InventoryItem
import com.pruebas.airolmagic.data.ManaCalculator
import com.pruebas.airolmagic.data.objects.StatsData
import com.pruebas.airolmagic.data.objects.WalletData
import com.pruebas.airolmagic.data.database.CharacterRepository
import com.pruebas.airolmagic.data.database.SpellsCantripsRepository
import com.pruebas.airolmagic.data.database.ItemsRepository
import kotlinx.coroutines.launch

class CharacterViewModel(
    application: Application,
    private val scRepository: SpellsCantripsRepository,
    private val itemsRepository: ItemsRepository,
    private val characterRepository: CharacterRepository
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
    private val _spellsCantripsList = MutableStateFlow<List<Int>>(emptyList())

    fun getCharacter(): CharacterProfile?{
        return _characterData.value
    }

    fun saveUserData(onSuccess: () -> Unit, onError: () -> Unit){
        viewModelScope.launch {
            val intelligence: Int = if (_attributesData.value?.intelligence == null) 0 else _attributesData.value?.intelligence!!
            val classId: Int = if (_classData.value?.id == null) 0 else _classData.value?.id!!
            val manaPoints: Int = manaCalculator.calculateMana(
                classId = classId,
                intelligence = intelligence,
                level = 1
            )
            saveItemsData(items = _backgroundData.value?.equipment!!)
            saveItemsData(items = _spellsCantripsList.value)

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
                background = appContext.getString(_backgroundData.value?.backgroundName ?: 0),
                backgroundTag = appContext.getString(_backgroundData.value?.tagId ?: 0),
                backgroundTagDetails = appContext.getString(_backgroundData.value?.tagDescriptionId ?: 0),

                race = _speciesData.value!!,
                classN = _classData.value!!,
                stats = _statsData.value!!,
                wallet = WalletData(gold = _backgroundData.value?.gold ?: 0),
                inventory = _inventoryData.value!!
            )

            Log.d("ViewModel", "CharacterData: ${_characterData.value}")
            val result = characterRepository.saveCharacterToFirebase(_characterData.value!!)

            result.onSuccess {
                Log.d("ViewModel", "Character saved successfully")
                onSuccess()
            }.onFailure {
                Log.e("ViewModel", "Error saving character", it)
                onError()
            }
        }
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
        var str = str; var des = des; var con = con
        var int = int; var sab = sab; var car = car

        _attributesCleanData.value = AttributesData(
            strength = str,
            dexterity = des,
            constitution = con,
            intelligence = int,
            wisdom = sab,
            charisma = car
        )

        when(backgroundId){
            R.string.background_acolyte -> { int += ext1; sab += ext2; car += ext3 }
            R.string.background_criminal -> { des += ext1; con += ext2; int += ext3 }
            R.string.background_sage -> { con += ext1; int += ext2; sab += ext3 }
            R.string.background_soldier -> { des += ext1; str += ext2; con += ext3 }
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
        _spellsCantripsList.value += spells
        _spellsCantripsList.value += cantrips
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

    //Funciones privadas
    private suspend fun saveItemsData(items: List<Int>){
            var weaponsCatalog: Map<String, InventoryItem> = emptyMap()
            var armorCatalog: Map<String, InventoryItem> = emptyMap()
            var itemsCatalog: Map<String, InventoryItem> = emptyMap()
            var ammoCatalog: Map<String, InventoryItem> = emptyMap()
            var consumablesCatalog: Map<String, InventoryItem> = emptyMap()
            var spellsCatalog: Map<String, InventoryItem> = emptyMap()
            var cantripsCatalog: Map<String, InventoryItem> = emptyMap()

            try {
                weaponsCatalog = itemsRepository.loadWeaponsCatalog()
                armorCatalog = itemsRepository.loadArmorCatalog()
                itemsCatalog = itemsRepository.loadItemsCatalog()
                ammoCatalog = itemsRepository.loadAmmoCatalog()
                consumablesCatalog = itemsRepository.loadConsumablesCatalog()
                spellsCatalog = scRepository.loadSpellsCatalog()
                cantripsCatalog = scRepository.loadCantripsCatalog()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            val fullCatalog = weaponsCatalog + armorCatalog + itemsCatalog + ammoCatalog + consumablesCatalog + spellsCatalog + cantripsCatalog
            val selectedItems = items.mapNotNull { resourceId ->
                val catalogId = getResourceKey(resourceId)
                val baseItem = fullCatalog[catalogId]

                if (baseItem != null) {
                    baseItem.copy(
                        instanceId = catalogId,
                        catalogId = catalogId,
                        name = appContext.getString(resourceId),
                    )
                } else {
                    Log.w(
                        "ViewModel",
                        "El item con clave $catalogId no fue encontrado en Firebase."
                    )
                    null
                }
            }
            if(selectedItems.isNotEmpty()) _inventoryItemsList.value += selectedItems
    }
}