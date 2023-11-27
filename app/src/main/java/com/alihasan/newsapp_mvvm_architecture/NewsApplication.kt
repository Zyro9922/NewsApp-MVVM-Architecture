package com.alihasan.newsapp_mvvm_architecture

import android.app.Application
import com.alihasan.newsapp_mvvm_architecture.di.component.ApplicationComponent
import com.alihasan.newsapp_mvvm_architecture.di.component.DaggerApplicationComponent
import com.alihasan.newsapp_mvvm_architecture.di.module.NewsApplicationModule

class NewsApplication : Application() {

    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        injectDependencies()
    }

    private fun injectDependencies() {
        applicationComponent = DaggerApplicationComponent
            .builder()
            .newsApplicationModule(NewsApplicationModule(this))
            .build()
        applicationComponent.inject(this)
    }

}