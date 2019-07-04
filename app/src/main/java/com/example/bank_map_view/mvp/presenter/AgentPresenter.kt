package com.example.bank_map_view.mvp.presenter

import com.example.bank_branch_details.event.RestApiEvents
import com.example.bank_branch_details.mvp.model.BranchModel
import com.example.bank_branch_details.mvp.presenter.BasePresenter
import com.example.bank_map_view.mvp.view.AgentView
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class AgentPresenter constructor(val agentView: AgentView) : BasePresenter() {

    override fun onStart(){
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this)
        }
    }

    fun startLoadingAgentDetails(){
        agentView.showLoading()
        BranchModel.getInstance().getTouchPointList()
    }

    override fun onStop(){
        if(EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this)
        }
    }

    @Subscribe
    fun onSuccess(event : RestApiEvents.ShowAgentDetails){
        agentView.dismissLoading()
        agentView.showAgentDetails()
        agentView.viewMap()
    }

    @Subscribe
    fun onError(event: RestApiEvents.ErrorInvokingAPIEvent){
        agentView.dismissLoading()
        agentView.showPrompt(event.message)
    }
}