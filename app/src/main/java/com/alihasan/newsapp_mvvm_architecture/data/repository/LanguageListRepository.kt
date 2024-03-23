package com.alihasan.newsapp_mvvm_architecture.data.repository

import com.alihasan.newsapp_mvvm_architecture.data.model.Language
import com.alihasan.newsapp_mvvm_architecture.di.ActivityScope
import com.alihasan.newsapp_mvvm_architecture.utils.AppConstant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@ActivityScope
class LanguageListRepository @Inject constructor() {

    fun getLanguages(): Flow<List<Language>> {
        return flow { emit(AppConstant.LANGUAGES) }
    }

}
