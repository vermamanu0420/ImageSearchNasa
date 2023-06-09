package com.example.imagesearchnasa.repository

import com.example.imagesearchnasa.model.Item
import com.example.imagesearchnasa.retrofit.ImageApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImageRepository @Inject constructor(private val imageApi: ImageApi){

    suspend fun getImages(query: String, type: String, page: Int): List<Item>? {
        val result = imageApi.getImages("Moon", "image", 1)
        if (result.isSuccessful && result.body() != null){
            return imageApi.getImages("Moon", "image", 1).body()?.collection?.items
        }
        return ArrayList()
    }
}