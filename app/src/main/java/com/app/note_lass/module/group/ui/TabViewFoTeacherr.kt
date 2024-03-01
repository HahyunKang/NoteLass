package com.app.note_lass.module.group.ui

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
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.app.note_lass.core.navigation.HomeScreen
import com.app.note_lass.module.home.tab.cafeteria.ui.previewCafeteria
import com.app.note_lass.module.home.tab.notice.data.previewNotice
import com.app.note_lass.module.home.tab.notice.ui.PreviewNotice
import com.app.note_lass.module.home.tab.schedule.ui.TableScreen
import com.app.note_lass.module.home.tab.schedule.ui.previewTable
import com.app.note_lass.ui.component.SectionHeader
import com.app.note_lass.ui.theme.NoteLassTheme
import com.app.note_lass.ui.theme.NoteLassTypography
import com.app.note_lass.ui.theme.PrimarayBlue
import com.app.note_lass.ui.theme.PrimaryBlack
import com.app.note_lass.ui.theme.PrimaryGray
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Composable
fun TabViewForTeacher(
    titleList : List<String>,
    text : String,
    tabSelected : (Int)->Unit
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
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp, vertical = 20.dp),
                horizontalArrangement= Arrangement.SpaceBetween

            ) {

                Text(text= text ,
                    style = NoteLassTheme.Typography.twenty_700_pretendard,
                    color = PrimaryBlack
                )

                TabRow(
                    selectedTabIndex = selectedTabIndex,
                    indicator = {},
                    divider = {},
                    modifier = Modifier
                        .width(150.dp),
                    containerColor = Color.White

                ) {
                    titleList.forEachIndexed { index, item ->
                        Tab(
                            selected = index == selectedTabIndex,
                            onClick = {
                                selectedTabIndex = index
                                tabSelected(selectedTabIndex)
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