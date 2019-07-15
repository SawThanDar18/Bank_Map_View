package com.example.bank_map_view.mvp.presenter

import com.example.bank_branch_details.event.RestApiEvents
import com.example.bank_branch_details.mvp.model.BranchModel
import com.example.bank_branch_details.mvp.presenter.BasePresenter
import com.example.bank_map_view.mvp.view.NearestCurrencyExchangeView
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class NearestCurrencyExchangePresenter constructor(val nearestCurrencyExchangeView: NearestCurrencyExchangeView) : BasePresenter() {

    override fun onStart(){
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this)
        }
    }

    fun startLoadingNearestExchangeDetails(value : String){
        nearestCurrencyExchangeView.showLoading()
        BranchModel.getInstance().getNearestExchange(value)
    }

    override fun onStop(){
        if(EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this)
        }
    }

    @Subscribe
    fun onSuccess(event : RestApiEvents.ShowNearestCurrencyExchange){
        nearestCurrencyExchangeView.dismissLoading()
        nearestCurrencyExchangeView.showNearestCurrencyExchange(event.nearestCurrencyExchangeResponse)
        nearestCurrencyExchangeView.showPlaces(event.nearestCurrencyExchangeResponse)
    }

    @Subscribe
    fun onError(event: RestApiEvents.ErrorInvokingAPIEvent){
        nearestCurrencyExchangeView.dismissLoading()
        nearestCurrencyExchangeView.showPrompt(event.message)
    }

}