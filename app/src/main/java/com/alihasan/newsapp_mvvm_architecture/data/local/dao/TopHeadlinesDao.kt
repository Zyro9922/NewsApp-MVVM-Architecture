package com.alihasan.newsapp_mvvm_architecture.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alihasan.newsapp_mvvm_architecture.data.model.TopHeadlineModel.TopHeadlinesEntity

@Dao
interface TopHeadlinesDao {
    @Query("SELECT * FROM top_headlines WHERE data_last_updated >= :minTimestamp")
    suspend fun getTopHeadlines(minTimestamp: Long): TopHeadlinesEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTopHeadlines(topHeadlinesEntity: TopHeadlinesEntity)
}