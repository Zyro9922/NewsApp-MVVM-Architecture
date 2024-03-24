package com.alihasan.newsapp_mvvm_architecture.ui.languageselection

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.alihasan.newsapp_mvvm_architecture.R
import com.alihasan.newsapp_mvvm_architecture.data.model.Language
import com.alihasan.newsapp_mvvm_architecture.ui.topheadline.TopHeadlineActivity

class LanguageSelectionAdapter(
    private var languageList: List<Language>
) : RecyclerView.Adapter<LanguageSelectionAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.string_item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val selectedLanguageId = languageList[position].id
        holder.bind(selectedLanguageId)
    }

    override fun getItemCount(): Int {
        return languageList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewItem: TextView = itemView.findViewById(R.id.textViewItem)

        fun bind(item: String) {
            textViewItem.text = languageList[bindingAdapterPosition].name
            textViewItem.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.language_selection_item_background_color))
            textViewItem.setTextColor(ContextCompat.getColor(itemView.context, R.color.language_selection_item_text_color))

            itemView.setOnClickListener {
                val intent = TopHeadlineActivity.getStartIntentForLanguage(itemView.context, item)
                itemView.context.startActivity(intent)
            }
        }
    }

    fun setLanguages(list: List<Language>) {
        languageList = list
    }
}