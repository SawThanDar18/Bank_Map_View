package com.example.bank_map_view.network.api

import com.example.bank_map_view.network.model.Access_NearestCurrencyExchange
import com.example.bank_map_view.network.response.NearestCurrencyExchangeResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface RequestNearestCurrencyExchangeApi {

    @POST("api/serviceTouchpoints")
    fun getNearestExchange(@Header("Authorization") authHeader: String,
                                   @Body access_Nearest_CurrencyExchange: Access_NearestCurrencyExchange) : Call<NearestCurrencyExchangeResponse>
}