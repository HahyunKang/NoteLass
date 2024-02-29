package com.app.note_lass.ui.component


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.note_lass.R
import com.app.note_lass.common.DashboardType
import com.app.note_lass.common.DateFormatter
import com.app.note_lass.common.Resources
import com.app.note_lass.common.StringToDate
import com.app.note_lass.core.Proto.ProtoViewModel
import com.app.note_lass.module.home.tab.notice.DashBoard
import com.app.note_lass.ui.theme.NoteLassTheme
import com.app.note_lass.ui.theme.PrimaryGray

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AcademicResources(
    resources : Resources,
    goToNoticeDetail : () -> Unit
) {

    Box(
        modifier = Modifier
            .shadow(
                elevation = 7.dp,
                shape = RoundedCornerShape(size = 8.dp),
                ambientColor = Color(0x0A26282B),
                spotColor = Color(0x3D26282B)
            )
            .fillMaxWidth()
            .height(141.dp)
            .background(
                color = if (resources.unread) Color.White else Color(0xFFF5F5FC),
                shape = RoundedCornerShape(8.dp)
            )
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    goToNoticeDetail()
                }
                .padding(horizontal = 32.dp, vertical = 24.dp)
              ,
            horizontalArrangement = Arrangement.SpaceBetween,
        ){
            Column(modifier = Modifier.fillMaxHeight(),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text= "[공지]"+resources.title,
                    style = NoteLassTheme.Typography.sixteen_700_pretendard,
                    color = if(!resources.unread) PrimaryGray else Color.Black
                )
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text= resources.content,
                    style = NoteLassTheme.Typography.sixteem_600_pretendard,
                    maxLines = 3,
                    color = if(!resources.unread) PrimaryGray else Color.Black

                )
            }
            Column(
                modifier = Modifier.fillMaxHeight(),
                horizontalAlignment = Alignment.End
            ) {
                if(!resources.unread){
                    Box(
                        modifier= Modifier
                            .wrapContentSize()
                            .background(
                                color = Color(0x339EA4AA),
                                shape = RoundedCornerShape(size = 20.dp)
                            )
                            .padding(start = 8.dp, top = 2.dp, end = 8.dp, bottom = 3.dp)
                    ){
                        Text("읽음",
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontFamily = FontFamily(Font(R.font.pretendard_regular)),
                                fontWeight = FontWeight(600),
                                color = Color(0xFF9EA4AA),
                                textAlign = TextAlign.Center,
                            )
                        )
                    }

                }else{
                    Box(
                        Modifier
                            .wrapContentSize()
                            .background(
                                color = Color(0x33FF7788),
                                shape = RoundedCornerShape(size = 20.dp)
                            )
                            .padding(start = 8.dp, top = 2.dp, end = 8.dp, bottom = 3.dp)
                    ){
                        Text(
                            text = "안읽음",
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontFamily = FontFamily(Font(R.font.pretendard_regular)),
                                fontWeight = FontWeight(600),
                                color = Color(0xFFFF7788),
                                textAlign = TextAlign.Center,
                            )
                        )
                    }

                }
                Spacer(modifier = Modifier.height(31.dp))

                Text(
                    text = resources.teacher,
                    style = NoteLassTheme.Typography.fourteen_600_pretendard,
                    color = PrimaryGray
                )


                Text(text = DateFormatter(StringToDate(resources.createdDate!!).localDateTime).formattedDate,
                    style = NoteLassTheme.Typography.fourteen_600_pretendard,
                    color = PrimaryGray
                )

            }
        }

    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DashBoardForTeacher(
    resources : DashBoard,
    goToDetail : () -> Unit,
) {

    Box(
        modifier = Modifier
            .shadow(
                elevation = 7.dp,
                shape = RoundedCornerShape(size = 8.dp),
                ambientColor = Color(0x0A26282B),
                spotColor = Color(0x3D26282B)
            )
            .fillMaxWidth()
            .height(141.dp)
            .background(color = Color.White)
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    goToDetail()
                }
                .padding(horizontal = 32.dp, vertical = 24.dp)
            ,
            horizontalArrangement = Arrangement.SpaceBetween,
        ){
            Column(modifier = Modifier.fillMaxHeight(),
                horizontalAlignment = Alignment.Start
            ) {
                if(resources.noticeId!=null)Text(
                    text= "[공지]"+resources.title,
                    style = NoteLassTheme.Typography.sixteen_700_pretendard,
                    color = Color.Black
                )else{
                    Text(
                        text= "[강의자료]"+resources.title,
                        style = NoteLassTheme.Typography.sixteen_700_pretendard,
                        color = Color.Black
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text= resources.content,
                    style = NoteLassTheme.Typography.sixteem_600_pretendard,
                    maxLines = 3,
                    color = Color.Black

                )
            }
            Column(
                modifier = Modifier.fillMaxHeight(),
            ) {

                Spacer(modifier = Modifier.height(60.dp))
                resources.teacherName?.let {
                    Text(
                        text = it + " 선생님",
                        style = NoteLassTheme.Typography.fourteen_600_pretendard,
                        color = PrimaryGray
                    )
                }


                Text(text = DateFormatter(StringToDate(resources.createdDate!!).localDateTime).formattedDate,
                    style = NoteLassTheme.Typography.fourteen_600_pretendard,
                    color = PrimaryGray
                )

            }
        }

    }
}