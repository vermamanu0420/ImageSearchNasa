package com.example.imagesearchnasa.di

import com.example.imagesearchnasa.retrofit.ImageApi
import com.example.imagesearchnasa.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class AppModules {

    @Provides
    fun provideImageApi() : Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

    }

    @Provides
    fun provideMyWorkRepository() : ImageApi {
        return provideImageApi().create(ImageApi::class.java)
    }
}