package com.app.note_lass.module.record.ui

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.net.Uri
import android.provider.ContactsContract.CommonDataKinds.Note
import android.provider.OpenableColumns
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.note_lass.R
import com.app.note_lass.module.record.data.Keywords
import com.app.note_lass.module.record.data.RecordBody
import com.app.note_lass.module.record.ui.viewModel.RecordViewModel
import com.app.note_lass.module.signup.domain.presentation.RegistrationFormEvent
import com.app.note_lass.ui.component.RectangleEnabledButton
import com.app.note_lass.ui.component.ToggleButton
import com.app.note_lass.ui.component.noRippleClickable
import com.app.note_lass.ui.theme.BackgroundBlue
import com.app.note_lass.ui.theme.Gray50
import com.app.note_lass.ui.theme.NoteLassTheme
import com.app.note_lass.ui.theme.PrimarayBlue
import com.app.note_lass.ui.theme.PrimaryBlack
import com.app.note_lass.ui.theme.PrimaryGray
import com.app.note_lass.ui.theme.PrimaryPurple
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okio.BufferedSink
import okio.source
import kotlin.time.minutes


@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun StudentRecordScreen(
    recordViewModel: RecordViewModel = hiltViewModel(),
    isMemoActive : (Boolean)-> Unit
) {

    val percentCriteria = remember {
        mutableStateOf("%")
    }
    val content = remember {
        mutableStateOf("")
    }
    val interactionSource = remember { MutableInteractionSource() }

    val guideLine = remember {
        mutableStateOf("")
    }

    val keyword = remember {
        mutableStateOf("")
    }
    val excelFile = remember {
        mutableStateOf<MultipartBody.Part?>(null)
    }

    val getRecordState = recordViewModel.getRecordState
    val getScoreState = recordViewModel.getScoreState
    val getGuidelineState = recordViewModel.getGuidelineState

    LaunchedEffect(key1 = getRecordState.value.isSuccess) {
        if (getRecordState.value.isSuccess) {
            content.value = getRecordState.value.content

        }
    }
    val context = LocalContext.current

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

    val excelLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            val file = uri?.asMultipart("file", context.contentResolver)

            excelFile.value = file
            excelFile.value?.let { recordViewModel.postExcel(it) }
        }
    )


    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1.5f)
                .padding(top=7.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Text(
                        text = "활동기록 총 정리",
                        style = NoteLassTheme.Typography.twenty_700_pretendard,
                        color = PrimaryBlack,
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Row(
                        modifier = Modifier
                            .size(width = 169.dp, height = 32.dp)
                            .border(1.dp, PrimarayBlue, RoundedCornerShape(12.dp))
                            .clickable {
                                val percentage = percentCriteria.value
                                    .dropLast(1)
                                    .toIntOrNull()
                                if (percentage != null) {
                                    recordViewModel.getStudentScore(percentage)
                                }
                            },
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "과제 데이터 불러오기",
                            style = NoteLassTheme.Typography.fourteen_600_pretendard,
                            color = PrimaryBlack
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.record_arrow_small),
                            contentDescription = null,
                            tint = Color.Unspecified
                        )

                    }
                }

                Box(modifier = Modifier.size(width = 74.dp, height = 40.dp)) {
                    RectangleEnabledButton(text = "저장하기") {
                        val recordBody = RecordBody(content = content.value)
                        recordViewModel.postStudentRecord(recordBody)
                    }
                }

            }

            Text(
                text = "태도 점수: ${getScoreState.value.score.attitudeScore}점 발표횟수: ${getScoreState.value.score.presentationNum}회",
                style = NoteLassTheme.Typography.fourteen_600_pretendard,
                color = PrimaryBlack,
                modifier = Modifier.padding(vertical = 4.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.percentage_criteria),
                    style = NoteLassTheme.Typography.fourteen_600_pretendard,
                    color = PrimaryBlack,
                    modifier = Modifier.padding(vertical = 10.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))

                BasicTextField(
                    value = percentCriteria.value,
                    onValueChange = { it ->
                        if (!it.contains("%")) percentCriteria.value = "$it%"
                        else percentCriteria.value = it
                    },
                    textStyle = NoteLassTheme.Typography.twelve_600_underline_pretendard,
                    modifier = Modifier
                        .height(22.dp)
                        .width(47.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)

                ) {
                    TextFieldDefaults.TextFieldDecorationBox(
                        value = percentCriteria.value,
                        innerTextField = it,
                        singleLine = true,
                        enabled = true,
                        visualTransformation = VisualTransformation.None,
                        interactionSource = interactionSource,
                        colors = TextFieldDefaults.colors(
                            focusedTextColor = PrimarayBlue,
                            unfocusedTextColor = PrimarayBlue,
                            focusedContainerColor = BackgroundBlue,
                            unfocusedContainerColor = BackgroundBlue,
                            focusedIndicatorColor = BackgroundBlue,
                            unfocusedIndicatorColor = BackgroundBlue
                        ),
                        contentPadding = PaddingValues(0.dp)
                    )

                }

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = ":",
                    style = NoteLassTheme.Typography.fourteen_600_pretendard,
                    color = PrimaryBlack,
                    modifier = Modifier.padding(vertical = 10.dp)
                )

                getScoreState.value.score.highScoreAssignmentList.forEach {
                    Text(
                        text = it.toString(),
                        style = NoteLassTheme.Typography.fourteen_600_pretendard,
                        color = PrimaryBlack,
                        modifier = Modifier.padding(vertical = 10.dp)
                    )

                }

            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            ) {
                OutlinedTextField(
                    value = content.value,
                    onValueChange = {
                        content.value = it
                    },
                    modifier = Modifier.fillMaxSize(),
                    textStyle = NoteLassTheme.Typography.twelve_600_pretendard,
                    shape = RoundedCornerShape(10.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.LightGray,
                        unfocusedIndicatorColor = Color.LightGray
                    )
                )
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(10.dp)
                ) {
                    Text(
                        "${content.value.length}/1500byte",
                        style = NoteLassTheme.Typography.twelve_600_pretendard,
                        color = Color.Gray
                    )
                }
            }
            TextButton(
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .size(width = 130.dp, height = 22.dp)
                        .align(Alignment.End),
                    colors = ButtonDefaults.buttonColors(containerColor = BackgroundBlue),
                    contentPadding = PaddingValues(3.dp),
            onClick = {
                excelLauncher.launch("application/octet-stream")
            }
            )
                {
            Text(
                "한셀에서 가져오기 >",
                style = NoteLassTheme.Typography.twelve_600_pretendard,
                color = PrimarayBlue, textAlign = TextAlign.Center
            )
                }

        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1.2f),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = "가이드라인 문장",
                    style = NoteLassTheme.Typography.twenty_700_pretendard,
                    color = PrimaryBlack,
                )
                Spacer(modifier = Modifier.width(5.dp))
                Row(
                    modifier = Modifier
                        .size(width = 169.dp, height = 32.dp)
                        .border(1.dp, PrimarayBlue, RoundedCornerShape(20.dp))
                        .clickable {
                            val percentage = percentCriteria.value
                                .dropLast(1)
                                .toIntOrNull()
                            if (percentage != null) {
                                recordViewModel.getStudentScore(percentage)
                            }
                        },
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "학생수첩 내용 연동하기",
                        style = NoteLassTheme.Typography.fourteen_600_pretendard,
                        color = PrimaryBlack,
                        modifier = Modifier.clickable {
                            isMemoActive(true)
                        }
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.record_arrow_small),
                        contentDescription = null,
                        tint = Color.Unspecified
                    )

                }
            }

            val textFieldsCount = remember {
                mutableIntStateOf(1)
            }
            val texts = remember {
                mutableStateOf(mutableStateListOf<String>(""))
            }
            val keywordSelected = remember {
                mutableStateOf(mutableStateListOf<String>())
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "키워드 입력 : ",
                        style = NoteLassTheme.Typography.sixteem_600_pretendard,
                        color = PrimaryBlack,
                        modifier = Modifier.padding(vertical = 5.dp)
                    )
                    texts.value.forEachIndexed { i, text ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .padding(4.dp)
                                .background(BackgroundBlue, RoundedCornerShape(6.dp))
                                .clip(RoundedCornerShape(6.dp))
                        ) {
                            BasicTextField(
                                value = text,
                                onValueChange = { it ->
                                    texts.value[i] = it
                                },
                                textStyle = NoteLassTheme.Typography.sixteem_600_pretendard,
                                modifier = Modifier
                                    .height(25.dp),
                                enabled = (i == textFieldsCount.intValue - 1)
                            ) {
                                TextFieldDefaults.TextFieldDecorationBox(
                                    value = texts.value[i],
                                    innerTextField = it,
                                    singleLine = true,
                                    enabled = (i == textFieldsCount.intValue - 1),
                                    visualTransformation = VisualTransformation.None,
                                    interactionSource = interactionSource,
                                    colors = TextFieldDefaults.colors(
                                        focusedTextColor = PrimarayBlue,
                                        unfocusedTextColor = PrimarayBlue,
                                        focusedContainerColor = BackgroundBlue,
                                        unfocusedContainerColor = BackgroundBlue,
                                        focusedIndicatorColor = BackgroundBlue,
                                        unfocusedIndicatorColor = BackgroundBlue,
                                        disabledContainerColor = BackgroundBlue,
                                        disabledIndicatorColor = BackgroundBlue
                                    ),
                                    contentPadding = PaddingValues(3.dp)
                                )
                            }

                            if (i != textFieldsCount.intValue - 1) {
                                Text(
                                    text = "X",
                                    modifier = Modifier.clickable {
                                        texts.value.remove(text)
                                        textFieldsCount.intValue--
                                    }
                                )
                            }
                        }
                    }
                    Log.e("index", textFieldsCount.intValue.toString())

                    Button(
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = BackgroundBlue,
                            contentColor = Color.Black
                        ),
                        onClick = {
                            textFieldsCount.intValue++
                            texts.value.add(" ")
                        }
                    ) {
                        Text(text = "+")
                    }

                }

                Icon(
                    painter = painterResource(id = R.drawable.record_keyword_small),
                    contentDescription = null,
                    tint = PrimarayBlue,
                    modifier = Modifier.clickable {
                        var keywordList = listOf<String>()
                        keywordList = keywordSelected.value + texts.value
                        recordViewModel.getGuideline(keywordList, emptyList())
                    }
                )
            }


            FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp),
                verticalArrangement = Arrangement.spacedBy(3.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Keywords.KEYWORDLIST.forEach {
                    ToggleButton(
                        text = it,
                        getSelected = { selected ->
                            if (selected) keywordSelected.value.add(it)
                            else keywordSelected.value.remove(it)
                        }
                    )
                }
            }

            Log.e("keyword", keywordSelected.value.size.toString())
            guideLine.value = getGuidelineState.value.guideLine

            OutlinedTextField(
                value = guideLine.value,
                onValueChange = {
                    guideLine.value = it
                },
                modifier = Modifier
                    .height(100.dp)
                    .fillMaxWidth(),
                textStyle = NoteLassTheme.Typography.twelve_600_pretendard,
                shape = RoundedCornerShape(10.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.LightGray,
                    unfocusedIndicatorColor = Color.LightGray
                )
            )

        }
    }
}

@Preview
@Composable
fun previewStudentRecord(){
  //  StudentRecordScreen()
}