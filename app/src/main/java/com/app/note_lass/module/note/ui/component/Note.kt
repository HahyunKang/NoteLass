package com.app.note_lass.module.note.ui.component

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.app.note_lass.R
import com.app.note_lass.common.DateFormatter
import com.app.note_lass.ui.theme.NoteLassTheme
import com.app.note_lass.ui.theme.PrimarayBlue
import com.app.note_lass.ui.theme.PrimaryGray
import java.time.LocalDateTime

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Note(
    title : String,
    teacher : String,
    onClickArrow : () -> Unit = {}
){
  //  val formatDate = DateFormatter(date).formattedDate
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Row(
            verticalAlignment = Alignment.CenterVertically
        ){
            Icon(painter = painterResource(id = R.drawable.note_lecture_small),contentDescription = null)
            Spacer(modifier = Modifier.width(24.dp))
            Column(
                verticalArrangement = Arrangement.Center
            ){
                Text(text = title,
                    style = NoteLassTheme.Typography.sixteem_600_pretendard
                )
                Text(text = teacher,
                    style = NoteLassTheme.Typography.twelve_600_pretendard,
                    color = PrimaryGray
                )
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically
        ){
            Icon(painter = painterResource(id = R.drawable.note_arrow_down),tint = PrimarayBlue, contentDescription = null)
            Spacer(modifier = Modifier.width(24.dp))
            Icon(painter = painterResource(id = R.drawable.note_star_small),tint = PrimarayBlue, contentDescription = null)

        }

    }
}