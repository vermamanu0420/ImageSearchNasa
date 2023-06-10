package com.example.imagesearchnasa.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.imagesearchnasa.navigation.SetupNavigationGraph
import com.example.imagesearchnasa.repository.ImageRepository
import com.example.imagesearchnasa.ui.theme.ImageSearchNasaTheme
import com.example.imagesearchnasa.view.HomeScreen
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
