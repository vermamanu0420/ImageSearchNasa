package com.example.imagesearchnasa.view

import android.widget.AdapterView.OnItemClickListener
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.imagesearchnasa.viewmodel.ImagesViewModel
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.example.imagesearchnasa.R
import com.example.imagesearchnasa.navigation.Screen
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(navHostController: NavHostController, imagesViewModel: ImagesViewModel) {
    Home(navHostController, imagesViewModel)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(navHostController: NavHostController, imagesViewModel: ImagesViewModel) {
    val listState = rememberLazyListState()
    var searchQuery by remember { mutableStateOf("") }
    val searchResults by imagesViewModel.searchResults.collectAsState()
    val focusManager = LocalFocusManager.current
    val isLoading by imagesViewModel.isLoading.collectAsState()
    val isLoadingMore by imagesViewModel.isLoadingMore.collectAsState()
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
                maxLines = 1,
                singleLine = true,
                onValueChange = { query -> searchQuery = query },
                label = { Text(text = "Search") },
                // this works from the device keyboard enter
                keyboardActions = KeyboardActions(
                    onDone = {
                        performSearch(searchQuery, focusManager, imagesViewModel)
                    }
                ),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Text
                ),
                modifier = Modifier.onKeyEvent {
                    // this only to handle the enter press form the keyboard while testing in emulator
                    if (it.nativeKeyEvent.keyCode == android.view.KeyEvent.KEYCODE_ENTER) {
                        performSearch(searchQuery, focusManager, imagesViewModel)
                        true
                    }
                    false
                }
            )
            Spacer(modifier = Modifier.width(2.dp))
            Button(onClick = {

               performSearch(searchQuery, focusManager, imagesViewModel)
            }) {
                Text(text = "Search")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            LazyColumn(state = listState) {
                items(searchResults) { result ->
                    CardWithImageAndTitle(
                        result.data.get(0).title,
                        result.links.get(0).href,
                        result.data.get(0).date_created
                    ) {
                        imagesViewModel.selectedResults.value = result
                        navHostController.navigate(Screen.Detail.route)
                    }
                }
            }
            if (isLoading) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Color.White),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LoadingAnimation()
                }
            }
        }
        // this shows the loading animation at the bottom on scroll
        if (isLoadingMore){
            LoadingAnimation()
        }

    }
    // call the extension function
    listState.OnBottomReached {
        if (searchQuery.trim().isEmpty())
            return@OnBottomReached;
        // do on load more
        GlobalScope.launch {
            imagesViewModel.fetchMoreImages(searchQuery.trim())
        }
    }
}

@Composable
fun LazyListState.OnBottomReached(
    loadMore: () -> Unit
) {
    val shouldLoadMore = remember {
        derivedStateOf {
            val lastVisibleItem = layoutInfo.visibleItemsInfo.lastOrNull()
                ?: return@derivedStateOf true

            lastVisibleItem.index == layoutInfo.totalItemsCount - 1
        }
    }

    // Convert the state into a cold flow and collect
    LaunchedEffect(shouldLoadMore) {
        snapshotFlow { shouldLoadMore.value }
            .collect {
                // if should load more, then invoke loadMore
                if (it) loadMore()
            }
    }
}

@Composable
fun CardWithImageAndTitle(title: String, url: String, date: String, onItemClickListener: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable {
                onItemClickListener.invoke()
            },
        shape = RoundedCornerShape(8.dp)

    ) {
        Row(
            modifier = Modifier.padding(16.dp)
        ) {
            AsyncImage(
                modifier = Modifier
                    .height(100.dp)
                    .width(100.dp)
                ,
                contentScale = ContentScale.FillBounds,
                model = url,
                placeholder = painterResource(id = R.drawable.placeholder_image_24),
                contentDescription = null // Provide an appropriate content description
            )

            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(4.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(

                    text = date,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(4.dp)
                )
            }
        }
    }
}

private fun performSearch(
    searchQuery: String,
    focusManager: FocusManager,
    imagesViewModel: ImagesViewModel
) {
    if (searchQuery.trim().isEmpty())
        return
    // physical keyboard adds linebreak on enter key so need to trim the query string
    imagesViewModel.getImages(searchQuery.trim());
    focusManager.clearFocus()
}
