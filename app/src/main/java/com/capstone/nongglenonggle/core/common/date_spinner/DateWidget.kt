package com.capstone.nongglenonggle.core.common.date_spinner

import android.view.ViewGroup
import android.widget.NumberPicker
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import java.time.LocalDate
import java.time.YearMonth
import kotlin.math.min

@Composable
fun DateSpinner(
    year: Int,
    month: Int,
    day: Int,
    onYearChange: (Int) -> Unit,
    onMonthChange: (Int) -> Unit,
    onDayChange: (Int) -> Unit,
    minDate: LocalDate = LocalDate.of(1900, 1, 1),
    maxDate: LocalDate = LocalDate.of(2100, 12, 31),
    zeroPadMonthDay: Boolean = true,
    pickerHeight: Dp = 160.dp,
    pickerSpacing: Dp = 12.dp
) {
    val minYear = minDate.year
    val maxYear = maxDate.year

    val monthMin = if (year == minYear) minDate.monthValue else 1
    val monthMax = if (year == maxYear) maxDate.monthValue else 12

    val daysInMonth = YearMonth.of(year, month).lengthOfMonth()
    val dayMin =
        if (year == minYear && month == minDate.monthValue) minDate.dayOfMonth else 1
    val dayMax =
        if (year == maxYear && month == maxDate.monthValue) min(maxDate.dayOfMonth, daysInMonth)
        else daysInMonth

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = pickerHeight),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(pickerSpacing)
    ) {
        // YEAR
        SpinnerNumberPicker(
            modifier = Modifier.weight(1f),
            value = year,
            range = minYear..maxYear,
            onValueChange = onYearChange,
            formatter = { "$it" },
        )

        // MONTH
        SpinnerNumberPicker(
            modifier = Modifier.weight(1f),
            value = month,
            range = monthMin..monthMax,
            onValueChange = onMonthChange,
            formatter = { if (zeroPadMonthDay) "%02d".format(it) else "$it" },
        )

        // DAY
        SpinnerNumberPicker(
            modifier = Modifier.weight(1f),
            value = day,
            range = dayMin..dayMax,
            onValueChange = onDayChange,
            formatter = { if (zeroPadMonthDay) "%02d".format(it) else "$it" },
        )
    }
}

@Composable
private fun SpinnerNumberPicker(
    modifier: Modifier = Modifier,
    value: Int,
    range: IntRange,
    onValueChange: (Int) -> Unit,
    formatter: (Int) -> String = { it.toString() },
) {
    val items = remember(range.first, range.last) {
        range.map(formatter).toTypedArray()
    }
    val currentIndex = (value - range.first).coerceIn(0, items.lastIndex)

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AndroidView(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp),
            factory = { context ->
                NumberPicker(context).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                    descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
                    wrapSelectorWheel = true
                    // 기본 세팅
                    minValue = 0
                    maxValue = items.lastIndex
                    displayedValues = items
                    this.value = currentIndex
                    setOnValueChangedListener { _, _, newIndex ->
                        onValueChange(range.first + newIndex)
                    }
                }
            },
            update = { picker ->
                val needsValues = picker.displayedValues?.contentEquals(items) != true
                if (needsValues) {
                    picker.minValue = 0
                    picker.maxValue = items.lastIndex
                    picker.displayedValues = null
                    picker.displayedValues = items
                }
                val newIndex = (value - range.first).coerceIn(0, items.lastIndex)
                if (picker.value != newIndex) {
                    picker.value = newIndex
                }
            }
        )
    }
}


