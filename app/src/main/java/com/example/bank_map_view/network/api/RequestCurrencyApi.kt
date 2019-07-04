package com.example.bank_map_view.network.api

import com.example.bank_map_view.network.model.BranchCode
import com.example.bank_map_view.network.response.CurrencyResponse
import retrofit2.Call
import retrofit2.http.*

interface RequestCurrencyApi {

    @POST("api/getCurrencyRate")
    fun getCurrency(@Header("Authorization") authHeader: String,
                    @Body branchCode: BranchCode,
                    @QueryMap parameters: Map<String, String>) : Call<CurrencyResponse>
}