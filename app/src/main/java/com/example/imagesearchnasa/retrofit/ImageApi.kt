package com.example.imagesearchnasa.retrofit




import com.example.imagesearchnasa.model.ImageDetailModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface ImageApi {
    @GET("search")
    suspend fun getImages(
        @Query("q") searchTerm: String?,
        @Query("media_type") dataType: String?,
        @Query("page") page: Int
    ): Response<ImageDetailModel>
}