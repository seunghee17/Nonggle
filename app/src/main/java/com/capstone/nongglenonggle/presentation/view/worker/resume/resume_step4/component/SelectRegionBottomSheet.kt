package com.capstone.nongglenonggle.presentation.view.worker.resume.resume_step4.component

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
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
import com.capstone.nongglenonggle.presentation.view.worker.resume.resume_step4.ResumeStep3Contract.Event
import com.capstone.nongglenonggle.presentation.view.worker.resume.resume_step4.ResumeStep3Contract.State

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectRegionBottomSheet(
    modifier: Modifier = Modifier,
    context: Context,
    onDismissRequest: () -> Unit,
    state: State,
    onEvent: (Event) -> Unit
) {

    NonggleBottomSheet(
        onDismissRequest = onDismissRequest,
        header = {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentHeight()
                    .padding(top = 10.dp, bottom = 32.dp, start = 20.dp, end = 20.dp),
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
                    modifier = modifier.padding(top = 20.dp, end = 20.dp),
                    onClick = onDismissRequest,
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.close),
                        modifier = modifier.size(width = 24.dp, height = 24.dp),
                        contentDescription = null,
                        tint = Color.Black
                    )
                }
            }
        },
        bodyContent = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp)
            ) {
                if (state.selectedRegionList.isNotEmpty()) {
                    LazyVerticalGrid(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        columns = GridCells.Fixed(3),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                    ) {
                        items(count = state.selectedRegionList.size) { index ->
                            selectedChipItem(
                                modifier = Modifier,
                                title = selectedRegionList[index],
                                removeChip = { deleteChipEvent(selectedRegionList[index]) },
                            )
                        }
                    }
                }
                Row(
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .fillMaxWidth()
                ) {
                    //상위 지역 선택
                    LazyColumn(
                        modifier = Modifier
                            .wrapContentWidth()
                    ) {
                        items(count = regionList.size) { index ->
                            Text(
                                modifier = Modifier
                                    .padding(
                                        top = 13.dp,
                                        bottom = 13.dp,
                                        start = 20.dp,
                                        end = 40.dp
                                    )
                                    .noRippleClickable {
                                        getSubRegion(regionList[index])
                                    },
                                text = regionList[index],
                                style = NonggleTheme.typography.b1_main.copy(color = NonggleTheme.colors.g2)
                            )
                        }
                    }
                }
                if (subRegionList.isNotEmpty()) {
                    itemDivider()
                    // 하위 지역 선택
                    LazyColumn(modifier = Modifier.weight(1f)) {
                        items(count = subRegionList.size) { index ->
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 11.dp, bottom = 11.dp, start = 20.dp)
                                    .noRippleClickable {
                                        addChipEvent(subRegionList[index])
                                    },
                                text = subRegionList[index],
                                style = NonggleTheme.typography.b1_main.copy(color = NonggleTheme.colors.g2)
                            )
                        }
                    }
                }
            }
        },
        footer = {
            FullButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
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
        state = State(),
        onEvent = {}
    )
}