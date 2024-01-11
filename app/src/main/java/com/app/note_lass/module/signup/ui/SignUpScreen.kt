package com.app.note_lass.module.signup.ui

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.note_lass.R
import com.app.note_lass.module.signup.domain.presentation.RegistrationFormEvent
import com.app.note_lass.ui.component.DialogSignUp
import com.app.note_lass.ui.component.RectangleButtonWithStatus
import com.app.note_lass.ui.component.RectangleEnabledWithBorderButton
import com.app.note_lass.ui.component.RectangleUnableButton
import com.app.note_lass.ui.component.noRippleClickable
import com.app.note_lass.ui.theme.Gray50
import com.app.note_lass.ui.theme.NoteLassTheme
import com.app.note_lass.ui.theme.PrimarayBlue
import com.app.note_lass.ui.theme.PrimaryBlack
import com.app.note_lass.ui.theme.PrimaryGray
import com.app.note_lass.ui.theme.PrimaryGreen
import com.app.note_lass.ui.theme.PrimaryPink
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class,
    ExperimentalFoundationApi::class, ExperimentalFoundationApi::class
)
@Composable
fun SignUpScreen (
    signUpViewModel: AuthSharedViewModel,
    onBack : () -> Unit,
    GotoLogin : () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }

    val state = signUpViewModel.state

    val signupState = signUpViewModel.signupState
    val validateSuccess = signUpViewModel.emailValidateState
    val emailValue = remember {
        mutableStateOf("")
    }

    var emailValidationValue = remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var repeatedPassword by remember {
        mutableStateOf("")
    }
    var buttonFilled by remember {
        mutableStateOf(false)
    }
    val emailValidated = remember {
        mutableStateOf(false)
    }
    var validatedEmail = remember {
        mutableStateOf("")
    }

    var showDialog by remember {
        mutableStateOf(false)
    }
    val scrollState = rememberScrollState()

    val bringIntoViewRequester = BringIntoViewRequester()
    val coroutineScope = rememberCoroutineScope()

    val bringIntoViewRequester_2 = BringIntoViewRequester()
    val coroutineScope_2 = rememberCoroutineScope()
    val context = LocalContext.current

    buttonFilled =
        emailValue.value != "" && state.emailError == null && password != "" && state.passwordError == null
                && repeatedPassword != "" && state.repeatedPasswordError == null && emailValidated.value


    emailValidated.value = validateSuccess.value.isSuccess

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .verticalScroll(scrollState)
                .align(Alignment.Center)
                .fillMaxWidth(0.45f)
                .padding(top = 10.dp),
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


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
            ) {
                BasicTextField(
                    value = emailValue.value,
                    onValueChange = { it ->
                        emailValue.value = it
                        signUpViewModel.onEvent(RegistrationFormEvent.EmailChanged(emailValue.value))
                        emailValidated.value = it == validatedEmail.value
                    },
                    textStyle = NoteLassTheme.Typography.twenty_600_pretendard,
                    modifier = Modifier
                        .weight(4f)
                        .height(60.dp)
                        .padding(0.dp),
                ) {
                    TextFieldDefaults.TextFieldDecorationBox(
                        value = emailValue.value,
                        innerTextField = it,
                        singleLine = true,
                        enabled = true,
                        isError = state.emailError != null,
                        visualTransformation = VisualTransformation.None,
                        interactionSource = interactionSource,
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            errorContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            disabledContainerColor = Color.White,
                            focusedIndicatorColor = Color.Black,
                            unfocusedIndicatorColor = Gray50,
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
                    onClick = {
                        signUpViewModel.emailRequest(emailValue.value,isToast = {
                            Toast.makeText(context ,"인증번호가 발송되었습니다.",Toast.LENGTH_SHORT).show()
                        }
                        )
                              },
                    containerColor = Color.White,
                    textColor = PrimarayBlue,
                    borderColor = PrimarayBlue,
                )

            }
            if (state.emailError != null) {
                Text(
                    text = state.emailError,
                    style = NoteLassTheme.Typography.twelve_600_pretendard,
                    color = PrimaryPink,
                    modifier = Modifier.align(Alignment.Start)
                )
            }
            Spacer(modifier = Modifier.height(25.dp))

            Text(
                text = "인증번호",
                style = NoteLassTheme.Typography.twenty_700_pretendard,
                color = PrimaryBlack,
                modifier = Modifier.align(Alignment.Start)
            )

            BasicTextField(
                value = emailValidationValue.value,
                onValueChange = { it ->
                    emailValidationValue.value = it
                    signUpViewModel.onEvent(RegistrationFormEvent.ValidationChanged(emailValidationValue.value))
                },
                textStyle = NoteLassTheme.Typography.twenty_600_pretendard,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(0.dp),
            ) {
                TextFieldDefaults.TextFieldDecorationBox(
                    value = emailValidationValue.value,
                    innerTextField = it,
                    singleLine = true,
                    enabled = true,
                    visualTransformation = VisualTransformation.None,
                    interactionSource = interactionSource,
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.White,
                        focusedIndicatorColor = Color.Black,
                        unfocusedIndicatorColor = Gray50
                    ),
                    trailingIcon = {
                        if(!emailValidated.value) {
                            Icon(
                                painter = painterResource(id = R.drawable.signup_check_circle),
                                tint = PrimaryGray,
                                contentDescription = null,
                                modifier = Modifier.clickable {
                                    signUpViewModel.emailValidate(emailValue.value, emailValidationValue.value,
                                        isToast = {
                                            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                                        }
                                    )
                                }
                            )
                        }else{
                            Icon(
                                painter = painterResource(id = R.drawable.signup_check_circle),
                                tint = PrimaryGreen,
                                contentDescription = null,
                                modifier = Modifier.clickable {
                                    signUpViewModel.emailValidate(emailValue.value, emailValidationValue.value,
                                        isToast = {
                                            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                                        }
                                    )
                                }
                            )
                        }
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
                    if (state.repeatedPasswordError == null) signUpViewModel.onEvent(
                        RegistrationFormEvent.RepeatedPassWordChanged(repeatedPassword)
                    )
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

                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() })
            ) {
                TextFieldDefaults.TextFieldDecorationBox(
                    value = password,
                    innerTextField = it,
                    singleLine = true,
                    enabled = true,
                    isError = state.passwordError != null,
                    visualTransformation = VisualTransformation.None,
                    interactionSource = interactionSource,
                    placeholder = {
                        Text(
                            "영문,숫자, 특수기호 포함 8자리 이상",
                            style = NoteLassTheme.Typography.twenty_600_pretendard,
                            color = PrimaryGray
                        )
                    },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        errorContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        disabledContainerColor = Color.White,
                        focusedIndicatorColor = Color.Black,
                        unfocusedIndicatorColor = Gray50,
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

            if (state.passwordError != null) {
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
                    signUpViewModel.onEvent(
                        RegistrationFormEvent.RepeatedPassWordChanged(
                            repeatedPassword
                        )
                    )

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

                    },
            ) {

                TextFieldDefaults.TextFieldDecorationBox(
                    value = repeatedPassword,
                    innerTextField = it,
                    singleLine = true,
                    enabled = true,
                    visualTransformation = VisualTransformation.None,
                    isError = state.repeatedPasswordError != null,
                    interactionSource = interactionSource,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        errorContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        disabledContainerColor = Color.White,
                        focusedIndicatorColor = Color.Black,
                        unfocusedIndicatorColor = Gray50,

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

            if (state.repeatedPasswordError != null) {
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

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                RectangleButtonWithStatus(
                    text = "회원가입",
                    onClick = {
                        showDialog = true
                    },
                    isEnabled = buttonFilled
                )
            }

        }
    }

    BackHandler(onBack = {
        onBack()
    }
    )

    val schoolInfo = signupState.value.school
    val grade = signupState.value.grade
    val studentClass = signupState.value.studentClass
    val id = signupState.value.studentId
    val name = signupState.value.name


    if (showDialog) {
        DialogSignUp(
            setShowDialog = {
                showDialog = it
            },
            content = if(grade.isNotEmpty())schoolInfo + " " + grade + "학년 " + studentClass + "반 " + id + "번 " + "\n" +
                    name + "님이 맞습니까?" else schoolInfo + "\n"+ name + "님이 맞습니까?",
            onDecline = { showDialog = false },
            onAccept = {
                Log.e("signUp API",signupState.value.email)
                signupState.value = signupState.value.copy(
                    email = emailValue.value,
                    password = password
                )
                signUpViewModel.postSignUp(signupState)
                showDialog = false
            }
        )


    }
    if (signUpViewModel.signUpApiState.value.isSuccess) {
        Toast.makeText(context,"회원가입이 완료되었습니다",Toast.LENGTH_SHORT).show()
        GotoLogin()
    }


}



@Preview
@Composable
fun SignUpPreviewScreen(){
   // SignUpScreen()
}