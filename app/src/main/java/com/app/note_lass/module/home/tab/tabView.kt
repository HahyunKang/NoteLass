package com.app.note_lass.module.home.tab

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
import androidx.leanback.widget.Row
import androidx.navigation.NavController
import com.app.note_lass.R
import com.app.note_lass.common.DashboardType
import com.app.note_lass.core.navigation.HomeScreen
import com.app.note_lass.module.home.tab.cafeteria.ui.previewCafeteria
import com.app.note_lass.module.home.tab.notice.DashBoard
import com.app.note_lass.module.home.tab.notice.data.previewNotice
import com.app.note_lass.module.home.tab.notice.ui.PreviewNotice
import com.app.note_lass.module.home.tab.schedule.ui.TableScreen
import com.app.note_lass.module.home.tab.schedule.ui.previewTable
import com.app.note_lass.ui.theme.NoteLassTheme
import com.app.note_lass.ui.theme.NoteLassTypography
import com.app.note_lass.ui.theme.PrimarayBlue
import com.app.note_lass.ui.theme.PrimaryGray
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun tabView(
    titleList : List<String>,
    noticeList : List<DashBoard>,
   onGoToDashBoard : () -> Unit,
    onGoToDetail : (DashboardType,Long)->Unit

){
    val coroutineScope = rememberCoroutineScope()

    var selectedTabIndex by remember{
        mutableStateOf(0)
    }

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 15.dp, vertical = 10.dp)
                    .weight(1f),
                horizontalArrangement= Arrangement.SpaceBetween

            ) {
                TabRow(selectedTabIndex = selectedTabIndex, indicator = {}, divider = {},
                    modifier = Modifier
                        .width(108.dp),

                ) {
                    titleList.forEachIndexed { index, item ->
                        Tab(
                            selected = index == selectedTabIndex,
                            onClick = {
                                selectedTabIndex = index
                            },
                            interactionSource = NoRippleInteractionSource()
                        ) {
                            if (index == selectedTabIndex) {
                                Box(
                                    Modifier
                                        .wrapContentWidth()
                                        .height(23.dp)
                                        .background(
                                            color = Color(0xFFEDEDFF),
                                            shape = RoundedCornerShape(size = 50.dp)
                                        )
                                        .padding(
                                            start = 8.dp,
                                            top = 4.dp,
                                            end = 8.dp,
                                            bottom = 3.dp
                                        )
                                )
                                {

                                    Text(
                                        text = item,
                                        fontSize = 12.sp,
                                        fontFamily = FontFamily(Font(R.font.pretendard_regular)),
                                        fontWeight = FontWeight(600),
                                        color = PrimarayBlue,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.align(Alignment.Center)
                                    )
                                }

                            } else {
                                Box(
                                    Modifier
                                        .wrapContentWidth()
                                        .height(23.dp)
                                        .background(
                                            color = Color(0xFFF5F5FC),
                                            shape = RoundedCornerShape(size = 50.dp)
                                        )
                                        .padding(
                                            start = 8.dp,
                                            top = 4.dp,
                                            end = 8.dp,
                                            bottom = 3.dp
                                        )
                                )
                                {

                                    Text(
                                        text = item,
                                        fontSize = 12.sp,
                                        fontFamily = FontFamily(Font(R.font.pretendard_regular)),
                                        fontWeight = FontWeight(600),
                                        color = Color(0xFF9EA4AA),
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.align(Alignment.Center)

                                    )
                                }


                            }
                        }
                    }
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable {
                        onGoToDashBoard()
                    }
                ){
                    Text("전체보기",
                        style = NoteLassTheme.Typography.twelve_600_pretendard,
                        color = PrimaryGray
                    )
                    Modifier.width(10.dp)
                    Icon(painter = painterResource(id = R.drawable.home_assignment_arrow),
                        contentDescription = null,tint= Color(0xFF9EA4AA) )
                }




            }
            val lunchList = listOf("등심돈까스" ,"우동국"," 마카로니 콘샐러드", "쌀밥", "단무지", "배추김치")
            val dinnerList = listOf("닭고기 김치덮밥" ,"미역국","유채나물", "깍두기")

              Box(modifier = Modifier.weight(2f)){
                  when(selectedTabIndex){
                      0 -> {
                          PreviewNotice(noticeList =  noticeList,
                              onGoToDetail = onGoToDetail
                          )
                      }
//                      1-> {
//                              TableScreen()
//
//                      }
//                      2->{
//                          previewCafeteria(lunchList = lunchList, dinnerList = dinnerList)
//
//                      }
                  }



              }


        }

    }


class NoRippleInteractionSource : MutableInteractionSource {
    override val interactions: Flow<Interaction>
        get() = emptyFlow()

    override suspend fun emit(interaction: Interaction) {}
    override fun tryEmit(interaction: Interaction): Boolean = true
}

@Composable
@Preview
fun TabViewPreview(){
    val items = listOf("학교 공지","급식","시간표")
    //tabView(titleList = items,)
}