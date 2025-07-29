package com.capstone.nongglenonggle.presentation.view.signup

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.capstone.nongglenonggle.R
import com.capstone.nongglenonggle.core.common.button.FullButton
import com.capstone.nongglenonggle.core.design_system.NonggleTheme
import com.capstone.nongglenonggle.core.design_system.spoqahanSansneo

@Composable
fun SelectTypeDescription() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            textAlign = TextAlign.Start,
            modifier = Modifier
                .padding(top = 10.dp),
            text = "가입 유형",
            color = NonggleTheme.colors.g2,
            style = NonggleTheme.typography.b1_main,
        )
        Text(
            textAlign = TextAlign.Start,
            modifier = Modifier
                .padding(top = 10.dp),
            text = "가입할 유형을 선택해주세요.",
            color = Color.Black,
            style = NonggleTheme.typography.t1,
        )
    }
}

@Composable
fun userTypeContainer(
    type: UserType,
    modifier: Modifier,
    onClick: () -> Unit,
    selectType: UserType
) {
    val titleText = if (type == UserType.WORKER) "구직자 회원" else "구인자 회원"
    val descriptionText = if (type == UserType.WORKER) "일자리를 찾고싶어요!" else "일손을 찾고싶어요!"
    val imageResourceId =
        if (type == UserType.WORKER) R.drawable.job_seeker_png else R.drawable.img_offer_circle

    OutlinedCard(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable {
                onClick()
            },
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        border = BorderStroke(
            1.dp,
            if (type == selectType) NonggleTheme.colors.m1 else NonggleTheme.colors.g_line
        ),
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                verticalArrangement = Arrangement.Top,
            ) {
                Text(
                    text = titleText,
                    style = NonggleTheme.typography.t3,
                    color = Color.Black
                )
                Text(
                    modifier = Modifier.padding(top = 12.dp),
                    text = descriptionText,
                    style = TextStyle(
                        fontFamily = spoqahanSansneo,
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                        letterSpacing = 0.sp,
                        platformStyle = PlatformTextStyle(includeFontPadding = false),
                    ),
                    color = NonggleTheme.colors.g2
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Column() {
                Spacer(modifier = Modifier.height(20.dp))
                Image(
                    modifier = Modifier
                        .size(width = 80.dp, height = 80.dp),
                    painter = painterResource(id = imageResourceId),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}

@Composable
fun nextBtn(modifier: Modifier, enable: Boolean, onClick: () -> Unit) {
    FullButton(
        modifier = modifier,
        enabled = enable,
        onClick = onClick,
        titleText = "다음",
        backgroundColor = NonggleTheme.colors.m1,
        disableBackGroundColor = NonggleTheme.colors.unactive,
        titleTextStyle = NonggleTheme.typography.t3
    )
}

enum class UserType {
    WORKER, MANAGER, NONE
}