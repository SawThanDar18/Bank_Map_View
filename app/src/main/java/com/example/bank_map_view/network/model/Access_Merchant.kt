package com.example.bank_map_view.network.model

import com.google.gson.annotations.SerializedName

class Access_Merchant {

    @SerializedName("Merchant_ID")
    val merchant_id : String? = null

    @SerializedName("Merchant_Name")
    val merchant_name : String? = null

    @SerializedName("Latitude")
    val latitude : Double? = null

    @SerializedName("Longitude")
    val longitude : Double? = null

    @SerializedName("Address")
    val merchant_address : String? = null
}

