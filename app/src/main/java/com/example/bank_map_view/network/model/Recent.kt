package com.example.bank_map_view.network.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import org.jetbrains.annotations.NotNull

@Entity(tableName = "recent")
data class Recent (

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @NotNull
    @ColumnInfo(name = "name")
    var name: String? = null
)