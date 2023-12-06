package com.alihasan.newsapp_mvvm_architecture.di.component

import com.alihasan.newsapp_mvvm_architecture.di.ActivityScope
import com.alihasan.newsapp_mvvm_architecture.di.module.LanguageSelectionActivityModule
import com.alihasan.newsapp_mvvm_architecture.ui.languageselection.LanguageSelectionActivity
import dagger.Component

@Component(modules = [LanguageSelectionActivityModule::class])
interface LanguageSelectionActivityComponent {

    fun inject(activity: LanguageSelectionActivity)
}