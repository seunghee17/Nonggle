package com.capstone.nongglenonggle.core.common.appbar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import java.time.format.TextStyle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NonggleAppBar(
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit,
    title: @Composable () -> Unit,
    titleColor: Color = Color.Black,
    backgroundColor: Color = Color.White,
) {
    TopAppBar(
        modifier = modifier,
        title = title,
        colors = TopAppBarColors(
            containerColor = backgroundColor,
            scrolledContainerColor = backgroundColor,
            navigationIconContentColor = Color.Black,
            titleContentColor = titleColor,
            actionIconContentColor = Color.Black,
        ),
        navigationIcon = {
            IconButton(onClick = onBackPressed) {
                Icon(Icons.Filled.ArrowBack, null)
            }
        },

    )
}
