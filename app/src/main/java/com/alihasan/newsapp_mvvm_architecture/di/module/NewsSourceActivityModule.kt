package com.alihasan.newsapp_mvvm_architecture.di.module

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.alihasan.newsapp_mvvm_architecture.di.ActivityContext
import com.alihasan.newsapp_mvvm_architecture.ui.newssource.NewsSourceAdapter
import dagger.Module
import dagger.Provides

@Module
class NewsSourceActivityModule(private val activity: AppCompatActivity) {
    @ActivityContext
    @Provides
    fun provideContext(): Context {
        return activity
    }

    @Provides
    fun provideNewsListAdapter() = NewsSourceAdapter(ArrayList())
}
