package com.example.bank_map_view.room

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.example.bank_map_view.network.model.Recent

@Dao
interface RecentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addRecentWord(recent: Recent) : Long

    @Query("SELECT * FROM recent")
    fun getRecentWords() : List<Recent>
}