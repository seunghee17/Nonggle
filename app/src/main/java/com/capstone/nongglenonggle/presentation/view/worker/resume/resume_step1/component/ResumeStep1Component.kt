package com.capstone.nongglenonggle.presentation.view.worker.resume.resume_step1.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
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
import java.util.LinkedHashMap

@Composable
fun certificationChipItem(
    modifier: Modifier = Modifier,
    removeChip: () -> Unit,
    certificationTitle: String,
) {
    OutlinedCard(
        modifier = modifier
            .height(48.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
    ) {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                certificationTitle,
                modifier = Modifier.padding(end = 16.dp),
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = spoqahanSansneo,
                    fontWeight = FontWeight.Normal,
                    color = NonggleTheme.colors.g2
                )
            )
            Image(
                modifier = Modifier.padding(end = 16.dp),
                painter = painterResource(id = R.drawable.xcircle),
                contentDescription = null
            )
        }
    }
}

@Composable
fun genderSelectButton(
    modifier: Modifier = Modifier,
    gender: String,
    selectGender: () -> Unit,
    genderSelectedMap: LinkedHashMap<String, Boolean>
) {
    OutlinedButton(
        modifier = modifier,
        titleText = gender,
        onClick = selectGender,
        titleTextStyle = TextStyle(
            fontFamily = spoqahanSansneo,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp
        ),
        enableColor = if (genderSelectedMap[gender] == true) NonggleTheme.colors.m1 else NonggleTheme.colors.g_line,
        enableContentColor = if (genderSelectedMap[gender] == true) NonggleTheme.colors.m1 else NonggleTheme.colors.g3,
        pressedColor = NonggleTheme.colors.m1,
    )
}

@Composable
fun certificationButton(
    modifier: Modifier = Modifier,
    title: String,
    changeCertificateState: () -> Unit,
    certificateAvailable: LinkedHashMap<String, Boolean>
) {
    OutlinedButton(
        modifier = modifier,
        titleText = title,
        onClick = changeCertificateState,
        titleTextStyle = TextStyle(
            fontFamily = spoqahanSansneo,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp
        ),
        enableColor = if (certificateAvailable[title] == true) NonggleTheme.colors.m1 else NonggleTheme.colors.g_line,
        enableContentColor = if (certificateAvailable[title]==true) NonggleTheme.colors.m1 else NonggleTheme.colors.g3,
        pressedColor = NonggleTheme.colors.m1,
    )
}

@Composable
fun certificationChipItem(
    modifier: Modifier = Modifier,
    title: String,
    removeChip: () -> Unit,
) {
    Box(
        modifier = Modifier
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

