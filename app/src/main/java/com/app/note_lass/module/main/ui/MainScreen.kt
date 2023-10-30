package com.app.note_lass.module.main.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.app.note_lass.R
import com.app.note_lass.core.navigation.MainNavGraph
import com.app.note_lass.module.navigation.ui.NavigationSideBar
import com.app.note_lass.ui.component.AppBar

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
    navController: NavHostController = rememberNavController()
){


    var selectedItemIndex by rememberSaveable() {
        mutableStateOf(0)
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
                MainNavGraph(navController = navController)
            }
        },

        )


        NavigationSideBar(items = items, selectedItemIndex =selectedItemIndex) {
            selectedItemIndex = it
            navController.navigate(items[selectedItemIndex].route)
        }


}