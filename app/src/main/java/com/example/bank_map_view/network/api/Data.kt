package com.example.bank_branch_details.network.api


interface Data {

    fun getRequestAuth()
    fun getTouchPointList(locationName : String, address : String)
    fun getBranchDetail(value: String)
}