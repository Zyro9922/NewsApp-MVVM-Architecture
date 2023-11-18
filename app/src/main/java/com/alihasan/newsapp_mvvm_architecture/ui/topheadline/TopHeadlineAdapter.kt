package com.alihasan.newsapp_mvvm_architecture.ui.topheadline
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alihasan.newsapp_mvvm_architecture.R
import com.alihasan.newsapp_mvvm_architecture.data.model.Article
import com.bumptech.glide.Glide

class TopHeadlineAdapter(private val context: Context, private var articles: List<Article>) :
    RecyclerView.Adapter<TopHeadlineAdapter.ArticleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.top_headline_item_layout, parent, false)
        return ArticleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = articles[position]

        // Load image using Glide
        Glide.with(context)
            .load(article.imageUrl)
            .into(holder.itemView.findViewById(R.id.imageViewBanner))

        holder.itemView.findViewById<TextView>(R.id.textViewTitle).text = article.title
        holder.itemView.findViewById<TextView>(R.id.textViewDescription).text = article.description
        holder.itemView.findViewById<TextView>(R.id.textViewSource).text = article.source.name
    }

    override fun getItemCount(): Int {
        return articles.size
    }

    fun updateData(newArticles: List<Article>) {
        Log.d("ZYRO", newArticles.toString());
        articles = newArticles
        notifyDataSetChanged()
    }

    inner class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
