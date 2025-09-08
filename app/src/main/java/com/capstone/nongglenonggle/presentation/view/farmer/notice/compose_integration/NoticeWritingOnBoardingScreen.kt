package com.capstone.nongglenonggle.presentation.view.farmer.notice.compose_integration

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.capstone.nongglenonggle.R
import com.capstone.nongglenonggle.core.common.appbar.NonggleAppBar
import com.capstone.nongglenonggle.core.common.button.FullButton
import com.capstone.nongglenonggle.core.design_system.NonggleTheme
import com.capstone.nongglenonggle.core.design_system.spoqahanSansneo

@Composable
fun NoticeWritingOnBoardingScreen() {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        NonggleAppBar(
            onBackPressed = {  },
            closeAction = true,
            title = {
                Text(
                    text = context.getString(R.string.공고쓰기),
                    style = TextStyle(
                        fontSize = 16.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Normal,
                        fontFamily = spoqahanSansneo
                    )
                )
            },
        )
        Spacer(modifier = Modifier.weight(1f))
        Image(
            modifier = Modifier.fillMaxWidth(),
            painter = painterResource(R.drawable.notice_guide),
            contentDescription = null
        )
        Text(
            modifier = Modifier.padding(top = 30.dp),
            text = context.getString(R.string.공고글_작성은),
            style = TextStyle(
                fontFamily = spoqahanSansneo,
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal,
                color = NonggleTheme.colors.g2
            )
        )
        Text(
            modifier = Modifier.padding(top = 16.dp),
            text = context.getString(R.string.모든_항목을),
            style = TextStyle(
                fontFamily = spoqahanSansneo,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                lineHeight = 5.sp,
                color = NonggleTheme.colors.g2
            )
        )
        Spacer(modifier = Modifier.weight(1f))
        FullButton(
            modifier = Modifier
                .fillMaxWidth(),
            enabled = true,
            onClick = {},
            titleText = context.getString(R.string.공고글_작성하러_가기),
            titleTextStyle = NonggleTheme.typography.t3.copy(color = Color.White),
        )
    }
}