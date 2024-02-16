package com.app.note_lass.module.group.ui.component

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.note_lass.R
import com.app.note_lass.module.group.data.join.JoinStudentInfo
import com.app.note_lass.module.group.ui.viewModel.GroupForTeacherViewModel
import com.app.note_lass.module.student.data.HandBookRequest
import com.app.note_lass.module.student.ui.viewmodel.StudentMemoViewModel
import com.app.note_lass.ui.component.DropDownMenuForMemo
import com.app.note_lass.ui.component.NumUpAndDown
import com.app.note_lass.ui.component.RectangleEnabledButton
import com.app.note_lass.ui.component.RectangleUnableButton
import com.app.note_lass.ui.theme.NoteLassTheme
import com.app.note_lass.ui.theme.PrimarayBlue
import com.app.note_lass.ui.theme.PrimaryBlack
import com.app.note_lass.ui.theme.PrimaryGray

@Composable
fun JoinDialog(
    setShowDialog : (Boolean)-> Unit ={},
    joinStudentList : List<JoinStudentInfo>,
    groupInfo: String,
    groupCode: Int,
    onClickDecline : (Int) -> Unit,
    onClickAccept : (Int) -> Unit,
    onClickAllAccept : () -> Unit,
    getStudentList : () -> Unit
) {


    Dialog(
        onDismissRequest = { setShowDialog(false) }
    ) {

        Column(
            modifier = Modifier
                .width(720.dp)
                .height(580.dp)
                .background(color = Color.White, shape = RoundedCornerShape(12.dp))
                .padding(horizontal = 40.dp, vertical = 30.dp),
        ) {

            Icon(
                painter = painterResource(id = R.drawable.group_filedelete_small),
                tint = Color(0xFF26282B),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.End)
                    .clickable {
                        setShowDialog(false)
                    }
            )

            Text(
                text = buildAnnotatedString {
                    withStyle(
                        SpanStyle(
                            color = PrimaryBlack,
                            fontSize = 16.sp,
                            fontWeight = FontWeight(700),
                            fontStyle = FontStyle(R.font.pretendard_regular)
                        )
                    ){
                        append(groupInfo)
                    }
                    withStyle(
                        SpanStyle(
                            color = PrimaryGray,
                            fontSize = 16.sp,
                            fontWeight = FontWeight(700),
                            fontStyle = FontStyle(R.font.pretendard_regular)
                        )
                    ){
                        append("•")
                    }
                    withStyle(
                        SpanStyle(
                            color = PrimarayBlue,
                            fontSize = 16.sp,
                            fontWeight = FontWeight(700),
                            fontStyle = FontStyle(R.font.pretendard_regular)
                        )
                    ){
                            append(groupCode.toString())
                        }
                }
            )
            Spacer(modifier = Modifier.height(20.dp))


            Box(
                modifier= Modifier
                    .wrapContentSize()
                    .background(color = Color(0x339EA4AA), shape = RectangleShape)
                    .padding(start = 8.dp, top = 2.dp, end = 8.dp, bottom = 3.dp)
                    .clickable {
                        onClickAllAccept()
                    }
            ){
                Text("한 번에 수락하기",
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.pretendard_regular)),
                        fontWeight = FontWeight(600),
                        color = Color(0xFF9EA4AA),
                        textAlign = TextAlign.Start,
                    )
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            )
            {
                itemsIndexed(joinStudentList){
                    index, item ->
                    ApplicationStudent(
                        id = index+1 ,
                        studentInfo = item,
                        onClickDecline = {
                             onClickDecline(it)
                        },
                        onClickAccept = {
                            onClickAccept(it)
                        },
                        getStudentList = getStudentList
                    )
                }
            }

        }
    }
}

@Preview
@Composable
fun preivewJoinDialog(){
 //   JoinDialog()
}