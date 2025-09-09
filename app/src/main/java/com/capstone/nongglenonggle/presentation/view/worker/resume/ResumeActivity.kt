package com.capstone.nongglenonggle.presentation.view.worker.resume

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.capstone.nongglenonggle.app.ResumeNavHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResumeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ResumeNavHost()
        }
    }
}