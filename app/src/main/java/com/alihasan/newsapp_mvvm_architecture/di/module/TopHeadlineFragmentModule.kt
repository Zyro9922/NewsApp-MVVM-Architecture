package com.alihasan.newsapp_mvvm_architecture.di.module

import android.content.Context
import androidx.fragment.app.Fragment
import com.alihasan.newsapp_mvvm_architecture.data.local.database.AppDatabase
import com.alihasan.newsapp_mvvm_architecture.di.ActivityContext
import com.alihasan.newsapp_mvvm_architecture.ui.topheadline.TopHeadlineAdapter
import dagger.Module
import dagger.Provides

@Module
class TopHeadlineFragmentModule(private val fragment: Fragment) {

    @ActivityContext
    @Provides
    fun provideContext(): Context {
        return fragment.requireContext()
    }

    @Provides
    fun provideTopHeadlineAdapter() = TopHeadlineAdapter(ArrayList())

    @Provides
    fun provideTopHeadlinesDao(database: AppDatabase) = database.topHeadlinesDao()

}