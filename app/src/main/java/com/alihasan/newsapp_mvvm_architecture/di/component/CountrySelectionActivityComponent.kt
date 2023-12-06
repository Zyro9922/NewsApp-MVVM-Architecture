package com.alihasan.newsapp_mvvm_architecture.di.component

import com.alihasan.newsapp_mvvm_architecture.di.module.CountrySelectionActivityModule
import com.alihasan.newsapp_mvvm_architecture.ui.countryselection.CountrySelectionActivity
import dagger.Component

@Component(modules = [CountrySelectionActivityModule::class])
interface CountrySelectionActivityComponent {
    fun inject(activity: CountrySelectionActivity)
}
