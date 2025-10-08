package com.capstone.nongglenonggle.presentation.view.worker.resume.main_screen

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.capstone.nongglenonggle.R
import com.capstone.nongglenonggle.core.common.appbar.NonggleAppBar
import com.capstone.nongglenonggle.core.common.button.FullButton
import com.capstone.nongglenonggle.core.common.component.NonggleTabRow
import com.capstone.nongglenonggle.core.design_system.NonggleTheme
import com.capstone.nongglenonggle.presentation.view.worker.home.WorkerMainActivity
import com.capstone.nongglenonggle.presentation.view.worker.resume.resume_step1.ResumeStep1Route
import com.capstone.nongglenonggle.presentation.view.worker.resume.resume_step1.ResumeStep1ViewModel
import com.capstone.nongglenonggle.presentation.view.worker.resume.resume_step2.ResumeStep2Route
import com.capstone.nongglenonggle.presentation.view.worker.resume.resume_step2.ResumeStep2ViewModel
import com.capstone.nongglenonggle.presentation.view.worker.resume.resume_step3.ResumeStep3Route
import com.capstone.nongglenonggle.presentation.view.worker.resume.resume_step3.ResumeStep3ViewModel
import com.capstone.nongglenonggle.presentation.view.worker.resume.main_screen.ResumeTabContract.Event as MainEvent
import com.capstone.nongglenonggle.presentation.view.worker.resume.main_screen.ResumeTabContract.Effect as MainEffect

import kotlinx.coroutines.launch

@Composable
fun ResumeTabScreen(
    navController: NavHostController,
    viewModel: ResumeMainViewModel,
    step1ViewModel: ResumeStep1ViewModel,
    step2ViewModel: ResumeStep2ViewModel,
    step3ViewModel: ResumeStep3ViewModel,
) {
    val effectFlow = viewModel.effect
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        effectFlow.collect { effect ->
            when (effect) {
                is MainEffect.NavigateToHomeScreen -> {
                    val intent = Intent(context, WorkerMainActivity::class.java).apply {
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    }
                    context.startActivity(intent)
                }
                is MainEffect.ShowToastMessage -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    val tabBarTitleList: List<String> = listOf(
        context.getString(R.string.worker_ResumeScreen_TabTitle_1),
        context.getString(R.string.worker_ResumeScreen_TabTitle_2),
        context.getString(R.string.worker_ResumeScreen_TabTitle_4)
    )

    val pageState = rememberPagerState { tabBarTitleList.size }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxSize()
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
                   0 -> ResumeStep1Route(viewModel = step1ViewModel)
                   1 -> ResumeStep2Route(viewModel = step2ViewModel)
                   2 -> ResumeStep3Route(viewModel = step3ViewModel)
               }
           }
       }
        FullButton(
            modifier = Modifier
                .fillMaxWidth(),
            enabled = true,
            onClick = {
                coroutineScope.launch {
                    val nextPage = pageState.currentPage+1
                    if(nextPage<tabBarTitleList.size) {
                        pageState.animateScrollToPage(nextPage)
                    } else {
                        val selectedGender = step1ViewModel.uiState.value.genderSelectedMap
                            .filterValues { it }
                            .keys
                            .firstOrNull()
                        val selectedCertificateAvailable = step1ViewModel.uiState.value.certificationPossessionSelectedMap
                            .filterValues { it }
                            .keys
                            .firstOrNull()
                        //이력서 저장 로직 실행
                        viewModel.setEvent(MainEvent.SaveResume(
                            name = step1ViewModel.uiState.value.userName,
                            birthDate = step1ViewModel.uiState.value.birthDate.toString(),
                            gender = selectedGender ?: "",
                            certificate = if(selectedCertificateAvailable == "있음") true else false,
                            certificateList = step1ViewModel.uiState.value.userCertificationList,
                            careerList = step2ViewModel.uiState.value.careerList,
                            regionList = step3ViewModel.uiState.value.preferLocationList,
                            categoryList = step3ViewModel.uiState.value.selectedPreferWorkCategoryList,
                            imageUri = step1ViewModel.uiState.value.imageProfileUri
                        ))
                    }
                }
            },
            titleText = context.getString(R.string.next_btn_Title),
            titleTextStyle = NonggleTheme.typography.t3
        )
    }
}