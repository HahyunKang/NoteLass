package com.app.note_lass.module.group.ui

import android.widget.Space
import android.widget.Toast
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeCompilerApi
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.note_lass.R
import com.app.note_lass.common.DashboardType
import com.app.note_lass.core.Proto.Role
import com.app.note_lass.module.group.ui.component.JoinDialog
import com.app.note_lass.module.group.ui.viewModel.GroupForTeacherViewModel
import com.app.note_lass.ui.component.AppBar
import com.app.note_lass.ui.component.AppBarForTeacherInGroup
import com.app.note_lass.ui.component.DialogCreateSelfEvaluation
import com.app.note_lass.ui.component.DialogDeleteConfirm
import com.app.note_lass.ui.component.DialogDeleteGroup
import com.app.note_lass.ui.component.DialogDeleteStudent
import com.app.note_lass.ui.component.DialogGroupInfo
import com.app.note_lass.ui.component.EmptyContent
import com.app.note_lass.ui.component.IconAndText
import com.app.note_lass.ui.component.SectionHeader
import com.app.note_lass.ui.component.SectionHeaderWithCreate
import com.app.note_lass.ui.theme.NoteLassTheme
import com.app.note_lass.ui.theme.PrimarayBlue
import com.app.note_lass.ui.theme.PrimaryBlack
import com.app.note_lass.ui.theme.PrimaryGray
import com.app.note_lass.ui.theme.PrimaryPurple

