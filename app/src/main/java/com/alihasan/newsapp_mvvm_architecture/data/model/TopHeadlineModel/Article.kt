package com.alihasan.newsapp_mvvm_architecture.data.model.TopHeadlineModel

import com.google.gson.annotations.SerializedName

data class Article(
    @SerializedName("title")
    val title: String = "",
    @SerializedName("description")
    val description: String = "",
    @SerializedName("url")
    val url: String = "",
    @SerializedName("urlToImage")
    val imageUrl: String = "",
    @SerializedName("source")
    val source: Source
)