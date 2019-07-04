package com.example.bank_branch_details.mvp.view

import com.example.bank_branch_details.network.model.Access_ATM
import com.example.bank_branch_details.network.model.Access_Branch
import com.example.bank_map_view.network.model.Access_Agent
import com.example.bank_map_view.network.model.Access_Merchant
import com.example.bank_map_view.network.response.CurrencyResponse
import com.example.bank_map_view.network.response.ServiceResponse

interface TouchPointListView {

    fun showPrompt(message : String)
    fun showLoading()
    fun dismissLoading()

    fun showPlaces(access_ATM: ArrayList<Access_ATM>, access_Branch: ArrayList<Access_Branch>, access_Agent: ArrayList<Access_Agent>, access_Merchant: ArrayList<Access_Merchant>)
    fun displayBranch(access_Branch: ArrayList<Access_Branch>)
    fun displayATM(access_ATM: ArrayList<Access_ATM>)
    fun displayAgent(access_Agent: ArrayList<Access_Agent>)
    fun displayMerchant(access_Merchant: ArrayList<Access_Merchant>)
}