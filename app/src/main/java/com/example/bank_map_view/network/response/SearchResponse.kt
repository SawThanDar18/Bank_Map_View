package com.example.bank_map_view.network.response

import com.example.bank_map_view.network.model.Search_List
import com.google.gson.annotations.SerializedName

class SearchResponse {

    @SerializedName("Data")
    val search_List : ArrayList<Search_List>? = null
}