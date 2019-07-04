package com.example.bank_map_view.mvp.presenter

import com.example.bank_branch_details.event.RestApiEvents
import com.example.bank_branch_details.mvp.model.BranchModel
import com.example.bank_branch_details.mvp.presenter.BasePresenter
import com.example.bank_map_view.mvp.view.MerchantView
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class MerchantPresenter constructor(val merchantView: MerchantView) : BasePresenter() {

    override fun onStart(){
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this)
        }
    }

    fun startLoadingMerchantDetails(){
        merchantView.showLoading()
        BranchModel.getInstance().getTouchPointList()
    }

    override fun onStop(){
        if(EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this)
        }
    }

    @Subscribe
    fun onSuccess(event : RestApiEvents.ShowMerchantDetails){
        merchantView.dismissLoading()
        merchantView.showMerchantDetails()
        merchantView.viewMap()
    }

    @Subscribe
    fun onError(event: RestApiEvents.ErrorInvokingAPIEvent){
        merchantView.dismissLoading()
        merchantView.showPrompt(event.message)
    }

}