package com.example.bank_map_view.room

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.example.bank_map_view.network.model.Service_List
import android.arch.lifecycle.LiveData



@Dao
interface ServicesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addServices(services: List<Service_List>) : LongArray

    @Query("SELECT * FROM service_list")
    fun getServices() : List<Service_List>

    @Query("SELECT * FROM service_list where title LIKE :keyword")
    fun getSearchResult(keyword: String): List<Service_List>

}