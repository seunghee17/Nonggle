package com.capstone.nongglenonggle.app.navigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.capstone.nongglenonggle.core.design_system.NonggleTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IntegrationMainActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NonggleTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = NonggleTheme.colors.white
                ) {
                    NonggleNavHost()
                }
            }
        }
    }
}