package com.app.note_lass.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextFieldDefaults

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.compose.ui.window.Dialog
import com.app.note_lass.R
import com.app.note_lass.module.group.data.applicationList.ApplicationStudent
import com.app.note_lass.module.group.ui.component.ApplicationStudent
import com.app.note_lass.ui.theme.Gray50
import com.app.note_lass.ui.theme.NoteLassTheme
import com.app.note_lass.ui.theme.PrimarayBlue
import com.app.note_lass.ui.theme.PrimaryBlack
import com.app.note_lass.ui.theme.PrimaryPurple


@Composable
fun DialogSignUp(
    setShowDialog : (Boolean)-> Unit,
    content : String,
    onDecline : () -> Unit,
    onAccept : () -> Unit
) {

    Dialog(
        onDismissRequest = { setShowDialog(false) }
    ) {

        Box(
            modifier = Modifier
                .size(width = 480.dp, height = 288.dp)
                .background(color = Color.White, shape = RoundedCornerShape(12.dp))
        ){
            Icon(
                painter= painterResource(id = R.drawable.group_filedelete_small),
                tint =Color(0xFF26282B),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(24.dp)
            )

            Column(modifier = Modifier
                .fillMaxSize()
                .padding(top = 70.dp, bottom = 40.dp, start = 16.dp, end = 15.dp),
                horizontalAlignment = Alignment.CenterHorizontally) {

                Text(text= content, style = NoteLassTheme.Typography.twenty_700_pretendard,color = PrimaryBlack)

                Spacer(modifier = Modifier.height(64.dp))

                Row(modifier = Modifier
                    .fillMaxWidth()
                    .height(58.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ){
                    Box(modifier = Modifier.weight(1f)){
                        RectangleUnableButton(
                            text ="아니오"
                        ) {
                         onDecline()   
                        }
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Box(modifier = Modifier.weight(1f)){
                        RectangleEnabledButton(
                            text ="네"
                        ) {
                            onAccept()
                        }
                    }
                }
            }

        }
    }
}

@Composable
fun CreateGroup(
    setShowDialog : (Boolean)-> Unit,
    getInfo : (String,String,String) -> Unit,
    onClickNext : () -> Unit
) {
    val gradeList = listOf("1","2","3")
    val classList = listOf("1","2","3","4","5")
    val subjectList = listOf("국어","수학","영어","과학","사회","음악","체육")

    val gradeInfo = remember {
        mutableStateOf("1")
    }
    val classInfo = remember {
        mutableStateOf("1")
    }
    val subjectInfo = remember {
        mutableStateOf("국어")
    }

    val gradeFilled = remember {
        mutableStateOf(false)
    }
    val classFilled = remember {
        mutableStateOf(false)
    }
    val subjectFilled = remember {
        mutableStateOf(false)
    }

    Dialog(
        onDismissRequest = { setShowDialog(false) }
    ) {

        Column(
            modifier = Modifier
                .background(color = Color.White, shape = RoundedCornerShape(12.dp))
                .size(width = 480.dp, height = 544.dp)
                .padding(horizontal = 40.dp, vertical = 25.dp)
        ){
            Icon(
                painter= painterResource(id = R.drawable.group_filedelete_small),
                tint =Color(0xFF26282B),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.End)
            )

            Text(
                text = "대상 학년 선택",
                style = NoteLassTheme.Typography.twenty_700_pretendard,
                color = PrimaryBlack,
                modifier = Modifier.align(Alignment.Start)
            )
            Spacer(modifier = Modifier.height(16.dp))

            DropDownMenu(
                menuList = gradeList ,
                iconDown = R.drawable.arrow_down,
                iconUp = R.drawable.arrow_down,
                placeHolder = "대상 학년을 입력해주세요",
                isSelected =  {
                    gradeFilled.value = true
                },
                onGetInfo = {
                    gradeInfo.value = it
                }
            )
            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "대상 반 선택",
                style = NoteLassTheme.Typography.twenty_700_pretendard,
                color = PrimaryBlack,
                modifier = Modifier.align(Alignment.Start)
            )
            Spacer(modifier = Modifier.height(16.dp))

            DropDownMenu(
                menuList = classList ,
                iconDown = R.drawable.arrow_down,
                iconUp = R.drawable.arrow_down,
                placeHolder = "대상 반을 입력해주세요",
                isSelected =  {
                    classFilled.value = true
                },
                onGetInfo = {
                    classInfo.value = it
                }
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "대상 과목 선택",
                style = NoteLassTheme.Typography.twenty_700_pretendard,
                color = PrimaryBlack,
                modifier = Modifier.align(Alignment.Start)
            )
            Spacer(modifier = Modifier.height(16.dp))

            DropDownMenu(
                menuList = subjectList ,
                iconDown = R.drawable.arrow_down,
                iconUp = R.drawable.arrow_down,
                placeHolder = "대상 과목을 입력해주세요",
                isSelected =  {
                    subjectFilled.value = true
                },
                onGetInfo = {
                    subjectInfo.value = it
                }
            )
            Spacer(modifier = Modifier.height(56.dp))

            Box(
                modifier = Modifier
                    .height(56.dp)
                    .width(400.dp)
            ) {
                RectangleButtonWithStatus(
                    text = "다음",
                    onClick = {
                              getInfo(gradeInfo.value,classInfo.value,subjectInfo.value)
                              onClickNext()
                    },
                    isEnabled = subjectFilled.value && gradeFilled.value && classFilled.value
                )

            }
        }
    }
}

