package com.example.bank_branch_details.event

import com.example.bank_branch_details.network.model.Access_ATM
import com.example.bank_branch_details.network.model.Access_Branch
import com.example.bank_branch_details.network.response.TouchPointListResponse

object RestApiEvents {

    class ErrorInvokingAPIEvent(val message : String)
    class ShowCurrentLocation(val touchPointListResponse: TouchPointListResponse)
    class ShowATMList(val touchPointListResponse: TouchPointListResponse)
    class ShowBranchList(val touchPointListResponse: TouchPointListResponse)
}