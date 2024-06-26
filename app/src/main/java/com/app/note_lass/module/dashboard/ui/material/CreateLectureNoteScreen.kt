package com.app.note_lass.module.dashboard.ui.material

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.OpenableColumns
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
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
import androidx.compose.material3.Divider
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.note_lass.R
import com.app.note_lass.common.DateFormatter
import com.app.note_lass.core.FilePicker.FileManager
import com.app.note_lass.core.Proto.GroupInfo
import com.app.note_lass.core.Proto.ProtoViewModel
import com.app.note_lass.core.Proto.Role
import com.app.note_lass.core.Proto.Token
import com.app.note_lass.module.note.NoteActivity
import com.app.note_lass.module.note.data.NoteRequest
import com.app.note_lass.module.dashboard.ui.viewmodel.UploadViewModel
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
fun CreateLectureNoteScreen(
    viewModel: UploadViewModel = hiltViewModel(),
    protoViewModel: ProtoViewModel = hiltViewModel(),
    goBackToGroup: (Role, Long,String) -> Unit
){
    val groupInfo  = protoViewModel.groupInfo.collectAsState(initial = GroupInfo("","",-1))
    val role  = protoViewModel.token.collectAsState(initial = Token("","",Role.NONE))

    val title = remember{
        mutableStateOf("")
    }
    val content = remember{
        mutableStateOf("")
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
            photoUri.value = null
        }
    )

    val photoLauncher = rememberLauncherForActivityResult(
        contract =  ActivityResultContracts.GetContent(),
        onResult = { uri :Uri? ->
            photoUri.value = uri
            val file = photoUri.value!!.asMultipart("file",context.contentResolver)
            requestFile.value= file
            pdfUri.value = null
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

      }else{
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




@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LectureNoteInfo(
    groupInfo: GroupInfo,
    // createdTime: LocalDateTime
){


    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        Text("강의자료  정보",
            style = TextStyle(
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(R.font.pretendard_regular)),
                fontWeight = FontWeight(700),
                color = PrimaryBlack,
            )
        )
        Spacer(modifier = Modifier.height(24.dp))

        Text(text = "작성자",
            style =NoteLassTheme.Typography.sixteem_600_pretendard,
            color = PrimaryBlack,
        )
        Spacer(modifier = Modifier.height(5.dp))

        groupInfo.teacherName?.let {
            Text(text = "$it 선생님",
                style =NoteLassTheme.Typography.sixteem_600_pretendard,
                color = PrimarayBlue,
                textDecoration = TextDecoration.Underline
            )
        }

        Divider(modifier = Modifier.padding(vertical = 16.dp), thickness = 1.dp,color = Color(0xFFEDEDFF))


        Text(text = "게시일",
            style =NoteLassTheme.Typography.sixteem_600_pretendard,
            color = PrimaryBlack
        )
        Spacer(modifier = Modifier.height(5.dp))

        val localDate = LocalDateTime.now()

        Text(text =  DateFormatter(localDate).formattedDateTime,
            style =NoteLassTheme.Typography.sixteem_600_pretendard,
            color = PrimarayBlue,
            textDecoration = TextDecoration.Underline

        )

        Divider(modifier = Modifier.padding(vertical = 16.dp), thickness = 1.dp,color = Color(0xFFEDEDFF))

        Text(text = "할당된 그룹",
            style =NoteLassTheme.Typography.sixteem_600_pretendard,
            color = PrimaryBlack
        )

        Spacer(modifier = Modifier.height(5.dp))

        groupInfo.groupName?.let {
            Text(text = it,
                style =NoteLassTheme.Typography.sixteem_600_pretendard,
                color = PrimarayBlue,
                textDecoration = TextDecoration.Underline

            )
        }
    }





}
