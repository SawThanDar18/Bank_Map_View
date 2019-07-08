package com.example.bank_map_view.network.response

import com.example.bank_branch_details.network.model.Access_BranchInfo
import com.example.bank_map_view.network.model.Access_BranchCode
import com.example.bank_map_view.network.model.Service_List
import com.google.gson.annotations.SerializedName

class BranchCodeResponse {

    @SerializedName("Request")
    val access_BranchCode : Access_BranchCode? = null

    @SerializedName("BRANCH_INFO")
    val access_BranchInfo : Access_BranchInfo? = null

    @SerializedName("Services")
    val service_List : ArrayList<Service_List>? = null
}