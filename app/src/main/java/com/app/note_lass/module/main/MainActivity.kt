package com.app.note_lass.module.main

import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.app.note_lass.R
import com.app.note_lass.core.navigation.RootNavGraph
import com.app.note_lass.module.navigation.ui.NavigationSideBar
import com.app.note_lass.ui.theme.NotelassTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    data class NavigationItem(
        val title : String,
        val selectedIcon : Int,
        val unselectedIcon : Int,
    )

    val items = listOf(
        NavigationItem(
            "홈",
            R.drawable.nav_active_home_small,
            R.drawable.nav_inactive_home_small,
        ),
        NavigationItem(
            "그룹",
            R.drawable.nav_active_group_small,
            R.drawable.nav_inactive_group_small,
        ),
        NavigationItem(
            "노트",
            R.drawable.nav_active_note_small,
            R.drawable.nav_inactive_note_small,
        ),
        NavigationItem(
            "환경설정",
            R.drawable.nav_inactive_setting_small,
            R.drawable.nav_inactive_setting_small,
        ),
    )
    @RequiresApi(Build.VERSION_CODES.O)
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            NotelassTheme {
                val windowClass = calculateWindowSizeClass(this)
                val showNavigationRail =  windowClass.widthSizeClass != WindowWidthSizeClass.Compact

                val navController = rememberNavController()
                RootNavGraph(navController = navController)



                // A surface container using the 'background' color from the theme

                



            }
        }
    }
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NotelassTheme {
        Greeting("Android")
    }
}