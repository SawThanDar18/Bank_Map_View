package com.example.bank_map_view.network.api

import com.example.bank_map_view.network.model.BranchCode
import com.example.bank_map_view.network.response.CurrencyResponse
import com.example.bank_map_view.network.response.ServiceResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface RequestServiceApi {

    @POST("api/servicelist")
    fun getService(@Header("Authorization") authHeader: String, @Body branchCode: BranchCode) : Call<ServiceResponse>
}