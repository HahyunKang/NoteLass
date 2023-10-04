package com.app.note_lass.module.signup.ui

import android.annotation.SuppressLint
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.note_lass.R
import com.app.note_lass.module.signup.domain.presentation.RegistrationFormEvent
import com.app.note_lass.ui.component.RectangleButtonWithStatus
import com.app.note_lass.ui.component.RectangleEnabledWithBorderButton
import com.app.note_lass.ui.component.RectangleUnableButton
import com.app.note_lass.ui.component.noRippleClickable
import com.app.note_lass.ui.theme.NoteLassTheme
import com.app.note_lass.ui.theme.PrimarayBlue
import com.app.note_lass.ui.theme.PrimaryBlack
import com.app.note_lass.ui.theme.PrimaryGray
import com.app.note_lass.ui.theme.PrimaryPink
import kotlinx.coroutines.launch

@SuppressLint("SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class,
    ExperimentalFoundationApi::class, ExperimentalFoundationApi::class
)
@Composable
fun SignUpScreen (
    signUpViewModel: AuthSharedViewModel,
    onBack : () -> Unit
){
    val interactionSource = remember { MutableInteractionSource() }

    val state = signUpViewModel.state

    val signupState = signUpViewModel.signupState

    var emailValue by remember {
        mutableStateOf("")
    }

    var emailValidationValue by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var repeatedPassword by remember {
        mutableStateOf("")
    }
    var buttonFilled by remember{
        mutableStateOf(false)
    }


    val scrollState= rememberScrollState()

    val bringIntoViewRequester = BringIntoViewRequester()
    val coroutineScope = rememberCoroutineScope()

    val bringIntoViewRequester_2 = BringIntoViewRequester()
    val coroutineScope_2 = rememberCoroutineScope()

    buttonFilled = emailValue!="" && state.emailError == null && password != "" && state.passwordError == null
                   && repeatedPassword != "" && state.repeatedPasswordError == null

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .verticalScroll(scrollState)
                .align(Alignment.Center)
                .fillMaxWidth(0.45f)
                .padding(top = 10.dp)
            ,
        ) {
            Text(
                text = "회원가입",
                style = NoteLassTheme.Typography.thritytwo_700_pretendard,
                color = PrimarayBlue,
                modifier = Modifier.align(Alignment.Start)
            )
            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "이메일 주소 입력",
                style = NoteLassTheme.Typography.twenty_700_pretendard,
                color = PrimaryBlack,
                modifier = Modifier.align(Alignment.Start)
            )


            Row(modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)) {
                BasicTextField(
                    value = emailValue,
                    onValueChange = { it ->
                        emailValue = it
                        signUpViewModel.onEvent(RegistrationFormEvent.EmailChanged(emailValue))
                    },
                    textStyle = NoteLassTheme.Typography.twenty_600_pretendard,
                    modifier = Modifier
                        .weight(4f)
                        .height(60.dp)
                        .padding(0.dp)
                    ,
                ) {
                    TextFieldDefaults.TextFieldDecorationBox(
                        value =emailValue,
                        innerTextField = it,
                        singleLine = true,
                        enabled = true,
                        visualTransformation = VisualTransformation.None,
                        interactionSource = interactionSource,
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = Color.White,
                            focusedIndicatorColor = Color.Black,
                        ),

                        placeholder = {
                            Text(
                                "이메일 주소를 입력해 주세요",
                                style = NoteLassTheme.Typography.twenty_600_pretendard,
                                color = PrimaryGray
                            )
                        },
                        contentPadding = PaddingValues(0.dp)
                    )

                }



                Spacer(modifier = Modifier.width(15.dp))

                RectangleEnabledWithBorderButton(
                    modifier = Modifier.weight(1f),
                    text = "전송",
                    onClick = { /*TODO*/ },
                    containerColor = Color.White,
                    textColor = PrimarayBlue,
                    borderColor = PrimarayBlue,
                )

            }
            if(state.emailError!= null){
            Text(text= state.emailError,
                style = NoteLassTheme.Typography.twelve_600_pretendard,
                color = PrimaryPink,
                modifier = Modifier.align(Alignment.Start))
        }
            Spacer(modifier = Modifier.height(25.dp))

            Text(
                text = "인증번호",
                style = NoteLassTheme.Typography.twenty_700_pretendard,
                color = PrimaryBlack,
                modifier = Modifier.align(Alignment.Start)
           )

            BasicTextField(
                value = emailValidationValue,
                onValueChange = { it ->
                    emailValidationValue= it
                },
                textStyle = NoteLassTheme.Typography.twenty_600_pretendard,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(0.dp),
            ) {
                TextFieldDefaults.TextFieldDecorationBox(
                    value =emailValidationValue,
                    innerTextField = it,
                    singleLine = true,
                    enabled = true,
                    visualTransformation = VisualTransformation.None,
                    interactionSource = interactionSource,
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.White,
                        focusedIndicatorColor = Color.Black,
                    ),
                    trailingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.signup_check_circle),
                            tint = PrimaryGray,
                            contentDescription = null
                        )
                    },
                    placeholder = {
                        Text(
                            "인증번호 6자리를 입력해 주세요",
                            style = NoteLassTheme.Typography.twenty_600_pretendard,
                            color = PrimaryGray
                        )
                    },
                    contentPadding = PaddingValues(0.dp)
                )

            }
            Spacer(modifier = Modifier.height(25.dp))

            val showPassword = remember { mutableStateOf(false) }

            Text(
                text = "비밀번호 입력",
                style = NoteLassTheme.Typography.twenty_700_pretendard,
                color = PrimaryBlack,
                modifier = Modifier.align(Alignment.Start)
            )
            val focusManager = LocalFocusManager.current
            BasicTextField(
                value = password,
                onValueChange = { it ->
                  password = it
                    signUpViewModel.onEvent(RegistrationFormEvent.PassWordChanged(password))
                   if(state.repeatedPasswordError==null) signUpViewModel.onEvent(
                       RegistrationFormEvent.RepeatedPassWordChanged(repeatedPassword))
                },
                visualTransformation = if (showPassword.value) VisualTransformation.None else PasswordVisualTransformation(),
                textStyle = NoteLassTheme.Typography.twenty_600_pretendard,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(0.dp)
                    .onFocusEvent {
                        coroutineScope.launch {
                            // This sends a request to all parents that asks them to scroll so
                            // that this item is brought into view.
                            Log.e("coroutine_test", "Coroutine")
                            if (it.isFocused) bringIntoViewRequester.bringIntoView()
                        }

                    },

                keyboardActions = KeyboardActions(onDone = {focusManager.clearFocus()})
            ) {
                TextFieldDefaults.TextFieldDecorationBox(
                    value =password,
                    innerTextField = it,
                    singleLine = true,
                    enabled = true,
                    visualTransformation = VisualTransformation.None,
                    interactionSource = interactionSource,
                    placeholder = {
                        Text(
                            "영문,숫자, 특수기호 포함 8자리 이상",
                            style = NoteLassTheme.Typography.fourteen_600_pretendard,
                            color = PrimaryGray
                        )
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.White,
                        focusedIndicatorColor = Color.Black,
                    ),
                    trailingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.signup_eye_slash),
                            tint = PrimaryGray,
                            contentDescription = null,
                            modifier = Modifier.noRippleClickable {
                                showPassword.value = !showPassword.value
                            }
                        )
                    },

                    contentPadding = PaddingValues(5.dp)
                )

            }

            if(state.passwordError!= null) {
                Text(
                    text = state.passwordError,
                    style = NoteLassTheme.Typography.twelve_600_pretendard,
                    color = PrimaryPink,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .bringIntoViewRequester(bringIntoViewRequester)
                )
            }

            Spacer(modifier = Modifier.height(25.dp))

            Text(
                text = "비밀번호 확인",
                style = NoteLassTheme.Typography.twenty_700_pretendard,
                color = PrimaryBlack,
                modifier = Modifier.align(Alignment.Start)
            )
            val showRepeatedPassword = remember { mutableStateOf(false) }

            BasicTextField(
                value = repeatedPassword,
                onValueChange = { it ->
                    repeatedPassword = it
                    signUpViewModel.onEvent(RegistrationFormEvent.RepeatedPassWordChanged(repeatedPassword))

                },
                visualTransformation = if (showRepeatedPassword.value) VisualTransformation.None else PasswordVisualTransformation(),
                textStyle = NoteLassTheme.Typography.twenty_600_pretendard,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .onFocusEvent {
                        coroutineScope_2.launch {
                            // This sends a request to all parents that asks them to scroll so
                            // that this item is brought into view.
                            Log.e("coroutine_test", "Coroutine_2")

                            if (it.isFocused) bringIntoViewRequester_2.bringIntoView()
                        }

                    }
                ,
            ) {

                TextFieldDefaults.TextFieldDecorationBox(
                    value = repeatedPassword,
                    innerTextField = it,
                    singleLine = true,
                    enabled = true,
                    visualTransformation = VisualTransformation.None,
                    interactionSource = interactionSource,
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.White,
                        focusedIndicatorColor = Color.Black,
                    ),
                    placeholder = {
                        Text(
                            "비밀번호를 한 번 더 입력해 주세요",
                            style = NoteLassTheme.Typography.twenty_600_pretendard,
                            color = PrimaryGray
                        )
                    },
                    trailingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.signup_eye_slash),
                            tint = PrimaryGray,
                            contentDescription = null,
                            modifier = Modifier.noRippleClickable {
                                showRepeatedPassword.value = !showRepeatedPassword.value
                            }
                        )
                    },
                    contentPadding = PaddingValues(5.dp)
                )

            }

            if(state.repeatedPasswordError!= null) {
                Text(
                    text = state.repeatedPasswordError,
                    style = NoteLassTheme.Typography.twelve_600_pretendard,
                    color = PrimaryPink,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .bringIntoViewRequester(bringIntoViewRequester_2)

                )
            }

            Spacer(modifier = Modifier.height(78.dp))

            Box(modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)) {
                RectangleButtonWithStatus(
                    text = "회원가입",
                    onClick = {
                              signUpViewModel.postSignUp(signupState)


                              },
                    isEnabled = buttonFilled)
//
//                RectangleUnableButton(text = "회원가입") {
//                    signupState.value = signupState.value.copy(
//                        email = emailValue,
//                        password = password
//                    )
//                }
            }

        }
    }

    BackHandler(onBack =  {
       onBack()
      }
    )
}

@Preview
@Composable
fun SignUpPreviewScreen(){
   // SignUpScreen()
}