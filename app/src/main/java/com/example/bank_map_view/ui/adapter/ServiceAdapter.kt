package com.example.bank_map_view.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.bank_map_view.R
import com.example.bank_map_view.network.BranchItemClickListener
import com.example.bank_map_view.network.model.Service_List
import com.example.bank_map_view.ui.holders.ServiceHolder


class ServiceAdapter(val context : Context, val itemClickListener: BranchItemClickListener) : RecyclerView.Adapter<ServiceHolder>() {

    private var serviceList: ArrayList<Service_List> = arrayListOf()

    private var isReverse = false

    override fun onCreateViewHolder(view: ViewGroup, position: Int): ServiceHolder {

        if (isReverse == false) {

            isReverse = true
            val layout = LayoutInflater.from(view.context).inflate(R.layout.currency_list_item, view, false)
            return ServiceHolder(layout, context, itemClickListener)

        } else {

            isReverse = false
            val layout = LayoutInflater.from(view.context).inflate(R.layout.currency_reverse_list_item, view, false)
            return ServiceHolder(layout, context, itemClickListener)
        }
    }

    override fun getItemCount(): Int {
        return serviceList.size
    }

    override fun onBindViewHolder(view: ServiceHolder, position: Int) {

        view.index(serviceList[position])
    }

    fun setNewData(service_List: ArrayList<Service_List>){
        serviceList = service_List
        notifyDataSetChanged()
    }
}