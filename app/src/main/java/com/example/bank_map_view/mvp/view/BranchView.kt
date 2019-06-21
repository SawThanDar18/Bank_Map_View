package com.example.bank_map_view.mvp.view

import com.example.bank_branch_details.network.response.TouchPointListResponse
import com.example.bank_map_view.network.model.Access_BranchCode
import com.example.bank_map_view.network.response.BranchCodeResponse


interface BranchView {

    fun showPrompt(message : String)
    fun showLoading()
    fun dismissLoading()
    fun showBranchDetails(branchCodeResponse: BranchCodeResponse)
    fun viewMap(branchCodeResponse: BranchCodeResponse)
    fun callBankPhone(branchCodeResponse: BranchCodeResponse)
}