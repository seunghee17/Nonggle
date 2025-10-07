package com.capstone.nongglenonggle.presentation.view.worker.resume.resume_step3.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.capstone.nongglenonggle.R
import com.capstone.nongglenonggle.core.common.button.OutlinedButton
import com.capstone.nongglenonggle.core.design_system.NonggleTheme
import com.capstone.nongglenonggle.core.design_system.spoqahanSansneo
import com.capstone.nongglenonggle.core.noRippleClickable

@Composable
fun workCategoryChip(
    modifier: Modifier = Modifier,
    categoryTitle: String,
    onClick: () -> Unit,
    selectCategoryList: List<String>
) {
    OutlinedButton(
        modifier = modifier,
        titleText = categoryTitle,
        onClick = onClick,
        titleTextStyle = TextStyle(
            fontFamily = spoqahanSansneo,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp
        ),
        enableColor = if (selectCategoryList.contains(categoryTitle)) NonggleTheme.colors.m1 else NonggleTheme.colors.g_line,
        enableContentColor = if (selectCategoryList.contains(categoryTitle)) NonggleTheme.colors.m1 else NonggleTheme.colors.g3,
        pressedColor = NonggleTheme.colors.m1,
    )
}

@Composable
fun selectedChipItem(
    modifier: Modifier = Modifier,
    title: String,
    removeChip: () -> Unit,
) {
    Box(
        modifier = modifier
            .wrapContentWidth()
            .wrapContentHeight()
            .border(
                BorderStroke(1.dp, NonggleTheme.colors.g_line),
                shape = RoundedCornerShape(20.dp)
            )
            .background(
                color = NonggleTheme.colors.g4,
                shape = RoundedCornerShape(20.dp)
            )
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 11.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = spoqahanSansneo,
                    fontWeight = FontWeight.Normal,
                    color = NonggleTheme.colors.g2)
            )
            Image(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .noRippleClickable { removeChip() },
                painter = painterResource(R.drawable.xcircle),
                contentDescription = null,
            )
        }
    }
}

@Composable
fun itemVerticalDivider() {
    VerticalDivider(
        color = NonggleTheme.colors.g_line,
        thickness = 1.dp,
        modifier = Modifier.fillMaxHeight()
    )
}

@Composable
fun itemHorizontalDivider() {
    HorizontalDivider(
        color = NonggleTheme.colors.g_line,
        thickness = 1.dp,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun regionListItem(
    modifier: Modifier,
    selectedRegion: String,
    itemText: String,
    selectedTextColor: Color,
    backgroundColor: Color
) {
    var isSelected = if(selectedRegion==itemText) true else false
    Box(
        modifier = modifier.background(color = if(isSelected) backgroundColor else Color.White),
    ) {
        Text(
            modifier = modifier
                .padding(start = 20.dp, top = 13.dp, bottom = 13.dp),
            text = itemText,
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = spoqahanSansneo,
                fontWeight = FontWeight.Normal,
                color = if(isSelected) selectedTextColor else NonggleTheme.colors.g2),

            )
    }
}