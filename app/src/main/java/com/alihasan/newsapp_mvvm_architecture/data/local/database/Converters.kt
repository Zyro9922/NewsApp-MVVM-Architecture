package com.alihasan.newsapp_mvvm_architecture.data.local.database
import androidx.room.TypeConverter
import com.alihasan.newsapp_mvvm_architecture.data.model.TopHeadlineModel.Article
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class Converters {
    @TypeConverter
    fun fromArticleList(articles: List<Article>): String {
        // Convert List<Article> to JSON string
        val gson = Gson()
        return gson.toJson(articles)
    }

    @TypeConverter
    fun toArticleList(json: String): List<Article> {
        // Convert JSON string to List<Article>
        val gson = Gson()
        val type: Type = object : TypeToken<List<Article>>() {}.type
        return gson.fromJson(json, type)
    }
}
