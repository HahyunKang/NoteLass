package com.app.note_lass.module.student.ui

import android.os.Build
import android.util.Log
import android.widget.Toast
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
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.note_lass.common.AndroidDownLoader
import com.app.note_lass.common.DownloadCompletedReceiver
import com.app.note_lass.core.Proto.GroupInfo
import com.app.note_lass.core.Proto.ProtoViewModel
import com.app.note_lass.module.student.ui.viewmodel.RecordViewModel
import com.app.note_lass.module.student.data.handbook.HandBook
import com.app.note_lass.ui.component.AppBarForNoGroup
import com.app.note_lass.ui.component.DialogEvaluationForTeacher
import com.app.note_lass.ui.component.DialogInRecord
import com.app.note_lass.ui.component.DialogModifyMemo

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun StudentRecordUploadScreen(
    studentId : Long,
    studentName : String,
    goBack : () -> Unit,
    onClickLogout : ()->Unit,
    protoViewModel : ProtoViewModel = hiltViewModel(),
    recordViewModel: RecordViewModel = hiltViewModel(),
) {


    val groupInfo = protoViewModel.groupInfo.collectAsState(initial = GroupInfo("", "", 0))
    val excelState = recordViewModel.getExcelState
    val handBookState = recordViewModel.getHandBookState


    DisposableEffect(Unit) {
        DownloadCompletedReceiver.registerListener(recordViewModel)
        onDispose {
            DownloadCompletedReceiver.unregisterListener()
        }
    }
    val idList = remember {
        mutableStateOf(mutableListOf<Int>())
    }

    val isFirstDialogShow = remember {
        mutableStateOf(false)
    }
    val isSecondDialogShow = remember {
        mutableStateOf(false)
    }
    val isDialogModifyMemo = remember {
        mutableStateOf(false)
    }
    val isDialogEvaluation= remember {
        mutableStateOf(false)
    }
    val modifyId = remember{
        mutableStateOf(0)
    }
    val modifyContent = remember{
        mutableStateOf("")
    }
    val isMemoActive = remember {
        mutableStateOf(false)
    }
    val isPrinted = remember {
        mutableStateOf(false)
    }
    val handBooks = remember {
        mutableStateOf(mutableListOf<HandBook>())

    }

    val context = LocalContext.current
    val evaluationState = recordViewModel.getEvaluationState

    val downloadStatus = recordViewModel.downloadStatus
    Log.e("다운로드",downloadStatus.value)

    LaunchedEffect(key1 = downloadStatus.value) {
        if (downloadStatus.value == "Successful") {
            isSecondDialogShow.value = true
            recordViewModel.deleteExcel()
            recordViewModel.setStatus()
        }
    }
    if (isFirstDialogShow.value) {
        DialogInRecord(
            setShowDialog = {
                isFirstDialogShow.value = it
            },
            content = "한셀로 출력하시겠습니까?",
            buttonText = "출력하기"
        ) {
            val downloader = AndroidDownLoader(context, "생기부.cell",null)
            recordViewModel.getExcel(downLoadExcel = {
                downloader.downloadFile(excelState.value.excelUrl)
            }
            )
            isFirstDialogShow.value = false

        }
    }
        if (isSecondDialogShow.value) {
            DialogInRecord(
                setShowDialog = {
                    isSecondDialogShow.value = it

                },
                content = "성공적으로 출력되었습니다.",
                buttonText = "확인"
            ) {
                isSecondDialogShow.value = false
                isPrinted.value = true
            }
        }
    if(isDialogModifyMemo.value){
        DialogModifyMemo(
            setShowDialog ={
                           isDialogModifyMemo.value = it
            } ,
            memoId = modifyId.value,
            memoContent = modifyContent.value,
            onClickModify ={
                recordViewModel.modifyHandBook(modifyId.value.toLong(),it)
            }
        )
    }
    if(isDialogEvaluation.value && evaluationState.value.isSuccess){
        DialogEvaluationForTeacher(
            setShowDialog = {
                isDialogEvaluation.value = it
            },
            evaluations = evaluationState.value.result!!
        )
    }

    val modifyState = recordViewModel.handBookModifyState
    LaunchedEffect(key1 = modifyState.value) {
        if (modifyState.value.isSuccess) {
            Toast.makeText(context,"수정이 완료되었습니다.",Toast.LENGTH_SHORT).show()
            isDialogModifyMemo.value = false

        }
    }
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                AppBarForNoGroup(
                    title = "${groupInfo.value.groupName} ${studentId}번 $studentName",
                    badgeCount = 12,
                    onClick = {
                        isFirstDialogShow.value = true
                    },
                    goBack = goBack,
                    onClickLogout = onClickLogout
                )
            },
            containerColor = Color(0xFFF5F5FC),
            contentColor = Color.Black,
            bottomBar = {
            },
            content = {
                Row(
                    modifier = Modifier
                        .padding(
                            top = it.calculateTopPadding() + 15.dp,
                            bottom = 20.dp,
                            start = 30.dp,
                            end = 30.dp
                        )
                ) {
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
                            StudentRecordScreen(isMemoActive =  {
                                isMemoActive.value = it
                            },
                                handbookList = idList.value
                            )
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
                    if(handBookState.value.isSuccess)  {
                        handBooks.value = handBookState.value.handBookList.toMutableList()

                        HandBookListScreen(
                            isMemoActive = isMemoActive.value,
                            getHandBookList = {
                                idList.value = it.toMutableList()
                            },
                            handBooks = handBookState.value.handBookList,
                            studentId = studentId,
                            isDelete = {
                                recordViewModel.deleteHandBook(it)
                            },
                            isModify ={
                                content,id ->
                                modifyContent.value = content
                                modifyId.value = id.toInt()
                                isDialogModifyMemo.value = true
                            },
                            isClickEvaluation = {
                                isDialogEvaluation.value = true
                                recordViewModel.getEvaluations()
                            }
                        )
                    }


                    }
                }

            })

    }


