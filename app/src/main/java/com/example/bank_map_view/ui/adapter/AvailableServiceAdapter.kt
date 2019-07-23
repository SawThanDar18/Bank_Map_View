package com.example.bank_map_view.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.bank_map_view.R
import com.example.bank_map_view.network.BranchItemClickListener
import com.example.bank_map_view.network.model.Service_List
import com.example.bank_map_view.ui.holders.AvailableServiceHolder

class AvailableServiceAdapter (val context: Context, var services : ArrayList<Service_List>, val branchItemClickListener: BranchItemClickListener) : RecyclerView.Adapter<AvailableServiceHolder>(){

    override fun onCreateViewHolder(view: ViewGroup, position: Int): AvailableServiceHolder {
        val layout = LayoutInflater.from(view.context).inflate(R.layout.available_list_items, view, false)
        return AvailableServiceHolder(layout, context, branchItemClickListener)
    }

    override fun getItemCount(): Int {
        return services.size
    }

    override fun onBindViewHolder(view: AvailableServiceHolder, position: Int) {

        view.index(services[position])
    }
}