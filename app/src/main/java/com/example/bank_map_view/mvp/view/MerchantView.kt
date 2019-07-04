package com.example.bank_map_view.mvp.view

interface MerchantView {
    fun showPrompt(message : String)
    fun showLoading()
    fun dismissLoading()
    fun showMerchantDetails()
    fun viewMap()
}