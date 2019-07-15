package com.example.bank_map_view.mvp.view

import com.example.bank_branch_details.network.model.Access_Branch
import com.example.bank_map_view.network.response.NearestCurrencyExchangeResponse

interface NearestCurrencyExchangeView {
    fun showPrompt(message : String)
    fun showLoading()
    fun dismissLoading()
    fun showNearestCurrencyExchange(nearestCurrencyExchangeResponse: NearestCurrencyExchangeResponse)
    fun showPlaces(nearestCurrencyExchangeResponse: NearestCurrencyExchangeResponse)
}