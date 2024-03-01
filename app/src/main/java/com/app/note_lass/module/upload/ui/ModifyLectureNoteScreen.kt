package com.app.note_lass.module.upload.ui

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import android.provider.OpenableColumns
import android.widget.Toast
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
import androidx.compose.runtime.LaunchedEffect
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
import com.app.note_lass.common.File
import com.app.note_lass.core.FilePicker.FileManager
import com.app.note_lass.core.Proto.GroupInfo
import com.app.note_lass.core.Proto.ProtoViewModel
import com.app.note_lass.core.Proto.Role
import com.app.note_lass.core.Proto.Token
import com.app.note_lass.module.note.NoteActivity
import com.app.note_lass.module.upload.ui.viewmodel.UploadViewModel
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
import java.io.FileOutputStream
import java.io.IOException

@Composable
fun ModifyLectureNoteScreen(
    viewModel: UploadViewModel = hiltViewModel(),
    protoViewModel: ProtoViewModel = hiltViewModel(),
    title:String,
    content : String,
    file : List<File>?,
    goBackToGroup: (Role, Long, String) -> Unit
){
    val context= LocalContext.current

    val groupInfo  = protoViewModel.groupInfo.collectAsState(initial = GroupInfo("","",-1))
    val role  = protoViewModel.token.collectAsState(initial = Token("", Role.NONE))
    val fileState = viewModel.getMaterialFileState.value
    val materialTitle = remember{
        mutableStateOf(title)
    }
    val materialContent = remember{
        mutableStateOf(content)
    }
    val originalFileName = remember{
        mutableStateOf("")
    }

    val fileIds =  remember {
        mutableStateOf(mutableListOf<Long>())
    }

    val material  =  remember {
        mutableStateOf(file)
    }
    val pdfUri = remember {
        mutableStateOf<Uri?>(null)
    }

    val photoUri = remember {
        mutableStateOf<Uri?>(null)
    }

    val requestFile  = remember {
        mutableStateOf<MultipartBody.Part?>(null)
    }
    if(viewModel.modifyState.value.isSuccess){
        goBackToGroup(Role.TEACHER,groupInfo.value.groupId!!,groupInfo.value.groupName!!)
        Toast.makeText(context,"강의자료 수정이 완료되었습니다.",1).show()
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
    val pdfLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri :Uri? ->
            pdfUri.value = uri
            val newFile = pdfUri.value!!.asMultipart("file",context.contentResolver)
            requestFile.value= newFile
            photoUri.value = null
        }
    )

    val photoLauncher = rememberLauncherForActivityResult(
        contract =  ActivityResultContracts.GetContent(),
        onResult = { uri :Uri? ->
            photoUri.value = uri
            val newFile = photoUri.value!!.asMultipart("file",context.contentResolver)
            requestFile.value= newFile
            pdfUri.value = null
        }
    )
    LaunchedEffect(fileState) {
        if(fileState.isSuccess) {

            val fileMaterial = java.io.File(context.filesDir, "myFile")
            try {
                FileOutputStream(fileMaterial).use { outputStream ->
                    val buffer = ByteArray(1024)
                    var length: Int
                    while (fileState.result!!.stream.read(buffer)
                            .also { length = it } != -1
                    ) {
                        outputStream.write(buffer, 0, length)
                    }
                }
                val intent = Intent(context, NoteActivity::class.java)
                if (originalFileName.value.contains("pdf")) {
                    intent.putExtra("filePath", fileMaterial.absolutePath)
                    intent.putExtra("pdfTitle", originalFileName.value)
                } else {
                    intent.putExtra("photoPath", fileMaterial.absolutePath)
                    intent.putExtra(
                        "pdfTitle",
                        originalFileName.value
                    )
                }
                context.startActivity(intent)

            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

    }


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
              value = materialTitle.value,
              onValueChange = {
                  materialTitle.value = it
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
              value = materialContent.value,
              onValueChange = {
                  materialContent.value = it
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
                          putExtra("pdfTitle",  fileName)
                      }
                      context.startActivity(intent)
                  },
                  onDelete = {
                      pdfUri.value = null
                  }
              )

          }
      } else if (photoUri.value != null) {
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
                          putExtra("pdfTitle",  fileName)
                      }
                      context.startActivity(intent)
                  },
                  onDelete = {
                      photoUri.value = null
                  }
              )
          }

      }else if(material.value!!.isNotEmpty()){
          Box(
              modifier = Modifier
                  .fillMaxWidth()
                  .weight(2f)
          ) {

              FileUpload(
                  title = material.value!![0].originalFileName,
                  fileSize = material.value!![0].size.toString(),
                  onClick = {

                      originalFileName.value = material.value!![0].originalFileName
                      viewModel.getFile(material.value!![0].id)

                            },
                  onDelete = {
                      fileIds.value = (fileIds.value + material.value!![0].id).toMutableList()
                      material.value = emptyList()
                  }
              )
          }
      }
      else{
          Box(
              modifier = Modifier
                  .fillMaxWidth()
                  .weight(2f)
          )
      }
        if(viewModel.makeMaterialState.value.isSuccess){
            Toast.makeText(context,"강의자료 생성이 완료되었습니다",Toast.LENGTH_SHORT).show()
            goBackToGroup(role.value.role,groupInfo.value.groupId!!,groupInfo.value.groupName!!)
        }

        Row(modifier = Modifier
            .align(Alignment.End)
            .weight(2f)
        ){
            Box(modifier = Modifier.size(49.dp,40.dp)){
                RectangleUnableButton(text = "취소",
                    onClick = {
                        goBackToGroup(role.value.role,groupInfo.value.groupId!!,groupInfo.value.groupName!!)
                    }
                )
            }
            Spacer(modifier = Modifier.width(16.dp))

            Box(modifier = Modifier.size(73.dp,40.dp)){
                RectangleEnabledButton(text = "수정하기",
                    onClick = {
                        if(fileIds.value.isEmpty() && file!!.isNotEmpty()){
                            fileIds.value = (fileIds.value +file[0].id).toMutableList()
                        }
                        if(materialTitle.value.isNotEmpty() && materialContent.value.isNotEmpty() && (
                                photoUri.value!= null || pdfUri.value!=null || material.value!!.isNotEmpty()
                                )
                            ){
                            viewModel.modifyMaterial(groupInfo.value.groupId!!,materialTitle.value,
                                materialContent.value,fileIds.value,requestFile.value)
                        }

                    }
                )

            }

        }

    }
}





