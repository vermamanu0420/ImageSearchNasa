package com.example.imagesearchnasa.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imagesearchnasa.model.Item
import com.example.imagesearchnasa.repository.ImageRepository
import com.example.imagesearchnasa.repository.NetworkConnectionInterceptor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImagesViewModel @Inject constructor(
    private val repository: ImageRepository,
    private val networkConnectionInterceptor: NetworkConnectionInterceptor
) : ViewModel() {

    val query = mutableStateOf("")

    private val _selectedResults = MutableStateFlow<Item?>(null)
    val selectedResults: MutableStateFlow<Item?> get() = _selectedResults

    private val _searchResults = MutableStateFlow<List<Item>>(emptyList())
    val searchResults: StateFlow<List<Item>> get() = _searchResults

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _isLoadingMore = MutableStateFlow(false)
    val isLoadingMore = _isLoadingMore.asStateFlow()

    private val _isNetworkConnected = MutableStateFlow(true)
    val isNetworkConnected = _isNetworkConnected.asStateFlow()

    private var currentPage = 1

    fun getImages() {
        currentPage = 1
        if (query.value.trim().isEmpty()) {
            _searchResults.value = emptyList()
            return
        }
        viewModelScope.launch {
            _isLoading.value = true
            if (networkConnectionInterceptor.isConnected) {
                _isNetworkConnected.value = true
                try {
                    _searchResults.value = repository.getImages(query.value, "image", currentPage)!!
                } catch (e: Exception) {
                    TODO("Not yet implemented")
                }
            } else {
                _isNetworkConnected.value = false
                _searchResults.value = emptyList()
            }
            _isLoading.value = false
        }
    }

    fun fetchMoreImages() {
        if (query.value.trim().isEmpty())
            return
        currentPage++

        viewModelScope.launch {
            _isLoadingMore.value = true
            _searchResults.update { items ->
                items + repository.getImages(query.value, "image", currentPage)!!
            }
            _isLoadingMore.value = false
        }
    }
}