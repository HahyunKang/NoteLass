package com.app.note_lass.core.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.app.note_lass.module.note.ui.FirstNoteScreen

@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.NoteNavGraph(navController: NavController, outerNavController:NavController) {


    navigation(startDestination = NoteScreen.Home.route, route = NOTE_ROUTE) {
        composable(NoteScreen.Home.route) {
            FirstNoteScreen(
                onClickLogout = {
                    outerNavController.navigate(AUTH_ROUTE){
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}

