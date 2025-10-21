package com.capstone.nongglenonggle.presentation.view.worker.resume.resume_step3

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.capstone.nongglenonggle.R
import com.capstone.nongglenonggle.core.design_system.NonggleTheme
import com.capstone.nongglenonggle.core.design_system.spoqahanSansneo
import com.capstone.nongglenonggle.core.noRippleClickable
import com.capstone.nongglenonggle.presentation.view.worker.resume.resume_step3.component.SelectRegionBottomSheet
import com.capstone.nongglenonggle.presentation.view.worker.resume.resume_step3.component.selectedChipItem
import com.capstone.nongglenonggle.presentation.view.worker.resume.resume_step3.component.workCategoryChip
import com.capstone.nongglenonggle.presentation.view.worker.resume.resume_step3.ResumeStep3Contract.Event as Step3Event
import com.capstone.nongglenonggle.presentation.view.worker.resume.resume_step3.ResumeStep3Contract.State as Step3State
import com.capstone.nongglenonggle.presentation.view.worker.resume.resume_step3.ResumeStep3Contract.Effect as Step3Effect


@Composable
internal fun ResumeStep3Route(
    viewModel: ResumeStep3ViewModel,
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val effectFlow = viewModel.effect

    LaunchedEffect(Unit) {
        effectFlow.collect { effect ->
            when (effect) {
                is Step3Effect.FailToastMessage -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }
    }

    ResumeStep3Screen(
        state = uiState,
        onEvent = viewModel::setEvent
    )
}

@Composable
fun ResumeStep3Screen(
    state: Step3State,
    onEvent: (Step3Event) -> Unit
) {
    val context = LocalContext.current

    LaunchedEffect(state.showSelectLocationBottomSheet) {
        if (!state.showSelectLocationBottomSheet) {
            onEvent(Step3Event.ClearSheetState)
        }
    }

    if(state.showSelectLocationBottomSheet) {
        SelectRegionBottomSheet(
            context = context,
            state = state,
            onEvent = onEvent,
            onDismissRequest = { onEvent(Step3Event.ShowRegionBottomSheet(false)) }
        )
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 20.dp, end = 20.dp, bottom = 60.dp)
    ) {
        item {
            Text(
                modifier = Modifier.padding(top = 24.dp, bottom = 12.dp),
                text = context.getString(R.string.희망_근무지역),
                style = TextStyle(
                    fontSize = 14.sp,
                    color = NonggleTheme.colors.g1,
                    fontWeight = FontWeight.Normal,
                    fontFamily = spoqahanSansneo
                )
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .border(
                        BorderStroke(1.dp, NonggleTheme.colors.g_line),
                        shape = RoundedCornerShape(4.dp)
                    )
                    .noRippleClickable {
                        onEvent(Step3Event.ShowRegionBottomSheet(true))
                    },

                ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = context.getString(R.string.희망_근무지역),
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            fontFamily = spoqahanSansneo,
                            color = NonggleTheme.colors.g3
                        )
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Image(
                        modifier = Modifier.size(size = 24.dp),
                        painter = painterResource(R.drawable.place),
                        contentDescription = null
                    )
                }
            }
        }
        if(state.preferLocationList.isNotEmpty() && !state.showSelectLocationBottomSheet) {
            item {
                LazyVerticalGrid(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .padding(top = 12.dp),
                    columns = GridCells.Fixed(3),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    items(count = state.preferLocationList.size) { index ->
                        selectedChipItem(
                            title = state.preferLocationList[index],
                            removeChip = {onEvent(Step3Event.RemovePreferLocation(state.preferLocationList[index]))}
                        )
                    }
                }
            }
        }
        item {
            Text(
                modifier = Modifier.padding(top = 32.dp),
                text = context.getString(R.string.희망_품목),
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    fontFamily = spoqahanSansneo,
                    color = NonggleTheme.colors.g1
                )
            )
            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = context.getString(R.string.다중_선택이),
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    fontFamily = spoqahanSansneo,
                    color = NonggleTheme.colors.g2
                )
            )
        }
        item { //품목
            LazyVerticalGrid(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 200.dp)
                    .padding(top = 12.dp),
                columns = GridCells.Fixed(3),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                items(
                    count = state.totalPreferWorkCategoryList.size,
                ) { index ->
                    workCategoryChip(
                        categoryTitle = state.totalPreferWorkCategoryList[index],
                        onClick = {
                            onEvent(Step3Event.SelectPreferWorkCategory(state.totalPreferWorkCategoryList[index]))
                        },
                        selectCategoryList = state.selectedPreferWorkCategoryList
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ResumeStep3PreviewScreen() {
    ResumeStep3Screen(
        state = Step3State(),
        onEvent = {}
    )
}
