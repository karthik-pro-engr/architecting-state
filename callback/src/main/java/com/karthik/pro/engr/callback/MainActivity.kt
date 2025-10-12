package com.karthik.pro.engr.callback

import android.os.Bundle
import android.os.Parcelable
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.karthik.pro.engr.callback.ui.screens.BalancedEnergyScreen
import com.karthik.pro.engr.callback.ui.theme.ArchitectingstateTheme
import com.karthik.pro.engr.lib.domain.energy.EnergyAnalyzer
import com.karthik.pro.engr.lib.domain.energy.StretchResult

class MainActivity : ComponentActivity(), EnergyListener {

    var houseTypes = mutableStateListOf<String>()

    var stretchResult by mutableStateOf<StretchResult?>(null)

    var errorMessage: String by mutableStateOf("")
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            val savedList =
                savedInstanceState?.getStringArrayList("houses")
            houseTypes = if (savedList != null) {
                mutableStateListOf<String>().apply { addAll(savedList) }
            } else {
                mutableStateListOf()
            }
            errorMessage = savedInstanceState?.getString("error") ?: ""
            stretchResult = savedInstanceState?.getParcelable("result")
        }
        enableEdgeToEdge()
        setContent {
            ArchitectingstateTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    BalancedEnergyScreen(
                        modifier = Modifier.padding(innerPadding),
                        this, houseTypes, stretchResult, errorMessage
                    )
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putStringArrayList("houses", ArrayList(houseTypes))
        outState.putString("error", errorMessage)
        outState.putParcelable("result", stretchResult as Parcelable?)
    }

    override fun addHouseType(type: String) {
        val trimmed = type.trim()
        when {
            trimmed.isEmpty() -> errorMessage = "Input cannot be empty"
            trimmed.lowercase() !in listOf("p", "c") -> errorMessage =
                "Input must be either 'p' or 'c'"

            else -> {
                houseTypes.add(type)
                errorMessage = ""
            }
        }
    }

    override fun computeResult() {
        stretchResult = EnergyAnalyzer.findLongestStretch(houseTypes)
    }

    override fun reset() {
        errorMessage = ""
        houseTypes.clear()
        stretchResult = null
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ArchitectingstateTheme {
        Greeting("Android")
    }
}