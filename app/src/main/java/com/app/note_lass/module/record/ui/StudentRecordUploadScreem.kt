package com.app.note_lass.module.record.ui

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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.note_lass.R
import com.app.note_lass.core.Proto.GroupInfo
import com.app.note_lass.core.Proto.ProtoViewModel
import com.app.note_lass.module.group.ui.viewModel.UploadViewModel
import com.app.note_lass.module.record.ui.viewModel.RecordViewModel
import com.app.note_lass.ui.component.AppBarForRecord
import com.app.note_lass.ui.component.AppBarForTeacherInGroup
import com.app.note_lass.ui.theme.PrimaryBlack

@Composable
fun StudentRecordUploadScreen(
    studentId : Long,
    studentName : String,
    protoViewModel : ProtoViewModel = hiltViewModel(),
){


    val groupInfo = protoViewModel.groupInfo.collectAsState(initial = GroupInfo("","",0))


    var selectedTabIndex by remember{
        mutableStateOf(0)
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
         AppBarForRecord(
             title = "${groupInfo.value.groupName} $studentId $studentName",
             badgeCount = 12
         )
        },
        containerColor =  Color(0xFFF5F5FC),
        contentColor = Color.Black,
        bottomBar = {
        },
        content = {

    Row(
        modifier = Modifier
            .padding(
                top = it.calculateTopPadding() + 30.dp,
                bottom = 20.dp,
                start = 30.dp,
                end = 30.dp
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
                StudentRecordScreen(groupId = groupInfo.value.groupId!!)
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
                    .padding(horizontal = 24.dp, vertical = 15.dp)
            ) {

                Text(
                    "공지/과제/강의 자료 정보",
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontFamily = FontFamily(Font(R.font.pretendard_regular)),
                        fontWeight = FontWeight(700),
                        color = PrimaryBlack,
                    )
                )


            }
        }

    })

}

