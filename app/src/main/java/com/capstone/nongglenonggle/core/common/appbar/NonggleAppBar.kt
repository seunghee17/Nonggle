package com.capstone.nongglenonggle.core.common.appbar

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NonggleAppBar(
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit,
    title: @Composable () -> Unit,
    backgroundColor: Color = Color.White,
) {
    TopAppBar(
        modifier = modifier,
        title = title,
    )
}
