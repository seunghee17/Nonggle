package com.capstone.nongglenonggle.core.common.tab_bar

import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.capstone.nongglenonggle.core.design_system.NonggleTheme
import kotlinx.coroutines.launch

@Composable
fun NonggleTabRow(
    modifier: Modifier = Modifier,
    tabs: List<String>,
    pagerState: PagerState,
) {
    val scope = rememberCoroutineScope()
    ScrollableTabRow (
        modifier = modifier,
        selectedTabIndex = pagerState.currentPage,
        containerColor = Color.Transparent,
        contentColor = NonggleTheme.colors.g3,
        indicator = { tabPositions ->
            TabRowDefaults.SecondaryIndicator(
                modifier = Modifier
                    .tabIndicatorOffset(
                        tabPositions[pagerState.currentPage],
                    )// 넓이, 애니메이션 지정
                    .clip(RoundedCornerShape(16.dp)),// 모양 지정
                color = NonggleTheme.colors.m1 // 색상 지정
            )
        },
    ) {
        tabs.forEachIndexed { index, title ->
            Tab(
                selected = pagerState.currentPage == index,
                onClick = {
                    scope.launch { pagerState.animateScrollToPage(index) }
                },
                text = {
                    Text(
                        text = title,
                        color = if(pagerState.currentPage == index) NonggleTheme.colors.m1 else NonggleTheme.colors.g3,
                    )
                }
            )
        }
        //HorizontalDivider
    }
}