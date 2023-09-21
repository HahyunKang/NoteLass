package com.app.note_lass.ui.component

import android.text.Layout.Alignment
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PaintingStyle.Companion.Stroke
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CircleArc(
    percentage: Float,
    number : Int,
    backgroundColor : Color,
    color : Color,
    radius : Dp,
    strokeWidth : Dp = 6.dp
    ){
    Box(
      modifier= Modifier.size(radius*2f)
    ){
        Canvas(modifier = Modifier.fillMaxSize()
            ){

           drawArc(
               color =backgroundColor ,
               -90f,
                360f,
                false,
               style = Stroke(strokeWidth.toPx(),cap = StrokeCap.Round))
            drawArc(
                color = color,
                -90f,
                360* percentage,
                useCenter = false,
                style = Stroke(strokeWidth.toPx(),cap = StrokeCap.Round)
            )

        }
        Text(
            text = number.toString(),
            color = Color.Black,
            fontSize = 12.sp,
            modifier = Modifier.align(androidx.compose.ui.Alignment.Center)
        )
    }

}

@Preview
@Composable
fun arcpreview(){
    Box(
        modifier = Modifier.padding(top=50.dp, start = 30.dp)
            .size(48.dp)
    ){
    CircleArc(percentage = 0.5f, number =12 , backgroundColor = Color(0x4DFF7788) , color = Color(0xFFFF7788), radius = 24.dp)
}
    }