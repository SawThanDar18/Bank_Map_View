package com.example.bank_map_view.mvp.presenter

import com.example.bank_branch_details.event.RestApiEvents
import com.example.bank_branch_details.mvp.model.BranchModel
import com.example.bank_branch_details.mvp.presenter.BasePresenter
import com.example.bank_map_view.mvp.view.ServiceDetailView
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class ServiceDetailPresenter  constructor(val serviceDetailView: ServiceDetailView) : BasePresenter() {

    override fun onStart(){
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this)
        }
    }

    fun startLoadingServiceDetails(value : String){
        serviceDetailView.showLoading()
        BranchModel.getInstance().getServiceDetail(value)
    }

    override fun onStop(){
        if(EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this)
        }
    }

    @Subscribe
    fun onSuccess(event : RestApiEvents.ShowServiceDetail){
        serviceDetailView.dismissLoading()
        serviceDetailView.showServiceDetails(event.serviceResponse)
    }

    @Subscribe
    fun onError(event: RestApiEvents.ErrorInvokingAPIEvent){
        serviceDetailView.dismissLoading()
        serviceDetailView.showPrompt(event.message)
    }
}