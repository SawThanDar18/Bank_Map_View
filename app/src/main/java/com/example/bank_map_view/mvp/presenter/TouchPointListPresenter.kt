package com.example.bank_branch_details.mvp.presenter

import com.example.bank_branch_details.event.RestApiEvents
import com.example.bank_branch_details.mvp.model.BranchModel
import com.example.bank_branch_details.mvp.view.TouchPointListView
import com.example.bank_branch_details.network.response.TouchPointListResponse
import com.google.android.gms.location.LocationResult
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
        touchPointListView.showPlaces(event.access_ATM, event.access_Branch)
        touchPointListView.showBranchPlaces(event.access_Branch)
        touchPointListView.showATMPlaces(event.access_ATM)
    }

    @Subscribe
    fun onError(event: RestApiEvents.ErrorInvokingAPIEvent){
        touchPointListView.dismissLoading()
        touchPointListView.showPrompt(event.message)
    }
}