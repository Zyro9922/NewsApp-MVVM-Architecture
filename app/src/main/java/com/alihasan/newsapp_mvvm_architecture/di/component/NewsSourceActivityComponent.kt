package com.alihasan.newsapp_mvvm_architecture.di.component

import com.alihasan.newsapp_mvvm_architecture.data.repository.SourceRepository
import com.alihasan.newsapp_mvvm_architecture.di.ActivityScope
import com.alihasan.newsapp_mvvm_architecture.di.module.NewsSourceActivityModule
import com.alihasan.newsapp_mvvm_architecture.ui.newssource.NewsSourceActivity
import dagger.Component

@ActivityScope
@Component(dependencies = [ApplicationComponent::class], modules = [NewsSourceActivityModule::class])
interface NewsSourceActivityComponent {
    fun inject(activity: NewsSourceActivity)
    fun getSourceRepository(): SourceRepository
}