@Composable
fun DialogGroupCode(
    setShowDialog : (Boolean)-> Unit,
    code : Int,
    onAccept : () -> Unit
) {

    Dialog(
        onDismissRequest = { setShowDialog(false) }
    ) {

        Column(
            modifier = Modifier
                .size(width = 480.dp, height = 544.dp)
                .padding(horizontal = 40.dp, vertical = 25.dp)
                .background(color = Color.White, shape = RoundedCornerShape(12.dp)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(id = R.drawable.group_filedelete_small),
                tint = Color(0xFF26282B),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.End)
            )

                Text(
                    text = "그룹 입장 코드",
                    style = NoteLassTheme.Typography.twenty_700_pretendard,
                    color = PrimaryBlack,
                )
                Spacer(modifier = Modifier.height(100.dp))
                Text(
                    text = code.toString(),
                    style = NoteLassTheme.Typography.fourtyeight_700_pretendard,
                    color = PrimarayBlue,
                )

            Spacer(modifier = Modifier.height(100.dp))

            Box(
                modifier = Modifier
                    .height(56.dp)
                    .width(350.dp)
            ) {
                RectangleEnabledButton(
                    text = "다음",
                    onClick = {
                        onAccept()
                    },
                )

            }
            }
        }
    }
@Composable
fun DialogEnterGroup(
    setShowDialog : (Boolean)-> Unit,
    getCode : (String) -> Unit,
    onAccept : () -> Unit
) {
    val code = remember{
        mutableStateOf("")
    }

    Dialog(
        onDismissRequest = { setShowDialog(false) }
    ) {

        Column(
            modifier = Modifier
                .size(width = 480.dp, height = 288.dp)
                .background(color = Color.White, shape = RoundedCornerShape(12.dp))
                .padding(horizontal = 40.dp, vertical = 25.dp),
        horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(id = R.drawable.group_filedelete_small),
                tint = Color(0xFF26282B),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.End)
            )

            Text(
                text = "입장 코드 입력",
                style = NoteLassTheme.Typography.twenty_700_pretendard,
                color = PrimaryBlack,
                textAlign = TextAlign.Start
            )
            Spacer(modifier = Modifier.height(30.dp))

           OutlinedTextField(
               value = code.value.toString() ,
               onValueChange = {
                   code.value = it
               },
               enabled= true ,
               modifier = Modifier
                   .size(width = 400.dp, height = 60.dp)
                   .padding(horizontal = 20.dp),
               textStyle = NoteLassTheme.Typography.sixteem_600_pretendard,
               placeholder = {
                   Text(
                       text = "입장 코드를 입력해주세요",
                       style = NoteLassTheme.Typography.sixteem_600_pretendard,
                       color= Color.LightGray
                       )
               },
               shape = RoundedCornerShape(8.dp)

           )
            Spacer(modifier = Modifier.height(47.dp))

            Box(
                modifier = Modifier
                    .height(56.dp)
                    .width(350.dp)
                    .padding(horizontal = 20.dp)
            ) {
                RectangleEnabledButton(
                    text = "다음",
                    onClick = {
                        getCode(code.value)
                        onAccept()
                    },
                )

            }
        }
    }
}

