package com.example.bank_branch_details.mvp.view

import com.example.bank_branch_details.network.model.Access_ATM
import com.example.bank_branch_details.network.model.Access_Branch

interface TouchPointListView {

    fun showPrompt(message : String)
    fun showLoading()
    fun dismissLoading()

    fun showPlaces(access_ATM: ArrayList<Access_ATM>, access_Branch: ArrayList<Access_Branch>)

    //for branch
    fun showBranchPlaces(access_Branch : ArrayList<Access_Branch>)

    //for atm
    fun showATMPlaces(access_ATM: ArrayList<Access_ATM>)
}