package com.karthik.pro.engr.architecting_state

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.karthik.pro.engr.architecting_state.ui.screens.BalancedEnergyScreen
import com.karthik.pro.engr.architecting_state.ui.theme.ArchitectingstateTheme
import com.karthik.pro.engr.devtools.AllVariantsPreview

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ArchitectingstateTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    BalancedEnergyScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@AllVariantsPreview
@Composable
fun GreetingPreview() {
    ArchitectingstateTheme {
        Greeting("Android")
    }
}