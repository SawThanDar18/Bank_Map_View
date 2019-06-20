package com.example.bank_branch_details.mvp.view

import com.example.bank_branch_details.network.model.Access_ATM
import com.example.bank_branch_details.network.model.Access_Branch
import com.example.bank_branch_details.network.model.Access_TouchPointList
import com.example.bank_branch_details.network.response.TouchPointListResponse
import com.example.bank_map_view.network.model.Access_BranchCode
import com.example.bank_map_view.network.response.BranchCodeResponse

interface TouchPointListView {

    fun showPrompt(message : String)
    fun showLoading()
    fun dismissLoading()
    //fun showCurrentLocation(touchPointListResponse: TouchPointListResponse)
    //fun showCurrentLocation(current : List<Access_TouchPointList>)
    fun showPlaces(access_ATM : List<Access_ATM>, access_Branch : List<Access_Branch>)
}