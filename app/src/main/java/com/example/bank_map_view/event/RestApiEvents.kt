package com.example.bank_branch_details.event

import com.example.bank_branch_details.network.model.Access_ATM
import com.example.bank_branch_details.network.model.Access_Branch
import com.example.bank_map_view.network.response.BranchCodeResponse

object RestApiEvents {

    class ErrorInvokingAPIEvent(val message : String)
    //class ShowCurrentLocation(val touchPointListResponse: TouchPointListResponse)
    //class ShowCurrentLocation(val current : List<Access_TouchPointList>)
    class ShowPlaces(val access_ATM: List<Access_ATM>, val access_Branch : List<Access_Branch>)
    class ShowBranchDetails(val branchCodeResponse: BranchCodeResponse)
    class ShowATMDetails(val access_ATM: List<Access_ATM>)
}