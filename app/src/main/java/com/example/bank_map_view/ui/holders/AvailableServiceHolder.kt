package com.example.bank_map_view.ui.holders

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.example.bank_map_view.R
import com.example.bank_map_view.network.BranchItemClickListener
import com.example.bank_map_view.roomdb.Services

class AvailableServiceHolder (itemView : View, private val context: Context, private val branchItemClickListener: BranchItemClickListener) : RecyclerView.ViewHolder(itemView){

    private val service_name : TextView

    init {
        service_name = itemView.findViewById(R.id.service_name)
    }

    fun index(services: Services) {

        service_name.text = services.title

        itemView.setOnClickListener {
            branchItemClickListener.onClicked(services.service_code)
        }
    }
}