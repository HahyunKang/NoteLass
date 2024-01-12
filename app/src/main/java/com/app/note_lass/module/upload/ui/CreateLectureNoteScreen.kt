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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.note_lass.core.FilePicker.FileManager
import com.app.note_lass.core.Proto.GroupInfo
import com.app.note_lass.core.Proto.ProtoViewModel
import com.app.note_lass.core.Proto.Role
import com.app.note_lass.core.Proto.Token
import com.app.note_lass.module.note.NoteActivity
import com.app.note_lass.module.note.data.NoteRequest
import com.app.note_lass.module.upload.data.viewmodel.UploadViewModel
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
fun CreateLectureNoteScreen(
    viewModel : UploadViewModel = hiltViewModel(),
    protoViewModel : ProtoViewModel = hiltViewModel()

){
    val groupInfo  = protoViewModel.groupInfo.collectAsState(initial = GroupInfo("","",-1))

    val title = remember{
        mutableStateOf("")
    }
    val content = remember{
        mutableStateOf("")
    }
    var pdfUri = remember {
        mutableStateOf<Uri?>(null)
    }

    var photoUri = remember {
        mutableStateOf<Uri?>(null)
    }

    val requestFile  = remember {
        mutableStateOf<MultipartBody.Part?>(null)
    }
    val context= LocalContext.current


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
        onResult = { uri :Uri? ->
            photoUri.value = uri
            val file = photoUri.value!!.asMultipart("file",context.contentResolver)
            requestFile.value= file
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


    var fileName by remember { mutableStateOf<String?>(null) }
    var fileSize by remember { mutableStateOf<Long?>(null) }
    val fileManager  = FileManager()

    Column(modifier= Modifier.fillMaxSize()) {

      Row(
          modifier = Modifier.fillMaxWidth()
              .weight(1f),
          verticalAlignment = Alignment.CenterVertically
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
      Spacer(modifier = Modifier.height(15.dp))

      Column(
          modifier = Modifier.fillMaxWidth()
              .weight(3f),
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
      Spacer(modifier = Modifier.height(18.dp))

      Row(
          modifier = Modifier.fillMaxWidth()
              .weight(1f),
          verticalAlignment = Alignment.CenterVertically
      ) {
          Text(text = "파일 첨부")
          Spacer(modifier = Modifier.width(26.dp))
          Box(modifier = Modifier.size(200.dp, 30.dp)) {
              RectangleEnabledWithBorderButton(
                  text = "라이브러리에서 파일 탐색",
                  onClick = { pdfLauncher.launch("application/pdf") },
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

      if (pdfUri.value != null) {
          fileName = fileManager.getFileName(context, pdfUri.value!!)
          fileSize = fileManager.getFileSize(context, pdfUri.value!!)

          Box(
              modifier = Modifier
                  .fillMaxWidth()
                  .weight(2f)
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

      if (photoUri.value != null) {
          fileName = fileManager.getFileName(context, photoUri.value!!)
          fileSize = fileManager.getFileSize(context, photoUri.value!!)

          Box(
              modifier = Modifier
                  .fillMaxWidth()
                  .weight(2f)
          ) {

              FileUpload(
                  title = fileName.toString(),
                  fileSize = fileSize.toString(),
                  onClick = {
                      val intent = Intent(context, NoteActivity::class.java).apply {
                          putExtra("photoUri", photoUri.value)
                      }
                      context.startActivity(intent)
                  },
                  onDelete = {
                      photoUri.value = null
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
                        if(title.value.isNotEmpty() && content.value.isNotEmpty() && requestFile.value!=null){
                            viewModel.makeMaterial(groupId = groupInfo.value.groupId!!.toLong(),
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




