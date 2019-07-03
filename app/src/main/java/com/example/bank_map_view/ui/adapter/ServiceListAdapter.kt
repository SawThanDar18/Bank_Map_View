package com.example.bank_map_view.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.bank_map_view.R
import com.example.bank_map_view.network.model.Service_List
import com.example.bank_map_view.ui.holders.ServiceListHolder

class ServiceListAdapter(val context : Context, val serviceList : ArrayList<Service_List>) : RecyclerView.Adapter<ServiceListHolder>() {

    override fun onCreateViewHolder(view: ViewGroup, position: Int): ServiceListHolder {
        val layout = LayoutInflater.from(view.context).inflate(R.layout.currency_list_item, view, false)
        return ServiceListHolder(layout, context)

    }

    override fun getItemCount(): Int {
        return serviceList!!.size
    }

    override fun onBindViewHolder(view: ServiceListHolder, position: Int) {

        view.index(serviceList!![position])
    }
}