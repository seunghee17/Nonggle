package com.capstone.nongglenonggle.presentation.view.worker.resume.compose_integration

import android.content.Context
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.capstone.nongglenonggle.R
import com.capstone.nongglenonggle.core.common.dialog.NonggleBottomSheet
import com.capstone.nongglenonggle.core.common.textfield.NonggleTextField
import com.capstone.nongglenonggle.core.common.textfield.TextFieldType
import com.capstone.nongglenonggle.core.design_system.NonggleTheme
import com.capstone.nongglenonggle.core.design_system.NongleTheme
import com.capstone.nongglenonggle.core.design_system.spoqahanSansneo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResumeCareerAddBottomSheet(
    modifier: Modifier = Modifier,
    viewModel: WorkerResumeComposeViewModel,
    context: Context,
    onDismissRequest: () -> Unit,
) {
    val uiState by viewModel.select { it.step2 }.collectAsStateWithLifecycle()

    NonggleBottomSheet(
        onDismissRequest = onDismissRequest,
        content = {
            LazyColumn(
                modifier = Modifier.fillMaxHeight(0.7f),
            ) {
                item {
                    Row(
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 20.dp)
                    ) {
                        Text(
                            context.getString(R.string.경력추가하기),
                            style = TextStyle(
                                fontSize = 18.sp,
                                fontFamily = spoqahanSansneo,
                                fontWeight = FontWeight.Medium,
                                color = Color.Black
                            )
                        )
                        Spacer(modifier = Modifier.weight(1f))
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
                    Spacer(modifier = Modifier.height(32.dp))
                    Text(
                        context.getString(R.string.경력소개),
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontFamily = spoqahanSansneo,
                            fontWeight = FontWeight.Medium,
                            color = NonggleTheme.colors.g1
                        )
                    )
                    NonggleTextField(
                        textFieldType = TextFieldType.Standard,
                        value = uiState.careerTextFieldValue,
                        onValueChange = {
                            viewModel.setEvent(WorkerResumeContract.Event.Step2.SetCareerTitle(it))
                        },
                        modifier = Modifier.padding(start = 4.dp, bottom = 16.dp, top = 16.dp),
                        placeholder = {
                            Text(
                                text = context.getString(R.string.경력을_소개해),
                                style = NonggleTheme.typography.b1_main)
                        },
                    )

                }
            }
        }
    )
}