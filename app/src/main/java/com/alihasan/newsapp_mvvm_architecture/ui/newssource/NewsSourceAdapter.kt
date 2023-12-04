package com.alihasan.newsapp_mvvm_architecture.ui.newssource

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.RecyclerView
import com.alihasan.newsapp_mvvm_architecture.R
import com.alihasan.newsapp_mvvm_architecture.data.model.SourceModel.Source
import com.alihasan.newsapp_mvvm_architecture.databinding.SourceItemLayoutBinding
import com.alihasan.newsapp_mvvm_architecture.ui.topheadline.TopHeadlineActivity
import javax.inject.Inject
class NewsSourceAdapter @Inject constructor(private var sources: List<Source>) :
    RecyclerView.Adapter<NewsSourceAdapter.DataViewHolder>() {

    class DataViewHolder(private val binding: SourceItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(source: Source) {
            binding.sourceNameTextView.text = source.sourceName
            binding.sourceDescriptionTextView.text = source.sourceDescription
            binding.categoryTextView.text = "${itemView.context.getString(R.string.category_text)}: ${source.category.capitalize()}"

            itemView.setOnClickListener {
                val intent = TopHeadlineActivity.getStartIntentForSource(itemView.context, source.id)
                itemView.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DataViewHolder(
            SourceItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(sources[position])

    override fun getItemCount(): Int {
        return sources.size
    }

    fun updateData(newsSource: List<Source>) {
        sources = newsSource
        notifyDataSetChanged()
    }
}