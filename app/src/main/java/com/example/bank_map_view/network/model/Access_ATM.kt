package com.example.bank_branch_details.network.model

import com.google.gson.annotations.SerializedName

data class Access_ATM (@SerializedName("Terminal_ID") var Terminal_ID : String,
                       @SerializedName("ATM_Type") var ATM_Type : String,
                       @SerializedName("Location_Name") var Location_Name : String,
                       @SerializedName("Latitude") var Latitude : Double,
                       @SerializedName("Longitude") var Longitude : Double,
                       @SerializedName("Address") var Address : String,
                       @SerializedName("TouchPointType") var TouchPointType : String,
                       @SerializedName("Distance") var Distance : Double)