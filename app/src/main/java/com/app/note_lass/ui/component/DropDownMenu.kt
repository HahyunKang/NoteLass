package com.app.note_lass.ui.component

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.window.PopupProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.note_lass.R
import com.app.note_lass.module.signup.ui.AuthSharedViewModel
import com.app.note_lass.ui.theme.NoteLassTheme
import com.app.note_lass.ui.theme.PrimaryBlack
import com.app.note_lass.ui.theme.PrimaryGray

@Composable
fun DropDownMenu(
    menuList : List<String>,
    iconDown : Int,
    iconUp : Int,
    placeHolder :  String,
    isSelected: (Boolean) -> Unit
){
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf("") }

    var textfieldSize by remember { mutableStateOf(androidx.compose.ui.geometry.Size.Zero)}
    val globalOffset = remember { mutableStateOf(IntOffset.Zero) }

    var scrollState = rememberScrollState()
    val icon = if (expanded) iconUp
    else
        iconDown

    Box(
        Modifier
            .fillMaxWidth()) {
        OutlinedTextField(
            value = selectedText,
            onValueChange = { selectedText = it },
            enabled = false,
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    //This value is used to assign to the DropDown the same width
                    textfieldSize = coordinates.size.toSize()
                },
            placeholder = {
                Text(
                    text = placeHolder,
                    style = NoteLassTheme.Typography.sixteem_600_pretendard,
                    color = PrimaryGray
                )

            },
            trailingIcon = {
                Icon(painter = painterResource(id = icon), "contentDescription",
                    Modifier.clickable { expanded = !expanded })
            }
        )
       Box(
           Modifier
               .fillMaxWidth()
               .verticalScroll(rememberScrollState())
               .align(Alignment.BottomStart)
        ) {

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .width(with(LocalDensity.current) { textfieldSize.width.toDp() })
                    .height(150.dp)
                    .background(color = Color.White)
                ,
                offset = DpOffset(0.dp, (-1).dp)
            ) {
                menuList.forEach { label ->
                    DropdownMenuItem(
                        onClick = {
                            selectedText = label
                            expanded = false
                            isSelected(true)
                        },
                        text = {
                            Text(
                                text = label,
                                style = NoteLassTheme.Typography.fourteen_600_pretendard,
                                color = PrimaryBlack
                            )
                        })
                }
            }
        }
    }
}
@OptIn(ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class)
@Composable
fun DropDownSearch(
    viewModel: AuthSharedViewModel = hiltViewModel(),
    icon: Int,
    placeHolder:  String,
    onSearchTextChange: (String) -> Unit,
    isSelected : (Boolean) -> Unit,
    modifier: Modifier = Modifier
){
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    //Log.e("selectedText",selectedText)
    val searchText by viewModel.searchText.collectAsState()
    val schools by viewModel.schools.collectAsState()
    var textfieldSize by remember { mutableStateOf(androidx.compose.ui.geometry.Size.Zero)}

    Column(
        modifier
            .fillMaxWidth()
            .size(width = 400.dp, height = 56.dp)) {
        OutlinedTextField(
            value =  if(expanded) searchText else selectedText,
            onValueChange = {
                           onSearchTextChange(it)
                           expanded = true
                            },
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    //This value is used to assign to the DropDown the same width
                    textfieldSize = coordinates.size.toSize()

                },
                //.noRippleClickable { expanded = true },
            placeholder = {
                Text(
                    text = placeHolder,
                    style = NoteLassTheme.Typography.sixteem_600_pretendard,
                    color = PrimaryGray
                )

            },
            trailingIcon = {
                Icon(painter = painterResource(id = icon), "contentDescription",
                    Modifier.clickable { expanded = !expanded })
            },
            keyboardActions = KeyboardActions(onDone={
                focusManager.clearFocus(true)
            })
        )
        Column(
            Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())

        ) {

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = {  },
                properties = PopupProperties(focusable = false),
                modifier = Modifier
                    .width(with(LocalDensity.current) { textfieldSize.width.toDp() })
                    .height(150.dp)
                    .background(color = Color.White)
            ) {
                schools.forEach { label ->
                    DropdownMenuItem(
                        onClick = {
                            selectedText = label.schoolName
                            expanded = false
                            isSelected(true)
                            focusManager.clearFocus(true)
                            keyboardController?.hide()
                        },
                        text = {
                            Text(
                                text = label.schoolName,
                                style = NoteLassTheme.Typography.fourteen_600_pretendard,
                                color = PrimaryBlack
                            )
                        })
                }
            }
        }
    }
}

@Composable
@Preview
fun DropDownPreview(){
    val menuList = listOf("광남고등학교","서울과학고등학교","휘문고등학교","숙명여자고등학교")

    //DropDownMenu(menuList = menuList , iconDown =  R.drawable.arrow_down, iconUp = R.drawable.arrow_down, placeHolder ="학교 정보를 입력하세요")
}