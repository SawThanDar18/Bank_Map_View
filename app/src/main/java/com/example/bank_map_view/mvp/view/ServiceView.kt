package com.example.bank_map_view.mvp.view

import com.example.bank_map_view.network.response.ServiceResponse

interface ServiceView {

    fun showPrompt(message : String)
    fun showLoading()
    fun dismissLoading()
    fun showServiceList(serviceResponse: ServiceResponse)
    fun saveToRoomDb(serviceResponse: ServiceResponse)
}