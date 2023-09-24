package com.app.note_lass.module.group.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.app.note_lass.module.home.assignment.Assignment
import com.app.note_lass.ui.component.RectangleEnabledButton
import com.app.note_lass.ui.component.RectangleEnabledWithBorderButton
import com.app.note_lass.ui.component.RectangleUnableButton
import com.app.note_lass.ui.component.SectionHeader
import com.app.note_lass.ui.theme.Gray50
import com.app.note_lass.ui.theme.NoteLassTheme
import com.app.note_lass.ui.theme.PrimaryGray

@Composable
fun AssignmentUploadScreen(){

    var assignmentTitle by remember{
        mutableStateOf("")
    }
    var assignmentContent by remember{
        mutableStateOf("")
    }
    Row(
        modifier = Modifier
        .padding
            (
            horizontal = 40.dp,
            vertical = 30.dp
        )){
        Box(
            Modifier
                .weight(2f)
                .shadow(
                    elevation = 7.dp,
                    shape = RoundedCornerShape(size = 8.dp),
                    ambientColor = Color(0x0A26282B),
                    spotColor = Color(0x3D26282B)
                )
                .background(
                    color = Color(0xFFFFFFFF)
                )
                .fillMaxHeight()
                .padding(horizontal = 24.dp)){
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
                SectionHeader(title = "과제 생성")
                Spacer(modifier = Modifier.height(23.dp))
                Row(modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically){
                    Text(text= "과제 제목")
                    Spacer(modifier = Modifier.width(26.dp))
                    OutlinedTextField(
                        value = assignmentTitle,
                        onValueChange = {
                            assignmentTitle = it
                        },
                        textStyle = NoteLassTheme.Typography.fourteen_600_pretendard,
                        placeholder = {
                            Text("과제 제목을 입력하세요",
                                style= NoteLassTheme.Typography.fourteen_600_pretendard,
                                color = PrimaryGray)
                        },
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color.Black,
                            unfocusedIndicatorColor = Gray50,
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White),
                        maxLines = 1,
                        modifier = Modifier.fillMaxWidth()

                    )
                }
                Spacer(modifier = Modifier.height(24.dp))

                Column(modifier = Modifier.fillMaxWidth(),
                  verticalArrangement = Arrangement.Center){
                    Text(text= "과제 설명")
                    Spacer(modifier = Modifier.height(26.dp))
                    OutlinedTextField(
                        value = assignmentContent,
                        onValueChange = {
                            assignmentContent = it
                        },
                        textStyle = NoteLassTheme.Typography.fourteen_600_pretendard,
                        placeholder = {
                            Text("과제 설명을 입력하세요",
                                style= NoteLassTheme.Typography.fourteen_600_pretendard,
                                color = PrimaryGray)
                        },
                        colors = TextFieldDefaults.colors(focusedIndicatorColor = Color.Black,
                            unfocusedIndicatorColor = Gray50, focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White),
                        modifier = Modifier
                            .height(210.dp)
                            .fillMaxWidth()

                        )

                }
                Spacer(modifier = Modifier.height(24.dp))
                Row(modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically){
                    Text(text= "파일 첨부")
                    Spacer(modifier = Modifier.width(26.dp))
                    Box(modifier = Modifier.size(200.dp,30.dp)){
                        RectangleEnabledWithBorderButton(
                            text = "라이브러리에서 파일 탐색",
                            onClick = { /*TODO*/ },
                            containerColor = Color.White,
                            textColor = PrimaryGray,
                            borderColor = Gray50
                        )
                    }
                    Spacer(modifier = Modifier.width(24.dp))

                    Box(modifier = Modifier.size(170.dp,30.dp)){
                        RectangleEnabledWithBorderButton(
                            text = "갤러리에서 파일 탐색",
                            onClick = { /*TODO*/ },
                            containerColor = Color.White,
                            textColor = PrimaryGray,
                            borderColor = Gray50
                        )
                    }
                }




            }

            Row(modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(24.dp)){
                Box(modifier = Modifier.size(49.dp,40.dp)){
                    RectangleUnableButton(text = "취소",
                        onClick = { TODO() })
                }
                Spacer(modifier = Modifier.width(16.dp))

                Box(modifier = Modifier.size(73.dp,40.dp)){
                    RectangleEnabledButton(text = "생성하기",
                        onClick = { TODO() })
                }
            }
            
            }
        Spacer(modifier = Modifier.width(44.dp))
        Column(
            Modifier
                .weight(1f)
                .shadow(
                    elevation = 7.dp,
                    shape = RoundedCornerShape(size = 8.dp),
                    ambientColor = Color(0x0A26282B),
                    spotColor = Color(0x3D26282B)
                )
                .background(
                    color = Color(0xFFFFFFFF)
                )
                .fillMaxHeight()
                .padding(horizontal = 24.dp)) {

            SectionHeader(title = "과제 설정")



        }


    }


}