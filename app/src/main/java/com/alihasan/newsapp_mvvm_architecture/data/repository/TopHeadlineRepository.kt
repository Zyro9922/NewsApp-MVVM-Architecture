package com.alihasan.newsapp_mvvm_architecture.data.repository

import com.alihasan.newsapp_mvvm_architecture.data.api.NetworkService
import com.alihasan.newsapp_mvvm_architecture.data.model.TopHeadlineModel.TopHeadlinesResponse
import com.alihasan.newsapp_mvvm_architecture.di.ActivityScope
import javax.inject.Inject

@ActivityScope
class TopHeadlineRepository @Inject constructor(private val networkService: NetworkService) {

    suspend fun getTopHeadlines(country: String? = null, sources: String? = null, language: String? = null): TopHeadlinesResponse {
        return networkService.getTopHeadlines(country, sources, language)
    }
    suspend fun getEverything(query: String): TopHeadlinesResponse {
        return networkService.getEverything(query)
    }
}