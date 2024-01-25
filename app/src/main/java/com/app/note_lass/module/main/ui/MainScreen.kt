package com.app.note_lass.module.main.ui

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.datastore.core.DataStore
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.app.note_lass.R
import com.app.note_lass.core.Proto.ProtoViewModel
import com.app.note_lass.core.Proto.Role
import com.app.note_lass.core.Proto.Token
import com.app.note_lass.core.navigation.MainNavGraph
import com.app.note_lass.module.navigation.ui.NavigationSideBar
import com.app.note_lass.ui.component.AppBar
import com.app.note_lass.ui.component.DialogStudentMemo

data class NavigationItem(
    val title : String,
    val route : String,
    val selectedIcon : Int,
    val unselectedIcon : Int,
)

val items = listOf(
    NavigationItem(
        "홈",
        "home/home",
        R.drawable.nav_active_home_small,
        R.drawable.nav_inactive_home_small,
    ),
    NavigationItem(
        "그룹",
        "group/home",
        R.drawable.nav_active_group_small,
        R.drawable.nav_inactive_group_small,
    ),
    NavigationItem(
        "노트",
        "note/home",
        R.drawable.nav_active_note_small,
        R.drawable.nav_inactive_note_small,
    ),
    NavigationItem(
        "환경설정",
        "setting/home",
        R.drawable.nav_inactive_setting_small,
        R.drawable.nav_inactive_setting_small,
    ),
)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen(
    outerNavController: NavController,
    navController: NavHostController = rememberNavController(),
    protoViewModel : ProtoViewModel = hiltViewModel()

){

    val role = protoViewModel.token.collectAsState(initial = Token("",Role.NONE)).value.role
    val isDialogShow = remember{
        mutableStateOf(false)
    }

    val selectedItemIndex = rememberSaveable() {
        mutableIntStateOf(0)
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor =  Color(0xFFF5F5FC),
        contentColor = Color.Black,
        bottomBar = {
        },
        content = {
            Box(
                modifier = Modifier
                    .padding(start = 240.dp, top = it.calculateTopPadding()),
            ) {
                MainNavGraph(navController = navController, outerNavController = outerNavController,role = role)
            }
        },
        )
    //현재 화면에 맞는 항목의 인덱스를 찾고, 그 인덱스를 selectedItemIndex에 저장
    navController.addOnDestinationChangedListener { _, destination, _ ->
        val index = items.indexOfFirst { it.route == destination.route }
        if (selectedItemIndex.intValue != index && index != -1) {
            selectedItemIndex.intValue = index
        }
    }
        NavigationSideBar(
            role = role,
            items = items,
            selectedItemIndex = selectedItemIndex.intValue,
            onNavigate = {
                selectedItemIndex.intValue = it
                navController.navigate(items[selectedItemIndex.intValue].route){
                    launchSingleTop = true
                }
            },
            onClick = {
                isDialogShow.value = true
            }
        )


    if(isDialogShow.value){
      DialogStudentMemo(
          setShowDialog = {
          isDialogShow.value = it
          }
      )
    }

}