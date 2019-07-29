package com.example.bank_map_view.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.bank_map_view.R
import com.example.bank_map_view.network.model.Recent
import com.example.bank_map_view.ui.holders.RecentHolder

class RecentAdapter(private val recent:ArrayList<Recent>, private val mContext: Context) : RecyclerView.Adapter<RecentHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.recent_list_items, parent, false)
        return RecentHolder(view, mContext)
    }

    override fun onBindViewHolder(holder: RecentHolder, position: Int) {

        holder?.index(recent[position])
    }

    override fun getItemCount(): Int {
        return recent.size
    }


}