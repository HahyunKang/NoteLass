package com.app.note_lass.module.navigation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.note_lass.R
import com.app.note_lass.core.Proto.Role
import com.app.note_lass.module.main.ui.NavigationItem
import com.app.note_lass.ui.theme.NoteLassTheme
import com.app.note_lass.ui.theme.PrimarayBlue
import com.app.note_lass.ui.theme.PrimaryBlack

@Composable
fun NavigationSideBar(
    items: List<NavigationItem>,
    selectedItemIndex: Int,
    onNavigate: (Int) -> Unit,
    role : Role,
    onClick : () -> Unit
){

    NavigationRail(
        header = {
            Row(verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical =40.dp)){
                Image(painter = painterResource(id = R.drawable.note_lass_logo),null)
                Spacer(modifier = Modifier.width(10.dp))
                Icon(painter = painterResource(id = R.drawable.logo_text),tint= PrimarayBlue, contentDescription = null)

            }
        },
        modifier =  Modifier.width(240.dp)
    ) {
        Column(
            modifier = Modifier.align(Alignment.Start)
                .weight(7f)
        ){
        items.forEachIndexed{ index,item ->
            NavigationRailItem(
                modifier = Modifier
                    .padding(start = 40.dp),
                selected = selectedItemIndex == index ,
                onClick = { onNavigate(index) },
                icon = {
                    NavigationIcon(item = item, selected = selectedItemIndex == index)
                       },
                colors = NavigationRailItemDefaults.colors(indicatorColor = Color.White)
            )
        }
        }

        if(role == Role.TEACHER) {
            Box(
                modifier = Modifier
                    .width(167.dp)
                    .weight(1f)
                    .padding(bottom = 30.dp)
                    .background(color = PrimarayBlue, shape = RoundedCornerShape(20.dp))
                    .clickable { onClick() }
            ) {
                Row(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .fillMaxSize(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Icon(
                        painter = painterResource(id = R.drawable.nav_student_memo),
                        tint = Color.White,
                        contentDescription = "학생 수첩"
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = "학생 수첩",
                        style = NoteLassTheme.Typography.sixteem_600_pretendard,
                        color = Color.White
                    )

                }

            }
        }


    }

}
fun Modifier.noRippleClickable(onClick: () -> Unit): Modifier = composed {
    clickable(indication = null,
        interactionSource = remember { MutableInteractionSource() }) {
        onClick()
    }
}
@Composable
fun NavigationIcon(
    item: NavigationItem,
    selected:Boolean
){
    Row(
        modifier = Modifier.padding(start= 5.dp),
        horizontalArrangement = Arrangement.Start
    ) {
        if(selected)
            Icon(painter = painterResource(item.selectedIcon),tint= PrimarayBlue,contentDescription = null )
        else  Icon(painter = painterResource(item.unselectedIcon), contentDescription = null)
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = item.title,
            fontSize = 16.sp,
            fontFamily = FontFamily(Font(R.font.pretendard_regular)),
            fontWeight = FontWeight(700),
            color = if(selected) Color(0xFF26282B)  else Color(0xFF9EA4AA)
        )

    }
}