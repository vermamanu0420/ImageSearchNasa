package com.example.imagesearchnasa.di

import android.content.Context
import com.example.imagesearchnasa.repository.NetworkConnectionInterceptor
import com.example.imagesearchnasa.retrofit.ImageApi
import com.example.imagesearchnasa.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class AppModules {

    @Provides
    fun provideApplicationContext(@ApplicationContext appContext: Context): Context {
        return appContext
    }

    @Provides
    fun provideNetworkConnectionInterceptor(applicationContext: Context): NetworkConnectionInterceptor {
        return NetworkConnectionInterceptor(applicationContext)
    }

    @Provides
    fun provideImageApi(networkConnectionInterceptor: NetworkConnectionInterceptor): Retrofit {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(networkConnectionInterceptor)
            .build()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()

    }

    @Provides
    fun provideMyWorkRepository(networkConnectionInterceptor: NetworkConnectionInterceptor): ImageApi {
        return provideImageApi(networkConnectionInterceptor).create(ImageApi::class.java)
    }
}