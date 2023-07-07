package com.submission.dicodingstory.dbroom

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.submission.dicodingstory.model.Story

@Database(entities = [Story::class, RemoteKeyModel::class], version = 2, exportSchema = false)
abstract class AppDb : RoomDatabase() {
    abstract fun storyDao(): StoryDao
    abstract fun rKDao(): RKDao
    companion object
    {
        @Volatile
        private var instance: AppDb? = null
        fun getDB(context: Context): AppDb =
            instance ?: synchronized(this)
            {
                instance ?: buildDB(context).also{
                    instance = it}}


        private fun buildDB(context: Context) =
            Room.databaseBuilder(context, AppDb::class.java, "App.db")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build()}

}