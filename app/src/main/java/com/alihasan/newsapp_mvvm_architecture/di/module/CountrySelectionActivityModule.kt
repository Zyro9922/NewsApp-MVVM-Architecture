package com.alihasan.newsapp_mvvm_architecture.di.module

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.alihasan.newsapp_mvvm_architecture.data.repository.CountryListRepository
import com.alihasan.newsapp_mvvm_architecture.ui.base.ViewModelProviderFactory
import com.alihasan.newsapp_mvvm_architecture.ui.countryselection.CountrySelectionAdapter
import com.alihasan.newsapp_mvvm_architecture.ui.countryselection.CountrySelectionViewModel
import dagger.Module
import dagger.Provides

@Module
class CountrySelectionActivityModule(private val activity: AppCompatActivity) {

    @Provides
    fun provideCountryListViewModel(
        countryListRepository: CountryListRepository
    ): CountrySelectionViewModel {
        return ViewModelProvider(activity, ViewModelProviderFactory(CountrySelectionViewModel::class) {
            CountrySelectionViewModel(countryListRepository)
        })[CountrySelectionViewModel::class.java]
    }

    @Provides
    fun provideCountryListAdapter() = CountrySelectionAdapter(ArrayList())
}
