package com.example.bank_map_view.ui.holders

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.example.bank_map_view.R
import com.example.bank_map_view.network.BranchItemClickListener
import com.example.bank_map_view.network.model.Service_List

class RecyclerHolder (itemView : View, private val context: Context, val itemClickListener: BranchItemClickListener) : RecyclerView.ViewHolder(itemView){

    private var service_title : TextView = itemView.findViewById(R.id.branch_detail_tv) as TextView

    init {
        service_title = itemView.findViewById(R.id.branch_detail_tv)
    }

    fun index(service_List: Service_List) {

        service_title.text = service_List.title

        itemView.setOnClickListener{
            itemClickListener.onClicked(service_List.service_code!!)
        }
    }
}