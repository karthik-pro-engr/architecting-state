package com.karthik.pro.engr.viewmodel.livedata.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.karthik.pro.engr.lib.domain.energy.EnergyAnalyzer
import com.karthik.pro.engr.lib.domain.energy.StretchResult
import com.karthik.pro.engr.viewmodel.livedata.util.asDelegate

class BalancedEnergyViewModelLive(private val ss: SavedStateHandle) : ViewModel() {
    private val _houseTypes = MutableLiveData<List<String>>(emptyList())
    val houseTypesLiveData: LiveData<List<String>> = _houseTypes

    private val _errorMessage = MutableLiveData<String?>("")
    val errorMessageLiveData: LiveData<String?> = _errorMessage

    private val _stretchResult = MutableLiveData<StretchResult?>(null)
    val stretchResultLiveData: LiveData<StretchResult?> = _stretchResult

    var houseTypes by _houseTypes.asDelegate()
    var errorMessage by _errorMessage.asDelegate()
    var stretchResult by _stretchResult.asDelegate()

    fun addHouseType(type: String) {
        val trimmed = type.trim()
        when {
            trimmed.isEmpty() -> errorMessage = "Input cannot be empty"
            trimmed.lowercase() !in listOf("producer", "consumer") -> errorMessage =
                "Input must be either 'producer' or 'consumer'"

            else -> {
                houseTypes = houseTypes.orEmpty().plus(trimmed)
                errorMessage = ""
            }
        }

    }

    fun computeResult() {
        stretchResult =
            if (houseTypes?.isEmpty() == true) null else findLongestStretch(houseTypes!!)

    }

    fun reset() {
        houseTypes = emptyList()
        errorMessage = ""
        stretchResult = null
    }

    private fun findLongestStretch(list: List<String>): StretchResult {
        return EnergyAnalyzer.findLongestStretch(list)
    }
}