package com.example.bank_branch_details.network.api


interface Data {

    fun getRequestAuth()
    fun getTouchPointList()
    fun getBranchDetail(value: String)
    fun getCurrency()
    fun getService()
    fun getServiceDetail(value : String)
    fun getSearchList(value : String)
}