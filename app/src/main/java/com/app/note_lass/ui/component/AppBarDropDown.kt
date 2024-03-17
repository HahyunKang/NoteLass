package com.app.note_lass.ui.component

import RetrievePDFfromUrl
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.pdf.PdfDocument
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.note_lass.R
import com.app.note_lass.core.Proto.ProtoViewModel
import com.app.note_lass.module.group.ui.viewModel.GroupForStudentViewModel
import com.app.note_lass.module.login.ui.LoginViewModel
import com.app.note_lass.module.note.NoteActivity
import com.app.note_lass.module.note.ui.NoteViewModel
import com.app.note_lass.module.dashboard.data.Material.Material
import com.app.note_lass.ui.theme.PrimaryGray
import java.io.FileOutputStream
import java.io.IOException


@Composable
fun AppBarDropDown(
    onClickLogout : () -> Unit
) {

    val protoViewModel : ProtoViewModel =  hiltViewModel()
    val loginViewModel : LoginViewModel = hiltViewModel()


    val logoutState = loginViewModel.logoutState
    val dropdownItems = listOf("개인 정보 수정", "로그아웃")
    var isContextMenuVisible by rememberSaveable {
        mutableStateOf(false)
    }

    var pressOffset by remember {
        mutableStateOf(DpOffset.Zero)
    }
    var pressIndex =  remember {
        mutableIntStateOf(0)
    }
    var itemWidth by remember {
        mutableStateOf(0.dp)
    }
    val interactionSource = remember {
        MutableInteractionSource()
    }
    val density = LocalDensity.current

    if(logoutState.value.isSuccess){
        LaunchedEffect(true) {
            onClickLogout()
        }
    }else{
    }
    Icon(painter = painterResource(id = R.drawable.appbar_arrowdown_small),
        contentDescription = null,
        tint = Color(0xFF26282B),
        modifier = Modifier
            .indication(interactionSource, LocalIndication.current)
            .pointerInput(true) {
                detectTapGestures(
                    onPress = {
                        pressOffset = DpOffset(it.x.toDp(), it.y.toDp())
                        isContextMenuVisible = true
                    }
                )

            }
            .onSizeChanged {
                itemWidth = with(density) { it.width.toDp() }
            }
    )
    Box(
        contentAlignment = Alignment.TopEnd
    ) {
        DropdownMenu(
            expanded = isContextMenuVisible,
            onDismissRequest = { isContextMenuVisible = false },

            offset = pressOffset.copy(
                x = -5.dp,
                y = pressOffset.y  + 30.dp
            )
        ) {

            dropdownItems.forEachIndexed {
                index,text->
                DropdownMenuItem(onClick = {
                    isContextMenuVisible = false
                    if(index ==0){

                    }else{
                        loginViewModel.logout(tokenViewModel = protoViewModel)
                        protoViewModel.resetGroupInfo()
                    }

                }
                ) {
                    Text(text = text,
                        textAlign = TextAlign.Center)
                }
            }
        }

    }
}




