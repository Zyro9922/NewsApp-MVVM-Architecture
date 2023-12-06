package com.alihasan.newsapp_mvvm_architecture.ui.countryselection

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alihasan.newsapp_mvvm_architecture.R
import com.alihasan.newsapp_mvvm_architecture.di.component.DaggerCountrySelectionActivityComponent
import com.alihasan.newsapp_mvvm_architecture.di.module.CountrySelectionActivityModule
import com.alihasan.newsapp_mvvm_architecture.ui.common.IntentfulListAdapter
import javax.inject.Inject

class CountrySelectionActivity : AppCompatActivity() {

    @Inject
    lateinit var context: Context

    @Inject
    lateinit var adapter: IntentfulListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_country_selection)

        // Dagger injection
        DaggerCountrySelectionActivityComponent.builder()
            .countrySelectionActivityModule(CountrySelectionActivityModule(this)).build().inject(this)

        // Create an instance of StringListAdapter
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }
}
