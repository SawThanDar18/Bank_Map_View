package com.example.bank_map_view.network.model

import com.google.gson.annotations.SerializedName

class Access_Agent {

    @SerializedName("Agent_ID")
    val agent_id : String? = null

    @SerializedName("Agent_Name")
    val agent_name : String? = null

    @SerializedName("Latitude")
    val latitude : Double? = null

    @SerializedName("Longitude")
    val longitude : Double? = null

    @SerializedName("Address")
    val agent_address : String? = null
}