package com.app.note_lass.module.note.ui.component

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import android.provider.OpenableColumns
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.note_lass.R
import com.app.note_lass.core.FilePicker.FileManager
import com.app.note_lass.module.note.NoteActivity
import com.app.note_lass.module.note.data.NoteRequest
import com.app.note_lass.module.note.ui.NoteViewModel
import com.app.note_lass.ui.component.DropDownMenuForMemo
import com.app.note_lass.ui.component.FileUpload
import com.app.note_lass.ui.component.RectangleEnabledButton
import com.app.note_lass.ui.component.RectangleEnabledWithBorderButton
import com.app.note_lass.ui.component.RectangleUnableButton
import com.app.note_lass.ui.theme.Gray50
import com.app.note_lass.ui.theme.NoteLassTheme
import com.app.note_lass.ui.theme.PrimaryGray
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okio.BufferedSink
import okio.source

@Composable
fun CreateLectureDialog(
    setShowDialog : (Boolean)-> Unit ={},
    viewModel  : NoteViewModel = hiltViewModel()
){
    val title = remember {
    mutableStateOf("")
    }

    val content = remember {
        mutableStateOf("")
    }
    val groupSelected = remember {
        mutableStateOf(false)
    }

    val groupId = remember {
        mutableIntStateOf(-1)
    }

    val pdfUri =  remember {
        mutableStateOf<Uri?>(null)
    }

    var photoUri by remember {
        mutableStateOf<Uri?>(null)
    }

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

    val requestFile  = remember {
        mutableStateOf<MultipartBody.Part?>(null)
    }
    val groupListState = viewModel.groupHashState
    val context= LocalContext.current

    val pdfLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri :Uri? ->
            pdfUri.value = uri
            val file = pdfUri.value!!.asMultipart("file",context.contentResolver)
            requestFile.value= file

        }
    )

    val photoLauncher = rememberLauncherForActivityResult(
        contract =  ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
        }
    )

    Dialog(
        onDismissRequest = { setShowDialog(false) }
    ) {
        Column(
            modifier = Modifier
                .width(580.dp)
                .height(644.dp)
                .background(color = Color.White, shape = RoundedCornerShape(12.dp))
                .padding(horizontal = 24.dp)
        ) {
            Text(
                text = "강의자료 생성",
                style = NoteLassTheme.Typography.twenty_700_pretendard,
                color = Color.Black,
                modifier = Modifier.padding(vertical = 24.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "강의자료 제목")
                Spacer(modifier = Modifier.width(26.dp))
                OutlinedTextField(
                    value = title.value,
                    onValueChange = {
                        title.value = it
                    },
                    textStyle = NoteLassTheme.Typography.fourteen_600_pretendard,
                    placeholder = {
                        Text(
                            "강의자료 제목을 입력하세요",
                            style = NoteLassTheme.Typography.fourteen_600_pretendard,
                            color = PrimaryGray
                        )
                    },
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Black,
                        unfocusedIndicatorColor = Gray50,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    ),
                    maxLines = 1,
                    modifier = Modifier.fillMaxWidth()

                )
            }
            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "할당된 그룹")
                Spacer(modifier = Modifier.width(26.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                    ) {
                        DropDownMenuForMemo(
                            menuList = groupListState.value.groupHash,
                            iconDown = R.drawable.arrow_down,
                            iconUp = R.drawable.arrow_down,
                            placeHolder = "그룹 선택",
                            isSelected = {
                               groupSelected.value = it
                            },
                            onGetInfo = { name, id ->
                                groupId.intValue = id
                            }
                        )
                    }

                }
            }
            Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = "강의자료 설명")
                    Spacer(modifier = Modifier.height(18.dp))
                    OutlinedTextField(
                        value = content.value,
                        onValueChange = {
                            content.value = it
                        },
                        textStyle = NoteLassTheme.Typography.fourteen_600_pretendard,
                        placeholder = {
                            Text(
                                "강의자료 설명을 입력하세요",
                                style = NoteLassTheme.Typography.fourteen_600_pretendard,
                                color = PrimaryGray
                            )
                        },
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color.Black,
                            unfocusedIndicatorColor = Gray50, focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White
                        ),
                        modifier = Modifier
                            .height(210.dp)
                            .fillMaxWidth()
                    )

                }
            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "파일 첨부")
                Spacer(modifier = Modifier.width(26.dp))
                Box(modifier = Modifier.size(200.dp, 30.dp)) {
                    RectangleEnabledWithBorderButton(
                        text = "라이브러리에서 파일 탐색",
                        onClick = {
                            pdfLauncher.launch("application/pdf")
                            //cell 확장자
                            //pdfLauncher.launch("application/octet-stream")
                        },
                        containerColor = Color.White,
                        textColor = PrimaryGray,
                        borderColor = Gray50
                    )
                }
                Spacer(modifier = Modifier.width(18.dp))

                Box(modifier = Modifier.size(170.dp, 30.dp)) {
                    RectangleEnabledWithBorderButton(
                        text = "갤러리에서 파일 탐색",
                        onClick = {
                            photoLauncher.launch("image/*")
                        },
                        containerColor = Color.White,
                        textColor = PrimaryGray,
                        borderColor = Gray50
                    )
                }

            }
            //  val context = LocalContext.current
            var fileName by remember { mutableStateOf<String?>(null) }
            var fileSize by remember { mutableStateOf<Long?>(null) }
            val fileManager  = FileManager()

            if (pdfUri.value != null) {
                fileName = fileManager.getFileName(context, pdfUri.value!!)
                fileSize = fileManager.getFileSize(context, pdfUri.value!!)

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {

                    FileUpload(
                        title = fileName.toString(),
                        fileSize = fileSize.toString(),
                        onClick = {
                            val intent = Intent(context, NoteActivity::class.java).apply {
                                putExtra("pdfUri", pdfUri.value)
                            }
                            pdfUri.value?.path?.let { Log.e("PDFURI", it) }
                            context.startActivity(intent)
                        },
                        onDelete = {
                            pdfUri.value = null
                        }
                    )

                }


            }

            if (photoUri != null) {
                fileName = fileManager.getFileName(context, photoUri!!)
                fileSize = fileManager.getFileSize(context, photoUri!!)

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {

                    FileUpload(
                        title = fileName.toString(),
                        fileSize = fileSize.toString(),
                        onClick = {
                            val intent = Intent(context, NoteActivity::class.java).apply {
                                putExtra("photoUri", photoUri)
                            }
                            context.startActivity(intent)
                        },
                        onDelete = {
                            photoUri = null
                        }
                    )

                }


            }

            Row(
                modifier = Modifier.align(Alignment.End)
            ){
                Box(modifier = Modifier.size(49.dp,40.dp)){
                    RectangleUnableButton(text = "취소",
                        onClick = { setShowDialog(false) })
                }
                Spacer(modifier = Modifier.width(16.dp))

                Box(modifier = Modifier.size(73.dp,40.dp)){
                    RectangleEnabledButton(text = "생성하기",
                        onClick = {
                            if(title.value.isNotEmpty() && content.value.isNotEmpty() && requestFile.value!=null){
                                viewModel.makeMaterial(groupId.intValue.toLong(),
                                    NoteRequest(title.value,content.value),
                                    requestFile.value!!
                                )
                            }

                        }
                    )
                }
            }


        }
        }
    }