package com.capstone.nongglenonggle.presentation.view.worker.resume.resume_step4.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
fun preferRegionChip (
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    preferLocation: String,
) {
    Box(
        modifier = Modifier
            .wrapContentHeight()
            .wrapContentWidth()
            .background(color = NonggleTheme.colors.g4)
            .border(
                BorderStroke(1.dp, NonggleTheme.colors.g_line),
                shape = RoundedCornerShape(28.dp)
            ),

        ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = preferLocation,
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = spoqahanSansneo,
                    fontWeight = FontWeight.Normal,
                    color = NonggleTheme.colors.g2
                )
            )
            Image(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .noRippleClickable { onClick() },
                painter = painterResource(R.drawable.xcircle),
                contentDescription = null
            )
        }
    }
}

@Composable
fun workCategoryChip(
    modifier: Modifier = Modifier,
    categoryTitle: String,
    onClick: () -> Unit,
    categoryActivate: Boolean
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
        enableColor = if (categoryActivate) NonggleTheme.colors.m1 else NonggleTheme.colors.g_line,
        enableContentColor = if (categoryActivate) NonggleTheme.colors.m1 else NonggleTheme.colors.g3,
        pressedColor = NonggleTheme.colors.m1,
    )
}