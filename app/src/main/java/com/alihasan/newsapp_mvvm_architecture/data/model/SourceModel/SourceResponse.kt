package com.alihasan.newsapp_mvvm_architecture.data.model.SourceModel

import com.google.gson.annotations.SerializedName

data class SourceResponse(
    @SerializedName("sources")
    val sources: List<Source>,
    @SerializedName("status")
    val status: String
)