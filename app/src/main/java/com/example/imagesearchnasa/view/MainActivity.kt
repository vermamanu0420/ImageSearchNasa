package com.example.imagesearchnasa.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.compose.rememberNavController
import com.example.imagesearchnasa.navigation.SetupNavigationGraph
import com.example.imagesearchnasa.ui.theme.ImageSearchNasaTheme
import com.example.imagesearchnasa.viewmodel.ImagesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val imageViewModel by viewModels<ImagesViewModel>()
        setContent {
            ImageSearchNasaTheme {
                val navController = rememberNavController()
                SetupNavigationGraph(navHostController = navController, imageViewModel)
            }
        }
    }
}
