package com.alihasan.newsapp_mvvm_architecture.di.component

import com.alihasan.newsapp_mvvm_architecture.di.ActivityScope
import com.alihasan.newsapp_mvvm_architecture.di.module.ActivityModule
import com.alihasan.newsapp_mvvm_architecture.ui.countryselection.CountrySelectionActivity
import com.alihasan.newsapp_mvvm_architecture.ui.languageselection.LanguageSelectionActivity
import dagger.Component

@ActivityScope
@Component(modules = [ActivityModule::class])
interface ActivityComponent {
    fun inject(activity: CountrySelectionActivity)
    fun inject(activity: LanguageSelectionActivity)
}