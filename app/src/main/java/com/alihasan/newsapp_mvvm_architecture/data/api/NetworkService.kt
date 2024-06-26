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
    suspend fun getTopHeadlinesForCountry(
        @Query("country") country: String
    ): TopHeadlinesResponse

    @Headers("X-Api-Key: $API_KEY")
    @GET("top-headlines")
    suspend fun getTopHeadlinesForSourceId(
        @Query("sources") country: String
    ): TopHeadlinesResponse

    @Headers("X-Api-Key: $API_KEY")
    @GET("top-headlines")
    suspend fun getTopHeadlinesForLanguage(
        @Query("language") country: String
    ): TopHeadlinesResponse


    @Headers("X-Api-Key: $API_KEY")
    @GET("top-headlines/sources")
    suspend fun getSources(
        @Query("country") country: String,
        @Query("language") language: String
    ): SourceResponse

    @Headers("X-Api-Key: $API_KEY")
    @GET("everything")
    suspend fun getEverything(
        @Query("q") query: String,
    ): TopHeadlinesResponse

}