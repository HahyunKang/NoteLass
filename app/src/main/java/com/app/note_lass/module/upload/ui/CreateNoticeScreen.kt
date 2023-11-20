package com.app.note_lass.module.upload.ui

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.app.note_lass.core.FilePicker.FileManager
import com.app.note_lass.core.Proto.GroupInfo
import com.app.note_lass.module.note.NoteActivity
import com.app.note_lass.ui.component.FileUpload
import com.app.note_lass.ui.component.RectangleEnabledButton
import com.app.note_lass.ui.component.RectangleEnabledWithBorderButton
import com.app.note_lass.ui.component.RectangleUnableButton
import com.app.note_lass.ui.theme.Gray50
import com.app.note_lass.ui.theme.NoteLassTheme
import com.app.note_lass.ui.theme.PrimarayBlue
import com.app.note_lass.ui.theme.PrimaryBlack
import com.app.note_lass.ui.theme.PrimaryGray
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okio.BufferedSink
import okio.source
import java.time.LocalDateTime

@Composable
fun CreateNoticeScreen(
    createNotice : (String,String,MultipartBody.Part?) -> Unit
){

    val noticeTitle = remember{
        mutableStateOf("")
    }
    val noticeContent = remember{
        mutableStateOf("")
    }
    var pdfUri by remember {
        mutableStateOf<Uri?>(null)
    }

    var photoUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val requestFileList  = remember {
        mutableStateOf<MultipartBody.Part?>(null)
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
    val context= LocalContext.current

    val pdfLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri :Uri? ->
            pdfUri = uri
            val file = pdfUri?.asMultipart("file",context.contentResolver)

            requestFileList.value= file

        }
    )

    val photoLauncher = rememberLauncherForActivityResult(
        contract =  ActivityResultContracts.GetContent(),
        onResult = { uri :Uri? ->
            photoUri = uri
//            photoUri?.let {
//                if (Build.VERSION.SDK_INT < 28) {
//                    bitmap.value = MediaStore.Images
//                        .Media.getBitmap(context.contentResolver,it)
//
//                } else {
//                    val source = ImageDecoder
//                        .createSource(context.contentResolver,it)
//                    bitmap.value = ImageDecoder.decodeBitmap(source)
//                }
//            }
        }
    )

//    @SuppressLint("Range")
//    fun getFileName(context: Context, uri: Uri): String? {
//        val cursor = context.contentResolver.query(uri, null, null, null, null)
//        var displayName: String? = "pdf"
//        cursor?.moveToFirst()
//        displayName = cursor?.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
//        cursor?.close()
//        return displayName
//    }
//
//    fun getFileSize(context: Context, uri: Uri): Long {
//        return try {
//            context.contentResolver.openFileDescriptor(uri, "r")?.use { parcelFileDescriptor ->
//                val size = parcelFileDescriptor.statSize / (1000000).toLong()
//                size
//            } ?: 0L
//        } catch (e: IOException) {
//            0L
//        }
//    }
  //  val context = LocalContext.current
    var fileName by remember { mutableStateOf<String?>(null) }
    var fileSize by remember { mutableStateOf<Long?>(null) }
    val fileManager  = FileManager()

    Column(modifier= Modifier.fillMaxSize()) {

      Row(
          modifier = Modifier
              .fillMaxWidth()
              .weight(1f),
          verticalAlignment = Alignment.CenterVertically
      ) {
          Text(text = "공지 제목")
          Spacer(modifier = Modifier.width(26.dp))
          OutlinedTextField(
              value = noticeTitle.value,
              onValueChange = {
                  noticeTitle.value = it
              },
              textStyle = NoteLassTheme.Typography.fourteen_600_pretendard,
              placeholder = {
                  Text(
                      "공지 제목을 입력하세요",
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
      Spacer(modifier = Modifier.height(15.dp))

      Column(
          modifier = Modifier
              .fillMaxWidth()
              .weight(3f),
          verticalArrangement = Arrangement.Center
      ) {
          Text(text = "공지 설명")
          Spacer(modifier = Modifier.height(18.dp))
          OutlinedTextField(
              value = noticeContent.value,
              onValueChange = {
                  noticeContent.value = it
              },
              textStyle = NoteLassTheme.Typography.fourteen_600_pretendard,
              placeholder = {
                  Text(
                      "공지 설명을 입력하세요",
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
      Spacer(modifier = Modifier.height(18.dp))

      Row(
          modifier = Modifier
              .fillMaxWidth()
              .weight(1f),
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

      if (pdfUri != null) {
          fileName = fileManager.getFileName(context, pdfUri!!)
          fileSize = fileManager.getFileSize(context, pdfUri!!)

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
                          putExtra("pdfUri", pdfUri)
                      }
                      pdfUri?.path?.let { Log.e("PDFURI", it) }
                      context.startActivity(intent)
                  },
                  onDelete = {
                      pdfUri = null
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

        Row(modifier = Modifier
            .align(Alignment.End)
            .weight(2f)
        ){
            Box(modifier = Modifier.size(49.dp,40.dp)){
                RectangleUnableButton(text = "취소",
                    onClick = { TODO() })
            }
            Spacer(modifier = Modifier.width(16.dp))

            Box(modifier = Modifier.size(73.dp,40.dp)){
                RectangleEnabledButton(text = "생성하기",
                    onClick = {
                        if(noticeTitle.value.isNotEmpty() && noticeContent.value.isNotEmpty()){
                            if(requestFileList.value == null ){
                                requestFileList.value = MultipartBody.Part.createFormData("file","empty")
                            }
                            createNotice(noticeTitle.value,noticeContent.value,requestFileList.value)
                        }

                    })
            }
        }

    }
  }


@Composable
fun NoticeInfo(
    groupInfo: GroupInfo,
   // createdTime: LocalDateTime
){


    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        Text(text = "작성자",
            style =NoteLassTheme.Typography.sixteem_600_pretendard,
            color = PrimaryBlack,
        )

        groupInfo.teacherName?.let {
            Text(text = it + "선생님",
                style =NoteLassTheme.Typography.sixteem_600_pretendard,
                color = PrimarayBlue,
                textDecoration = TextDecoration.Underline
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "게시일",
            style =NoteLassTheme.Typography.sixteem_600_pretendard,
            color = PrimaryBlack
        )

        Text(text =  "2023년 11월 5일",
            style =NoteLassTheme.Typography.sixteem_600_pretendard,
            color = PrimarayBlue,
            textDecoration = TextDecoration.Underline

        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "할당된 그룹",
            style =NoteLassTheme.Typography.sixteem_600_pretendard,
            color = PrimaryBlack
        )

        groupInfo.groupName?.let {
            Text(text = it,
                style =NoteLassTheme.Typography.sixteem_600_pretendard,
                color = PrimarayBlue,
                textDecoration = TextDecoration.Underline

            )
        }



    }



}


