package com.app.note_lass.module.record.ui

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.ContentResolver
import android.net.Uri
import android.os.Build
import android.provider.OpenableColumns
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.note_lass.common.AndroidDownLoader
import com.app.note_lass.common.DownloadCompletedReceiver
import com.app.note_lass.common.DownloadStatusListener
import com.app.note_lass.core.Proto.GroupInfo
import com.app.note_lass.core.Proto.ProtoViewModel
import com.app.note_lass.module.record.ui.viewModel.RecordViewModel
import com.app.note_lass.module.student.ui.HandBookListScreen
import com.app.note_lass.ui.component.AppBarForRecord
import com.app.note_lass.ui.component.DialogInRecord
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okio.BufferedSink
import okio.source

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun StudentRecordUploadScreen(
    studentId : Long,
    studentName : String,
    protoViewModel : ProtoViewModel = hiltViewModel(),
    recordViewModel: RecordViewModel = hiltViewModel(),
) {


    val groupInfo = protoViewModel.groupInfo.collectAsState(initial = GroupInfo("", "", 0))
    val excelState = recordViewModel.getExcelState

    @SuppressLint("Range")
    fun Uri.asMultipart(name: String, contentResolver: ContentResolver): MultipartBody.Part? {
        return contentResolver.query(this, null, null, null, null)?.let {
            if (it.moveToNext()) {
                val displayName = it.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                val requestBody = object : RequestBody() {
                    override fun contentType(): MediaType? {
                        return contentResolver.getType(this@asMultipart)?.toMediaType()
                    }

                    override fun writeTo(sink: BufferedSink) {
                        sink.writeAll(contentResolver.openInputStream(this@asMultipart)?.source()!!)
                    }
                }
                it.close()
                MultipartBody.Part.createFormData(name, displayName, requestBody)
            } else {
                it.close()
                null
            }
        }
    }

    DisposableEffect(Unit) {
        DownloadCompletedReceiver.registerListener(recordViewModel)
        onDispose {
            DownloadCompletedReceiver.unregisterListener()
        }
    }

    val isFirstDialogShow = remember {
        mutableStateOf(false)
    }
    val isSecondDialogShow = remember {
        mutableStateOf(false)
    }

    val context = LocalContext.current

    val handBookListState = recordViewModel.getHandBookState
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
            val downloader = AndroidDownLoader(context, "생기부.cell")
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
            }
        }

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                AppBarForRecord(
                    title = "${groupInfo.value.groupName} ${studentId}번 $studentName",
                    badgeCount = 12,
                    onClick = {
                        isFirstDialogShow.value = true
                    }
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
                            top = it.calculateTopPadding() + 30.dp,
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
                            StudentRecordScreen()
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
                        HandBookListScreen(handBookList = handBookListState.value.handBookList)


                    }
                }

            })

    }


