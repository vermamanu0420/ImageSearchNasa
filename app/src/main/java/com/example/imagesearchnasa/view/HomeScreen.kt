package com.example.imagesearchnasa.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController


@Composable
fun HomeScreen(navHostController: NavHostController) {
    Home(navHostController)
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(navHostController: NavHostController) {
    var searchQuery by remember { mutableStateOf("") }
    var searchResults by remember { mutableStateOf(listOf<String>()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { query -> searchQuery = query },
                label = { Text(text = "Search") }
            )
            Spacer(modifier = Modifier.width(2.dp))
            Button(onClick = {  }) {
                Text(text = "Search")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn (modifier = Modifier.weight(1f)){
            items(searchResults) { result ->
                Text(text = result)
            }
        }
    }
}