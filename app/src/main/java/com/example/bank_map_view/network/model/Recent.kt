package com.example.bank_map_view.network.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import org.jetbrains.annotations.NotNull
import java.io.Serializable

@Entity(tableName = "recent")
data class Recent (

    @PrimaryKey
    @NotNull
    var name : String

)