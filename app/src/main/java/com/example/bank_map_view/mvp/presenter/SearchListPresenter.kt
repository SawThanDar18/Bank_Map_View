package com.example.bank_map_view.mvp.presenter

import com.example.bank_branch_details.event.RestApiEvents
import com.example.bank_branch_details.mvp.model.BranchModel
import com.example.bank_branch_details.mvp.presenter.BasePresenter
import com.example.bank_map_view.mvp.view.SearchListView
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class SearchListPresenter constructor(val searchListView: SearchListView) : BasePresenter() {

    override fun onStart(){
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this)
        }
    }

    fun startLoadingSearchList(value : String){
        searchListView.showLoading()
        BranchModel.getInstance().getSearchList(value)
    }

    override fun onStop(){
        if(EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this)
        }
    }

    @Subscribe
    fun onSuccess(event : RestApiEvents.ShowSearchList){
        searchListView.dismissLoading()
        searchListView.showSearchList(event.searchResponse)
    }

    @Subscribe
    fun onError(event: RestApiEvents.ErrorInvokingAPIEvent){
        searchListView.dismissLoading()
        searchListView.showPrompt(event.message)
    }

}