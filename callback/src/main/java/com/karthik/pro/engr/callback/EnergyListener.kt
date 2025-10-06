package com.karthik.pro.engr.callback

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

interface EnergyListener {

    fun addHouseType(type: String)
    fun computeResult()
    fun reset()
}