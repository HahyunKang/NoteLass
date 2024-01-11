package com.app.note_lass.ui.component

import android.widget.Toast
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.note_lass.R
import com.app.note_lass.core.Proto.ProtoViewModel
import com.app.note_lass.module.login.ui.LoginViewModel
import kotlinx.coroutines.selects.whileSelect


@Composable
fun AppBarDropDown(
    onClickLogout : () -> Unit
) {

    val protoViewModel : ProtoViewModel =  hiltViewModel()
    val loginViewModel : LoginViewModel = hiltViewModel()

    val logoutState = loginViewModel.logoutState
    val dropdownItems = listOf("개인 정보 수정", "로그아웃")
    var isContextMenuVisible by rememberSaveable {
        mutableStateOf(false)
    }

    var pressOffset by remember {
        mutableStateOf(DpOffset.Zero)
    }
    var itemWidth by remember {
        mutableStateOf(0.dp)
    }
    val interactionSource = remember {
        MutableInteractionSource()
    }
    val density = LocalDensity.current

    if(logoutState.value.isSuccess){
        onClickLogout()
    }else{
    }
    Icon(painter = painterResource(id = R.drawable.appbar_arrowdown_small),
        contentDescription = null,
        tint = Color(0xFF26282B),
        modifier = Modifier
            .indication(interactionSource, LocalIndication.current)
            .pointerInput(true) {
                detectTapGestures(
                    onPress = {
                        pressOffset = DpOffset(it.x.toDp(), it.y.toDp())
                        isContextMenuVisible = true
                    }
                )

            }
            .onSizeChanged {
                itemWidth = with(density) { it.width.toDp() }
            }
    )
    Box(
        contentAlignment = Alignment.TopEnd
    ) {
        DropdownMenu(
            expanded = isContextMenuVisible,
            onDismissRequest = { isContextMenuVisible = false },

            offset = pressOffset.copy(
                x = -5.dp,
                y = pressOffset.y  + 30.dp
            )
        ) {

            dropdownItems.forEachIndexed {
                index,text->
                DropdownMenuItem(onClick = {
                    isContextMenuVisible = false
                    if(index ==0){

                    }else{
                        loginViewModel.logout(tokenViewModel = protoViewModel)
                    }

                }
                ) {
                    Text(text = text,
                        textAlign = TextAlign.Center)
                }
            }
        }

    }
}





@Preview
@Composable
fun dropDownPreview(){
   // AppBarDropDown()
}

