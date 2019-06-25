package com.example.bank_branch_details.mvp.view

import com.example.bank_branch_details.network.model.Access_ATM
import com.example.bank_branch_details.network.model.Access_Branch

interface TouchPointListView {

    fun showPrompt(message : String)
    fun showLoading()
    fun dismissLoading()
    fun showPlaces(access_ATM : List<Access_ATM>, access_Branch : List<Access_Branch>)
    fun showBottomSheet()
}