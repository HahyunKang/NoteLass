package com.app.note_lass.module.home.tab.notice.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.note_lass.R
import com.app.note_lass.common.DashboardType
import com.app.note_lass.common.DateFormatter
import com.app.note_lass.common.StringToDate
import com.app.note_lass.module.home.tab.notice.DashBoard
import com.app.note_lass.module.home.tab.notice.data.previewNotice
import com.app.note_lass.ui.theme.PrimaryBlack

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PreviewNotice(
    noticeList :List<DashBoard>,
    onGoToDetail : (DashboardType,Long)->Unit
){

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ) {
        noticeList.forEachIndexed {
            index,dashboard->
            if(index<2) {
                PreviewNoticeComponent(title = dashboard.title, date =
                DateFormatter(StringToDate(dashboard.createdDate).localDateTime).formattedDateTime,
                    onGoToDetail = {
                        if (dashboard.noticeId != null) onGoToDetail(
                            DashboardType.NOTICE,
                            dashboard.noticeId
                        )
                        if (dashboard.lectureMaterialId != null) onGoToDetail(
                            DashboardType.MATERIAL,
                            dashboard.lectureMaterialId
                        )
                    })


            }
        }
    }
}



@Composable
fun PreviewNoticeComponent(
    title : String,
    date : String,
    onGoToDetail : ()->Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 15.dp, vertical =5.dp)
            .clickable { onGoToDetail() },
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(

        ) {
            Text(
                text = title,
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.pretendard_regular)),
                    fontWeight = FontWeight(600),
                    color = PrimaryBlack,
                )
            )
            Text(
                text = date,
                style = TextStyle(
                    fontSize = 10.sp,
                    fontFamily = FontFamily(Font(R.font.pretendard_regular)),
                    fontWeight = FontWeight(600),
                    color = Color(0xFF9EA4AA),
                )
            )
        }

//        if(isRead){
//
//            Box(
//                modifier= Modifier
//                    .wrapContentSize()
//                    .background(color = Color(0x339EA4AA), shape = RoundedCornerShape(size = 20.dp))
//                    .padding(start = 8.dp, top = 2.dp, end = 8.dp, bottom = 3.dp)
//            ){
//                Text("읽음",
//                    style = TextStyle(
//                        fontSize = 14.sp,
//                        fontFamily = FontFamily(Font(R.font.pretendard_regular)),
//                        fontWeight = FontWeight(600),
//                        color = Color(0xFF9EA4AA),
//                        textAlign = TextAlign.Center,
//                    )
//                    )
//            }
//
//        }else{
//            Box(
//                Modifier
//                    .wrapContentSize()
//                    .background(color = Color(0x33FF7788), shape = RoundedCornerShape(size = 20.dp))
//                    .padding(start = 8.dp, top = 2.dp, end = 8.dp, bottom = 3.dp)
//            ){
//                Text(
//                    text = "안읽음",
//                    style = TextStyle(
//                        fontSize = 14.sp,
//                        fontFamily = FontFamily(Font(R.font.pretendard_regular)),
//                        fontWeight = FontWeight(600),
//                        color = Color(0xFFFF7788),
//                        textAlign = TextAlign.Center,
//                    )
//                )
//            }
//
//        }
//
//
//    }


    }
}