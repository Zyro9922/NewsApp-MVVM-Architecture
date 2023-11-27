package com.alihasan.newsapp_mvvm_architecture.di.component

import com.alihasan.newsapp_mvvm_architecture.data.repository.TopHeadlineRepository
import com.alihasan.newsapp_mvvm_architecture.di.ActivityScope
import com.alihasan.newsapp_mvvm_architecture.di.module.TopHeadlineActivityModule
import com.alihasan.newsapp_mvvm_architecture.ui.topheadline.TopHeadlineActivity
import dagger.Component

@ActivityScope
@Component(dependencies = [ApplicationComponent::class], modules = [TopHeadlineActivityModule::class])
interface TopHeadlineActivityComponent {

    fun inject(activity: TopHeadlineActivity)
    fun getTopHeadlineRepository(): TopHeadlineRepository
}