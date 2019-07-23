package com.example.bank_map_view.network.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import org.jetbrains.annotations.NotNull

@Entity(tableName = "service_list")
class Service_List {

    @PrimaryKey
    @NotNull
    @SerializedName("service_code")
    var service_code : String? = null

    @SerializedName("title")
    var title : String? = null

    @Ignore
    @SerializedName("description")
    val description : String? = null

    @Ignore
    @SerializedName("image_path")
    val image_path : String? = null

    @Ignore
    @SerializedName("service_detail")
    val service_detail : String? = null
}