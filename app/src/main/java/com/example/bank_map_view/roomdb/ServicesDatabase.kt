package com.example.bank_map_view.roomdb

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

@Database(entities = [Services::class], version = 1)
abstract class ServicesDatabase : RoomDatabase() {

    abstract fun getServicesDao() : ServicesDao

    companion object{

        private const val DB_NAME = "services.db"
        private var INSTANCE : ServicesDatabase? = null

        fun getDatabase(context: Context) : ServicesDatabase{
            if(INSTANCE == null){
                INSTANCE = Room.databaseBuilder(context, ServicesDatabase::class.java, DB_NAME)
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