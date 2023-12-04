package com.alihasan.newsapp_mvvm_architecture.di.component

import android.content.Context
import com.alihasan.newsapp_mvvm_architecture.NewsApplication
import com.alihasan.newsapp_mvvm_architecture.data.api.NetworkService
import com.alihasan.newsapp_mvvm_architecture.data.local.database.AppDatabase
import com.alihasan.newsapp_mvvm_architecture.data.repository.TopHeadlineRepository
import com.alihasan.newsapp_mvvm_architecture.di.ApplicationContext
import com.alihasan.newsapp_mvvm_architecture.di.module.NewsApplicationModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NewsApplicationModule::class])
interface ApplicationComponent {

    fun inject(application: NewsApplication)

    @ApplicationContext
    fun getContext(): Context

    fun getNetworkService(): NetworkService
    fun getDatabaseService(): AppDatabase

}