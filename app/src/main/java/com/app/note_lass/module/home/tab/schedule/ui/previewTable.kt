package com.app.note_lass.module.home.tab.schedule.ui

import android.text.Layout.Alignment
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.component1
import androidx.core.graphics.component2
import com.app.note_lass.ui.theme.NoteLassTheme
import com.app.note_lass.ui.theme.PrimarayBlue

@Composable
fun RowScope.TableCell(
    text: String,
    weight: Float
) {
    Text(
        text = text,
        Modifier
            .border(0.5.dp, PrimarayBlue)
            .weight(weight)
            .padding(8.dp),
        textAlign = TextAlign.Center

    )
}

@Composable
fun ColumnScope.TableCell(
    text: String?,
    weight: Float
) {
    if (text != null) {
        Box(
            Modifier
                .border(0.5.dp, PrimarayBlue)
                .weight(weight)
                .padding(8.dp)
        ) {
            Text(
                text = text,
                modifier = Modifier.fillMaxSize(),
                textAlign = TextAlign.Center

            )
        }
    } else {
        Box(
            Modifier
                .border(0.5.dp, PrimarayBlue)
                .weight(weight)
                .padding(8.dp)
        ) {
            Text(
                text = "",
                modifier = Modifier.fillMaxSize(),
                textAlign = TextAlign.Center

            )
        }
    }
}
@Composable
fun ColumnScope.TableCellBackGround(
    text: String?,
    weight: Float
) {
    if (text != null) {
        Box(
            Modifier
                .background(Color(0xFFF5F5FC))
                .border(0.5.dp, PrimarayBlue)
                .weight(weight)
                .padding(8.dp)
        ) {
            Text(
                text = text,
                modifier = Modifier.fillMaxSize(),
                textAlign = TextAlign.Center

            )
        }
    } else {
        Box(
            Modifier
                .background(Color(0xFFF5F5FC))
                .border(0.5.dp, PrimarayBlue)
                .weight(weight)
                .padding(8.dp)
        ) {
            Text(
                text = "",
                modifier = Modifier.fillMaxSize(),
                textAlign = TextAlign.Center

            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColumnScope.TextField(
    text: String?,
    weight: Float
) {
    var textValue by remember {
        mutableStateOf("")
    }
    Box(
        modifier = Modifier
        .border(0.5.dp, PrimarayBlue)
            .padding(20.dp)
        .weight(weight)){

        androidx.compose.material3.TextField(
            modifier = Modifier.align(androidx.compose.ui.Alignment.Center),
            value = textValue,
            onValueChange = { it ->
                textValue = it
            },
            textStyle = NoteLassTheme.Typography.fourteen_600_pretendard,
            colors = TextFieldDefaults.textFieldColors(containerColor = Color.White,
                focusedIndicatorColor = Color.Black)
        )
    }




}


@Composable
fun TableScreen() {
    // Just a fake data... a Pair of Int and String
    val list = listOf<String>("1교시", "2교시", "3교시", "4교시", "5교시", "6교시", "7교시")
    val listsubject = listOf("수학", "영어", "문학", "과학", "비문학", "음악", "체육")
    val totalList = listOf(list, listsubject)

    Column(
        Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(vertical = 5.dp, horizontal = 10.dp)
            .border(1.dp, Color.White)) {
        // Here is the header
            Row(Modifier.background(Color(0xFFF5F5FC))
                ) {
                list.forEachIndexed { index, s ->
                    TableCell(text = list[index], weight = 1f)

                }
            }
            Row(Modifier.background(Color.White)) {
                listsubject.forEachIndexed { index, s ->
                    TableCell(text = listsubject[index], weight = 1f)
                }
            }
        }

}

@Composable
fun TableScreen_All() {
    // Just a fake data... a Pair of Int and String
    val list = listOf<String?>(null, "1", "2", "3", "4", "5", "6", "7")
    val listTime = listOf(
        "시간",
        "9:00~9:50",
        "10.00~10:50",
        "11:00~11:50",
        "12:00~13:00",
        "13:00~14:50",
        "14:00~15:50",
        "15:00~16:50"
    )
    val week = listOf("월", "화", "수", "목", "금")

    Row(
        Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(vertical = 5.dp, horizontal = 10.dp)
    ) {
        // Here is the header
        Column(
            Modifier
                .background(Color(0xFFF5F5FC))
                .weight(1f)
        ) {
            list.forEachIndexed { index, s ->
                if (index == 0) TableCell(text = list[index], weight = 0.4f)
                else TableCell(text = list[index], weight = 1f)
            }
        }
        Column(
            Modifier
                .background(Color.White)
                .weight(3f)
        ) {
            listTime.forEachIndexed { index, s ->
                if (index == 0) TableCellBackGround(text = listTime[index], weight = 0.4f)
                else TableCell(text = listTime[index], weight = 1f)

            }
        }
        Row(
            Modifier
                .background(Color.White)
                .weight(20f)
        ) {
            week.forEachIndexed { index1, s ->
                Column(
                    Modifier
                        .background(Color.White)
                        .weight(3f)
                ) {
                    listTime.forEachIndexed { index, s ->
                        if (index == 0) TableCellBackGround(text = week[index1], weight = 0.4f)
                        else TextField(text = "", weight = 1f)

                    }
                }
            }

        }

    }
}
    @Composable
    fun previewTable() {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(20.dp)
        ) {
            val list = listOf<String>("1교시", "2교시", "3교시", "4교시", "5교시", "6교시", "7교시")
            val listsubject = listOf("수학", "영어", "3교시", "4교시", "5교시", "6교시", "7교시")
            val totalList = listOf(list, listsubject)

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                content = {
                    itemsIndexed(totalList) { rowIndex, row ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            for (cellValue in row) {
                             //   TableCell(text = cellValue.toString())

                                // 열 간의 구분 선 추가
                                if (rowIndex != 0) {
                                    Divider(
                                        modifier = Modifier
                                            .width(1.dp)
                                            .fillMaxHeight(),
                                        color = Color.Gray
                                    )
                                }
                            }
                        }
                    }
                })


        }

    }

    @Composable
    fun TableCells(text: String) {

        Text(
            text = text,
            modifier = Modifier.padding(4.dp)
        )
    }

@Composable
@Preview
fun previewTables(){
   TableScreen_All()
}


