package com.app.note_lass.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.note_lass.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    title : String,
    badgeCount : Int,
    onArrowClick : () -> Unit,
    isGroupButton : Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(start = 280.dp, end = 48.dp, top = 50.dp)
            .background(color = Color.Transparent),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(
            title, fontSize = 20.sp,
            fontFamily = FontFamily(Font(R.font.pretendard_regular)),
            fontWeight = FontWeight(700),
            color = Color(0xFF26282B)
            )


        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.width(384.dp)

        ){
            Box(modifier = Modifier
                .width(224.dp)
               ){
            SearchBar(hintText = "노트, 학습자료")
            }
            Spacer(modifier = Modifier.width(20.dp))

            Box(
                modifier = Modifier.size(36.dp) // 아이콘과 뱃지의 크기를 조절합니다.
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.appbar_bell_small),
                    contentDescription = null,
                    tint = Color(0xFF26282B),
                    modifier = Modifier
                        .fillMaxSize() // 아이콘이 Box 내부를 가득 채우도록 합니다.
                )

                Badge(
                    modifier = Modifier
                        .offset(16.dp, -5.dp) // 뱃지의 위치를 조절하여 겹치도록 합니다.
                ) {
                    Text(
                        text = badgeCount.toString(),
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(R.font.pretendard_regular)),
                            fontWeight = FontWeight(600),
                            color = Color(0xFFFFFFFF),
                            textAlign = TextAlign.Center,
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.width(20.dp))
            Icon(painter = painterResource(id = R.drawable.appbar_person_circle_small), contentDescription = null)
            Spacer(modifier = Modifier.width(20.dp))
            AppBarDropDown()
        }
    }


}



@Composable
@Preview
fun appBarPreview(){
    AppBar(title = "홈", badgeCount = 12, onArrowClick = { /*TODO*/ }, isGroupButton = false)
}