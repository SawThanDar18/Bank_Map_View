package com.example.bank_map_view.roomdb

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query

@Dao
interface ServicesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addServices(services: Services)

    @Query("SELECT * FROM services")
    fun getServices() : List<Services>

}