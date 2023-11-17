package com.app.note_lass.module.record.ui

import android.provider.ContactsContract.CommonDataKinds.Note
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.note_lass.R
import com.app.note_lass.module.record.data.RecordBody
import com.app.note_lass.module.record.ui.viewModel.RecordViewModel
import com.app.note_lass.module.signup.domain.presentation.RegistrationFormEvent
import com.app.note_lass.ui.component.RectangleEnabledButton
import com.app.note_lass.ui.theme.BackgroundBlue
import com.app.note_lass.ui.theme.Gray50
import com.app.note_lass.ui.theme.NoteLassTheme
import com.app.note_lass.ui.theme.PrimarayBlue
import com.app.note_lass.ui.theme.PrimaryBlack
import com.app.note_lass.ui.theme.PrimaryGray
import com.app.note_lass.ui.theme.PrimaryPurple


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentRecordScreen(
    groupId : Long,
    recordViewModel: RecordViewModel = hiltViewModel()
) {

    val percentCriteria = remember{
        mutableStateOf("%")
    }
    val content = remember {
        mutableStateOf("")
    }

    val guideLine = remember {
        mutableStateOf("")
    }
    val getRecordState= recordViewModel.getRecordState
    if(getRecordState.value.isSuccess){
        content.value = getRecordState.value.content
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "총 활동 기록",
            style = NoteLassTheme.Typography.twenty_700_pretendard,
            color = PrimaryBlack,
            modifier = Modifier.padding(vertical = 24.dp)
        )

        Text(
            text = "태도 점수: 10점 발표횟수: 5회",
            style = NoteLassTheme.Typography.fourteen_600_pretendard,
            color = PrimaryBlack,
            modifier = Modifier.padding(vertical = 10.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.record_summary),
                style = NoteLassTheme.Typography.fourteen_600_pretendard,
                color = PrimaryBlack,
                modifier = Modifier.padding(vertical = 10.dp)
            )
            val interactionSource = remember { MutableInteractionSource() }

            BasicTextField(
                value = percentCriteria.value,
                onValueChange = { it ->
                    if (!it.contains("%")) percentCriteria.value = "$it%"
                    else percentCriteria.value = it
                },
                textStyle = NoteLassTheme.Typography.twelve_600_underline_pretendard,
                modifier = Modifier
                    .height(22.dp)
                    .width(47.dp)

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

        }

        Box(modifier = Modifier.size(width = 542.dp, height = 210.dp)) {
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
                    unfocusedContainerColor = Color.Transparent
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
                TextButton(
                    modifier = Modifier.size(width = 130.dp, height = 22.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = BackgroundBlue),
                    contentPadding = PaddingValues(3.dp),
                    onClick = {
                        recordViewModel.getStudentRecord(groupId)
                    }
                ) {
                    Text(
                        "한셀에서 가져오기 >",
                        style = NoteLassTheme.Typography.twelve_600_pretendard,
                        color = PrimarayBlue, textAlign = TextAlign.Center
                    )
                }

            }


        }

        Text(
            text = "가이드라인 문장",
            style = NoteLassTheme.Typography.twenty_700_pretendard,
            color = PrimaryBlack,
            modifier = Modifier.padding(vertical = 24.dp)
        )

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
                unfocusedContainerColor = Color.Transparent
            )
        )

        Box(modifier = Modifier.align(Alignment.End)) {
            RectangleEnabledButton(text = "저장하기") {
                val recordBody = RecordBody(content = content.value)
                recordViewModel.postStudentRecord(groupId,recordBody)
            }
        }


    }
}

@Preview
@Composable
fun previewStudentRecord(){
  //  StudentRecordScreen()
}