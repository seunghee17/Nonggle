package com.capstone.nongglenonggle.presentation.view.worker.resume

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.capstone.nongglenonggle.R
import com.capstone.nongglenonggle.core.common.appbar.NonggleAppBar
import com.capstone.nongglenonggle.core.common.tab_bar.NonggleTabRow

@Composable
fun ResumeScreen(
    navController: NavHostController,
    viewModel: WorkerResumeComposeViewModel,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val effectFlow = viewModel.effect
    val context = LocalContext.current

    val tabBarTitleList: List<String> = listOf(
        context.getString(R.string.worker_ResumeScreen_TabTitle_1),
        context.getString(R.string.worker_ResumeScreen_TabTitle_2),
        context.getString(R.string.worker_ResumeScreen_TabTitle_3),
        context.getString(R.string.worker_ResumeScreen_TabTitle_4)
    )

    val pageState = rememberPagerState { tabBarTitleList.size }

    LaunchedEffect(true) {
        effectFlow.collect { effect ->
            when (effect) {
                else -> {}
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        NonggleAppBar(
            onBackPressed = { navController.popBackStack() },
            backAction = true,
            title = {},
        )
        NonggleTabRow(
            modifier = Modifier.fillMaxWidth(),
            tabs = tabBarTitleList,
            pagerState = pageState
        )
       HorizontalPager(
           state = pageState
       ) { page ->
           Column(
               modifier = Modifier
                   .fillMaxSize(),
               horizontalAlignment = Alignment.CenterHorizontally
           ) {
               when(page) {
                   0 -> ResumeStep1Screen(viewModel = viewModel)
                   1 -> ResumeStep2Screen(viewModel = viewModel)
                   2 -> ResumeStep3Screen()
                   3 -> ResumeStep4Screen()
               }
           }
       }
    }
}