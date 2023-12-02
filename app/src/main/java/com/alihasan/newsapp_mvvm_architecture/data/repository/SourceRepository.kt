package com.alihasan.newsapp_mvvm_architecture.data.repository

import com.alihasan.newsapp_mvvm_architecture.data.api.NetworkService
import com.alihasan.newsapp_mvvm_architecture.data.model.SourceModel.SourceResponse
import com.alihasan.newsapp_mvvm_architecture.di.ActivityScope
import javax.inject.Inject

@ActivityScope
class SourceRepository @Inject constructor(private val networkService: NetworkService) {

    suspend fun getSources(country: String, language: String): SourceResponse {
        return networkService.getSources(country, language)
    }
}
