package com.app.note_lass.module.home.tab.cafeteria.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.note_lass.R
import com.app.note_lass.ui.theme.PrimarayBlue
import com.app.note_lass.ui.theme.PrimaryBlack

@Composable
fun previewCafeteria(
    lunchList : List<String>,
    dinnerList : List<String>
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            . padding(start= 20.dp,top = 10.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Row(modifier = Modifier
            .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ){
            Text("중식",
                style = TextStyle(
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.pretendard_regular)),
                fontWeight = FontWeight(600),
                color = PrimarayBlue,
            )
            )
            Spacer(modifier = Modifier.width(8.dp))
            Icon(painter = painterResource(id = R.drawable.cafeteria_sun_small),null,
                tint = PrimarayBlue, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(16.dp))
            lunchList.forEachIndexed { index, s ->
                if(index == lunchList.size-1){
                    Text(text = lunchList[index],
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontFamily = FontFamily(Font(R.font.pretendard_regular)),
                            fontWeight = FontWeight(600),
                            color = PrimaryBlack,
                        ))
                }else{
                Text(text = lunchList[index] + ",")

                }
            }
        }
        Spacer(modifier = Modifier.width(30.dp))

        Row(modifier = Modifier
            .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically){
            Text("석식",
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.pretendard_regular)),
                    fontWeight = FontWeight(600),
                    color = PrimarayBlue,
                )
            )
            Spacer(modifier = Modifier.width(8.dp))
            Icon(painter = painterResource(id = R.drawable.cafeteria_moon_small),tint = PrimarayBlue, contentDescription = null,
                modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(16.dp))
           dinnerList.forEachIndexed { index, s ->
                if(index == lunchList.size-1){
                    Text(text = dinnerList[index],
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontFamily = FontFamily(Font(R.font.pretendard_regular)),
                            fontWeight = FontWeight(600),
                            color = PrimaryBlack,
                        ))
                }else{
                    Text(text = dinnerList[index] + ",")

                }
            }
        }
    }

}