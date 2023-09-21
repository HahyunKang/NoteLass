package com.app.note_lass.module.login.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.note_lass.R
import com.app.note_lass.ui.component.RectangleEnabledButton
import com.app.note_lass.ui.theme.PrimarayBlue

@Composable
fun LoginScreen(){

    Column(
        modifier = Modifier.fillMaxSize(),
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
        var emailText by remember {
            mutableStateOf("")
        }

        OutlinedTextField(
            value = emailText,
            modifier = Modifier
                .border(1.dp, color = Color(0x80C9CDD2), shape = RoundedCornerShape(8.dp))
                .size(width = 400.dp, height = 56.dp),
            textStyle =
            TextStyle(
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(R.font.pretendard_regular)),
                fontWeight = FontWeight(600),
                color = Color(0xFF26282B),

                ),

            onValueChange = {
                emailText = it
            },
            placeholder = {
                Text(
                    text = "이메일을 입력해주세요",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.pretendard_regular)),
                        fontWeight = FontWeight(600),
                        color = Color(0xFFC9CDD2),
                    )
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
                focusedBorderColor = Color(0x80C9CDD2)
            )
        )
        Spacer(modifier = Modifier.height(30.dp))

        OutlinedTextField(
            value = emailText,
            modifier = Modifier
                .border(1.dp, color = Color(0x80C9CDD2), shape = RoundedCornerShape(8.dp))
                .size(width = 400.dp, height = 56.dp),
            textStyle =
            TextStyle(
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(R.font.pretendard_regular)),
                fontWeight = FontWeight(600),
                color = Color(0xFF26282B),

                ),

            onValueChange = {
                emailText = it
            },
            placeholder = {
                Text(
                    text = "비밀번호를 입력해주세요",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.pretendard_regular)),
                        fontWeight = FontWeight(600),
                        color = Color(0xFFC9CDD2),
                    )
                )
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.login_password_small),
                    tint = PrimarayBlue,
                    contentDescription = null
                )
            },
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color(0x80C9CDD2),
                focusedBorderColor = Color(0x80C9CDD2)
            )
        )
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
                )
            )
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
                text = "아이디 찾기",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.pretendard_regular)),
                    fontWeight = FontWeight(600),
                    color = Color(0xFF9EA4AA),
                )
            )
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
                text = "비밀번호 찾기",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.pretendard_regular)),
                    fontWeight = FontWeight(600),
                    color = Color(0xFF9EA4AA),
                )
            )


        }
        Spacer(modifier = Modifier.height(40.dp))

        Box(
            modifier = Modifier
                .width(400.dp)
                .height(56.dp)
        ) {
            RectangleEnabledButton(text = "로그인 하기") {
                { }
            }
        }
    }




    }


@Preview
@Composable
fun LoginScreenPreview(){
    LoginScreen()
}