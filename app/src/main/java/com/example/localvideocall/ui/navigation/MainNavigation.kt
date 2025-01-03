package com.example.localvideocall.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.localvideocall.ui.screen.ClientScreen
import com.example.localvideocall.ui.screen.HostScreen
import com.example.localvideocall.ui.screen.MainScreen
import com.example.localvideocall.utils.Constants.HOST_SCREEN
import com.example.localvideocall.utils.Constants.MAIN_SCREEN

@Composable
fun MainNavigation(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = MAIN_SCREEN){
        composable(MAIN_SCREEN){
            MainScreen(navController = navController)
        }
        composable(HOST_SCREEN){
            HostScreen(navController = navController)
        }
        composable("ClientScreen/{serverAddress}", arguments = listOf(navArgument("serverAddress"){type = NavType.StringType})) {
            ClientScreen(navController = navController, serverAddress = it.arguments?.getString("serverAddress"))
        }
    }
}