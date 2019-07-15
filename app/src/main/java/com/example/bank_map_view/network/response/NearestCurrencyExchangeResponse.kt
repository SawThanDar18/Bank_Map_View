package com.example.bank_map_view.network.response

import com.example.bank_branch_details.network.model.Access_Branch
import com.example.bank_map_view.network.model.Access_NearestCurrencyExchange
import com.google.gson.annotations.SerializedName

class NearestCurrencyExchangeResponse {

    @SerializedName("Request")
    val access_Nearest_CurrencyExchange : Access_NearestCurrencyExchange? = null

    @SerializedName("BRANCH")
    val access_Branch : ArrayList<Access_Branch>? = null
}