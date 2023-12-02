package com.alihasan.newsapp_mvvm_architecture

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.alihasan.newsapp_mvvm_architecture.ui.countryselection.CountrySelectionActivity
import com.alihasan.newsapp_mvvm_architecture.ui.languageselection.LanguageSelectionActivity
import com.alihasan.newsapp_mvvm_architecture.ui.newssource.NewsSourceActivity
import com.alihasan.newsapp_mvvm_architecture.ui.search.SearchActivity
import com.alihasan.newsapp_mvvm_architecture.ui.topheadline.TopHeadlineActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setContentView(R.layout.activity_main)

        findViewById<View>(R.id.btnTopHeadlines).setOnClickListener(this)
        findViewById<View>(R.id.btnNewsSources).setOnClickListener(this)
        findViewById<View>(R.id.btnCountrySelection).setOnClickListener(this)
        findViewById<View>(R.id.btnLanguageSelection).setOnClickListener(this)
        findViewById<View>(R.id.btnSearch).setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btnTopHeadlines -> startActivity(Intent(this, TopHeadlineActivity::class.java))
            R.id.btnNewsSources -> startActivity(Intent(this, NewsSourceActivity::class.java))
            R.id.btnCountrySelection -> startActivity(Intent(this, CountrySelectionActivity::class.java))
            R.id.btnLanguageSelection -> startActivity(Intent(this, LanguageSelectionActivity::class.java))
            R.id.btnSearch -> startActivity(Intent(this, SearchActivity::class.java))
        }
    }
}