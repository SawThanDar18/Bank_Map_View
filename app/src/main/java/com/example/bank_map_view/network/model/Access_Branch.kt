package com.example.bank_branch_details.network.model

import com.google.gson.annotations.SerializedName

data class Access_Branch (@SerializedName("Branch_Code") var Branch_Code : String,
                          @SerializedName("Branch_Alias") var Branch_Alias : String,
                          @SerializedName("Branch_Name") var Branch_Name : String,
                          @SerializedName("Latitude") var Latitude : Double,
                          @SerializedName("Longitude") var Longitude : Double,
                          @SerializedName("Phone") var Phone : String,
                          @SerializedName("Address") var Address : String,
                          @SerializedName("TouchPointType") var TouchPointType : String,
                          @SerializedName("Distance") var Distance : Double)