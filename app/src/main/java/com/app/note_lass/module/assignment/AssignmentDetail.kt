package com.app.note_lass.module.assignment

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.app.note_lass.ui.theme.NoteLassTheme
import com.app.note_lass.ui.theme.PrimarayBlue
import com.app.note_lass.ui.theme.PrimaryBlack

@Composable
fun AssignmentDetail() {

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        Text(text = "마감 기한",
            style = NoteLassTheme.Typography.sixteem_600_pretendard,
            color = PrimaryBlack,
        )

        Text(
            text =  "23.05.01 , 12:00 AM",
            style = NoteLassTheme.Typography.sixteem_600_pretendard,
            color = PrimarayBlue,
            textDecoration = TextDecoration.Underline
        )

    }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "게시일",
            style = NoteLassTheme.Typography.sixteem_600_pretendard,
            color = PrimaryBlack
        )

        Text(text =  "2023년 11월 5일",
            style = NoteLassTheme.Typography.sixteem_600_pretendard,
            color = PrimarayBlue,
            textDecoration = TextDecoration.Underline

        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "할당된 그룹",
            style = NoteLassTheme.Typography.sixteem_600_pretendard,
            color = PrimaryBlack
        )


            Text(text = "수학",
                style = NoteLassTheme.Typography.sixteem_600_pretendard,
                color = PrimarayBlue,
                textDecoration = TextDecoration.Underline

            )
        }



