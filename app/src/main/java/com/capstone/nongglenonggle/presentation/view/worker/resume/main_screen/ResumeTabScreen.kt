package com.capstone.nongglenonggle.presentation.view.worker.resume.main_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.capstone.nongglenonggle.R
import com.capstone.nongglenonggle.core.common.appbar.NonggleAppBar
import com.capstone.nongglenonggle.core.common.button.FullButton
import com.capstone.nongglenonggle.core.common.component.NonggleTabRow
import com.capstone.nongglenonggle.core.design_system.NonggleTheme
import com.capstone.nongglenonggle.presentation.view.worker.resume.resume_step1.ResumeStep1Screen
import com.capstone.nongglenonggle.presentation.view.worker.resume.resume_step1.ResumeStep1ViewModel
import com.capstone.nongglenonggle.presentation.view.worker.resume.resume_step2.ResumeStep2Screen
import com.capstone.nongglenonggle.presentation.view.worker.resume.resume_step2.ResumeStep2ViewModel
import com.capstone.nongglenonggle.presentation.view.worker.resume.resume_step3.ResumeStep3Screen
import com.capstone.nongglenonggle.presentation.view.worker.resume.resume_step3.ResumeStep3ViewModel
import com.capstone.nongglenonggle.presentation.view.worker.resume.resume_step4.ResumeStep4Screen
import com.capstone.nongglenonggle.presentation.view.worker.resume.resume_step4.ResumeStep4ViewModel

@Composable
fun ResumeTabScreen(
    navController: NavHostController,
    viewModel: ResumeMainViewModel,
    step1ViewModel: ResumeStep1ViewModel,
    step2ViewModel: ResumeStep2ViewModel,
    step3ViewModel: ResumeStep3ViewModel,
    step4ViewModel: ResumeStep4ViewModel,
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
           state = pageState,
           modifier = Modifier
               .fillMaxWidth()
               .weight(1f)
       ) { page ->
           Column(
               modifier = Modifier
                   .fillMaxWidth(),
               horizontalAlignment = Alignment.CenterHorizontally
           ) {
               when(page) {
                   0 -> ResumeStep1Screen(viewModel = step1ViewModel)
                   1 -> ResumeStep2Screen(viewModel = step2ViewModel)
                   2 -> ResumeStep3Screen(viewModel = step3ViewModel)
                   3 -> ResumeStep4Screen(viewModel = step4ViewModel)
               }
           }
       }
        FullButton(
            modifier = Modifier
                .fillMaxWidth(),
            enabled = true,
            onClick = {},
            titleText = context.getString(R.string.next_btn_Title),
            titleTextStyle = NonggleTheme.typography.t3
        )
    }
}