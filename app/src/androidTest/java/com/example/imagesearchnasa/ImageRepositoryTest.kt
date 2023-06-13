package com.example.imagesearchnasa

import com.example.imagesearchnasa.model.Collection
import com.example.imagesearchnasa.model.ImageDetailModel
import com.example.imagesearchnasa.model.Metadata
import com.example.imagesearchnasa.repository.ImageRepository
import com.example.imagesearchnasa.retrofit.ImageApi
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response


@RunWith(MockitoJUnitRunner::class)
class ImageRepositoryTest {
    @Mock
    private lateinit var imageApi: ImageApi
    private lateinit var imageRepository: ImageRepository

    @Before
    fun initMocks() {
        imageRepository = ImageRepository(imageApi)
    }

    @Test
    fun test_Success_API_Response() = runBlocking {
        val mockData =
            ImageDetailModel(Collection("link", emptyList(), emptyList(), Metadata(1), "1.0"))
        val mockResponse = Response.success(mockData)
        `when`(imageApi.getImages("moon", "image", 1)).thenReturn(mockResponse)


        assertEquals(mockData.collection.items, imageRepository.getImages("moon", "image", 1))

    }
}

