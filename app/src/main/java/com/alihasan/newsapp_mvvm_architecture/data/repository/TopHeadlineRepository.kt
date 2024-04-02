package com.alihasan.newsapp_mvvm_architecture.data.repository

import com.alihasan.newsapp_mvvm_architecture.data.api.NetworkService
import com.alihasan.newsapp_mvvm_architecture.data.model.TopHeadlineModel.Article
import com.alihasan.newsapp_mvvm_architecture.data.model.TopHeadlineModel.TopHeadlinesResponse
import com.alihasan.newsapp_mvvm_architecture.di.ActivityScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@ActivityScope
class TopHeadlineRepository @Inject constructor(private val networkService: NetworkService) {

    suspend fun getTopHeadlinesByCountry(country: String): Flow<List<Article>> {
        return flow {
            val topHeadlinesResponse = networkService.getTopHeadlinesForCountry(country)
            emit(topHeadlinesResponse)
        }.map { response ->
            response.articles
        }
    }

    suspend fun getTopHeadlinesBySourceId(sourceId: String): Flow<List<Article>> {
        return flow {
            val topHeadlinesResponse = networkService.getTopHeadlinesForSourceId(sourceId)
            emit(topHeadlinesResponse)
        }.map { response ->
            response.articles // Extracting list of articles from the response
        }
    }

    suspend fun getTopHeadlinesByLanguage(language: String): Flow<List<Article>> {
        return flow {
            val topHeadlinesResponse = networkService.getTopHeadlinesForLanguage(language)
            emit(topHeadlinesResponse)
        }.map { response ->
            response.articles // Extracting list of articles from the response
        }
    }
    suspend fun getEverything(query: String): TopHeadlinesResponse {
        return networkService.getEverything(query)
    }
}