package com.app.note_lass.module.upload.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.note_lass.core.Proto.GroupInfo
import com.app.note_lass.core.Proto.ProtoViewModel
import com.app.note_lass.core.Proto.Role
import com.app.note_lass.module.group.ui.TabViewForTeacher
import com.app.note_lass.module.upload.ui.viewmodel.NoticeDetailViewModel
import com.app.note_lass.module.upload.ui.viewmodel.UploadViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ModifyDashboardScreen(
    noticeDetailViewModel : NoticeDetailViewModel = hiltViewModel(),
    protoViewModel : ProtoViewModel = hiltViewModel(),
    goBackToGroup: (Role,Long,String) -> Unit
){


    val groupInfo = protoViewModel.groupInfo.collectAsState(initial = GroupInfo("","",0))
    val detailState = noticeDetailViewModel.noticeDetailState
    val titleList = listOf("공지","강의자료")

    var selectedTabIndex by remember{
        mutableIntStateOf(0)
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
                            if(detailState.value.isSuccess) {
                                ModifyNoticeScreen(
                                    goBackToGroup = goBackToGroup,
                                    title = detailState.value.noticeDetail.title,
                                    content = detailState.value.noticeDetail.content,
                                    file = detailState.value.noticeDetail.file!!
                                )
                            }
                        }
//                        1 -> {
//                            CreateAssignmentScreen()
//                        }

                        1 -> {
                            CreateLectureNoteScreen(
                                goBackToGroup = goBackToGroup
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.width(20.dp))

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
                .padding(horizontal = 24.dp, vertical = 15.dp)) {


            when(selectedTabIndex) {
                0 -> {
                   DashBoardInfo(groupInfo.value,"공지")
                }
                1-> {
                    DashBoardInfo(groupInfo.value,"강의자료")
                }
        }



        }


    }


}