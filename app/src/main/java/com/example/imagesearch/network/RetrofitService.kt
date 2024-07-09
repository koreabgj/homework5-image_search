package com.example.imagesearch.network

import com.example.imagesearch.data.SearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface RetrofitService {
    @GET("v2/search/image")
    suspend fun getSearchImages(
        @Header("Authorization") authorization: String = "KakaoAK d7dad5f8832c904973babb0a21d079ab",
        @Query("query") query: String,
        @Query("sort") sort: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Response<SearchResponse>
}