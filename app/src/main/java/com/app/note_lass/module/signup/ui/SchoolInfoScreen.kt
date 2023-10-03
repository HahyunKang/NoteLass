package com.app.note_lass.module.signup.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.note_lass.R
import com.app.note_lass.ui.component.CheckBox
import com.app.note_lass.ui.component.DropDownMenu
import com.app.note_lass.ui.component.DropDownSearch
import com.app.note_lass.ui.component.RectangleButtonWithStatus
import com.app.note_lass.ui.component.RectangleUnableButton
import com.app.note_lass.ui.theme.NoteLassTheme
import com.app.note_lass.ui.theme.PrimarayBlue
import com.app.note_lass.ui.theme.PrimaryBlack


@Composable
fun SchoolInfoScreen(
    viewModel : AuthSharedViewModel = hiltViewModel(),
    GotoSignUp : () -> Unit
){

    var buttonFilled by remember{
        mutableStateOf(false)
    }

    var schoolFilled by remember{
        mutableStateOf(false)
    }

    var yearFilled by remember{
        mutableStateOf(false)
    }
    var isChecked by remember{
        mutableStateOf(false)
    }
    var gradeFilled by remember{
        mutableStateOf(true)
    }

    buttonFilled = schoolFilled && yearFilled && isChecked

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth(0.3f)
                .padding(top = 10.dp)
            ,
        ) {
            Text(
                text = "학교 정보 입력",
                style = NoteLassTheme.Typography.thritytwo_700_pretendard,
                color = PrimarayBlue,
                modifier = Modifier.align(Alignment.Start)
            )
            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "학교 검색",
                style = NoteLassTheme.Typography.twenty_700_pretendard,
                color = PrimaryBlack,
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(15.dp))

            DropDownSearch(
                viewModel = viewModel,
                icon = R.drawable.search_appbar_small,
                placeHolder = "학교 이름을 입력해주세요",
                isSelected = {
                             schoolFilled = it
                },
                onSearchTextChange = viewModel :: onSearchTextChange,
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(25.dp))

            Text(
                text = "입학 년도",
                style = NoteLassTheme.Typography.twenty_700_pretendard,
                color = PrimaryBlack,
                modifier = Modifier.align(Alignment.Start)
            )
            val menuList = listOf("2016년","2017년","2018년","2019년"
            ,"2020년","2021년","2022년","2023년")

            Spacer(modifier = Modifier.height(15.dp))

            DropDownMenu(
                menuList = menuList ,
                iconDown =  R.drawable.arrow_down,
                iconUp = R.drawable.arrow_down,
                placeHolder ="입학 년도를 선택해주세요",
                isSelected = {
                    yearFilled = it
                }
            )


            Spacer(modifier = Modifier.height(25.dp))
            Text(
                text = "신분 선택",
                style = NoteLassTheme.Typography.twenty_700_pretendard,
                color = PrimaryBlack,
                modifier = Modifier.align(Alignment.Start)
            )
            Spacer(modifier = Modifier.height(15.dp))

            CheckBox(isChecked = {
                isChecked = it
            })

            Spacer(modifier = Modifier.height(25.dp))

            Text(
                text = "반,번호 입력",
                style = NoteLassTheme.Typography.twenty_700_pretendard,
                color = PrimaryBlack,
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(15.dp))

            val gradeList = listOf("1","2","3")
            val classList = listOf("1","2","3","4","5")
            val idList = (1..31).map { it.toString() }
            val dropDown = R.drawable.arrow_down


            Row(modifier = Modifier.fillMaxWidth()){
                Row(modifier = Modifier
                    .weight(1f)
                    .padding(2.dp)){
                    Box(modifier = Modifier.weight(2f)) {
                        DropDownMenu(
                            menuList = gradeList,
                            iconDown = dropDown,
                            iconUp = dropDown,
                            placeHolder = "1",
                            isSelected = {
                                gradeFilled = it
                            }
                        )
                    }
                    Spacer(modifier = Modifier.width(6.dp))

                    Text(
                        text = "학년",
                        style = NoteLassTheme.Typography.twenty_700_pretendard,
                        color = PrimaryBlack,
                        modifier = Modifier
                            .weight(1f)
                            .align(Alignment.CenterVertically)
                    )

                }

                Row(modifier = Modifier.weight(1f)){
                    Box(modifier = Modifier.weight(2f)) {
                        DropDownMenu(
                            menuList = classList,
                            iconDown = dropDown,
                            iconUp = dropDown,
                            placeHolder = "1",
                            isSelected = {
                                gradeFilled = it
                            }
                        )
                    }
                    Spacer(modifier = Modifier.width(6.dp))

                    Text(
                        text = "반",
                        style = NoteLassTheme.Typography.twenty_700_pretendard,
                        color = PrimaryBlack,
                        modifier = Modifier
                            .weight(1f)
                            .align(Alignment.CenterVertically)
                    )

                }

                Row(modifier = Modifier.weight(1f)){
                    Box(modifier = Modifier.weight(2f)) {
                        DropDownMenu(
                            menuList = idList,
                            iconDown = dropDown,
                            iconUp = dropDown,
                            placeHolder = "1",
                            isSelected = {
                                gradeFilled = it
                            }
                        )
                    }
                    Spacer(modifier = Modifier.width(6.dp))

                    Text(
                        text = "번",
                        style = NoteLassTheme.Typography.twenty_700_pretendard,
                        color = PrimaryBlack,
                        modifier = Modifier
                            .weight(1f)
                            .align(Alignment.CenterVertically)
                    )

                }
            }


            Spacer(modifier = Modifier.height(125.dp))

            Box(modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)) {
               RectangleButtonWithStatus(
                   text = "다음",
                   onClick = { GotoSignUp() },
                   isEnabled = buttonFilled)
            }


        }
    }
}

@Preview
@Composable
fun SchoolInfoPreviewScreen(){
  //  SchoolInfoScreen()
}