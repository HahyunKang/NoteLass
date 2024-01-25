package com.app.note_lass.module.student.ui.component

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import com.app.note_lass.common.StringToDate
import com.app.note_lass.module.upload.data.Material.Material
import com.app.note_lass.ui.component.MaterialDropDown
import com.app.note_lass.ui.theme.NoteLassTheme
import com.app.note_lass.ui.theme.PrimaryGray

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MaterialForStudent(
    material : Material,
){
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){
        Row(
            verticalAlignment = Alignment.CenterVertically
        ){
            Icon(painter = painterResource(id = R.drawable.note_lecture_small),contentDescription = null,tint = Color.Unspecified)
            Spacer(modifier = Modifier.width(16.dp))
            Column(verticalArrangement = Arrangement.Center) {
                Text(
                    text = material.title,
                    style = NoteLassTheme.Typography.fourteen_600_pretendard
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = DateFormatter(StringToDate(material.createdDate!!).localDateTime).formattedDateTime,
                    style = NoteLassTheme.Typography.fourteen_600_pretendard,
                    color = PrimaryGray
                )

            }
        }
        MaterialDropDown(material = material)
    }

}