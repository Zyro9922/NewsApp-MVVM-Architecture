package com.alihasan.newsapp_mvvm_architecture.ui.common

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alihasan.newsapp_mvvm_architecture.R
import com.alihasan.newsapp_mvvm_architecture.ui.topheadline.TopHeadlineActivity

class StringListAdapter(
    private val context: Context,
    private val items: List<String>,
    private val backgroundColor: Int,
    private val textColor: Int
) : RecyclerView.Adapter<StringListAdapter.ViewHolder>() {

    enum class IntentType {
        COUNTRY, SOURCE, LANGUAGE
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.string_item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewItem: TextView = itemView.findViewById(R.id.textViewItem)

        fun bind(item: String) {
            textViewItem.text = item
            textViewItem.setBackgroundColor(backgroundColor)
            textViewItem.setTextColor(textColor)

//            itemView.setOnClickListener {
//                val intent = createIntent(context, item)
//                itemView.context.startActivity(intent)
//            }
        }
    }
}
