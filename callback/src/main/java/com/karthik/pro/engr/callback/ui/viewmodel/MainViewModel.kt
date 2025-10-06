package com.karthik.pro.engr.callback.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.karthik.pro.engr.lib.domain.energy.EnergyAnalyzer
import com.karthik.pro.engr.lib.domain.energy.StretchResult

class MainViewModel(private val ss: SavedStateHandle) : ViewModel() {

    companion object {
        private const val KEY_HOUSES = "houses"
        private const val KEY_ERROR = "error"
        private const val KEY_RESULT = "result"
    }

    val houseTypes = mutableStateListOf<String>().apply {
        ss.get<List<String>>(KEY_HOUSES)?.let { addAll(it) }
    }

    var errorMessage by mutableStateOf(ss.get<String>(KEY_ERROR) ?: "")
        private set

    var stretchResult by mutableStateOf(ss.get<StretchResult?>(KEY_RESULT))
        private set

    private fun persist() {
        ss[KEY_HOUSES] = houseTypes.toList()
        ss[KEY_ERROR] = errorMessage
        ss[KEY_RESULT] = stretchResult
    }

    fun addHouseType(type: String) {
        val trimmed = type.trim()
        when {
            trimmed.isEmpty() -> errorMessage = "Input cannot be empty"
            trimmed.lowercase() !in listOf("producer", "consumer") -> errorMessage =
                "Input must be either 'producer' or 'consumer'"

            else -> {
                houseTypes.add(trimmed)
                errorMessage = ""
            }
        }
        persist()
    }

    fun computeResult() {
        stretchResult = if (houseTypes.isEmpty()) null else findLongestStretch(houseTypes)
        persist()
    }

    fun reset() {
        houseTypes.clear()
        errorMessage = ""
        stretchResult = null
        persist()
    }

    private fun findLongestStretch(list: List<String>): StretchResult {
        return EnergyAnalyzer.findLongestStretch(list)
    }
}