package com.alihasan.newsapp_mvvm_architecture.ui.languageselection

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alihasan.newsapp_mvvm_architecture.R
import com.alihasan.newsapp_mvvm_architecture.di.component.DaggerLanguageSelectionActivityComponent
import com.alihasan.newsapp_mvvm_architecture.di.module.LanguageSelectionActivityModule
import com.alihasan.newsapp_mvvm_architecture.ui.common.IntentfulListAdapter
import javax.inject.Inject

class LanguageSelectionActivity : AppCompatActivity() {

    @Inject
    lateinit var adapter: IntentfulListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_language_selection)

        // Dagger injection
        DaggerLanguageSelectionActivityComponent.builder()
            .languageSelectionActivityModule(LanguageSelectionActivityModule(this)).build().inject(this)

        // Create an instance of StringListAdapter
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }
}