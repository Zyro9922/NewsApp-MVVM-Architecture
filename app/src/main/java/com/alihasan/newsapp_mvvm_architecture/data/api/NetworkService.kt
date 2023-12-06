package com.alihasan.newsapp_mvvm_architecture.data.api

import com.alihasan.newsapp_mvvm_architecture.data.model.SourceModel.SourceResponse
import com.alihasan.newsapp_mvvm_architecture.data.model.TopHeadlineModel.TopHeadlinesResponse
import com.alihasan.newsapp_mvvm_architecture.utils.AppConstant.API_KEY
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface NetworkService {
    @Headers("X-Api-Key: $API_KEY")
    @GET("top-headlines")
    suspend fun getTopHeadlines(
        @Query("country") country: String? = null,
        @Query("sources") sources: String? = null,
        @Query("language") language: String? = null
    ): TopHeadlinesResponse

    @Headers("X-Api-Key: $API_KEY")
    @GET("top-headlines/sources")
    suspend fun getSources(
        @Query("country") country: String,
        @Query("language") language: String
    ): SourceResponse



}