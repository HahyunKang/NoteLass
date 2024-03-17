package com.app.note_lass.module.student.ui

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.note_lass.R
import com.app.note_lass.common.StringToDate
import com.app.note_lass.module.student.data.handbook.HandBook
import com.app.note_lass.module.student.ui.viewmodel.StudentMemoViewModel
import com.app.note_lass.ui.theme.NoteLassTheme
import com.app.note_lass.ui.theme.PrimaryBlack

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HandBookListScreen(
    isMemoActive: Boolean,
    getHandBookList: (List<Int>) -> Unit,
    handBooks : List<HandBook>,
    studentId: Long,
    isDelete : (Long) -> Unit,
    isModify : (String,Long) -> Unit,
    isClickEvaluation : () -> Unit,
    studentMemoViewModel: StudentMemoViewModel = hiltViewModel()
) {
    val scrollState = rememberScrollState()
    val idList = remember {
        mutableStateOf(mutableListOf<Int>())
    }
    val handbookListState = studentMemoViewModel.getHandBookListUseCase
    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "학생 수첩",
                style = NoteLassTheme.Typography.twenty_700_pretendard,
                color = PrimaryBlack,
                modifier = Modifier.padding(vertical = 16.dp)
            )
            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .background(
                        color = Color(0xFFEDEDFF),
                        shape = RoundedCornerShape(size = 6.dp)
                    )
                    .padding(start = 8.dp, top = 2.dp, end = 8.dp, bottom = 3.dp)
                    .clickable {
                        isClickEvaluation()
                    }
            ) {
                Text(
                    "학생의 자기평가서 보기",
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.pretendard_regular)),
                        fontWeight = FontWeight(600),
                        color = Color(0xFF4849FF),
                        textAlign = TextAlign.Center,
                    )
                )
            }
        }
            handBooks.forEach { handbook ->
                val formatDate = StringToDate(handbook.createdDate).localDateTime
                HandBookContent(
                    date = formatDate,
                    content = handbook.content,
                    isMemoActive = isMemoActive,
                    isChecked = {
                        if (!it) idList.value.remove(handbook.id)
                        else idList.value.add(handbook.id)
                        getHandBookList(idList.value)
                        Log.e("학생수첩 test", idList.value.size.toString())
                    },
                    isDelete = {
                        isDelete(handbook.id.toLong())
                    },
                    isModify = {
                        isModify(handbook.content, handbook.id.toLong())
                    }
                )
                Spacer(modifier = Modifier.height(15.dp))

            }

        }

}