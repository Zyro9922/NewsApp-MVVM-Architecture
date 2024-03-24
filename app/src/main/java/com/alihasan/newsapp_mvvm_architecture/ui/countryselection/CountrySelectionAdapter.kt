package com.alihasan.newsapp_mvvm_architecture.ui.countryselection

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.alihasan.newsapp_mvvm_architecture.R
import com.alihasan.newsapp_mvvm_architecture.data.model.Country
import com.alihasan.newsapp_mvvm_architecture.ui.topheadline.TopHeadlineActivity

class CountrySelectionAdapter(
    private var countryList: List<Country>
) : RecyclerView.Adapter<CountrySelectionAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.string_item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val selectedLanguageId = countryList[position].id
        holder.bind(selectedLanguageId)
    }

    override fun getItemCount(): Int {
        return countryList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewItem: TextView = itemView.findViewById(R.id.textViewItem)

        fun bind(item: String) {
            textViewItem.text = countryList[bindingAdapterPosition].name
            textViewItem.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.country_selection_item_background_color))
            textViewItem.setTextColor(ContextCompat.getColor(itemView.context, R.color.country_selection_item_text_color))

            itemView.setOnClickListener {
                val intent = TopHeadlineActivity.getStartIntentForCountry(itemView.context, item)
                itemView.context.startActivity(intent)
            }
        }
    }

    fun setCountries(list: List<Country>) {
        countryList = list
    }
}