package com.example.bank_map_view.network.response

import com.example.bank_map_view.network.model.Currency
import com.google.gson.annotations.SerializedName

class CurrencyResponse {

    @SerializedName("Data")
    val currency : ArrayList<Currency>? = null
}