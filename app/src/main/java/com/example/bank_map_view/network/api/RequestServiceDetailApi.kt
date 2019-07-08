package com.example.bank_map_view.network.api

import com.example.bank_map_view.network.model.ServiceCode
import com.example.bank_map_view.network.response.ServiceResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface RequestServiceDetailApi {

    @POST("api/servicebycode")
    fun getServiceDetail(@Header("Authorization") authHeader: String, @Body serviceCode: ServiceCode) : Call<ServiceResponse>
}