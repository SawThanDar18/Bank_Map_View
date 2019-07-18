package com.example.bank_map_view.mvp.presenter

import com.example.bank_branch_details.event.RestApiEvents
import com.example.bank_branch_details.mvp.model.BranchModel
import com.example.bank_branch_details.mvp.presenter.BasePresenter
import com.example.bank_map_view.mvp.view.ServiceView
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class ServicePresenter constructor(val serviceView: ServiceView) : BasePresenter() {

    override fun onStart(){
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this)
        }
    }

    fun startLoadingServiceList(){
        serviceView.showLoading()
        BranchModel.getInstance().getRequestAuth()
    }

    override fun onStop(){
        if(EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this)
        }
    }

    @Subscribe
    fun onSuccess(event : RestApiEvents.ShowService){
        serviceView.dismissLoading()
        serviceView.showServiceList(event.serviceResponse)
        serviceView.saveToRoomDb(event.serviceResponse)
    }

    @Subscribe
    fun onError(event: RestApiEvents.ErrorInvokingAPIEvent){
        serviceView.dismissLoading()
        serviceView.showPrompt(event.message)
    }
}