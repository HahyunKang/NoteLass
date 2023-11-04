package com.app.note_lass.module.group.ui

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.app.note_lass.ui.component.SectionHeader

@Composable
fun AssignmentUploadScreen(){

    val titleList = listOf("공지","과제","강의자료")

    var selectedTabIndex by remember{
        mutableStateOf(0)
    }


    Row(
        modifier = Modifier
        .padding
            (
            horizontal = 40.dp,
            vertical = 30.dp
        )
    ){
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
                .padding(horizontal = 24.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Box(modifier = Modifier.weight(1f)) {
                    TabViewForTeacher(
                        titleList = titleList,
                        tabSelected = {
                            selectedTabIndex = it
                        }
                    )
                }
                Box(modifier = Modifier.weight(5f)) {
                    when (selectedTabIndex) {
                        0 -> {
                            CreateNoticeScreen()
                        }

                        1 -> {
                            CreateAssignmentScreen()
                        }

                        2 -> {
                            CreateLectureNoteScreen()
                        }
                    }
                }
            }
        }
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