package com.example.imagesearchnasa.repository

import android.util.Log
import com.example.imagesearchnasa.model.Item
import com.example.imagesearchnasa.retrofit.ImageApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImageRepository @Inject constructor(private val imageApi: ImageApi){

    suspend fun getImages(query: String, type: String, page: Int): List<Item>? {

        try {
            val result = imageApi.getImages(query.trim(), "image", page)
            if (result.isSuccessful && result.body() != null){
                return result.body()?.collection?.items
            }
        } catch (e: Exception) {
            Log.e("Error", " api error", e)
        }
        return ArrayList()
    }
}