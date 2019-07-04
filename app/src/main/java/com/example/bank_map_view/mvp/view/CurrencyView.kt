package com.example.bank_map_view.mvp.view

import com.example.bank_map_view.network.model.Currency
import com.example.bank_map_view.network.response.CurrencyResponse

interface CurrencyView {

    fun showPrompt(message : String)
    fun showLoading()
    fun dismissLoading()
    fun showCurrencyDetails(currency : ArrayList<Currency>)
}