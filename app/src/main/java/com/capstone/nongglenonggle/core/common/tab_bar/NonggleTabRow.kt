package com.capstone.nongglenonggle.core.common.tab_bar

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.capstone.nongglenonggle.core.design_system.NonggleTheme

@Composable
fun NonggleTabRow(
    modifier: Modifier = Modifier,
    tabs: List<String>,
    selectedTabIndex: MutableState<Int>,
) {
    val density = LocalDensity.current
    TabRow(
        modifier = modifier,
        selectedTabIndex = selectedTabIndex.value,
        containerColor = Color.Transparent,
        contentColor = NonggleTheme.colors.g3,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                modifier = Modifier
                    .tabIndicatorOffset(
                        tabPositions[selectedTabIndex.value],
                    )// 넓이, 애니메이션 지정
                    .graphicsLayer {
                        shape = RoundedCornerShape(
                            topStart = 16.dp,
                            topEnd = 16.dp,
                            bottomStart = 16.dp,
                            bottomEnd = 16.dp
                        )
                        clip = true
                    }, // 모양 지정
                color = NonggleTheme.colors.m1 // 색상 지정
            )
        },
        divider = {},

    ) {
        tabs.forEachIndexed { index, title ->
            Tab(
                selected = selectedTabIndex.value == index,
                onClick = {
                    selectedTabIndex.value = index
                },
                text = {
                    Text(
                        text = title,
                        color = if(selectedTabIndex.value == index) NonggleTheme.colors.m1 else NonggleTheme.colors.g3,
                    )
                }
            )
        }
    }
}