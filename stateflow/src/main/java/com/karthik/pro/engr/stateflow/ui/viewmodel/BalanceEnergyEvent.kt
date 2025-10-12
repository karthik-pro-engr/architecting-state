package com.karthik.pro.engr.stateflow.ui.viewmodel

sealed class BalanceEnergyEvent {
    data class InputChanged(val value: String) : BalanceEnergyEvent()
    object AddHouseType : BalanceEnergyEvent()
    object ComputeResult : BalanceEnergyEvent()
    object Reset : BalanceEnergyEvent()
}