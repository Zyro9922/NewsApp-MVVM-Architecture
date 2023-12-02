package com.alihasan.newsapp_mvvm_architecture.data.model.SourceModel

import com.google.gson.annotations.SerializedName

data class Source(
    @SerializedName("category")
    val category: String,
    @SerializedName("country")
    val country: String,
    @SerializedName("description")
    val sourceDescription: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("language")
    val language: String,
    @SerializedName("name")
    val sourceName: String,
    @SerializedName("url")
    val sourceUrl: String
)