package com.app.note_lass.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.app.note_lass.R
import com.app.note_lass.ui.theme.PrimaryBlack
import com.app.note_lass.ui.theme.Purple40


@Composable
fun HomeDialog(
    onDismissReQuest : (Boolean)-> Unit,
){
    Dialog(
        onDismissRequest = {
                           onDismissReQuest(false)
        },
    ) {
        Box(
            modifier = Modifier
                .wrapContentHeight()
                .wrapContentHeight()
                .background(color = Color.White, shape = RoundedCornerShape(size = 8.dp))
        ) {
            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(horizontal = 15.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    "개인정보 수정",
                    style =
                    TextStyle(
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(R.font.pretendard_regular)),
                        fontWeight = FontWeight(600),
                        color = PrimaryBlack,
                        textAlign = TextAlign.Center,
                    ),modifier = Modifier
                        .background(color = Color(0xFFF5F5FC),shape = RoundedCornerShape(size = 8.dp))
                        .padding(8.dp)

                )

                Text(
                    "로그아웃",
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(R.font.pretendard_regular)),
                        fontWeight = FontWeight(600),
                        color = PrimaryBlack,
                        textAlign = TextAlign.Center,
                    ),modifier = Modifier.background(color = Color.White)
                        .padding(8.dp)

                )

            }
        }
    }
}

@Preview
@Composable
fun previewDialog() {
    var showDialog by remember {
        mutableStateOf(true)
    }
    if (showDialog) {

        HomeDialog(onDismissReQuest ={
            showDialog= it
        })
    }
}