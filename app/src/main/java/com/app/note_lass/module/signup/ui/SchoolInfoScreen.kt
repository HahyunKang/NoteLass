package com.app.note_lass.module.signup.ui

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
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
import java.time.Year


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SchoolInfoScreen(
    viewModel : AuthSharedViewModel,
    GotoNext : () -> Unit
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

    val signupState = viewModel.signupState

    buttonFilled =schoolFilled && yearFilled


    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth(0.45f)
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
                onGetSearchText = {
                  signupState.value = signupState.value.copy(
                      school = it
                  )
                },
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(25.dp))

            Text(
                text = "입학 년도",
                style = NoteLassTheme.Typography.twenty_700_pretendard,
                color = PrimaryBlack,
                modifier = Modifier.align(Alignment.Start)
            )
            val menuList = listOf("2016","2017","2018","2019"
            ,"2020","2021","2022","2023","2024")

            Spacer(modifier = Modifier.height(15.dp))

            DropDownMenu(
                menuList = menuList ,
                iconDown =  R.drawable.arrow_down,
                iconUp = R.drawable.arrow_down,
                placeHolder ="입학 년도를 선택해주세요",
                isSelected = {
                    yearFilled = it
                },
                onGetInfo = {
                    signupState.value = signupState.value.copy(
                        admissionYear = Year.parse(it).value
                    )

                }
            )


            Spacer(modifier = Modifier.height(271.dp))

            Box(modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)) {
               RectangleButtonWithStatus(
                   text = "다음",
                   onClick =
                   {
                       GotoNext()
                   },
                   isEnabled = buttonFilled
               )
            }


        }
    }
}

@Preview
@Composable
fun SchoolInfoPreviewScreen(){
  //  SchoolInfoScreen()
}