@Composable
fun MaterialDropDown(
    material  : Material
) {


    val noteViewModel : NoteViewModel = hiltViewModel()
    val groupForStudentViewModel : GroupForStudentViewModel = hiltViewModel()

    val dropdownItems = listOf("PDF로 내보내기", "노트탭에 불러오기","자료보기")
    var isContextMenuVisible by rememberSaveable {
        mutableStateOf(false)
    }

    val materialToNote = noteViewModel.getMaterialToNoteState

    var pressOffset by remember {
        mutableStateOf(DpOffset.Zero)
    }
    var pressIndex = remember {
        mutableStateOf(0)
    }
    var itemWidth by remember {
        mutableStateOf(0.dp)
    }
    val interactionSource = remember {
        MutableInteractionSource()
    }
    val density = LocalDensity.current
    val context = LocalContext.current
    val fileState = groupForStudentViewModel.getMaterialFileState

    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) {
            result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val uri = result.data?.data
           if(material.files?.get(0)?.originalFileName?.contains("pdf") == true)RetrievePDFfromUrl().addPdf(context, uri,fileState.value.result!!.stream)
            else{

               lateinit var bitmap: Bitmap
               fileState.value.result!!.stream.use { inputStream ->
                   bitmap = BitmapFactory.decodeStream(inputStream)
               }

               val pdfDocument = PdfDocument()
               val pageInfo = PdfDocument.PageInfo.Builder(bitmap.width, bitmap.height, 1).create()
               val page = pdfDocument.startPage(pageInfo)

               val canvas = page.canvas
               canvas.drawBitmap(bitmap, 0f, 0f, null)
               pdfDocument.finishPage(page)

               if (uri != null) {
                   context.contentResolver?.openFileDescriptor(uri, "w")?.use { parcelFileDescriptor ->
                       pdfDocument.writeTo(FileOutputStream(parcelFileDescriptor.fileDescriptor))
                   }
               }

               pdfDocument.close()
           }
        }
    }


    LaunchedEffect(materialToNote.value) {
        if(materialToNote.value.isSuccess) {
            Toast.makeText(context,"노트 탭에 업로드되었습니다.",Toast.LENGTH_SHORT).show()
        }
        if(materialToNote.value.isError) {
            Toast.makeText(context,"노트 탭 업로드에 실패하였습니다.",Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(fileState.value) {
        if(fileState.value.isSuccess) {
            when(pressIndex.value) {
                0-> {
                    if (material.files?.isNotEmpty() == true &&
                        fileState.value.result!!.fileId == material.files[0].id
                    ) {
                        val fileName = material.title
                        val intent = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
                            addCategory(Intent.CATEGORY_OPENABLE)
                            type = "application/pdf"
                            putExtra(Intent.EXTRA_TITLE, fileName)
                        }
                        launcher.launch(intent)
                    }
                }
                2 -> {
                    if (material.files?.isNotEmpty() == true &&
                        fileState.value.result!!.fileId == material.files[0].id
                    ) {
                        val fileMaterial = java.io.File(context.filesDir, "myFile")
                        try {
                            FileOutputStream(fileMaterial).use { outputStream ->
                                val buffer = ByteArray(1024)
                                var length: Int
                                while (fileState.value.result!!.stream.read(buffer)
                                        .also { length = it } != -1
                                ) {
                                    outputStream.write(buffer, 0, length)
                                }
                            }
                            val intent = Intent(context, NoteActivity::class.java)
                            if (material.files?.get(0)?.originalFileName?.contains("pdf") == true) {
                                intent.putExtra("filePath", fileMaterial.absolutePath)
                                intent.putExtra("pdfTitle", material.files[0].originalFileName)
                            } else {
                                intent.putExtra("photoPath", fileMaterial.absolutePath)
                                intent.putExtra(
                                    "pdfTitle",
                                    material.files?.get(0)?.originalFileName
                                )
                            }
                            context.startActivity(intent)

                        } catch (e: IOException) {
                            e.printStackTrace()
                        }
                    }
                }
            }
        }

    }
    if(materialToNote.value.isError) {
        Toast.makeText(context,"노트 탭 업로드에 실패하였습니다.",Toast.LENGTH_SHORT).show()
    }
//    if(fileState.value.isSuccess &&
//        material.files?.isNotEmpty() == true &&
//        fileState.value.result!!.fileId == material.files[0].id
//        ){
//        val fileName = material.title
//        val intent = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
//            addCategory(Intent.CATEGORY_OPENABLE)
//            type = "application/pdf"
//            putExtra(Intent.EXTRA_TITLE, fileName)
//        }
//        launcher.launch(intent)
//    }

    Icon(painter = painterResource(id = R.drawable.appbar_dropdown),
        contentDescription = null,
        tint = PrimaryGray,
        modifier = Modifier
            .indication(interactionSource, LocalIndication.current)
            .pointerInput(true) {
                detectTapGestures(
                    onPress = {
                        pressOffset = DpOffset(it.x.toDp(), it.y.toDp())
                        isContextMenuVisible = true
                    }
                )

            }
            .onSizeChanged {
                itemWidth = with(density) { it.width.toDp() }
            }
    )
    Box(
        contentAlignment = Alignment.TopEnd
    ) {
        DropdownMenu(
            expanded = isContextMenuVisible,
            onDismissRequest = { isContextMenuVisible = false },
            offset = pressOffset.copy(
                x = -5.dp,
                y = pressOffset.y
            )
        ) {

            dropdownItems.forEachIndexed {
                    index,text->
                DropdownMenuItem(onClick = {
                    isContextMenuVisible = false
                    pressIndex.value = index
                   when(index){
                       0 ->{
                        if(material.files!!.isNotEmpty())
                            groupForStudentViewModel.getFile(material.files[0].id)

                           }
                       1 ->{
                           material.files?.get(0)?.let { noteViewModel.makeMaterialToNote(materialId = it.id) }
                       }
                       2-> {
//                           val intent = Intent(context, NoteActivity::class.java).apply {
//                          //    putExtra("pdfString", material.fileUrl)
//                           }
//                         //  material.fileUrl.value?.path?.let { Log.e("PDFURI", it) }
//                           context.startActivity(intent)
                           if(material.files!!.isNotEmpty())
                           groupForStudentViewModel.getFile(material.files[0].id)

                       }
                   }

                }
                ) {
                    Text(text = text,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

    }
}

@Composable
fun NoteDropDown(
    noteId : Long,
    onClickUpdate : () -> Unit
) {

    val noteViewModel : NoteViewModel = hiltViewModel()

    val dropdownItems = listOf("내보내기", "복제하기","휴지통으로 이동")
    var isContextMenuVisible by rememberSaveable {
        mutableStateOf(false)
    }


    var pressOffset by remember {
        mutableStateOf(DpOffset.Zero)
    }
    var pressIndex = remember {
        mutableStateOf(0)
    }
    var itemWidth by remember {
        mutableStateOf(0.dp)
    }
    val interactionSource = remember {
        MutableInteractionSource()
    }
    val density = LocalDensity.current
    val context = LocalContext.current

    val noteDeleteState= noteViewModel.deleteNoteState

    LaunchedEffect(key1 = noteDeleteState.value){
        if(noteDeleteState.value.isSuccess) onClickUpdate()
    }


//   LaunchedEffect(fileState.value) {
//        if(fileState.value.isSuccess) {
//            when(pressIndex.value) {
//                0-> {
//                    if (material.files?.isNotEmpty() == true &&
//                        fileState.value.result!!.fileId == material.files[0].id
//                    ) {
//                        val fileName = material.title
//                        val intent = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
//                            addCategory(Intent.CATEGORY_OPENABLE)
//                            type = "application/pdf"
//                            putExtra(Intent.EXTRA_TITLE, fileName)
//                        }
//                        launcher.launch(intent)
//                    }
//                }
//                2 -> {
//                    if (material.files?.isNotEmpty() == true &&
//                        fileState.value.result!!.fileId == material.files[0].id
//                    ) {
//                        val fileMaterial = java.io.File(context.filesDir, "myFile")
//                        try {
//                            FileOutputStream(fileMaterial).use { outputStream ->
//                                val buffer = ByteArray(1024)
//                                var length: Int
//                                while (fileState.value.result!!.stream.read(buffer)
//                                        .also { length = it } != -1
//                                ) {
//                                    outputStream.write(buffer, 0, length)
//                                }
//                            }
//                            val intent = Intent(context, NoteActivity::class.java)
//                            if (material.files?.get(0)?.originalFileName?.contains("pdf") == true) {
//                                intent.putExtra("filePath", fileMaterial.absolutePath)
//                                intent.putExtra("pdfTitle", material.files[0].originalFileName)
//                            } else {
//                                intent.putExtra("photoPath", fileMaterial.absolutePath)
//                                intent.putExtra(
//                                    "pdfTitle",
//                                    material.files?.get(0)?.originalFileName
//                                )
//                            }
//                            context.startActivity(intent)
//
//                        } catch (e: IOException) {
//                            e.printStackTrace()
//                        }
//                    }
//                }
//            }
//        }
//
//    }

    Icon(painter = painterResource(id = R.drawable.note_arrow_down),
        contentDescription = null,
        tint = PrimaryGray,
        modifier = Modifier
            .indication(interactionSource, LocalIndication.current)
            .pointerInput(true) {
                detectTapGestures(
                    onPress = {
                        pressOffset = DpOffset(it.x.toDp(), it.y.toDp())
                        isContextMenuVisible = true
                    }
                )

            }
            .onSizeChanged {
                itemWidth = with(density) { it.width.toDp() }
            }
    )
    Box(
        contentAlignment = Alignment.TopEnd
    ) {
        DropdownMenu(
            expanded = isContextMenuVisible,
            onDismissRequest = { isContextMenuVisible = false },
            offset = pressOffset.copy(
                x = -5.dp,
                y = pressOffset.y
            )
        ) {

            dropdownItems.forEachIndexed {
                    index,text->
                DropdownMenuItem(onClick = {
                    isContextMenuVisible = false
                    pressIndex.value = index
                    when(index){
                        0 ->{
//                            if(material.files!!.isNotEmpty())
//                                groupForStudentViewModel.getFile(material.files[0].id)

                        }
                        1 ->{
                        }
                        2-> {
                            noteViewModel.deleteNote(noteId)
                        }
                    }

                }
                ) {
                    Text(text = text,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

    }
}



@Preview
@Composable
fun dropDownPreview(){
   // AppBarDropDown()
}

