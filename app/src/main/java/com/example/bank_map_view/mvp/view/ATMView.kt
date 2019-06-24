package com.example.bank_map_view.mvp.view

import com.example.bank_branch_details.network.model.Access_ATM

interface ATMView {
    fun showPrompt(message : String)
    fun showLoading()
    fun dismissLoading()
    fun showATMDetails()
    fun viewMap()
}