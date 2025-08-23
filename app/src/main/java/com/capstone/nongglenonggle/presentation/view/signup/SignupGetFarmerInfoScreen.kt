package com.capstone.nongglenonggle.presentation.view.signup

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.capstone.nongglenonggle.R
import com.capstone.nongglenonggle.app.Screens
import com.capstone.nongglenonggle.core.common.appbar.NonggleAppBar
import com.capstone.nongglenonggle.core.common.button.NonggleIconButton
import com.capstone.nongglenonggle.core.common.button.stepButton
import com.capstone.nongglenonggle.core.common.textfield.NonggleTextField
import com.capstone.nongglenonggle.core.common.textfield.TextFieldType
import com.capstone.nongglenonggle.core.design_system.NonggleTheme
import com.capstone.nongglenonggle.core.noRippleClickable

@Composable
fun SignupGetFarmerInfoScreen(
    navController: NavHostController,
    viewModel: SignupViewModel,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val effectFlow = viewModel.effect
    val context = LocalContext.current

    LaunchedEffect(true) {
        effectFlow.collect { effect ->
            when (effect) {
                is SignupContract.Effect.NavigateToHomeScreen -> {
                }

                is SignupContract.Effect.NavigateToAddressSearchScreen -> {
                    navController.navigate(Screens.Signup.AddressSearchWebView.route)
                }

                else -> {}
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 72.dp)
        ) {
            item {
                NonggleAppBar(
                    onBackPressed = { navController.popBackStack() },
                    title = {},
                )
                Text(
                    text = context.getString(R.string.sign_up_Title),
                    modifier = Modifier.padding(start = 20.dp, end = 20.dp),
                    style = NonggleTheme.typography.TextInputEditTextStyle,
                    color = NonggleTheme.colors.g2,
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(start = 20.dp, end = 20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.padding(top = 10.dp),
                        text = context.getString(R.string.sign_up_Farmer_InfoTitle),
                        fontWeight = FontWeight.W400,
                        fontSize = 24.sp,
                        color = Color(0xFF1E1E1E),
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Row(
                        verticalAlignment = Alignment.Bottom
                    ) {
                        stepButton("1", SignupStep.STEP3.stepNum.toString())
                        Spacer(modifier = Modifier.width(10.dp))
                        stepButton("2", SignupStep.STEP3.stepNum.toString())
                        Spacer(modifier = Modifier.width(10.dp))
                        stepButton("3", SignupStep.STEP3.stepNum.toString())
                    }
                }
                Text(
                    modifier = Modifier
                        .padding(top = 32.dp, start = 20.dp, end = 20.dp),
                    text = context.getString(R.string.sign_up_Farmer_AddressInfoSubTitle),
                    style = NonggleTheme.typography.TextInputEditTextStyle,
                    color = Color.Black,
                )
                Text(
                    modifier = Modifier
                        .padding(top = 8.dp, start = 20.dp, end = 20.dp),
                    text = context.getString(R.string.sign_up_Farmer_MathchingDescriptionSubTitle),
                    style = NonggleTheme.typography.TextInputEditTextStyle.copy(fontSize = 14.sp),
                    color = NonggleTheme.colors.g2
                )
                setUserAddressDoroTextField(
                    context = context,
                    modifier = Modifier.padding(top = 20.dp, start = 10.dp, end = 10.dp),
                    doroAddress = uiState.farmerAddressSearchFromDoro,
                    navigateToSearchAddress = {
                        viewModel.navigateToAddressScreen()
                    }
                )
                setUserAddressDetailTextField(
                    context = context,
                    modifier = Modifier.padding(top = 10.dp, start = 10.dp, end = 10.dp),
                    doroAddressDetail = uiState.farmerAddressDeatail,
                    clearValueAction = {
                        viewModel.setEvent(SignupContract.Event.ClearFarmerAddressDetail)
                    },
                    onValueChange = {
                        viewModel.setEvent(SignupContract.Event.InputFarmerAddressDetail(it))
                    },
                )
                Text(
                    modifier = Modifier.padding(top = 32.dp, start = 20.dp, end = 20.dp),
                    text = context.getString(R.string.sign_up_Farmer_ChoiceCropTitle),
                    style = NonggleTheme.typography.TextInputEditTextStyle,
                    color = Color.Black
                )
                Text(
                    modifier = Modifier.padding(
                        top = 8.dp,
                        bottom = 20.dp,
                        start = 20.dp,
                        end = 20.dp
                    ),
                    text = context.getString(R.string.sign_up_Farmer_ChoiceCropSubTitle),
                    style = NonggleTheme.typography.TextInputEditTextStyle,
                    color = NonggleTheme.colors.g2
                )
                LazyVerticalGrid(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 200.dp)
                        .padding(start = 20.dp, end = 20.dp),
                    columns = GridCells.Fixed(3),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    items(
                        count = uiState.farmerCategory.size,
                    ) { index ->
                        cropsInfo(
                            onClick = {
                                viewModel.setEvent(SignupContract.Event.SelectFarmerCategory(uiState.farmerCategory[index]))
                            },
                            cropItem = uiState.farmerCategory[index],
                            selectCropList = uiState.selectedFarmerCategory
                        )
                    }
                }
            }
        }
        nextBtn(
            context = context,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .align(Alignment.BottomCenter),
            enable = true,
            onClick = {
                viewModel.setEvent(SignupContract.Event.navigateToHomeButton)
            }
        )
    }
}

