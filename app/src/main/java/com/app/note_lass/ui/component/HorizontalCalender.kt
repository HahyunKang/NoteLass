package com.app.note_lass.ui.component

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.note_lass.R
import com.app.note_lass.ui.theme.PrimarayBlue
import com.app.note_lass.ui.theme.PrimaryBlack
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.Locale

data class HorizontalCalendarConfig(
    val yearRange: IntRange = 1970..2100, // 연도 범위, 기본값: 1970부터 2100까지
    val locale: Locale = Locale.getDefault() // 로캘(언어 및 국가) 설정, 기본값: 기기의 로캘
    // 다른 캘린더 구성 옵션을 필요에 따라 추가할 수 있음
)
@OptIn(ExperimentalFoundationApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HorizontalCalendar(
    modifier: Modifier = Modifier,
    currentDate: LocalDate = LocalDate.now(),
    config: HorizontalCalendarConfig = HorizontalCalendarConfig(),
    onSelectedDate: (LocalDate) -> Unit) {
    val initialPage = (currentDate.year - config.yearRange.first) * 12 + currentDate.monthValue - 1
    var currentSelectedDate by remember { mutableStateOf(currentDate) }
    var currentMonth by remember { mutableStateOf(YearMonth.now()) }
    var currentPage by remember { mutableStateOf(initialPage) }
    var pageYear by remember { mutableStateOf(2023) }
    val pageCount = (config.yearRange.last - config.yearRange.first) * 12

    var pageMonth by remember { mutableStateOf(1) }
    val pagerState = rememberPagerState(
        initialPage = initialPage,
        initialPageOffsetFraction = 0f
    ) {
        pageCount
    }
    LaunchedEffect(pagerState.currentPage) {
        val addMonth = (pagerState.currentPage - currentPage).toLong()
        currentMonth = currentMonth.plusMonths(addMonth)
        currentPage = pagerState.currentPage
    }

    LaunchedEffect(currentSelectedDate) {
        onSelectedDate(currentSelectedDate)
    }
    val dateFormatter = DateTimeFormatter.ofPattern("MM월")
    Column(modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally) {
        val headerText = pageYear.toString()  + "년" + pageMonth.toString() + "월"
        CalendarHeader(
            modifier = Modifier.padding(20.dp),
            text = headerText
        )
        // 페이징 성능 개선을 위한 조건문
        HorizontalPager(
            modifier = Modifier,
            state = pagerState){
            page->
                val date = LocalDate.of(
                    config.yearRange.first + page / 12,
                    page % 12 + 1,
                    1
                )

                LaunchedEffect(pagerState.currentPage) {
                    val year = config.yearRange.first + pagerState.currentPage / 12
                    val month = pagerState.currentPage % 12 + 1
                    pageYear = year
                    pageMonth = month
                }

                if (page in pagerState.currentPage - 1..pagerState.currentPage + 1) {
                    // 페이징 성능 개선을 위한 조건문


                    CalendarMonthItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp),
                        currentDate = date,
                        selectedDate = currentSelectedDate,
                        onSelectedDate = { date ->
                            currentSelectedDate = date
                        }
                    )
                }
            }

    }
}

@Composable
fun CalendarHeader(
    modifier: Modifier = Modifier,
    text: String,
) {
    Box(modifier = modifier) {
        Text(
            text = text,
            style = TextStyle(
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(R.font.pretendard_regular)),
                fontWeight = FontWeight(700),
                color = Color(0xFF26282B),
                textAlign = TextAlign.Center,
            )
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarMonthItem(
    modifier: Modifier = Modifier,
    currentDate: LocalDate,
    selectedDate: LocalDate,
    onSelectedDate: (LocalDate) -> Unit) {
    val lastDay by remember { mutableStateOf(currentDate.lengthOfMonth()) }
    val firstDayOfWeek by remember { mutableStateOf(currentDate.dayOfWeek.value) }
    val days by remember { mutableStateOf(IntRange(1, lastDay).toList()) }

    Column(modifier = modifier) {
        DayOfWeek()
        LazyVerticalGrid(
            modifier = Modifier.height(260.dp),
            columns = GridCells.Fixed(7)
        ) {
            if(firstDayOfWeek!=7) {
                for (i in 0 until firstDayOfWeek) { // 처음 날짜가 시작하는 요일 전까지 빈 박스 생성
                    item {
                        Box(
                            modifier = Modifier
                                .size(30.dp)
                                .padding(top = 10.dp)
                        )
                    }
                }
            }
            items(days) { day ->
                val date = currentDate.withDayOfMonth(day)
                val isSelected = remember(selectedDate) {
                    selectedDate.compareTo(date) == 0
                }
                CalendarDay(
                    modifier = Modifier.padding(top = 10.dp),
                    date = date,
                    isToday = date == LocalDate.now(),
                    isSelected = isSelected,
                    onSelectedDate = onSelectedDate
                )
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarDay(
    modifier: Modifier = Modifier,
    date: LocalDate,
    isToday: Boolean,
    isSelected: Boolean,
    onSelectedDate: (LocalDate) -> Unit
) {
    val hasEvent = false// TODO
    Column(
        modifier = modifier
            .wrapContentSize()
            .size(30.dp)
            .clip(shape = RoundedCornerShape(10.dp))
//            .conditional(isToday) {
//                background(gray07)
//            }
//            .conditional(isSelected) {
//                background(gray0)
//            }
//            .conditional(!isToday && !isSelected) {
//                background(gray08)
//            }
            .noRippleClickable { onSelectedDate(date) },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center

    ) {
     //   val textColor = if (isSelected) gray09 else gray0
        val textColor = if(isToday) PrimarayBlue else PrimaryBlack
        val modifier = if(isToday) Modifier
            .background(color = Color(0xFFEDEDFF),shape = CircleShape)
            .padding(1.dp)
        else Modifier
        Text(
            modifier = modifier,
            textAlign = TextAlign.Center,
            text = date.dayOfMonth.toString(),
         //   style = BoldN12,
          color = textColor
        )
        if (hasEvent) {
            Box(
                modifier = Modifier
                    .size(4.dp)
                    .clip(shape = RoundedCornerShape(4.dp))
//                    .conditional(isSelected) {
//                        background(gray09)
//                    }
//                    .conditional(!isSelected) {
//                        background(gray0)
//                    }
            )
        }
    }
}

@Composable
fun DayOfWeek(
    modifier: Modifier = Modifier
) {
    Row(modifier) {
        DayOfWeek.values().forEach { dayOfWeek ->
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                text = dayOfWeek.dayOfWeek,
                style = TextStyle(
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(R.font.pretendard_regular)),
                    fontWeight = FontWeight(600),
                    color = Color(0xFF9EA4AA),
                    textAlign = TextAlign.Center,
                )
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
@Preview
fun calendarPreview(){
    HorizontalCalendar(onSelectedDate = {})
}