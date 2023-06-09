package com.example.imagesearchnasa.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imagesearchnasa.model.ImageDetailModel
import com.example.imagesearchnasa.model.Item
import com.example.imagesearchnasa.repository.ImageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImagesViewModel @Inject constructor(private val repository: ImageRepository): ViewModel() {
    private val _searchResults = MutableStateFlow<List<Item>>(emptyList())
    val searchResults: StateFlow<List<Item>> get() = _searchResults

    init {
        viewModelScope.launch {
            getImages()
        }

    }
    private suspend fun getImages() {
        viewModelScope.launch {
            _searchResults.value = repository.getImages("moon", "image", 1)!!
        }
    }
}