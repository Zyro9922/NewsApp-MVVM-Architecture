package com.alihasan.newsapp_mvvm_architecture.ui.topheadline

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.RecyclerView
import com.alihasan.newsapp_mvvm_architecture.R
import com.alihasan.newsapp_mvvm_architecture.data.model.TopHeadlineModel.Article
import com.alihasan.newsapp_mvvm_architecture.databinding.TopHeadlineItemLayoutBinding
import com.bumptech.glide.Glide
import javax.inject.Inject

class TopHeadlineAdapter @Inject constructor (private var articles: List<Article>) :
    RecyclerView.Adapter<TopHeadlineAdapter.DataViewHolder>() {

    class DataViewHolder(private val binding: TopHeadlineItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Article) {
            binding.textViewTitle.text = article.title
            binding.textViewDescription.text = article.description
            binding.textViewSource.text = article.source.name
            Glide.with(binding.imageViewBanner.context)
                .load(article.imageUrl)
                .fallback(R.drawable.baseline_broken_image_24)
                .into(binding.imageViewBanner)
            itemView.setOnClickListener {
                val builder = CustomTabsIntent.Builder()
                val customTabsIntent = builder.build()
                customTabsIntent.launchUrl(it.context, Uri.parse(article.url))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DataViewHolder(
            TopHeadlineItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(articles[position])

    override fun getItemCount(): Int {
        return articles.size
    }

    fun updateData(newArticles: List<Article>) {
        articles = newArticles
        notifyDataSetChanged()
    }
}
