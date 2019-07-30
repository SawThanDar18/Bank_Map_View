package com.example.bank_map_view.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.example.bank_map_view.network.model.Recent

@Database(entities = [Recent::class], version = 1)
abstract class RecentDatabase : RoomDatabase() {

    abstract fun getRecentDao() : RecentDao

    companion object{

        private const val DB_NAME = "recent_words.db"
        private var INSTANCE : RecentDatabase? = null

        fun getDatabase(context: Context) : RecentDatabase{
            if(INSTANCE == null){
                INSTANCE = Room.databaseBuilder(context, RecentDatabase::class.java, DB_NAME)
                    .allowMainThreadQueries()
                    .build()
            }
            return INSTANCE!!
        }

        fun destroyInstance(){
            INSTANCE = null
        }
    }
}