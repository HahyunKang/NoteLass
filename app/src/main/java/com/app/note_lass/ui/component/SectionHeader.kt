package com.app.note_lass.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.note_lass.R
import com.app.note_lass.ui.theme.PrimaryBlack

@Composable
fun SectionHeader(
    title : String,
    onTouchIcon : ()-> Unit = {}
)
{
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.
        fillMaxWidth().
        padding( vertical = 8.dp)
    ){
        Text(title,
            style = TextStyle(
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(R.font.pretendard_regular)),
                fontWeight = FontWeight(700),
                color = PrimaryBlack,
            )
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable {
                onTouchIcon()
            }
        ){
            Text("전체보기",
                style = TextStyle(
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(R.font.pretendard_regular)),
                    fontWeight = FontWeight(600),
                    color = Color(0xFF9EA4AA),
                )
            )
            Modifier.width(20.dp)
            Icon(painter = painterResource(id = R.drawable.home_assignment_arrow),
                contentDescription = null,tint= Color(0xFF9EA4AA) )
        }

    }

}

@Composable
fun SectionHeaderWithCreate(
    title : String,
    onTouchWatchAll : ()-> Unit = {},
    onTouchCreate : () -> Unit = {}
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            title,
            style = TextStyle(
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(R.font.pretendard_regular)),
                fontWeight = FontWeight(700),
                color = PrimaryBlack,
            )
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                "전체보기",
                style = TextStyle(
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(R.font.pretendard_regular)),
                    fontWeight = FontWeight(600),
                    color = Color(0xFF9EA4AA),
                ),
                modifier = Modifier.clickable {
                    onTouchWatchAll()
                }
            )
            Modifier.width(20.dp)
            Icon(
                painter = painterResource(id = R.drawable.home_assignment_arrow),
                contentDescription = null, tint = Color(0xFF9EA4AA)
            )
            Modifier.width(25.dp)

            Text(
                "생성하기 +",
                style = TextStyle(
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(R.font.pretendard_regular)),
                    fontWeight = FontWeight(600),
                    color = Color(0xFF9EA4AA),
                ),
                modifier = Modifier.clickable {
                    onTouchCreate()
                }
            )
        }

    }
}