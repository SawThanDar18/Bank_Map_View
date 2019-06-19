package com.example.bank_branch_details.mvp.view

import com.example.bank_branch_details.network.model.Access_ATM
import com.example.bank_branch_details.network.model.Access_Branch
import com.example.bank_branch_details.network.response.TouchPointListResponse

interface TouchPointListView {

    fun showPrompt(message : String)
    fun showLoading()
    fun dismissLoading()
    fun showCurrentLocation(touchPointListResponse: TouchPointListResponse)
    fun showATMList(touchPointListResponse: TouchPointListResponse)
    fun showBranchList(touchPointListResponse: TouchPointListResponse)
}