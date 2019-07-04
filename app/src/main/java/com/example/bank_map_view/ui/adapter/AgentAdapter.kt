package com.example.bank_map_view.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.bank_map_view.R
import com.example.bank_map_view.network.ItemClickListener
import com.example.bank_map_view.network.model.Access_Agent
import com.example.bank_map_view.ui.holders.AgentHolder

class AgentAdapter(val context : Context, val itemClickListener: ItemClickListener) : RecyclerView.Adapter<AgentHolder>() {

    private var access_Agent : ArrayList<Access_Agent> = arrayListOf()

    override fun onCreateViewHolder(view: ViewGroup, position: Int): AgentHolder {
        val layout = LayoutInflater.from(view.context).inflate(R.layout.agent_list_item, view, false)
        return AgentHolder(layout, context, itemClickListener)

    }

    override fun getItemCount(): Int {
        return access_Agent!!.size
    }

    override fun onBindViewHolder(view: AgentHolder, position: Int) {

        view.index(access_Agent!![position])
    }

    fun setNewData(agentList: ArrayList<Access_Agent>){
        access_Agent = agentList
        notifyDataSetChanged()
    }
}