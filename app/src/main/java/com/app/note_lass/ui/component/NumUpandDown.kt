package com.app.note_lass.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.note_lass.R
import com.app.note_lass.ui.theme.NoteLassTheme
import com.app.note_lass.ui.theme.PrimaryBlack

@Composable
fun NumUpAndDown(
    isNumChange : (Int) -> Unit
){

    val num = remember{
        mutableStateOf(0)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .border(2.dp, color = Color.LightGray,shape = RoundedCornerShape(12.dp))
            .background(Color.White,shape= RoundedCornerShape(12.dp) )

    ){
        Row(modifier= Modifier
            .align(Alignment.Center)
            .fillMaxSize()
            .padding(6.dp)
        ){

            Box(modifier= Modifier
                .weight(1f)
                .height(20.dp)
                .background(Color.LightGray,shape = RoundedCornerShape(8.dp))
                .clickable {
                        num.value --
                        isNumChange(num.value)

                }
            ){
                Icon(painter = painterResource(
                    id = R.drawable.memo_down_fill),
                    tint = Color.Gray,
                    contentDescription = "down_icon",
                    modifier = Modifier.align(Alignment.Center))
            }


            Text(text= num.value.toString(),
                style = NoteLassTheme.Typography.sixteem_600_pretendard,
                textAlign = TextAlign.Center,
                color = PrimaryBlack,
                modifier = Modifier.weight(3f)
            )


            Box(modifier= Modifier
                .weight(1f)
                .height(20.dp)
                .background(Color.LightGray, shape = RoundedCornerShape(8.dp))
                .clickable {
                    num.value ++
                    isNumChange(num.value)
                }
            ){
                Icon(painter = painterResource(
                    id = R.drawable.memo_up_fill),
                    tint = Color.Gray,
                    contentDescription = "up_icon",
                    modifier = Modifier.align(Alignment.Center))
            }


        }
    }
}

@Preview
@Composable
fun upAndDownPreview(){
    Box(modifier = Modifier.size(width = 80.dp, height = 32.dp)){
   // NumUpAndDown()
}
}