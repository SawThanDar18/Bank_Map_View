package com.example.bank_branch_details.ui.detail.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.bank_map_view.R
import com.example.bank_map_view.network.BranchItemClickListener
import com.example.bank_map_view.network.model.Service_List
import com.example.bank_map_view.ui.holders.RecyclerHolder

class RecyclerAdapter(val context : Context, val itemClickListener: BranchItemClickListener) : RecyclerView.Adapter<RecyclerHolder>() {

    private var service_List : ArrayList<Service_List> = arrayListOf()

    override fun onCreateViewHolder(view: ViewGroup, viewType: Int): RecyclerHolder {

        val view = LayoutInflater.from(view.context).inflate(R.layout.branch_details_recycler_items, view, false)
        return RecyclerHolder(view, context, itemClickListener)
    }

    override fun getItemCount(): Int {
        return service_List.size
    }

    override fun onBindViewHolder(holder: RecyclerHolder, position: Int) {

        holder.index(service_List[position])
    }

    fun setNewData(serviceList : ArrayList<Service_List>){
        service_List = serviceList
        notifyDataSetChanged()
    }
}