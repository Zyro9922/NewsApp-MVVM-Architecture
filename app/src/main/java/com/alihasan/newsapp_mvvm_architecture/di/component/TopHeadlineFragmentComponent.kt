package com.alihasan.newsapp_mvvm_architecture.di.component

import com.alihasan.newsapp_mvvm_architecture.data.repository.TopHeadlineRepository
import com.alihasan.newsapp_mvvm_architecture.di.ActivityScope
import com.alihasan.newsapp_mvvm_architecture.di.module.TopHeadlineFragmentModule
import com.alihasan.newsapp_mvvm_architecture.ui.topheadline.TopHeadlineFragment
import dagger.Component

@ActivityScope
@Component(dependencies = [ApplicationComponent::class], modules = [TopHeadlineFragmentModule::class])
interface TopHeadlineFragmentComponent {

    fun inject(fragment: TopHeadlineFragment)
    fun getTopHeadlineRepository(): TopHeadlineRepository
}