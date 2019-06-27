package com.example.bank_branch_details.ui.detail.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.bank_branch_details.network.model.Access_BranchServices
import com.example.bank_map_view.R

class RecyclerAdapter(val branch_service : ArrayList<Access_BranchServices>, val mContext : Context) : RecyclerView.Adapter<RecyclerAdapter.MyHolder>() {

    override fun onCreateViewHolder(view: ViewGroup, viewType: Int): MyHolder {

        val view = LayoutInflater.from(view.context).inflate(R.layout.branch_details_recycler_items, view, false)
        return MyHolder(view)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {

        val branch_services : Access_BranchServices = branch_service[position]
        holder.service_title.text = branch_services.service_title
    }

    class MyHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val service_title = itemView.findViewById(R.id.branch_detail_tv) as TextView
    }

    override fun getItemCount(): Int {
        return branch_service.size
    }
}