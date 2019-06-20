package com.example.bank_branch_details.network.model

import com.google.gson.annotations.SerializedName

class Access_TouchPointList(@SerializedName("category") val category : String,
                            @SerializedName("pageNo") val pageNo : String,
                            @SerializedName("currentLat") val currentLat : Double,
                            @SerializedName("currentLong") val currentLong : Double,
                            @SerializedName("range") val range : String,
                            @SerializedName("appVersion") val appVersion : String)


