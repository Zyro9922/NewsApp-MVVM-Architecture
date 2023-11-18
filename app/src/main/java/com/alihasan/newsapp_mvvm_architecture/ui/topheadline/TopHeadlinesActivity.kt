package com.alihasan.newsapp_mvvm_architecture.ui.topheadline

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alihasan.newsapp_mvvm_architecture.R
import com.alihasan.newsapp_mvvm_architecture.data.api.NetworkService
import com.alihasan.newsapp_mvvm_architecture.data.repository.TopHeadlineRepository
import com.alihasan.newsapp_mvvm_architecture.utils.AppConstant
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TopHeadlinesActivity : AppCompatActivity() {
    private val newsRepository = TopHeadlineRepository(RetrofitClient.instance.create(NetworkService::class.java))
    private lateinit var articleAdapter: TopHeadlineAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top_headlines)

        // Initialize RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        articleAdapter = TopHeadlineAdapter(this, emptyList()) // Initially, the list is empty
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = articleAdapter

        // Fetch data from the repository
        fetchData()
    }

    private fun fetchData() {
        lifecycleScope.launch {
            try {
                val result = newsRepository.getTopHeadlines(AppConstant.COUNTRY)
                // Update the UI on the main thread
                withContext(Dispatchers.Main) {
                    articleAdapter.updateData(result.articles)
                }
            } catch (e: HttpException) {
                e.printStackTrace()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    object RetrofitClient {
        private const val BASE_URL = "https://newsapi.org/v2/"

        /**
         * lazy is a way to create a lazy property
         * meaning its value is computed only once when it is accessed for the first time,
         * and subsequent accesses return the previously computed value.
         */
        val instance: Retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}