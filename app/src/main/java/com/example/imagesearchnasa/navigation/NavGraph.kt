package com.example.imagesearchnasa.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.imagesearchnasa.view.HomeScreen
import com.example.imagesearchnasa.view.ImageDetailScreen
import com.example.imagesearchnasa.view.SplashScreen
import com.example.imagesearchnasa.viewmodel.ImagesViewModel

@Composable
fun SetupNavigationGraph(navHostController: NavHostController, imagesViewModel: ImagesViewModel) {
    NavHost(navController = navHostController, startDestination = Screen.Splash.route) {
        composable(route = Screen.Splash.route){
            SplashScreen(navHostController)
        }

        composable(route = Screen.Home.route){
            HomeScreen(navHostController, imagesViewModel)
        }

        composable(route = Screen.Detail.route){
            ImageDetailScreen(navHostController, imagesViewModel)
        }
    }
}