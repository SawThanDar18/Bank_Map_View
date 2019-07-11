package com.example.bank_map_view.network.model

import com.google.gson.annotations.SerializedName

class Search_List {

    @SerializedName("TouchPointType")
    val touchPointType : String? = null

    @SerializedName("ID")
    val id : String? = null

    @SerializedName("Location_Name")
    val location_Name : String? = null

    @SerializedName("Latitude")
    val latitude : Double? = null

    @SerializedName("Longitude")
    val longitude : Double? = null

    @SerializedName("Address")
    val address : String? = null

    @SerializedName("Phone")
    val phone : String? = null
}