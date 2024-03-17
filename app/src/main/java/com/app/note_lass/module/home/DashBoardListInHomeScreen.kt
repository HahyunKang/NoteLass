package com.app.note_lass.module.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.note_lass.R
import com.app.note_lass.common.DashboardType
import com.app.note_lass.ui.component.AppBarForNotice
import com.app.note_lass.ui.component.DashBoardForTeacher
import com.app.note_lass.ui.theme.PrimarayBlue
import com.app.note_lass.ui.theme.PrimaryBlack

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DashBoardListInHomeScreen(
    viewModel : HomeViewModel = hiltViewModel(),
    goToDetailScreen : (DashboardType,Long)-> Unit,
    onClickLogout : () ->Unit={},
    goBack : ()-> Unit
){

    val state =viewModel.dashBoardState
    val isRead = remember{
        mutableStateOf(false)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AppBarForNotice(title = "공지/강의자료", onClickBack = goBack)

        },
        containerColor =  Color(0xFFF5F5FC),
        contentColor = Color.Black,
        bottomBar = {
        },
        content = {
            Column(
                modifier = Modifier.padding(top = it.calculateTopPadding(),start =40.dp,end = 20.dp)
            ) {
                Spacer(modifier = Modifier.height(30.dp))
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            SpanStyle(
                                color = PrimaryBlack,
                                fontSize = 16.sp,
                                fontWeight = FontWeight(700),
                                fontStyle = FontStyle(R.font.pretendard_regular)
                            )
                        ) {
                            append("총 ")
                        }
                        withStyle(
                            SpanStyle(
                                color = PrimarayBlue,
                                fontSize = 16.sp,
                                fontWeight = FontWeight(700),
                                fontStyle = FontStyle(R.font.pretendard_regular)
                            )
                        ) {
                            append(state.value.result?.size.toString())
                        }
                        withStyle(
                            SpanStyle(
                                color = PrimaryBlack,
                                fontSize = 16.sp,
                                fontWeight = FontWeight(700),
                                fontStyle = FontStyle(R.font.pretendard_regular)
                            )
                        ) {
                            append("개")
                        }
                    }
                )

                Spacer(modifier = Modifier.height(27.dp))

                Spacer(modifier = Modifier.height(27.dp))

                if (state.value.isSuccess) {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {

                        if (state.value.result != null) {
                            itemsIndexed(state.value.result!!) { index, item ->
                                DashBoardForTeacher(resources = item) {
                                    if (item.noticeId != null) goToDetailScreen(
                                        DashboardType.NOTICE,
                                        item.noticeId
                                    )
                                    if (item.lectureMaterialId != null) goToDetailScreen(
                                        DashboardType.MATERIAL,
                                        item.lectureMaterialId
                                    )
                                }
                            }
                        }
                    }
                }
            }
        })
    }