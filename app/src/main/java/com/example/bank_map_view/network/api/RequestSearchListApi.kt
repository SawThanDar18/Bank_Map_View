package com.example.bank_map_view.network.api

import com.example.bank_map_view.network.model.SearchCode
import com.example.bank_map_view.network.response.SearchResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface RequestSearchListApi {

    @POST("api/searchTouchpointList")
    fun getSearchList(@Header("Authorization") authHeader: String,
                    @Body searchCode: SearchCode) : Call<SearchResponse>
}