package com.alihasan.newsapp_mvvm_architecture.di.module

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.alihasan.newsapp_mvvm_architecture.data.repository.LanguageListRepository
import com.alihasan.newsapp_mvvm_architecture.ui.base.ViewModelProviderFactory
import com.alihasan.newsapp_mvvm_architecture.ui.languageselection.LanguageSelectionAdapter
import com.alihasan.newsapp_mvvm_architecture.ui.languageselection.LanguageSelectionViewModel
import dagger.Module
import dagger.Provides

@Module
class LanguageSelectionActivityModule(private val activity: AppCompatActivity) {

    @Provides
    fun provideLanguageListViewModel(
        languageListRepository: LanguageListRepository
    ): LanguageSelectionViewModel {
        return ViewModelProvider(activity, ViewModelProviderFactory(LanguageSelectionViewModel::class) {
            LanguageSelectionViewModel(languageListRepository)
        })[LanguageSelectionViewModel::class.java]
    }

    @Provides
    fun provideLanguageListAdapter() = LanguageSelectionAdapter(ArrayList())
}