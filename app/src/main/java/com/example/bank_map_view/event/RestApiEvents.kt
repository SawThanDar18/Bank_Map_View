package com.example.bank_branch_details.event

import com.example.bank_branch_details.network.model.Access_ATM
import com.example.bank_branch_details.network.model.Access_Branch
import com.example.bank_branch_details.network.response.TouchPointListResponse
import com.example.bank_map_view.network.model.Access_Agent
import com.example.bank_map_view.network.model.Access_Merchant
import com.example.bank_map_view.network.response.BranchCodeResponse
import com.example.bank_map_view.network.response.CurrencyResponse

object RestApiEvents {

    class ErrorInvokingAPIEvent(val message : String)
    class ShowPlaces(val access_ATM: ArrayList<Access_ATM>, val access_Branch : ArrayList<Access_Branch>, val access_Agent: ArrayList<Access_Agent>, val access_Merchant: ArrayList<Access_Merchant>)
    class ShowBranchDetails(val branchCodeResponse: BranchCodeResponse)
    class ShowATMDetails(val access_ATM: ArrayList<Access_ATM>)
    class ShowAgentDetails(val access_Agent: ArrayList<Access_Agent>)
    class ShowMerchantDetails(val access_Merchant: ArrayList<Access_Merchant>)

    
}