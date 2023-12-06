package com.alihasan.newsapp_mvvm_architecture.di.module

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.alihasan.newsapp_mvvm_architecture.R
import com.alihasan.newsapp_mvvm_architecture.di.BackgroundColor
import com.alihasan.newsapp_mvvm_architecture.di.TextColor
import com.alihasan.newsapp_mvvm_architecture.ui.common.StringListAdapter
import dagger.Module
import dagger.Provides

@Module
class CountrySelectionActivityModule(private val activity: AppCompatActivity) {

    @Provides
    fun provideContext(): Context {
        return activity
    }

    @Provides
    fun provideListOfCountries(): List<String> {
        return activity.resources.getStringArray(R.array.country_list).toList()
    }

    @Provides
    fun provideStringListAdapter(
        context: Context,
        @BackgroundColor backgroundColor: Int,
        @TextColor textColor: Int
    ): StringListAdapter {
        return StringListAdapter(context, provideListOfCountries(), backgroundColor, textColor)
    }

    @Provides
    @BackgroundColor
    fun provideBackgroundColor(): Int {
        return 0xFFFFA500.toInt() // Orange color
    }

    @Provides
    @TextColor
    fun provideTextColor(): Int {
        return 0xFF000000.toInt() // Black color (contrasting)
    }
}