@Composable
fun DialogEnterGroupAccept(
    setShowDialog : (Boolean)-> Unit,
    groupInfo : String,
    onAccept : () -> Unit
) {

    Dialog(
        onDismissRequest = { setShowDialog(false) }
    ) {

        Column(
            modifier = Modifier
                .size(width = 480.dp, height = 288.dp)
                .background(color = Color.White, shape = RoundedCornerShape(12.dp))
                .padding(horizontal = 40.dp, vertical = 25.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(id = R.drawable.group_filedelete_small),
                tint = Color(0xFF26282B),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.End)
            )

            Text(
                text = "${groupInfo}에\n 입장하시겠습니까?",
                style = NoteLassTheme.Typography.twenty_700_pretendard,
                color = PrimaryBlack,
                textAlign = TextAlign.Start
            )
            Spacer(modifier = Modifier.height(50.dp))

            Box(
                modifier = Modifier
                    .height(56.dp)
                    .width(350.dp)
                    .padding(horizontal = 20.dp)
            ) {
                RectangleEnabledButton(
                    text = "입장",
                    onClick = {
                        onAccept()
                    },
                )

            }
        }
    }
}

@Composable
fun DialogGroupTeacherAccept(
    setShowDialog : (Boolean)-> Unit,
    list : List<ApplicationStudent>,

    onAccept : () -> Unit
) {

    Dialog(
        onDismissRequest = { setShowDialog(false) }
    ) {

        Column(
            modifier = Modifier
                .size(width = 480.dp, height = 288.dp)
                .background(color = Color.White, shape = RoundedCornerShape(12.dp))
                .padding(horizontal = 40.dp, vertical = 25.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(id = R.drawable.group_filedelete_small),
                tint = Color(0xFF26282B),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.End)
            )
//            list.forEachIndexed { index, applicationStudent ->
//                ApplicationStudent(
//                    memberId = ,
//                    id = index+1 ,
//                    name = ,
//                    onClickAccept = ,
//                    onClickDecline =
//                )
//            }


        }
    }
}
@Preview
@Composable
fun DialogPreview(){
    val showDialog = remember {
        mutableStateOf(false)
    }


    Column {

//
//        DialogSignUp(
//            setShowDialog = {
//                showDialog.value = it
//            },
//            content = "노트고등학교 3학년 1반 1번\n   김OO님이 맞습니까?",
//            onDecline = { /*TODO*/ }
//        ) {
//            /*TODO*/
//        }
//
//        CreateGroup(
//            setShowDialog = {
//                            showDialog.value = it
//            },
//        getInfo ={
//            gradeInfo, classInfo , subjctInfo ->
//
//        }
//
//        ) {
//
//        }
//        DialogGroupCode(
//            setShowDialog = {
//                            showDialog.value = it
//            }, code = 123456) {
//
//        }

        DialogEnterGroup(setShowDialog = {
                                         showDialog.value = it
        }, getCode = {}) {

        }

    }
}

