package com.example.bank_map_view.network.api

import com.example.bank_map_view.network.model.Access_BranchCode
import com.example.bank_map_view.network.response.BranchCodeResponse
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

internal interface RequestBranchDetailApi {

    @POST("api/branchDetail")
    fun getBranchDetail(@Header("Authorization") authHeader : String, @Body access_BranchCode: Access_BranchCode) : Call<BranchCodeResponse>
}