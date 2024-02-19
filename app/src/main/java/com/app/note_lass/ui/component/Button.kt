package com.app.note_lass.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.gestures.forEachGesture
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.note_lass.R
import com.app.note_lass.ui.theme.BackgroundGray
import com.app.note_lass.ui.theme.NoteLassTheme
import com.app.note_lass.ui.theme.NotelassTheme
import com.app.note_lass.ui.theme.PrimarayBlue
import com.app.note_lass.ui.theme.PrimaryGray

@Composable
fun RectangleButtonWithStatus(
    text : String,
    onClick : () -> Unit,
    isEnabled : Boolean = false
){
    Button(onClick = {onClick() },
        modifier = Modifier.fillMaxSize(),
        enabled = isEnabled,
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.textButtonColors(
            containerColor = PrimarayBlue,
            disabledContainerColor = BackgroundGray,
            contentColor = Color.White,
            disabledContentColor = PrimaryGray
        ),
        contentPadding = PaddingValues(0.dp)
    )
    {
        Text(
            text,
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.pretendard_regular)),
                fontWeight = FontWeight(700),
                color = Color(0xFFFFFFFF),
                textAlign = TextAlign.Center,
            )
        )

    }


}

@Composable
fun RectangleEnabledButton(
    text : String,
    onClick : () -> Unit
){
   Button(
       onClick = onClick,
       modifier = Modifier.fillMaxSize(),
       shape = RoundedCornerShape(8.dp),
       colors = ButtonDefaults.textButtonColors(containerColor = PrimarayBlue),
       contentPadding = PaddingValues(0.dp)
   )
   {
       Text(
           text,
           style = TextStyle(
               fontSize = 16.sp,
               fontFamily = FontFamily(Font(R.font.pretendard_regular)),
               fontWeight = FontWeight(700),
               color = Color(0xFFFFFFFF),
               textAlign = TextAlign.Center,
           )
       )

   }


}

@Composable
fun RectangleUnableButton(
    text : String,
    onClick : () -> Unit
){
    Button(onClick = { onClick() },
        modifier = Modifier.fillMaxSize(),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(containerColor = BackgroundGray),
        contentPadding = PaddingValues(0.dp)
    )
    {
        Text(
            text = text,
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.pretendard_regular)),
                fontWeight = FontWeight(700),
                color = Color(0xFF9EA4AA),
                textAlign = TextAlign.Center,
            )
        )

    }


}

@Composable
fun RectangleEnabledWithBorderButton(
    modifier : Modifier = Modifier,
    text : String,
    onClick : () -> Unit,
    containerColor : Color,
    textColor :  Color,
    borderColor : Color
){
    Button(onClick = {onClick() },
        modifier = modifier.fillMaxSize(),
        colors = ButtonDefaults.textButtonColors(containerColor =containerColor),
        shape = RoundedCornerShape(8.dp),
        contentPadding = PaddingValues(0.dp),
        border = BorderStroke(width = 1.dp,color = borderColor)
    )
    {
        Text(
            text,
            style =NoteLassTheme.Typography.sixteem_600_pretendard,
            color =textColor
        )

    }


}
@Composable
fun RectangleUnableWithBorderButton(
    modifier : Modifier = Modifier,
    text : String,
    onClick : () -> Unit,
    containerColor : Color,
    textColor :  Color,
    borderColor : Color
){
    Button(onClick = {onClick },
        modifier = modifier.fillMaxSize(),
        enabled = false,
        colors = ButtonDefaults.textButtonColors(containerColor =containerColor),
        shape = RoundedCornerShape(8.dp),
        contentPadding = PaddingValues(0.dp),
        border = BorderStroke(width = 1.dp,color = borderColor)
    )
    {
        Text(
            text,
            style =NoteLassTheme.Typography.sixteem_600_pretendard,
            color =textColor
        )

    }


}
@Preview
@Composable
fun ButtonPreview(){
    Column(modifier = Modifier.fillMaxSize()) {
        RectangleEnabledButton(text = "로그인") {

        }
        RectangleUnableButton(text = "다음") {

        }
    }
}