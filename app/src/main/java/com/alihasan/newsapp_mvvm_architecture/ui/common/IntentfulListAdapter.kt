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

class IntentfulListAdapter(
    private val context: Context,
    private val titleList: List<String>,
    private val codeList: List<String>,
    private val backgroundColor: Int,
    private val textColor: Int,
    private val intentType: IntentType
) : RecyclerView.Adapter<IntentfulListAdapter.ViewHolder>() {

    enum class IntentType {
        COUNTRY, SOURCE, LANGUAGE
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.string_item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(codeList[position])
    }

    override fun getItemCount(): Int {
        return codeList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewItem: TextView = itemView.findViewById(R.id.textViewItem)

        fun bind(item: String) {
            textViewItem.text = titleList[bindingAdapterPosition]
            textViewItem.setBackgroundColor(backgroundColor)
            textViewItem.setTextColor(textColor)

            itemView.setOnClickListener {
                val intent = createIntent(context, item)
                itemView.context.startActivity(intent)
            }
        }

        private fun createIntent(context: Context, item: String): Intent {
            return when (intentType) {
                IntentType.COUNTRY -> TopHeadlineActivity.getStartIntentForCountry(context, item)
                IntentType.SOURCE -> TopHeadlineActivity.getStartIntentForSourceId(context, item)
                IntentType.LANGUAGE -> TopHeadlineActivity.getStartIntentForLanguage(context, item)
            }
        }
    }


}
