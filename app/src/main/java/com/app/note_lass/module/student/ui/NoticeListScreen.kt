package com.app.note_lass.module.student.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.note_lass.R
import com.app.note_lass.common.Resources
import com.app.note_lass.module.student.ui.viewmodel.StudentNoticeListViewModel
import com.app.note_lass.ui.component.AcademicResources
import com.app.note_lass.ui.component.AppBar
import com.app.note_lass.ui.component.AppBarForNotice
import com.app.note_lass.ui.theme.NoteLassTheme
import com.app.note_lass.ui.theme.PrimarayBlue
import com.app.note_lass.ui.theme.PrimaryBlack
import com.app.note_lass.ui.theme.PrimaryGray
import org.w3c.dom.Text

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NoticeListScreen(
    viewModel : StudentNoticeListViewModel = hiltViewModel(),
){

    val state =viewModel.noticeListState
    val isRead = remember{
        mutableStateOf(false)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AppBarForNotice(title = "  공지")

        },
        containerColor =  Color(0xFFF5F5FC),
        contentColor = Color.Black,
        bottomBar = {
        },
        content = {
            Column(
                modifier = Modifier.padding(top = it.calculateTopPadding(),start =40.dp,end = 20.dp)
            ) {

                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            SpanStyle(
                                color = PrimaryBlack,
                                fontSize = 16.sp,
                                fontWeight = FontWeight(700),
                                fontStyle = FontStyle(R.font.pretendard_regular)
                            )
                        ){
                            append("총 ")
                        }
                        withStyle(
                            SpanStyle(
                                color = PrimarayBlue,
                                fontSize = 16.sp,
                                fontWeight = FontWeight(700),
                                fontStyle = FontStyle(R.font.pretendard_regular)
                            )
                        ){
                            append(state.value.result?.size.toString())
                        }
                        withStyle(
                            SpanStyle(
                                color = PrimaryBlack,
                                fontSize = 16.sp,
                                fontWeight = FontWeight(700),
                                fontStyle = FontStyle(R.font.pretendard_regular)
                            )
                        ){
                            append("개")
                        }
                    }
                )

                Spacer(modifier = Modifier.height(27.dp))

                Row(){
                    Icon(
                        painter = painterResource(id = R.drawable.handbook_check_small),
                        tint = if(!isRead.value) PrimaryGray else PrimarayBlue,
                        contentDescription = "noticeRead",
                        modifier = Modifier.clickable {
                            isRead.value = !isRead.value
                        }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "안읽음",style = NoteLassTheme.Typography.fourteen_600_pretendard,color = PrimaryGray)
                }

                Spacer(modifier = Modifier.height(27.dp))

                var noticeList=  emptyList<Resources>()
                if(state.value.isSuccess)
                    noticeList = if(isRead.value) state.value.result!!.reversed().filter { it.unread }
                    else {
                        state.value.result!!.reversed()
                    }
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ){
                    items(noticeList.size){
                        AcademicResources(resources = noticeList[it])
                    }
                }

            }





        })
    }