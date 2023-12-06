package com.alihasan.newsapp_mvvm_architecture.di.component

import com.alihasan.newsapp_mvvm_architecture.di.ActivityScope
import com.alihasan.newsapp_mvvm_architecture.di.module.SearchActivityModule
import com.alihasan.newsapp_mvvm_architecture.ui.search.SearchActivity
import dagger.Component

@ActivityScope
@Component(dependencies = [ApplicationComponent::class], modules = [SearchActivityModule::class])
interface SearchActivityComponent {
    fun inject(activity: SearchActivity)
}
