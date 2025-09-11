package com.capstone.nongglenonggle.presentation.view.worker.resume.resume_step1.component

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.capstone.nongglenonggle.R
import com.capstone.nongglenonggle.core.common.button.FullButton
import com.capstone.nongglenonggle.core.common.button.OutlinedButton
import com.capstone.nongglenonggle.core.common.date_spinner.DateSpinner
import com.capstone.nongglenonggle.core.common.dialog.NonggleBottomSheet
import com.capstone.nongglenonggle.core.design_system.NonggleTheme
import com.capstone.nongglenonggle.core.design_system.spoqahanSansneo
import java.time.LocalDate
import java.time.YearMonth
import java.util.Date
import kotlin.math.min

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun dateSpinnerBottomSheet(
    onConfirm: (Date) -> Unit,
    onDismissRequest: () -> Unit,
    context: Context,
    initialDate: LocalDate = LocalDate.now(),
    minDate: LocalDate = LocalDate.of(1900, 1, 1),
    maxDate: LocalDate = LocalDate.of(2100, 12, 31),
) {
    var year by rememberSaveable(initialDate) { mutableStateOf(initialDate.year) }
    var month by rememberSaveable(initialDate) { mutableStateOf(initialDate.monthValue) }
    var day by rememberSaveable(initialDate) { mutableStateOf(initialDate.dayOfMonth) }

    val minYear = minDate.year
    val maxYear = maxDate.year

    // 선택된 연도에 따른 월 범위 동적 제한
    val monthMin = if (year == minYear) minDate.monthValue else 1
    val monthMax = if (year == maxYear) maxDate.monthValue else 12
    month = month.coerceIn(monthMin, monthMax)

    val daysInMonth = YearMonth.of(year, month).lengthOfMonth()
    val dayMin =
        if (year == minYear && month == minDate.monthValue) minDate.dayOfMonth else 1
    val dayMax =
        if (year == maxYear && month == maxDate.monthValue) min(maxDate.dayOfMonth, daysInMonth)
        else daysInMonth
    day = day.coerceIn(dayMin, dayMax)

    NonggleBottomSheet(
        occupyWeight = 0.5f,
        onDismissRequest = onDismissRequest,
        header = {
            Row(
                modifier = Modifier.padding(top = 16.dp, start = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = context.getString(R.string.날짜),
                    style = TextStyle(
                        fontFamily = spoqahanSansneo,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black,
                        fontSize = 18.sp
                    )
                )
                Spacer(modifier = Modifier.weight(1f))
                Image(
                    modifier = Modifier
                        .size(24.dp)
                        .padding(end = 20.dp)
                        .clickable { onDismissRequest() },
                    painter = painterResource(id = R.drawable.close),
                    contentDescription = null
                )
            }
        },
        bodyContent = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .imePadding()
            ) {
                // 스피너는 화면별로 제공하신 DateSpinner 사용
                DateSpinner(
                    year = year,
                    month = month,
                    day = day,
                    onYearChange = { year = it },
                    onMonthChange = { month = it },
                    onDayChange = { day = it },
                    minDate = minDate,
                    maxDate = maxDate
                )

                Spacer(Modifier.height(16.dp))
            }
        },
        footer = {
            FullButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    onConfirm(Date(year, month, day))
                    onDismissRequest()
                },
                titleText = context.getString(R.string.확인),
                titleTextStyle = TextStyle(
                    fontFamily = spoqahanSansneo,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    color = Color.White
                ),
                enabled = true
            )
        }
    )
}

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
    selectedGender: String
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
        enableColor = if (selectedGender == gender) NonggleTheme.colors.m1 else NonggleTheme.colors.g_line,
        enableContentColor = if (selectedGender == gender) NonggleTheme.colors.m1 else NonggleTheme.colors.g3,
        pressedColor = NonggleTheme.colors.m1,
    )
}

@Composable
fun certificationButton(
    modifier: Modifier = Modifier,
    title: String,
    changeCertificateState: () -> Unit,
    certificateAvailable: Boolean
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
        enableColor = if (certificateAvailable && title == "있음" || !certificateAvailable && title == "없음") NonggleTheme.colors.m1 else NonggleTheme.colors.g_line,
        enableContentColor = if (certificateAvailable && title == "있음" || !certificateAvailable && title == "없음") NonggleTheme.colors.m1 else NonggleTheme.colors.g3,
        pressedColor = NonggleTheme.colors.m1,
    )
}

