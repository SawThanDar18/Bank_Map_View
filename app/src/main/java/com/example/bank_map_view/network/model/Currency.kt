package com.example.bank_map_view.network.model

import com.google.gson.annotations.SerializedName

class Currency {

    @SerializedName("CurrencyCode")
    val currecyCode : String? = null

    @SerializedName("Denomination")
    val denomination : String? = null

    @SerializedName("Buy_Rate")
    val buy_rate : String? = null

    @SerializedName("Sell_Rate")
    val sell_rate : String? = null
}