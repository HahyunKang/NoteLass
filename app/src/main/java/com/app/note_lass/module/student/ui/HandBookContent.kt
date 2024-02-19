package com.app.note_lass.module.student.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.app.note_lass.R
import com.app.note_lass.common.DateFormatter
import com.app.note_lass.ui.theme.NoteLassTheme
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment
import java.time.LocalDateTime

@RequiresApi(Build.VERSION_CODES.O)

@Composable
fun HandBookContent(
    date : LocalDateTime,
    content : String,
    isMemoActive : Boolean,
    isChecked : (Boolean) -> Unit,
    isDelete : () -> Unit,
    isModify : () -> Unit
){
    val formatDate = DateFormatter(date).formattedDate
    val checked = remember {
        mutableStateOf(false)
    }
    Column(verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = formatDate,
                    style = NoteLassTheme.Typography.twelve_600_pretendard,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.width(5.dp))
                if(isMemoActive){
                    if(checked.value)
                    {
                        Icon(painter = painterResource(id = R.drawable.handbook_check_small), contentDescription = "check", tint = Color.Unspecified,
                            modifier = Modifier.clickable {
                                checked.value = false
                                isChecked(false)
                            }
                        )

                    }
                    else{
                        Icon(painter = painterResource(id = R.drawable.handbook_uncheck_small), contentDescription = "uncheck",tint = Color.Unspecified,
                            modifier = Modifier.clickable {
                                checked.value = true
                                isChecked(true)
                            }
                        )
                    }
                }

            }

            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "수정",
                    style = NoteLassTheme.Typography.twelve_600_pretendard,
                    color = Color.Gray,
                    modifier = Modifier.clickable {
                        isModify()
                    }
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "삭제",
                    style = NoteLassTheme.Typography.twelve_600_pretendard,
                    color = Color.Gray,
                    modifier = Modifier.clickable {
                        isDelete()
                    }
                )
            }

        }
        Spacer(modifier = Modifier.height(8.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color.LightGray, RoundedCornerShape(12.dp))
                .wrapContentHeight()
        ) {
            Text(
                text = content,
                style = NoteLassTheme.Typography.fourteen_600_pretendard,
                color = Color.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(vertical = 15.dp)
            )
        }

    }
}

