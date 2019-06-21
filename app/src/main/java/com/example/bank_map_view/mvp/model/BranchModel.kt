package com.example.bank_branch_details.mvp.model

import com.example.bank_branch_details.network.DataImpl
import com.example.bank_map_view.network.model.Access_BranchCode

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

}