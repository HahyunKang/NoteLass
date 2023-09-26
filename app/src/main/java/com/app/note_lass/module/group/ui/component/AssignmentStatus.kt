package com.app.note_lass.module.group.ui.component

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.app.note_lass.R
import com.app.note_lass.ui.theme.Gray50
import com.app.note_lass.ui.theme.NoteLassTheme
import com.app.note_lass.ui.theme.PrimarayBlue
import com.app.note_lass.ui.theme.PrimaryGray
import com.app.note_lass.ui.theme.PrimaryGreen
import com.app.note_lass.ui.theme.PrimaryPink
import com.app.note_lass.ui.theme.PrimaryPurple
import com.app.note_lass.ui.theme.PrimaryYellow
import com.app.note_lass.ui.theme.arcPurple

@Composable
fun AssignmentDoneStatus(
    title : String,
    ratio : Float?,
    totalScore : Int,
    score: Int,
    isGraded : Boolean
) {

    var backgroundColor: Color = Gray50
    if (isGraded) {
        if (ratio != null) {
            Log.e("ratio",ratio.toString())
            backgroundColor = if (ratio <= 0.3) PrimaryPink
            else if (ratio <= 0.6) PrimaryYellow
            else PrimaryGreen
        }
    }


    Box(modifier = Modifier.fillMaxWidth()
        .height(40.dp)) {
        Row(
            modifier = Modifier.wrapContentWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(
                painter = painterResource(id = R.drawable.group_note_small),
                tint = PrimaryGray,
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = title,
                style = NoteLassTheme.Typography.fourteen_600_pretendard
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = if (isGraded) "채점 완료" else "제출 완료",
                style = NoteLassTheme.Typography.fourteen_600_pretendard,
                color = PrimaryGray,
                modifier = Modifier.background(
                    color = Gray50,
                    shape = RoundedCornerShape(20.dp)
                )
                    .padding(horizontal = 8.dp, vertical = 5.dp),
                textAlign = TextAlign.Center
            )


        }

        Text(
            text = if (isGraded) "$score/$totalScore"
            else " -/$totalScore ",
            style = NoteLassTheme.Typography.fourteen_600_pretendard,
            color = Color.White,
            modifier = Modifier.background(
                color = backgroundColor,
                shape = RoundedCornerShape(20.dp)
            )
                .padding(horizontal = 8.dp, vertical = 5.dp)
                .align(Alignment.CenterEnd),
            textAlign = TextAlign.Center
        )

    }
}

@Composable
fun AssignmentNotSubmit(
    title : String,
    deadline : Int,
){
    Row(
        modifier = Modifier.wrapContentWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Icon(
            painter = painterResource(id = R.drawable.group_note_small),
            tint = PrimarayBlue,
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = title,
            style = NoteLassTheme.Typography.fourteen_600_pretendard
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "D-$deadline",
            style = NoteLassTheme.Typography.fourteen_600_pretendard,
            color = PrimarayBlue,
            modifier = Modifier.background(
                color = arcPurple,
                shape = RoundedCornerShape(20.dp)
            )
                .padding(horizontal = 8.dp, vertical = 5.dp),
            textAlign = TextAlign.Center
        )


    }

}