package com.example.bank_map_view.mvp.view

interface AgentView{
    fun showPrompt(message : String)
    fun showLoading()
    fun dismissLoading()
    fun showAgentDetails()
    fun viewMap()
}