package com.capstone.nongglenonggle.presentation.view.worker.resume.resume_step2.component

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.capstone.nongglenonggle.R
import com.capstone.nongglenonggle.core.common.button.FullButton
import com.capstone.nongglenonggle.core.common.date_spinner.DateSpinner
import com.capstone.nongglenonggle.core.design_system.NonggleTheme
import com.capstone.nongglenonggle.core.design_system.spoqahanSansneo
import java.time.LocalDate
import java.util.Date

@Composable
fun datePickerDialog(
    context: Context,
    onConfirm: (Date) -> Unit,
    onDismissRequest: () -> Unit,
    initialDate: LocalDate = LocalDate.now(),
    minDate: LocalDate = LocalDate.of(1900, 1, 1),
    maxDate: LocalDate = LocalDate.of(2100, 12, 31),
) {
    var year by rememberSaveable(initialDate) { mutableStateOf(initialDate.year) }
    var month by rememberSaveable(initialDate) { mutableStateOf(initialDate.monthValue) }

    val minYear = minDate.year
    val maxYear = maxDate.year

    // 선택된 연도에 따른 월 범위 동적 제한
    val monthMin = if (year == minYear) minDate.monthValue else 1
    val monthMax = if (year == maxYear) maxDate.monthValue else 12
    month = month.coerceIn(monthMin, monthMax)

    Dialog(
        onDismissRequest = { onDismissRequest() },
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
        )
    ) {
        Card(
            shape = RoundedCornerShape(8.dp),
        )
        {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    modifier = Modifier.padding(start = 20.dp, bottom = 32.dp),
                    text = context.getString(R.string.년_월_선택),
                    style = TextStyle(
                        fontFamily = spoqahanSansneo,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black,
                        fontSize = 18.sp
                    )
                )
                DateSpinner(
                    year = year,
                    month = month,
                    onYearChange = { year = it },
                    onMonthChange = { month = it },
                    onDayChange = {},
                    day = 0,
                )
                Spacer(Modifier.height(16.dp))
                Row(
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .fillMaxWidth()
                ) {
                    FullButton(
                        modifier = Modifier.weight(weight = 0.5f),
                        onClick = {
                            onDismissRequest()
                        },
                        titleText = context.getString(R.string.취소),
                        backgroundColor = NonggleTheme.colors.g4,
                        titleTextStyle = TextStyle(
                            fontFamily = spoqahanSansneo,
                            fontWeight = FontWeight.Medium,
                            fontSize = 16.sp,
                            color = NonggleTheme.colors.g1
                        ),
                        enabled = true
                    )
                    FullButton(
                        modifier = Modifier.weight(weight = 0.5f),
                        onClick = {
                            onConfirm(Date(year, month, 0))
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
            }
        }
    }
}