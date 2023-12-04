package com.alihasan.newsapp_mvvm_architecture.di.module

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.alihasan.newsapp_mvvm_architecture.data.local.dao.TopHeadlinesDao
import com.alihasan.newsapp_mvvm_architecture.data.local.database.AppDatabase
import com.alihasan.newsapp_mvvm_architecture.di.ActivityContext
import com.alihasan.newsapp_mvvm_architecture.ui.topheadline.TopHeadlineAdapter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class TopHeadlineActivityModule(private val activity: AppCompatActivity) {

    @ActivityContext
    @Provides
    fun provideContext(): Context {
        return activity
    }

    @Provides
    fun provideTopHeadlineAdapter() = TopHeadlineAdapter(ArrayList())

    @Provides
    fun provideTopHeadlinesDao(database: AppDatabase) = database.topHeadlinesDao()

}