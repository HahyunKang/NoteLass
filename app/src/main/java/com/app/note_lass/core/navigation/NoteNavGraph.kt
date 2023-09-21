package com.app.note_lass.core.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.app.note_lass.module.note.ui.NoteScreen

fun NavGraphBuilder.NoteNavGraph(navController: NavController) {


    navigation(startDestination = NoteScreen.Home.route, route = NOTE_ROUTE) {
        composable(NoteScreen.Home.route) {
            NoteScreen()
        }

    }
}

