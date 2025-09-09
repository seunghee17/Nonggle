package com.capstone.nongglenonggle.presentation.view.farmer.notice.compose_integration

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.capstone.nongglenonggle.R
import com.capstone.nongglenonggle.core.common.appbar.NonggleAppBar
import com.capstone.nongglenonggle.core.common.button.FullButton
import com.capstone.nongglenonggle.core.common.tab_bar.NonggleTabRow
import com.capstone.nongglenonggle.core.design_system.NonggleTheme

@Composable
fun NoticeScreen(
    //navController: NavHostController,
) {
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
            onBackPressed = {  },
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
//                    0 -> ResumeStep1Screen(viewModel = viewModel)
//                    1 -> ResumeStep2Screen(viewModel = viewModel)
//                    2 -> ResumeStep3Screen(viewModel = viewModel)
//                    3 -> ResumeStep4Screen(viewModel = viewModel)
                }
            }
        }
        FullButton(
            modifier = Modifier.fillMaxWidth(),
            enabled = true,
            onClick = {},
            titleText = context.getString(R.string.next_btn_Title),
            titleTextStyle = NonggleTheme.typography.t3
        )
    }
}