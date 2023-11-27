package com.alihasan.newsapp_mvvm_architecture.ui.topheadline

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alihasan.newsapp_mvvm_architecture.NewsApplication
import com.alihasan.newsapp_mvvm_architecture.R
import com.alihasan.newsapp_mvvm_architecture.data.repository.TopHeadlineRepository
import com.alihasan.newsapp_mvvm_architecture.di.component.DaggerTopHeadlineActivityComponent
import com.alihasan.newsapp_mvvm_architecture.di.module.TopHeadlineActivityModule
import com.alihasan.newsapp_mvvm_architecture.utils.AppConstant
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject

class TopHeadlineActivity : AppCompatActivity() {
    @Inject
    lateinit var topHeadlineRepository: TopHeadlineRepository

    @Inject
    lateinit var articleAdapter: TopHeadlineAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top_headlines)

        injectDependencies()
        initializeRecyclerView()
        fetchData()
    }

    private fun initializeRecyclerView(){
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = articleAdapter
    }

    private fun injectDependencies() {
        DaggerTopHeadlineActivityComponent.builder()
            .applicationComponent((application as NewsApplication).applicationComponent)
            .topHeadlineActivityModule(TopHeadlineActivityModule(this)).build().inject(this)
    }

    private fun fetchData() {
        lifecycleScope.launch {
            try {
                val result = topHeadlineRepository.getTopHeadlines(AppConstant.COUNTRY)
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
}