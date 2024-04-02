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
        const val EXTRAS_SOURCE_ID = "EXTRAS_SOURCE"
        const val EXTRAS_LANGUAGE = "EXTRAS_LANGUAGE"

        fun getStartIntentForCountry(context: Context, country: String): Intent {
            return Intent(context, TopHeadlineActivity::class.java)
                .apply {
                    putExtra(EXTRAS_COUNTRY, country)
                }
        }

        fun getStartIntentForSourceId(context: Context, sourceId: String): Intent {
            return Intent(context, TopHeadlineActivity::class.java)
                .apply {
                    putExtra(EXTRAS_SOURCE_ID, sourceId)
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
        val extrasCountry = intent.getStringExtra(EXTRAS_COUNTRY)
        val extrasLanguage = intent.getStringExtra(EXTRAS_LANGUAGE)
        val extrasSourceId = intent.getStringExtra(EXTRAS_SOURCE_ID)

        val fragment = when {
            extrasCountry != null -> TopHeadlineFragment.forCountry(extrasCountry)
            extrasLanguage != null -> TopHeadlineFragment.forLanguage(extrasLanguage)
            extrasSourceId != null -> TopHeadlineFragment.forSource(extrasSourceId)
            else -> TopHeadlineFragment.newInstance()
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}