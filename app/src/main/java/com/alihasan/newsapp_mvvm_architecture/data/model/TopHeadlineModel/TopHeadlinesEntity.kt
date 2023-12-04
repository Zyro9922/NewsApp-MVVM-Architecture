package com.alihasan.newsapp_mvvm_architecture.data.model.TopHeadlineModel

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "top_headlines")
data class TopHeadlinesEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @SerializedName("status")
    val status: String = "",
    @SerializedName("totalResults")
    val totalResults: Int = 0,
    @SerializedName("articles")
    val articles: List<Article> = emptyList(),
    @ColumnInfo(name = "data_last_updated")
    val dataLastUpdated: Long = 0
)