package com.app.note_lass.module.note.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.note_lass.core.Proto.GroupInfo
import com.app.note_lass.core.Proto.ProtoViewModel
import com.app.note_lass.ui.component.AppBarForRecord
import com.app.note_lass.ui.theme.NoteLassTheme

@Composable
fun NoteForTeacherScreen(
    protoViewModel : ProtoViewModel = hiltViewModel(),
){
    val groupInfo = protoViewModel.groupInfo.collectAsState(initial = GroupInfo("", "", 0))
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AppBarForRecord(
                title = "${groupInfo.value.groupName}",
                badgeCount = 12,
                onClick = {
                }
            )
        },
        containerColor = Color(0xFFF5F5FC),
        contentColor = Color.Black,
        bottomBar = {
        },
        content = {

            Column(
                modifier = Modifier
                    .padding(
                        top = it.calculateTopPadding() + 30.dp,
                        bottom = 20.dp,
                        start = 30.dp,
                        end = 30.dp
                    )
            ) {
                Box(
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
                        .padding(horizontal = 24.dp)
                ) {
                    Text(text = "노트",
                        style = NoteLassTheme.Typography.twenty_600_pretendard,
                        color = Color.Black,
                        modifier = Modifier.padding(20.dp)
                    )
                }

                Box(
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
                        .padding(horizontal = 24.dp)
                ) {

                }


            }
        }
    )
}