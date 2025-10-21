package com.capstone.nongglenonggle.presentation.view.farmer.notice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.capstone.nongglenonggle.app.navigation.NoticeNavHost
import com.capstone.nongglenonggle.presentation.view.farmer.notice.notice_main.NoticeMainScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NoticeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            NoticeNavHost()
        }
    }
}