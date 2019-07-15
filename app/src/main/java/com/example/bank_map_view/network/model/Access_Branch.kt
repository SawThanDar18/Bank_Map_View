package com.example.bank_branch_details.network.model

import com.google.gson.annotations.SerializedName

class Access_Branch {@SerializedName("Branch_Code") val Branch_Code : String? = null
                          @SerializedName("Branch_Alias") val Branch_Alias : String? = null
                          @SerializedName("Branch_Name") val Branch_Name : String? = null
                          @SerializedName("Latitude") val Latitude : Double? = null
                          @SerializedName("Longitude") val Longitude : Double? = null
                          @SerializedName("Phone") val Phone : String? = null
                          @SerializedName("Address") val Address : String? = null
                          @SerializedName("TouchPointType") val TouchPointType : String? = null
                          @SerializedName("Distance") val Distance : Double? = null
}