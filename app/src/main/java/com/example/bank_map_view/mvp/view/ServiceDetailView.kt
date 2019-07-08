package com.example.bank_map_view.mvp.view

import com.example.bank_map_view.network.response.ServiceResponse

interface ServiceDetailView {

    fun showPrompt(message : String)
    fun showLoading()
    fun dismissLoading()
    fun showServiceDetails(serviceResponse: ServiceResponse)
}