package com.karthik.pro.engr.architectingstate.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.karthik.pro.engr.architectingstate.domain.energy.EnergyAnalyzer
import com.karthik.pro.engr.architectingstate.domain.energy.StretchResult
import kotlin.text.isEmpty
import kotlin.text.lowercase
import kotlin.text.trim

class BalancedEnergyViewmodel : ViewModel() {
    private val _houseTypes = mutableStateListOf<String>()
    val houseTypes: List<String> get() = _houseTypes

    var stretchResult by mutableStateOf<StretchResult?>(null)
        private set

    var errorMessage by mutableStateOf("")
        private set

    fun addHouseType(type: String) {
        val trimmed = type.trim()
        when {
            trimmed.isEmpty() -> errorMessage = "Input cannot be empty"
            trimmed.lowercase() !in listOf("producer", "consumer") -> errorMessage =
                "Input must be either 'producer' or 'consumer'"

            else -> {
                _houseTypes.add(type)
                errorMessage = ""
            }
        }
    }

    fun calculateBalancedEnergy() {
        stretchResult = EnergyAnalyzer.findLongestStretch(_houseTypes)
    }

    fun reset() {
        _houseTypes.clear()
        errorMessage = ""
    }
}
