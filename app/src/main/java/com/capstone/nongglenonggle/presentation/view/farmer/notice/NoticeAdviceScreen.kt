package com.capstone.nongglenonggle.presentation.view.farmer.notice

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.capstone.nongglenonggle.core.common.appbar.NonggleAppBar
import com.capstone.nongglenonggle.presentation.view.signup.SignupViewModel

@Composable
fun NoticeAdviceScreen(
    navController: NavHostController,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        NonggleAppBar(
            onBackPressed = { navController.popBackStack() },
            title = {},
        )
    }
}

