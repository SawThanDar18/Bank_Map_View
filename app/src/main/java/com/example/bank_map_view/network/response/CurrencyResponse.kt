package com.example.bank_map_view.network.response

import com.example.bank_map_view.network.model.Currency
import com.example.bank_map_view.network.model.Service_List
import com.google.gson.annotations.SerializedName

class CurrencyResponse {

    @SerializedName("Data")
    val currency : ArrayList<Currency>? = null

    @SerializedName("Data")
    val service_List : ArrayList<Service_List>? = null

}