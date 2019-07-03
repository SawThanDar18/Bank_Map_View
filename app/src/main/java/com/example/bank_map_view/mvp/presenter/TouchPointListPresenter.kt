package com.example.bank_branch_details.mvp.presenter

import com.example.bank_branch_details.event.RestApiEvents
import com.example.bank_branch_details.mvp.model.BranchModel
import com.example.bank_branch_details.mvp.view.TouchPointListView
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class TouchPointListPresenter constructor(val touchPointListView: TouchPointListView) : BasePresenter() {

    override fun onStart(){
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this)
        }
    }

    fun startLoadingTouchList(){
        touchPointListView.showLoading()
        BranchModel.getInstance().getRequestAuth()
    }

    override fun onStop(){
        if(EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this)
        }
    }

    @Subscribe
        fun onSuccess(event : RestApiEvents.ShowPlaces){
        touchPointListView.dismissLoading()
        touchPointListView.showPlaces(event.access_ATM, event.access_Branch, event.access_Agent, event.access_Merchant)
        touchPointListView.displayBranch(event.access_Branch)
        touchPointListView.displayATM(event.access_ATM)
        touchPointListView.displayAgent(event.access_Agent)
        touchPointListView.displayMerchant(event.access_Merchant)
    }

    @Subscribe
    fun onError(event: RestApiEvents.ErrorInvokingAPIEvent){
        touchPointListView.dismissLoading()
        touchPointListView.showPrompt(event.message)
    }
}