package com.capstone.nongglenonggle.presentation.view.worker.resume.resume_step3.component

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.capstone.nongglenonggle.R
import com.capstone.nongglenonggle.core.common.button.FullButton
import com.capstone.nongglenonggle.core.common.dialog.NonggleBottomSheet
import com.capstone.nongglenonggle.core.design_system.NonggleTheme
import com.capstone.nongglenonggle.core.design_system.spoqahanSansneo
import com.capstone.nongglenonggle.core.noRippleClickable
import com.capstone.nongglenonggle.presentation.view.worker.resume.resume_step3.ResumeStep3Contract.Event as Step3Event
import com.capstone.nongglenonggle.presentation.view.worker.resume.resume_step3.ResumeStep3Contract.State as Step3State

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectRegionBottomSheet(
    modifier: Modifier = Modifier,
    context: Context,
    onDismissRequest: () -> Unit,
    state: Step3State,
    onEvent: (Step3Event) -> Unit
) {

    NonggleBottomSheet(
        modifier = modifier,
        onDismissRequest = onDismissRequest,
        header = {
            Row(
                modifier = Modifier
                    .wrapContentHeight()
                    .wrapContentHeight()
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = context.getString(R.string.희망근무지역),
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontFamily = spoqahanSansneo,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black
                    )
                )
                Spacer(modifier = Modifier.weight(1f))
                IconButton(
                    onClick = onDismissRequest,
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.close),
                        modifier = Modifier.size(width = 24.dp, height = 24.dp),
                        contentDescription = null,
                        tint = Color.Black
                    )
                }
            }
        },
        bodyContent = {
            if(state.isLocationBottomSheetLoading) {
                CircularProgressIndicator()
            }
           else {
                Column(
                    modifier = Modifier
                        .wrapContentHeight()
                        .padding(horizontal = 20.dp)
                ) {
                    if (state.preferLocationList.isNotEmpty()) {
                        LazyVerticalGrid(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .padding(bottom = 20.dp),
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
                    itemHorizontalDivider()
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(height = 310.dp)
                    ) {
                        LazyColumn(
                            modifier = Modifier
                                .weight(3f)
                        ) {
                            items(count = state.regionList.size) { index ->
                                regionListItem(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .noRippleClickable { onEvent(Step3Event.GetSubRegionList(state.regionList[index])) },
                                    selectedRegion = state.selectedParentRegion,
                                    itemText = state.regionList[index],
                                    selectedTextColor = Color.White,
                                    backgroundColor = NonggleTheme.colors.m1
                                )
                            }
                        }
                        itemVerticalDivider()
                        //하위 지역 리스트
                        LazyColumn(
                            modifier = Modifier
                                .weight(7f)
                        ) {
                            items(count = state.subRegionList.size) { index ->
                                regionListItem(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .noRippleClickable { onEvent(Step3Event.AddPreferLocation(state.subRegionList[index])) },
                                    selectedRegion = state.selectedSubRegion,
                                    itemText = state.subRegionList[index],
                                    selectedTextColor = NonggleTheme.colors.m1,
                                    backgroundColor = NonggleTheme.colors.m6
                                )
                            }
                        }
                    }
                    itemHorizontalDivider()
                }
            }
        },
        footer = {
            FullButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 40.dp, bottom = 20.dp),
                enabled = true,
                onClick = {
                    onDismissRequest()
                },
                titleText = context.getString(R.string.확인),
                titleTextStyle = NonggleTheme.typography.t3
            )
        }
    )
}

@Preview
@Composable
fun SelectRegionPreviewScreen() {
    SelectRegionBottomSheet(
        context = LocalContext.current,
        state = Step3State(),
        onEvent = {},
        onDismissRequest = {}
    )
}