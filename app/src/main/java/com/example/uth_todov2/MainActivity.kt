package com.example.uth_todov2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.uth_todov2.navigation.AppNavHost
import com.example.uth_todov2.ui.theme.UTH_todov2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UTH_todov2Theme {
                AppNavHost()
            }
        }
    }
}
