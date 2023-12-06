package com.alihasan.newsapp_mvvm_architecture.ui.topheadline

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alihasan.newsapp_mvvm_architecture.R
import com.alihasan.newsapp_mvvm_architecture.databinding.ActivityTopHeadlinesBinding

class TopHeadlineActivity : AppCompatActivity() {

    companion object {

        const val EXTRAS_COUNTRY = "EXTRAS_COUNTRY"
        const val EXTRAS_SOURCE = "EXTRAS_SOURCE"
        const val EXTRAS_LANGUAGE = "EXTRAS_LANGUAGE"

        fun getStartIntentForCountry(context: Context, country: String): Intent {
            return Intent(context, TopHeadlineActivity::class.java)
                .apply {
                    putExtra(EXTRAS_COUNTRY, country)
                }
        }

        fun getStartIntentForSource(context: Context, source: String): Intent {
            return Intent(context, TopHeadlineActivity::class.java)
                .apply {
                    putExtra(EXTRAS_SOURCE, source)
                }
        }

        fun getStartIntentForLanguage(context: Context, language: String): Intent {
            return Intent(context, TopHeadlineActivity::class.java)
                .apply {
                    putExtra(EXTRAS_LANGUAGE, language)
                }
        }
    }

    private lateinit var binding: ActivityTopHeadlinesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTopHeadlinesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        handleFetchingAndOpeningFragment()
    }

    private fun handleFetchingAndOpeningFragment() {
        val extrasSource = intent.getStringExtra(EXTRAS_SOURCE)
        val extrasCountry = intent.getStringExtra(EXTRAS_COUNTRY)
        val extrasLanguage = intent.getStringExtra(EXTRAS_LANGUAGE)

        // Open TopHeadlineFragment
        val fragment = TopHeadlineFragment.newInstance(extrasCountry, extrasSource, extrasLanguage)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}