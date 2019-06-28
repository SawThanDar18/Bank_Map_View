package com.example.bank_branch_details.event

import com.example.bank_branch_details.network.model.Access_ATM
import com.example.bank_branch_details.network.model.Access_Branch
import com.example.bank_branch_details.network.response.TouchPointListResponse
import com.example.bank_map_view.network.response.BranchCodeResponse

object RestApiEvents {

    class ErrorInvokingAPIEvent(val message : String)
    class ShowPlaces(val access_ATM: ArrayList<Access_ATM>, val access_Branch : ArrayList<Access_Branch>)
    class ShowBranchDetails(val branchCodeResponse: BranchCodeResponse)
    class ShowATMDetails(val access_ATM: List<Access_ATM>)
}