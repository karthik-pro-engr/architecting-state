package com.karthik.pro.engr.stateflow.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.karthik.pro.engr.lib.domain.energy.EnergyAnalyzer
import com.karthik.pro.engr.lib.domain.energy.StretchResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.text.isLetter

class BalancedEnergyViewModelStateFlow() : ViewModel() {
    private val _uiState = MutableStateFlow(BalancedEnergyUiState())
    val uiState: StateFlow<BalancedEnergyUiState> = _uiState

    fun onEvent(event: BalanceEnergyEvent) {
        when (event) {
            BalanceEnergyEvent.AddHouseType -> {
                viewModelScope.launch {
                    _uiState.update {
                        val trimmed = it.input.trim()
                        val error = when {
                            trimmed.isEmpty() -> "Input cannot be empty"
                            trimmed.lowercase() !in listOf(
                                "producer",
                                "consumer"
                            ) ->
                                "Input must be either 'producer' or 'consumer'"

                            else -> {
                                ""
                            }
                        }
                        val list = if (error.isEmpty()) {
                            it.houseTypes.plus(trimmed)
                        } else it.houseTypes

                        it.copy(
                            "",
                            error,
                            list,
                            null
                        )
                    }
                }

            }

            is BalanceEnergyEvent.InputChanged -> {
                viewModelScope.launch {
                    _uiState.update {
                        if(event.value.all { input-> input.isLetter() })
                            it.copy(input = event.value)
                        else it.copy()
                    }
                }
            }

            BalanceEnergyEvent.ComputeResult -> {
                viewModelScope.launch {
                    _uiState.update {
                        val stretchResult = if (it.houseTypes == null) {
                            null
                        } else findLongestStretch(it.houseTypes)
                        it.copy(stretchResult = stretchResult)
                    }
                }
            }

            BalanceEnergyEvent.Reset -> {
                viewModelScope.launch {
                    _uiState.update {
                        BalancedEnergyUiState()
                    }
                }
            }
        }
    }

    private fun findLongestStretch(list: List<String>): StretchResult {
        return EnergyAnalyzer.findLongestStretch(list)
    }
}