@Composable
fun GroupTeacherScreen(
    groupInfo : String,
    onTouchCreateNotice : () -> Unit,
    onTouchWatchAll : () -> Unit,
    onClickStudentRecord : (Long,Long,String) -> Unit,
    goBackToGroup : () -> Unit,
    goBack : ()->Unit,
    onClickLogout : () -> Unit,
    onClickDetail : (DashboardType,Long) -> Unit,
    viewModel : GroupForTeacherViewModel = hiltViewModel()
){
    val isShowDialog = remember{
        mutableStateOf(false)
    }
    val isGroupInfoDialog = remember{
        mutableStateOf(false)
    }
    val isGroupDeleteDialog = remember{
        mutableStateOf(false)
    }
    val isGroupModifyDialog = remember{
        mutableStateOf(false)
    }
    val isStudentDeleteDialog = remember{
        mutableStateOf(false)
    }
    val isSelfEvaluationDialog = remember{
        mutableStateOf(false)
    }
    val context=  LocalContext.current

    val joinStudentListState = viewModel.joinStudentListState
    val dashBoardState = viewModel.dashBoardState

    if(isShowDialog.value && joinStudentListState.value.isSuccess){
        JoinDialog(
            setShowDialog = {
                isShowDialog.value = it
            },
            groupInfo = joinStudentListState.value.groupInfo,
            groupCode = joinStudentListState.value.groupCode,
            joinStudentList = joinStudentListState.value.joinStudentList,
            onClickDecline = {
                  viewModel.rejectGroup(it.toLong())
            },
            onClickAccept = {
                viewModel.approveGroup(
                    userId = it.toLong(),
                    isToast = {
                        Toast.makeText(context,"승인이 완료되었습니다",Toast.LENGTH_SHORT).show()
                    }
                )

            },
            onClickAllAccept = {
                               viewModel.approveAllGroup(
                                   isToast = {
                                   Toast.makeText(context,"일괄 승인이 완료되었습니다",Toast.LENGTH_SHORT).show()
                                       isShowDialog.value = false

                                   }
                               )


            },
            getStudentList= { viewModel.getJoinStudentList() }
        )
    }

    if(isGroupInfoDialog.value){
        DialogGroupInfo(
            setShowDialog ={
                isGroupInfoDialog.value = it
            },
            onClickDelete = {
                isGroupDeleteDialog.value = true
            },
            onClickModify = {
                isGroupModifyDialog.value = true
            }
        )
    }
    val deleteStudentId = remember {
        mutableStateOf(0)
    }
    if(isGroupModifyDialog.value){
        DialogDeleteStudent(
            groupInfo = groupInfo,
            setShowDialog = {
                  isGroupModifyDialog.value = it
            },
            onClickDelete = {
                isStudentDeleteDialog.value = true
                deleteStudentId.value = it
            }
        )
    }
    if(isStudentDeleteDialog.value){
        DialogDeleteConfirm(
            setShowDialog = {
                            isStudentDeleteDialog.value = it
            },
            studentId = deleteStudentId.value,
        )
    }


    if(isGroupDeleteDialog.value){
        DialogDeleteGroup(
            setShowDialog = {
            isGroupDeleteDialog.value = it
        },
            goBackToGroup = goBackToGroup

        )
    }

    if(isSelfEvaluationDialog.value){
        DialogCreateSelfEvaluation(
            setShowDialog = {
                isSelfEvaluationDialog.value = it
            },
        )
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AppBarForTeacherInGroup(
                title = groupInfo,
                badgeCount = 12,
                onGroupClick ={
                    isShowDialog.value = true
                    viewModel.getJoinStudentList()
                },
                onGroupInfoClick = {
                    isGroupInfoDialog.value = true
                },
                onSelfEvaluationClick = {
                    isSelfEvaluationDialog.value = true
                },
                onClickLogout = onClickLogout,
                goBack = goBack
            )

        },
        containerColor =  Color(0xFFF5F5FC),
        contentColor = Color.Black,
        bottomBar = {
        },
        content = {

            val studentListState = viewModel.studentListState

            Row(
                modifier = Modifier
                    .padding(
                        top = it.calculateTopPadding() + 30.dp,
                        bottom = 20.dp,
                        start = 30.dp,
                        end = 30.dp
                    )
            )
            {
                Column(
                    modifier = Modifier.weight(2f)
                ) {
                    Column(
                        modifier = Modifier
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
                            .padding(horizontal = 24.dp),
                        verticalArrangement = Arrangement.Top
                    ) {

                        SectionHeaderWithCreate(
                                title = "공지/학습자료",
                                onTouchCreate = onTouchCreateNotice,
                                onTouchWatchAll = onTouchWatchAll
                            )


                        if(dashBoardState.value.isSuccess){
                            if(dashBoardState.value.result!=null){
                                dashBoardState.value.result!!.onEachIndexed {
                                    index,it->
                                    if(index < 3) {
                                        IconAndText(
                                            icon = R.drawable.group_notice_read,
                                            iconColor = PrimarayBlue,
                                            text = it.title,
                                            onClick = {
                                                if (it.noticeId != null) onClickDetail(
                                                    DashboardType.NOTICE,
                                                    it.noticeId
                                                )
                                                if (it.lectureMaterialId != null) onClickDetail(
                                                    DashboardType.MATERIAL,
                                                    it.lectureMaterialId
                                                )
                                            }
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                    }
                                }
                            }

                        }

                    }
                    Spacer(modifier = Modifier.height(24.dp))
                    val assignmentList = listOf("과제1", "과제2", "과제3")
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .shadow(
                                elevation = 7.dp,
                                shape = RoundedCornerShape(size = 8.dp),
                                ambientColor = Color(0x0A26282B),
                                spotColor = Color(0x3D26282B)
                            )
                            .background(
                                color = Color(0xFFFFFFFF),
                            )
                            .padding(horizontal = 24.dp)
                    ) {
                        Text("과제별 성적 열람",
                            style = TextStyle(
                                fontSize = 20.sp,
                                fontFamily = FontFamily(Font(R.font.pretendard_regular)),
                                fontWeight = FontWeight(700),
                                color = PrimaryBlack,
                            ),
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                        Spacer(modifier = Modifier.height(25.dp))
                        Text(
                            text = "준비 중입니다.",
                            color = PrimaryGray,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            style = NoteLassTheme.Typography.sixteem_600_pretendard
                        )

                                            }
                    Spacer(modifier = Modifier.height(24.dp))

                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .shadow(
                                elevation = 7.dp,
                                shape = RoundedCornerShape(size = 8.dp),
                                ambientColor = Color(0x0A26282B),
                                spotColor = Color(0x3D26282B)
                            )
                            .background(
                                color = Color(0xFFFFFFFF),
                            )
                            .padding(start = 24.dp)
                            .fillMaxWidth()

                    ) {
                        Text("성적별 성적 열람",
                            style = TextStyle(
                                fontSize = 20.sp,
                                fontFamily = FontFamily(Font(R.font.pretendard_regular)),
                                fontWeight = FontWeight(700),
                                color = PrimaryBlack,
                            ),
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                        Spacer(modifier = Modifier.height(25.dp))
                        Text(
                            text = "준비 중입니다.",
                            color = PrimaryGray,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            style = NoteLassTheme.Typography.sixteem_600_pretendard
                        )
                    }
                }

                Spacer(modifier = Modifier.width(24.dp))
                val scrollState = rememberScrollState()

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .shadow(
                            elevation = 7.dp,
                            shape = RoundedCornerShape(size = 8.dp),
                            ambientColor = Color(0x0A26282B),
                            spotColor = Color(0x3D26282B)
                        )
                        .background(
                            color = Color(0xFFFFFFFF),
                        )
                        .fillMaxHeight()
                        .padding(start = 24.dp, end = 24.dp)
                ) {

                    Text("생기부 관리",
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontFamily = FontFamily(Font(R.font.pretendard_regular)),
                            fontWeight = FontWeight(700),
                            color = PrimaryBlack,
                        ),
                        modifier =Modifier.padding(vertical = 8.dp)
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Column(
                        modifier = Modifier
                            .verticalScroll(scrollState)


                    ) {

                        if (studentListState.value.isSuccess) {
                            if(studentListState.value.studentList.isEmpty()){
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier
                                        .padding(vertical = 80.dp)
                                        .fillMaxWidth()
                                ) {
                                    Text(text ="학생 목록이 없어요",color = PrimaryGray,style = NoteLassTheme.Typography.sixteen_700_pretendard)

                                    Spacer(modifier = Modifier.height(8.dp))

                                    Text(text ="학생을 등록해 주세요",color = PrimaryGray,style = NoteLassTheme.Typography.fourteen_600_pretendard)

                                }
                            }
                            studentListState.value.studentList.forEachIndexed() { index, student->
                                val id = index + 1
                                IconAndText(
                                    icon = R.drawable.group_person_small,
                                    iconColor = PrimarayBlue,
                                    text = "$id  ${student.name}",
                                    onClick = {
                                        onClickStudentRecord(student.id.toLong(),id.toLong(),student.name)
                                    }
                                )
                                Spacer(modifier = Modifier.height(20.dp))

                            }
                        }
                    }


                }


            }
        })
}



@Composable
@Preview
fun GroupTeacherPreview(){
  //  GroupTeacherScreen()
}