package com.example.bank_map_view.mvp.view

import com.example.bank_map_view.network.response.SearchResponse

interface SearchListView {

    fun showPrompt(message : String)
    fun showLoading()
    fun dismissLoading()
    fun showSearchList(searchResponse: SearchResponse)
    fun retrieveFromDB()
}