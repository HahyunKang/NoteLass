package com.app.note_lass.module.signup.ui

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
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.note_lass.R
import com.app.note_lass.core.FilePicker.getFileName
import com.app.note_lass.ui.component.CheckBox
import com.app.note_lass.ui.component.DropDownMenu
import com.app.note_lass.ui.component.RectangleButtonWithStatus
import com.app.note_lass.ui.theme.NoteLassTheme
import com.app.note_lass.ui.theme.PrimarayBlue
import com.app.note_lass.ui.theme.PrimaryBlack
import com.app.note_lass.ui.theme.PrimaryGray

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentInfoScreen(
    viewModel : AuthSharedViewModel = hiltViewModel(),
    GotoNext : () -> Unit
){
    val signupState = viewModel.signupState

    var nameValue by remember{
        mutableStateOf("")
    }
    var isStudent by remember{
        mutableStateOf(false)
    }
    var isChecked by remember{
        mutableStateOf(false)
    }
    var gradeFilled by remember{
        mutableStateOf(false)
    }
    var classFilled by remember{
        mutableStateOf(false)
    }
    var idFilled by remember{
        mutableStateOf(false)
    }
    var buttonFilled by remember{
        mutableStateOf(false)
    }

    buttonFilled = if(isStudent) isChecked && nameValue != "" && gradeFilled && classFilled && idFilled
                  else isChecked && nameValue != ""
    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth(0.45f)
                .padding(top = 10.dp)
        ) {

            Text(
                text =  "정보 입력",
                style = NoteLassTheme.Typography.thritytwo_700_pretendard,
                color = PrimarayBlue,
                modifier = Modifier.align(Alignment.Start)
            )
            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "신분 선택",
                style = NoteLassTheme.Typography.twenty_700_pretendard,
                color = PrimaryBlack,
                modifier = Modifier.align(Alignment.Start)
            )
            Spacer(modifier = Modifier.height(15.dp))

            CheckBox(
                isChecked = {
                    isChecked = it
                },
                onGetRole = {
                    signupState.value = signupState.value.copy(
                        role = it
                    )
                    if(it == "STUDENT") isStudent = true
                }
            )

            Spacer(modifier = Modifier.height(25.dp))



            val gradeList = listOf("1","2","3")
            val classList = listOf("1","2","3","4","5")
            val idList = (1..31).map { it.toString() }
            val dropDown = R.drawable.arrow_down


            if(isStudent) {

                Text(
                    text = "반,번호 입력",
                    style = NoteLassTheme.Typography.twenty_700_pretendard,
                    color = PrimaryBlack,
                    modifier = Modifier.align(Alignment.Start)
                )

                Spacer(modifier = Modifier.height(15.dp))

                Row(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier
                            .weight(1f)
                            .padding(2.dp)
                    ) {
                        Box(modifier = Modifier.weight(2f)) {
                            DropDownMenu(
                                menuList = gradeList,
                                iconDown = dropDown,
                                iconUp = dropDown,
                                placeHolder = "1",
                                isSelected = {
                                    gradeFilled = it
                                },
                                onGetInfo = {
                                    signupState.value = signupState.value.copy(
                                        grade = it
                                    )
                                }
                            )
                        }
                        Spacer(modifier = Modifier.width(6.dp))

                        Text(
                            text = "학년",
                            style = NoteLassTheme.Typography.twenty_700_pretendard,
                            color = PrimaryBlack,
                            modifier = Modifier
                                .weight(1f)
                                .align(Alignment.CenterVertically)
                        )

                    }

                    Row(modifier = Modifier.weight(1f)) {
                        Box(modifier = Modifier.weight(2f)) {
                            DropDownMenu(
                                menuList = classList,
                                iconDown = dropDown,
                                iconUp = dropDown,
                                placeHolder = "1",
                                isSelected = {
                                    classFilled= it
                                },
                                onGetInfo = {
                                    signupState.value.studentClass = it
                                }
                            )
                        }
                        Spacer(modifier = Modifier.width(6.dp))

                        Text(
                            text = "반",
                            style = NoteLassTheme.Typography.twenty_700_pretendard,
                            color = PrimaryBlack,
                            modifier = Modifier
                                .weight(1f)
                                .align(Alignment.CenterVertically)
                        )

                    }

                    Row(modifier = Modifier.weight(1f)) {
                        Box(modifier = Modifier.weight(2f)) {
                            DropDownMenu(
                                menuList = idList,
                                iconDown = dropDown,
                                iconUp = dropDown,
                                placeHolder = "1",
                                isSelected = {
                                    idFilled = it
                                },
                                onGetInfo = {
                                    signupState.value = signupState.value.copy(
                                        studentId = it
                                    )
                                }
                            )
                        }
                        Spacer(modifier = Modifier.width(6.dp))

                        Text(
                            text = "번",
                            style = NoteLassTheme.Typography.twenty_700_pretendard,
                            color = PrimaryBlack,
                            modifier = Modifier
                                .weight(1f)
                                .align(Alignment.CenterVertically)
                        )

                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "이름 입력",
                style = NoteLassTheme.Typography.twenty_700_pretendard,
                color = PrimaryBlack,
                modifier = Modifier.align(Alignment.Start)
            )
            Spacer(modifier = Modifier.height(10.dp))

            val interactionSource = remember { MutableInteractionSource() }

            BasicTextField(
                value = nameValue,
                onValueChange = { it ->
                    nameValue= it
                },
                textStyle = NoteLassTheme.Typography.twenty_600_pretendard,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(0.dp),
            ) {
                TextFieldDefaults.TextFieldDecorationBox(
                    value = nameValue,
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
                            "이름을 입력해주세요.",
                            style = NoteLassTheme.Typography.twenty_600_pretendard,
                            color = PrimaryGray
                        )
                    },
                    contentPadding = PaddingValues(0.dp)
                )

            }
            Spacer(modifier = Modifier.height(207.dp))

            Box(modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)) {
                RectangleButtonWithStatus(
                    text = "다음",
                    onClick =
                    {
                        signupState.value = signupState.value.copy(
                            name = nameValue
                        )
                        GotoNext()
                    },
                    isEnabled = buttonFilled
                )
            }


        }



        }

}