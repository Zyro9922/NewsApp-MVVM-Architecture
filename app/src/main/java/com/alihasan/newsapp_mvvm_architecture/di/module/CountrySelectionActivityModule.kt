package com.alihasan.newsapp_mvvm_architecture.di.module

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.alihasan.newsapp_mvvm_architecture.R
import com.alihasan.newsapp_mvvm_architecture.di.BackgroundColor
import com.alihasan.newsapp_mvvm_architecture.di.TextColor
import com.alihasan.newsapp_mvvm_architecture.ui.common.IntentfulListAdapter
import dagger.Module
import dagger.Provides

@Module
class CountrySelectionActivityModule(private val activity: AppCompatActivity) {

    @Provides
    fun provideContext(): Context {
        return activity
    }

    @Provides
    fun provideListOfCountriesName(): List<String> {
        val countryCodeNamePairs = activity.resources.getStringArray(R.array.country_code_name_pairs)
        return countryCodeNamePairs.map { pair ->
            pair.split(":")[1]
        }
    }

    @Provides
    fun provideListOfCountries(): List<String> {
        val countryCodeNamePairs = activity.resources.getStringArray(R.array.country_code_name_pairs)
        return countryCodeNamePairs.map { pair ->
            pair.split(":")[0]
        }
    }

    @Provides
    fun provideStringListAdapter(
        context: Context,
        @BackgroundColor backgroundColor: Int,
        @TextColor textColor: Int
    ): IntentfulListAdapter {
        return IntentfulListAdapter(context, provideListOfCountriesName(), provideListOfCountries(), backgroundColor, textColor, IntentfulListAdapter.IntentType.COUNTRY)
    }

    @Provides
    @BackgroundColor
    fun provideBackgroundColor(): Int {
        return ContextCompat.getColor(activity, R.color.country_selection_item_background_color)
    }

    @Provides
    @TextColor
    fun provideTextColor(): Int {
        return ContextCompat.getColor(activity, R.color.country_selection_item_text_color)
    }
}
