package com.app.note_lass.ui.component

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.app.note_lass.R
import com.app.note_lass.core.Proto.Role


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    title : String,
    badgeCount : Int,
    role : Role,
    isGroupButton : Boolean,
    onGroupClick : () -> Unit  = {},
    onClickLogout : () -> Unit ={}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(start = 30.dp, end = 48.dp, top = 50.dp)
            .background(color = Color.Transparent),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(
            title, fontSize = 20.sp,
            fontFamily = FontFamily(Font(R.font.pretendard_regular)),
            fontWeight = FontWeight(700),
            color = Color(0xFF26282B)
            )
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ){
            Log.e("role in Log(Test in AppBar)",role.toString())
          if(isGroupButton && role == Role.TEACHER)
          {
              Box(modifier = Modifier
                  .width(76.dp)
                  .height(40.dp)
              ){
                  RectangleEnabledButton(text = "그룹 생성") {
                      onGroupClick()
                  }
              }

          }else if(isGroupButton && role == Role.STUDENT){
              Box(modifier = Modifier
                  .width(76.dp)
                  .height(40.dp)
              ){
                  RectangleEnabledButton(text = "그룹 입장") {
                      onGroupClick()
                  }
              }

          }
            Spacer(modifier = Modifier.width(20.dp))

            Box(modifier = Modifier
                .width(224.dp)
               ){
            SearchBar(hintText = "노트, 학습자료")
            }
            Spacer(modifier = Modifier.width(20.dp))

            Box(
                modifier = Modifier.size(36.dp) // 아이콘과 뱃지의 크기를 조절합니다.
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.appbar_bell_small),
                    contentDescription = null,
                    tint = Color(0xFF26282B),
                    modifier = Modifier
                        .fillMaxSize() // 아이콘이 Box 내부를 가득 채우도록 합니다.
                )

                Badge(
                    modifier = Modifier
                        .offset(16.dp, -5.dp) // 뱃지의 위치를 조절하여 겹치도록 합니다.
                ) {
                    Text(
                        text = badgeCount.toString(),
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(R.font.pretendard_regular)),
                            fontWeight = FontWeight(600),
                            color = Color(0xFFFFFFFF),
                            textAlign = TextAlign.Center,
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.width(20.dp))
            Icon(painter = painterResource(id = R.drawable.appbar_person_circle_small), contentDescription = null)
            Spacer(modifier = Modifier.width(20.dp))
            AppBarDropDown(onClickLogout = onClickLogout)
        }
    }


}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBarForTeacherInGroup(
    title : String,
    badgeCount : Int,
    onGroupInfoClick : () -> Unit= {},
    onGroupClick : () -> Unit  = {},
    onSelfEvaluationClick: () -> Unit = {},
    onClickLogout : () -> Unit = {},
    goBack: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(start = 30.dp, end = 48.dp, top = 50.dp)
            .background(color = Color.Transparent),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){
        Icon(painter = painterResource(id = R.drawable.arrow_left_small), contentDescription = null,modifier=Modifier.clickable {
            goBack()
        })

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            title, fontSize = 20.sp,
            fontFamily = FontFamily(Font(R.font.pretendard_regular)),
            fontWeight = FontWeight(700),
            color = Color(0xFF26282B)
        )

        Box(modifier = Modifier
            .width(76.dp)
            .height(40.dp)
        ){
            RectangleEnabledButton(text = "그룹 정보") {
                onGroupInfoClick()
            }
        }
        Box(modifier = Modifier
            .width(76.dp)
            .height(40.dp)
        ){
            RectangleEnabledButton(text = "학생등록") {
                onGroupClick()
            }
        }
        Box(modifier = Modifier
            .width(133.dp)
            .height(40.dp)
        ){
            RectangleEnabledButton(text = "자기평가서 생성") {
                onSelfEvaluationClick()
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
        ){

            Box(modifier = Modifier
                .width(224.dp)
            ){
                SearchBar(hintText = "노트, 학습자료")
            }
            Spacer(modifier = Modifier.width(20.dp))

            Box(
                modifier = Modifier.size(36.dp) // 아이콘과 뱃지의 크기를 조절합니다.
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.appbar_bell_small),
                    contentDescription = null,
                    tint = Color(0xFF26282B),
                    modifier = Modifier
                        .fillMaxSize() // 아이콘이 Box 내부를 가득 채우도록 합니다.
                )

                Badge(
                    modifier = Modifier
                        .offset(16.dp, -5.dp) // 뱃지의 위치를 조절하여 겹치도록 합니다.
                ) {
                    Text(
                        text = badgeCount.toString(),
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(R.font.pretendard_regular)),
                            fontWeight = FontWeight(600),
                            color = Color(0xFFFFFFFF),
                            textAlign = TextAlign.Center,
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.width(20.dp))
            Icon(painter = painterResource(id = R.drawable.appbar_person_circle_small), contentDescription = null)
            Spacer(modifier = Modifier.width(20.dp))
            AppBarDropDown(onClickLogout = onClickLogout)
        }
    }


}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBarForStudentInGroup(
    title : String,
    badgeCount : Int,
    onSelfEvaluationClick : () -> Unit= {},
    onClickLogout : () -> Unit = {},
    goBack : () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(start = 30.dp, end = 48.dp, top = 50.dp)
            .background(color = Color.Transparent),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){
        Row(
            verticalAlignment = Alignment.CenterVertically,
            ) {
            Icon(
                painter = painterResource(id = R.drawable.arrow_left_small),
                contentDescription = null,
                modifier = Modifier.clickable {
                    goBack()
                }
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                title, fontSize = 20.sp,
                fontFamily = FontFamily(Font(R.font.pretendard_regular)),
                fontWeight = FontWeight(700),
                color = Color(0xFF26282B)
            )
            Spacer(modifier = Modifier.width(12.dp))

            Box(
                modifier = Modifier
                    .width(76.dp)
                    .height(40.dp)
            ) {
                RectangleEnabledButton(text = "자기평가") {
                    onSelfEvaluationClick()
                }
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
        ){

            Box(modifier = Modifier
                .width(224.dp)
            ){
                SearchBar(hintText = "노트, 학습자료")
            }
            Spacer(modifier = Modifier.width(20.dp))

            Box(
                modifier = Modifier.size(36.dp) // 아이콘과 뱃지의 크기를 조절합니다.
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.appbar_bell_small),
                    contentDescription = null,
                    tint = Color(0xFF26282B),
                    modifier = Modifier
                        .fillMaxSize() // 아이콘이 Box 내부를 가득 채우도록 합니다.
                )

                Badge(
                    modifier = Modifier
                        .offset(16.dp, -5.dp) // 뱃지의 위치를 조절하여 겹치도록 합니다.
                ) {
                    Text(
                        text = badgeCount.toString(),
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(R.font.pretendard_regular)),
                            fontWeight = FontWeight(600),
                            color = Color(0xFFFFFFFF),
                            textAlign = TextAlign.Center,
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.width(20.dp))
            Icon(painter = painterResource(id = R.drawable.appbar_person_circle_small), contentDescription = null)
            Spacer(modifier = Modifier.width(20.dp))
            AppBarDropDown(onClickLogout = onClickLogout)
        }
    }


}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBarForNotice(
    title : String,
    onClickBack : () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(start = 30.dp, top = 50.dp)
            .background(color = Color.Transparent),
        verticalAlignment = Alignment.CenterVertically
    ){
        Icon(
            painter = painterResource(id = R.drawable.back_icon_small),
            tint = Color.Black,
            contentDescription = "back_icon",
            modifier = Modifier.clickable {
                onClickBack()
            }
        )
        Spacer(modifier = Modifier.width(16.dp))

        Text(
            title,
            fontSize = 20.sp,
            fontFamily = FontFamily(Font(R.font.pretendard_regular)),
            fontWeight = FontWeight(700),
            color = Color(0xFF26282B)
        )


        }
    }


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBarForNoGroup(
    title : String,
    badgeCount : Int,
    goBack: () -> Unit={},
    onClick : () -> Unit  = {},
    onClickLogout : () -> Unit={}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(start = 30.dp, end = 48.dp, top = 40.dp)
            .background(color = Color.Transparent),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){

        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(painter = painterResource(id = R.drawable.arrow_left_small), contentDescription = null,
                modifier = Modifier.clickable { goBack() })
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                title, fontSize = 20.sp,
                fontFamily = FontFamily(Font(R.font.pretendard_regular)),
                fontWeight = FontWeight(700),
                color = Color(0xFF26282B)
            )
        }


        Row(
            verticalAlignment = Alignment.CenterVertically,
        ){
            Box(modifier = Modifier
                .width(109.dp)
                .height(40.dp)
            ) {
                RectangleEnabledButton(text = "한셀로 출력") {
                    onClick()
                }
            }

            Spacer(modifier = Modifier.width(20.dp))

            Box(modifier = Modifier
                .width(224.dp)
            ){
                SearchBar(hintText = "노트, 학습자료")
            }
            Spacer(modifier = Modifier.width(20.dp))

            Box(
                modifier = Modifier.size(36.dp) // 아이콘과 뱃지의 크기를 조절합니다.
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.appbar_bell_small),
                    contentDescription = null,
                    tint = Color(0xFF26282B),
                    modifier = Modifier
                        .fillMaxSize() // 아이콘이 Box 내부를 가득 채우도록 합니다.
                )

                Badge(
                    modifier = Modifier
                        .offset(16.dp, -5.dp) // 뱃지의 위치를 조절하여 겹치도록 합니다.
                ) {
                    Text(
                        text = badgeCount.toString(),
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(R.font.pretendard_regular)),
                            fontWeight = FontWeight(600),
                            color = Color(0xFFFFFFFF),
                            textAlign = TextAlign.Center,
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.width(20.dp))
            Icon(painter = painterResource(id = R.drawable.appbar_person_circle_small), contentDescription = null)
            Spacer(modifier = Modifier.width(20.dp))
            AppBarDropDown(onClickLogout = onClickLogout)
        }
    }


}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBarForNote(
    title : String,
    badgeCount : Int,
    onClickLogout : () -> Unit={}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(start = 30.dp, end = 48.dp, top = 40.dp)
            .background(color = Color.Transparent),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                title, fontSize = 20.sp,
                fontFamily = FontFamily(Font(R.font.pretendard_regular)),
                fontWeight = FontWeight(700),
                color = Color(0xFF26282B)
            )
        }


        Row(
            verticalAlignment = Alignment.CenterVertically,
        ){


            Box(modifier = Modifier
                .width(224.dp)
            ){
                SearchBar(hintText = "노트, 학습자료")
            }
            Spacer(modifier = Modifier.width(20.dp))

            Box(
                modifier = Modifier.size(36.dp) // 아이콘과 뱃지의 크기를 조절합니다.
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.appbar_bell_small),
                    contentDescription = null,
                    tint = Color(0xFF26282B),
                    modifier = Modifier
                        .fillMaxSize() // 아이콘이 Box 내부를 가득 채우도록 합니다.
                )

                Badge(
                    modifier = Modifier
                        .offset(16.dp, -5.dp) // 뱃지의 위치를 조절하여 겹치도록 합니다.
                ) {
                    Text(
                        text = badgeCount.toString(),
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(R.font.pretendard_regular)),
                            fontWeight = FontWeight(600),
                            color = Color(0xFFFFFFFF),
                            textAlign = TextAlign.Center,
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.width(20.dp))
            Icon(painter = painterResource(id = R.drawable.appbar_person_circle_small), contentDescription = null)
            Spacer(modifier = Modifier.width(20.dp))
            AppBarDropDown(onClickLogout = onClickLogout)
        }
    }


}

@Composable
@Preview
fun appBarPreview(){
   // AppBar(title = "홈", badgeCount = 12, onArrowClick = { /*TODO*/ }, isGroupButton = false)
}