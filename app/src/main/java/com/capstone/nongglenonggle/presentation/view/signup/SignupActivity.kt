package com.capstone.nongglenonggle.presentation.view.signup

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.capstone.nongglenonggle.app.NonggleNavHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignupActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            NonggleNavHost()
        }
    }

}
