package com.example.imagesearchnasa.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imagesearchnasa.model.Item
import com.example.imagesearchnasa.repository.ImageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImagesViewModel @Inject constructor(private val repository: ImageRepository): ViewModel() {
    private val _searchResults = MutableStateFlow<List<Item>>(emptyList())
    val searchResults: StateFlow<List<Item>> get() = _searchResults
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _isLoadingMore = MutableStateFlow(false)
    val isLoadingMore = _isLoadingMore.asStateFlow()

    private var currentPage = 1

    fun getImages(query: String, page:Int = 1) {
        currentPage = 1
        viewModelScope.launch {
            _isLoading.value = true
            _searchResults.value = repository.getImages(query, "image", page)!!
            _isLoading.value = false
        }
    }

    fun fetchMoreImages(query: String) {
        if (query.isEmpty())
            return

        currentPage++

        viewModelScope.launch {
            _isLoadingMore.value = true
            _searchResults.update { items ->
                items + repository.getImages(query, "image", currentPage)!!
            }
            _isLoadingMore.value = false
        }
    }
}