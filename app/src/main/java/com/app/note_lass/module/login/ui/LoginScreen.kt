package com.app.note_lass.module.login.ui

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.note_lass.R
import com.app.note_lass.core.Proto.ProtoViewModel
import com.app.note_lass.ui.component.RectangleEnabledButton
import com.app.note_lass.ui.theme.Gray50
import com.app.note_lass.ui.theme.NoteLassTheme
import com.app.note_lass.ui.theme.PrimarayBlue
import com.app.note_lass.ui.theme.PrimaryGray

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onGoToSignUp : () -> Unit,
    onGoTOHome : () -> Unit ,
    onGoToReset : ()-> Unit,
    loginViewModel: LoginViewModel =  hiltViewModel(),
    tokenViewModel: ProtoViewModel = hiltViewModel()
){
    val emailText = remember  {
        mutableStateOf("")
    }

    val passwordText =  remember {
        mutableStateOf("")
    }
    val context = LocalContext.current
    val loginState =  loginViewModel.loginState

    LaunchedEffect(key1 = loginState.value){
        if(loginState.value.isError){
            Toast.makeText(context, "아이디와 비밀번호를 다시 확인해주세요",Toast.LENGTH_SHORT).show()
        }
    }
    //Log.e("passwordText",passwordText.value)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "태블릿 속 또 다른 강의실",
            style = TextStyle(
                fontSize = 22.sp,
                fontFamily = FontFamily(Font(R.font.pretendard_regular)),
                fontWeight = FontWeight(700),
                color = PrimarayBlue,
                textAlign = TextAlign.Center,
            )
        )

        Text(
            text = "Note-lass",
            style = TextStyle(
                fontSize = 48.sp,
                fontFamily = FontFamily(Font(R.font.sfprorounded_regular)),
                fontWeight = FontWeight(700),
                color = PrimarayBlue,
                textAlign = TextAlign.Center,
            )
        )

        Spacer(modifier = Modifier.height(72.dp))


        OutlinedTextField(
            value = emailText.value,
            onValueChange = {
                emailText.value = it
            },
            enabled= true ,
            modifier = Modifier
                .size(width = 400.dp, height = 56.dp),
            textStyle = NoteLassTheme.Typography.sixteem_600_pretendard,
            placeholder = {
                    Text(
                        text = "이메일을 입력해주세요",
                        color = Gray50
                    )
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.login_email_small),
                    tint = PrimarayBlue,
                    contentDescription = null
                )
            },
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color(0x80C9CDD2),
                focusedBorderColor = Color(0x80C9CDD2),
                unfocusedPlaceholderColor = Color.Unspecified,
                focusedPlaceholderColor = Color.Unspecified
            )
        )
        val interactionSource = remember { MutableInteractionSource() }

        Spacer(modifier = Modifier.height(30.dp))
        BasicTextField(
            value = passwordText.value,
            onValueChange = { it ->
                passwordText.value=  it
            },
            visualTransformation = PasswordVisualTransformation(),
            textStyle = NoteLassTheme.Typography.twenty_600_pretendard,
            modifier = Modifier
                .border(1.dp, color = Color(0x80C9CDD2), shape = RoundedCornerShape(8.dp))
                .size(width = 400.dp, height = 56.dp),
        ) {
            TextFieldDefaults.TextFieldDecorationBox(
                value = passwordText.value,
                innerTextField = it,
                singleLine = true,
                enabled = true,
                visualTransformation =  VisualTransformation.None,
                interactionSource = interactionSource,
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                leadingIcon = {
                    Icon(
                    painter = painterResource(id = R.drawable.login_password_small),
                    tint = PrimarayBlue,
                    contentDescription = null
                )
                },
                placeholder = {
                    Text(
                        text = "비밀번호를 입력해주세요",
                        color = Gray50
                    )
                },
                contentPadding = PaddingValues(0.dp),
                shape = RoundedCornerShape(8.dp)
            )

        }
//        OutlinedTextField(
//            value = passwordText.value,
//            modifier = Modifier
//                .border(1.dp, color = Color(0x80C9CDD2), shape = RoundedCornerShape(8.dp))
//                .size(width = 400.dp, height = 56.dp),
//            textStyle =
//            TextStyle(
//                fontSize = 20.sp,
//                fontFamily = FontFamily(Font(R.font.pretendard_regular)),
//                fontWeight = FontWeight(600),
//                color = Color(0xFF26282B),
//                ),
//            onValueChange = {
//               passwordText.value = it
//            },
//            placeholder = {
//                    Text(
//                        text = "비밀번호를 입력해주세요",
//                        color = Gray50
//                    )
//            },
//            leadingIcon = {
//                Icon(
//                    painter = painterResource(id = R.drawable.login_password_small),
//                    tint = PrimarayBlue,
//                    contentDescription = null
//                )
//            },
//            shape = RoundedCornerShape(8.dp),
//            colors = OutlinedTextFieldDefaults.colors(
//                unfocusedBorderColor = Color(0x80C9CDD2),
//                focusedBorderColor = Color(0x80C9CDD2),
//                focusedPlaceholderColor = Gray50,
//                unfocusedPlaceholderColor = Color.White
//            )
//        )
        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.width(360.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = "회원가입",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.pretendard_regular)),
                    fontWeight = FontWeight(600),
                    color = Color(0xFF9EA4AA),
                ),
                modifier = Modifier.clickable {
                    onGoToSignUp()
                }
            )
//            Text(
//                text = "|",
//                style = TextStyle(
//                    fontSize = 16.sp,
//                    fontFamily = FontFamily(Font(R.font.pretendard_regular)),
//                    fontWeight = FontWeight(600),
//                    color = Color(0xFF9EA4AA),
//                )
//            )
//            Text(
//                text = "아이디 찾기",
//                style = TextStyle(
//                    fontSize = 16.sp,
//                    fontFamily = FontFamily(Font(R.font.pretendard_regular)),
//                    fontWeight = FontWeight(600),
//                    color = Color(0xFF9EA4AA),
//                )
//            )
            Text(
                text = "|",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.pretendard_regular)),
                    fontWeight = FontWeight(600),
                    color = Color(0xFF9EA4AA),
                )
            )
            Text(
                text = "비밀번호 재설정",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.pretendard_regular)),
                    fontWeight = FontWeight(600),
                    color = Color(0xFF9EA4AA),
                ),
                modifier = Modifier.clickable {
                    onGoToReset()
                }
            )


        }
        Spacer(modifier = Modifier.height(40.dp))

        Box(
            modifier = Modifier
                .width(400.dp)
                .height(56.dp)
        ) {
            RectangleEnabledButton(text = "로그인 하기") {

               loginViewModel.login(emailText.value,passwordText.value,tokenViewModel)
            }
        }
    }

    if(loginState.value.isSuccess){
        onGoTOHome()
    }
    else{
    }




    }


@Preview
@Composable
fun LoginScreenPreview(){
   // LoginScreen()
}