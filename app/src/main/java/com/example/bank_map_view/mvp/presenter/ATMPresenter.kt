package com.example.bank_map_view.mvp.presenter

import com.example.bank_branch_details.event.RestApiEvents
import com.example.bank_branch_details.mvp.model.BranchModel
import com.example.bank_branch_details.mvp.presenter.BasePresenter
import com.example.bank_map_view.mvp.view.ATMView
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class ATMPresenter constructor(val atmView: ATMView) : BasePresenter() {

    override fun onStart(){
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this)
        }
    }

    fun startLoadingATMDetails(locationName : String, address : String){
        atmView.showLoading()
        BranchModel.getInstance().getTouchPointList(locationName, address)
    }
    override fun onStop(){
        if(EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this)
        }
    }

    @Subscribe
    fun onSuccess(event : RestApiEvents.ShowATMDetails){
        atmView.dismissLoading()
        atmView.showATMDetails(event.access_ATM)
        atmView.viewMap(event.access_ATM)
    }

    @Subscribe
    fun onError(event: RestApiEvents.ErrorInvokingAPIEvent){
        atmView.dismissLoading()
        atmView.showPrompt(event.message)
    }
}