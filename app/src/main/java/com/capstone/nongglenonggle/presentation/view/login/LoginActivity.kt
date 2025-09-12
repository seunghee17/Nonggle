package com.capstone.nongglenonggle.presentation.view.login

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.capstone.nongglenonggle.app.navigation.NonggleNavHost
import dagger.hilt.android.AndroidEntryPoint
import com.capstone.nongglenonggle.core.design_system.NongleTheme

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            NongleTheme {
                NonggleNavHost()
            }
        }

    }

}
