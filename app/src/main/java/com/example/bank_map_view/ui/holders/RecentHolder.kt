package com.example.bank_map_view.ui.holders

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.example.bank_map_view.R
import com.example.bank_map_view.network.BranchItemClickListener
import com.example.bank_map_view.network.model.Recent

class RecentHolder (itemView : View, private val context: Context, private val itemClickListener: BranchItemClickListener) : RecyclerView.ViewHolder(itemView){

    private val recent_name : TextView

    init {
        recent_name = itemView.findViewById(R.id.recent_textView)
    }

    fun index(recent : Recent){
        recent_name.text = recent.name

        itemView.setOnClickListener{
            itemClickListener.onClicked(recent.name)
        }
    }
}