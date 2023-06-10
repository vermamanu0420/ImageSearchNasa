package com.example.imagesearchnasa.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imagesearchnasa.model.ImageDetailModel
import com.example.imagesearchnasa.model.Item
import com.example.imagesearchnasa.repository.ImageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImagesViewModel @Inject constructor(private val repository: ImageRepository): ViewModel() {
    private val _searchResults = MutableStateFlow<List<Item>>(emptyList())
    val searchResults: StateFlow<List<Item>> get() = _searchResults
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()


    fun getImages(query: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _searchResults.value = repository.getImages(query, "image", 1)!!
            _isLoading.value = false
        }
    }
}