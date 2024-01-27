package com.app.note_lass.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import com.app.note_lass.R

private val Pretendard= FontFamily(
    Font(R.font.pretendard_regular, FontWeight.Medium), //500
)

private val sfProrounded= FontFamily(
    Font(R.font.sfprorounded_regular, FontWeight.Medium), //500
)


@Suppress("DEPRECATION")
val defaultTextStyle = TextStyle(
    platformStyle = PlatformTextStyle( //플랫폼 텍스트 스타일
        includeFontPadding = false   //텍스트 주위의 폰트 padding을 제거
    ),
    lineHeightStyle = LineHeightStyle( //줄 간격 스타일
        alignment = LineHeightStyle.Alignment.Center, //텍스트의 줄 간격을 수직 중앙에 맞게 설정
        trim = LineHeightStyle.Trim.None //텍스트 주위의 여백을 제거하지 않고 유지하도록 설정
    )
)

@OptIn(ExperimentalTextApi::class)
data class NoteLassTypography (

    val twelve_600_pretendard : TextStyle = defaultTextStyle.copy(
        fontFamily = Pretendard,
        fontWeight = FontWeight(600),
        fontSize = 12.sp,
    ),

    val fourteen_600_pretendard : TextStyle = defaultTextStyle.copy(
        fontFamily = Pretendard,
        fontWeight = FontWeight(600),
        fontSize = 14.sp,
    ),

    val sixteem_600_pretendard : TextStyle = defaultTextStyle.copy(
        fontFamily = Pretendard,
        fontWeight = FontWeight(600),
        fontSize = 16.sp,
    ),
    val sixteen_700_pretendard : TextStyle = defaultTextStyle.copy(
        fontFamily = Pretendard,
        fontWeight = FontWeight(700),
        fontSize = 16.sp,
    ),

    val twenty_600_pretendard : TextStyle = defaultTextStyle.copy(
        fontFamily = Pretendard,
        fontWeight = FontWeight(600),
        fontSize = 20.sp,
    ),
    val twenty_700_pretendard : TextStyle = defaultTextStyle.copy(
        fontFamily = Pretendard,
        fontWeight = FontWeight(700),
        fontSize = 20.sp,
    ),

    val thritytwo_700_pretendard : TextStyle = defaultTextStyle.copy(
        fontFamily = Pretendard,
        fontWeight = FontWeight(700),
        fontSize = 32.sp,
    ),
    val fourtyeight_700_pretendard : TextStyle = defaultTextStyle.copy(
        fontFamily = Pretendard,
        fontWeight = FontWeight(700),
        fontSize = 48.sp,
    ),
    val twelve_600_underline_pretendard : TextStyle = defaultTextStyle.copy(
        fontFamily = Pretendard,
        fontWeight = FontWeight(700),
        textAlign = TextAlign.Center,
        fontSize = 12.sp,
        textDecoration = TextDecoration.Underline
    ),
    )