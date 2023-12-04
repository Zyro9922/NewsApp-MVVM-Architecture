package com.alihasan.newsapp_mvvm_architecture.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.alihasan.newsapp_mvvm_architecture.data.local.dao.TopHeadlinesDao
import com.alihasan.newsapp_mvvm_architecture.data.model.TopHeadlineModel.TopHeadlinesEntity

@Database(entities = [TopHeadlinesEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun topHeadlinesDao(): TopHeadlinesDao
}