package com.capstone.nongglenonggle.presentation.view.worker.resume.compose_integration

import android.content.Context
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.capstone.nongglenonggle.R
import com.capstone.nongglenonggle.core.common.dialog.NonggleBottomSheet
import com.capstone.nongglenonggle.core.design_system.spoqahanSansneo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResumeCareerAddBottomSheet(
    modifier: Modifier = Modifier,
    context: Context,
    onDismissRequest: () -> Unit,
) {
    NonggleBottomSheet(
        onDismissRequest = onDismissRequest,
        content = {
            LazyColumn(
                modifier = Modifier.fillMaxHeight(0.7f),
            ) {
                item {
                    Row {
                        Text(
                            context.getString(R.string.경력추가하기),
                            style = TextStyle(
                                fontSize = 18.sp,
                                fontFamily = spoqahanSansneo,
                                fontWeight = FontWeight.Medium,
                                color = Color.Black
                            )
                        )
                        IconButton(
                            modifier = modifier.padding(top = 20.dp),
                            onClick = onDismissRequest,
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.close),
                                modifier = modifier.size(width = 24.dp, height = 24.dp),
                                contentDescription = null,
                            )
                        }
                    }
                }
            }
        }
    )
}