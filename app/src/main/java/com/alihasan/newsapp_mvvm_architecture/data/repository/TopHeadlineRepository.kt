package com.alihasan.newsapp_mvvm_architecture.data.repository

import com.alihasan.newsapp_mvvm_architecture.data.api.NetworkService
import com.alihasan.newsapp_mvvm_architecture.data.model.TopHeadlinesResponse
import com.alihasan.newsapp_mvvm_architecture.di.ActivityScope
import javax.inject.Inject

@ActivityScope
class TopHeadlineRepository @Inject constructor(private val networkService: NetworkService) {

    suspend fun getTopHeadlines(country: String): TopHeadlinesResponse {
        return networkService.getTopHeadlines(country)
    }

}