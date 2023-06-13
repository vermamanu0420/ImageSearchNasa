package com.example.imagesearchnasa.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.imagesearchnasa.R
import com.example.imagesearchnasa.navigation.Screen
import com.example.imagesearchnasa.viewmodel.ImagesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageDetailScreen(navController: NavHostController, imagesViewModel: ImagesViewModel) {
    val selectedItem by imagesViewModel.selectedResults.collectAsState()
    Scaffold(
        Modifier.background(Color.White),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = MaterialTheme.colorScheme.primary),
                title = {
                    selectedItem?.data?.get(0)?.let {
                        Text(
                            text = it.title,
                            color = Color.White,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate(Screen.Home.route) }) {
                        Icon(Icons.Filled.ArrowBack, "backIcon", tint = Color.White)
                    }
                },
            )
        }
    ) { paddingValue ->
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState)
                .padding(paddingValue)
                .background(Color.White),
        ) {
            Box(
                modifier = Modifier
                    .height(400.dp)
                    .background(Color.White)
                    .graphicsLayer {
                        translationY = 0.5f * scrollState.value
                    },
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.FillBounds,
                    model = selectedItem?.links?.get(0)?.href,
                    placeholder = painterResource(id = R.drawable.placeholder_image_24),
                    contentDescription = null // Provide an appropriate content description
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(16.dp, 0.dp, 16.dp, 16.dp)
            ) {
                Text(
                    text = "Date: ${selectedItem?.data?.get(0)?.date_created}",
                    textAlign = TextAlign.End,
                    fontStyle = FontStyle.Italic,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(0.dp)
                        .align(Alignment.End)
                )

                Text(
                    text = "Description",
                    textAlign = TextAlign.End,
                    fontStyle = FontStyle.Italic,
                    style = MaterialTheme.typography.titleMedium,
                )

                selectedItem?.data?.get(0)?.let {
                    Text(
                        text = it.description,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier
                            .background(
                                Color.White
                            )
                            .fillMaxWidth()
                            .padding(16.dp),

                        )
                }

                Spacer(modifier = Modifier.height(16.dp))
            }

        }
    }
}