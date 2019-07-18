package com.example.bank_map_view.roomdb

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "services")
data class Services(
    @PrimaryKey
    @NotNull
    @ColumnInfo(name = "service_code") var service_code : String,
    @ColumnInfo(name = "title") var title : String
)