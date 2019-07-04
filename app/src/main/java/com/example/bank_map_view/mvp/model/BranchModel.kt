package com.example.bank_branch_details.mvp.model

import com.example.bank_branch_details.network.DataImpl

class BranchModel {

    companion object{
        private var INSTANCE : BranchModel? = null
        fun getInstance() : BranchModel {
            if(INSTANCE == null){
                INSTANCE = BranchModel()
            }
            val i = INSTANCE
            return i!!
        }
    }

    fun getRequestAuth(){
        DataImpl.getInstance().getRequestAuth()
    }

    fun getTouchPointList(){
        DataImpl.getInstance().getTouchPointList()
    }

    fun getBranchDetail(value : String){
        DataImpl.getInstance().getBranchDetail(value)
    }

    fun getCurrency(){
        DataImpl.getInstance().getCurrency()
    }

    fun getService(){
        DataImpl.getInstance().getService()
    }
}