package com.app.note_lass.ui.component

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.app.note_lass.R
import com.app.note_lass.ui.theme.NoteLassTheme
import com.app.note_lass.ui.theme.PrimaryBlack


@Composable
fun DialogSignUp(
    setShowDialog : (Boolean)-> Unit,
    content : String,
    onDecline : () -> Unit,
    onAccept : () -> Unit
) {

    Dialog(
        onDismissRequest = { setShowDialog(false) }
    ) {

        Box(
            modifier = Modifier
                .size(width = 480.dp, height = 288.dp)
                .background(color = Color.White,shape = RoundedCornerShape(12.dp))
        ){
            Icon(
                painter= painterResource(id = R.drawable.group_filedelete_small),
                tint =Color(0xFF26282B),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(24.dp)
            )

            Column(modifier = Modifier
                .fillMaxSize()
                .padding(top = 70.dp, bottom = 40.dp, start = 16.dp, end = 15.dp),
                horizontalAlignment = Alignment.CenterHorizontally) {

                Text(text= content, style = NoteLassTheme.Typography.twenty_700_pretendard,color = PrimaryBlack)

                Spacer(modifier = Modifier.height(64.dp))

                Row(modifier = Modifier
                    .fillMaxWidth()
                    .height(58.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ){
                    Box(modifier = Modifier.weight(1f)){
                        RectangleUnableButton(
                            text ="아니오"
                        ) {
                         onDecline()   
                        }
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Box(modifier = Modifier.weight(1f)){
                        RectangleEnabledButton(
                            text ="네"
                        ) {
                            onAccept()
                        }
                    }
                }
            }

        }
    }
}

@Preview
@Composable
fun DialogPreview(){
    val showDialog = remember {
        mutableStateOf(false)
    }
    DialogSignUp(
        setShowDialog =  {
            showDialog.value = it
        },
        content = "노트고등학교 3학년 1반 1번\n   김OO님이 맞습니까?",
        onDecline = { /*TODO*/ }
    ) {
        /*TODO*/
    }
}