package com.capstone.nongglenonggle.presentation.view.farmer.notice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.capstone.nongglenonggle.presentation.view.farmer.notice.compose_integration.NoticeScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NoticeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            NoticeScreen()
        }
    }
}