package com.karthik.pro.engr.stateflow.ui.viewmodel

import com.karthik.pro.engr.lib.domain.energy.StretchResult

data class BalancedEnergyUiState(
    val input: String = "",
    val errorMessage: String = "",
    val houseTypes: List<String> = emptyList(),
    val stretchResult: StretchResult? = null
)