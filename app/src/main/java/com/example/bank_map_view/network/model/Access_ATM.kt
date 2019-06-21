package com.example.bank_branch_details.network.model

import com.google.gson.annotations.SerializedName

class Access_ATM {
    @SerializedName("Terminal_ID")
    var Terminal_ID : String? = null

    @SerializedName("ATM_Type")
    var ATM_Type : String? = null

    @SerializedName("Location_Name")
    var Location_Name : String? = null

    @SerializedName("Latitude")
    var Latitude : Double? = null

    @SerializedName("Longitude")
    var Longitude : Double? = null

    @SerializedName("Address")
    var Address : String? = null

    @SerializedName("TouchPointType")
    var TouchPointType : String? = null

    @SerializedName("Distance")
    var Distance : Double? = null
}