@Composable
fun setUserAddressDoroTextField(
    modifier: Modifier = Modifier,
    context: Context,
    doroAddress: String,
    navigateToSearchAddress: () -> Unit,
) {
    NonggleTextField(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .noRippleClickable {
                navigateToSearchAddress()
            },
        textFieldType = TextFieldType.Standard,
        value = doroAddress,
        onValueChange = {},
        textStyle = NonggleTheme.typography.TextInputEditTextStyle,
        textColor = Color(0xFF1E1E1E),
        enabled = false,
        placeholder = {
            Text(
                text = context.getString(R.string.sign_up_Farmer_AddressInputTextFieldHintText),
                style = NonggleTheme.typography.HintTextAppearance,
                color = NonggleTheme.colors.g3,
            )
        },
        label = {
            Text(
                modifier = modifier,
                text = context.getString(R.string.sign_up_Farmer_AddressInputTextFieldLabel),
                style = NonggleTheme.typography.b2_sub,
                color = NonggleTheme.colors.g1,
            )
        }
    )
}

@Composable
fun setUserAddressDetailTextField(
    context: Context,
    modifier: Modifier = Modifier,
    doroAddressDetail: String,
    clearValueAction: () -> Unit,
    onValueChange: (String) -> Unit,
) {
    var isTextFieldFocused by rememberSaveable { mutableStateOf(false) }

    NonggleTextField(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .onFocusChanged { focusState -> isTextFieldFocused = focusState.isFocused },
        textFieldType = TextFieldType.Standard,
        value = doroAddressDetail,
        onValueChange = onValueChange,
        textStyle = NonggleTheme.typography.TextInputEditTextStyle,
        textColor = Color(0xFF1E1E1E),
        trailingIcon = {
            if (doroAddressDetail.isNotEmpty() && isTextFieldFocused) {
                NonggleIconButton(
                    ImageResourceId = R.drawable.xcircle,
                    onClick = clearValueAction
                )
            }
        },

        placeholder = {
            Text(
                text = context.getString(R.string.sign_up_Farmer_AddressDetailInfoInputTextFieldHintText),
                style = NonggleTheme.typography.HintTextAppearance,
                color = NonggleTheme.colors.g3,
            )
        },
    )
}

@Composable
fun cropsInfo(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    cropItem: String,
    selectCropList: List<String>
) {
    OutlinedCard(
        modifier = modifier
            .height(48.dp)
            .fillMaxWidth()
            .noRippleClickable {
                onClick()
            },
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        border = BorderStroke(
            width = 1.dp,
            color = if (selectCropList.contains(cropItem)) NonggleTheme.colors.m2 else NonggleTheme.colors.g_line
        )
    ) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier.padding(horizontal = 18.dp),
                textAlign = TextAlign.Center,
                text = cropItem,
                style = NonggleTheme.typography.b1_main,
                color = if (selectCropList.contains(cropItem)) NonggleTheme.colors.m1 else NonggleTheme.colors.g3,
            )
        }
    }
}