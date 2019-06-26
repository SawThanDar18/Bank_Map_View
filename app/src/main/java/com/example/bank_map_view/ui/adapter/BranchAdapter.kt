package com.example.details_design.branch

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.bank_map_view.R

class BranchAdapter(val bankList : ArrayList<Branch>) : RecyclerView.Adapter<BranchAdapter.ViewHolder>(){

    override fun onCreateViewHolder(view: ViewGroup, position: Int): ViewHolder {
        val layout = LayoutInflater.from(view.context).inflate(R.layout.bank_list_item, view, false)
        return ViewHolder(layout)

    }

    override fun getItemCount(): Int {
        return bankList.size
    }

    override fun onBindViewHolder(view: ViewHolder, position: Int) {
        val branch : Branch = bankList[position]
        view.bank_type.text = branch.bank_type
        view.bank_name.text = branch.bank_name
        view.bank_address.text = branch.bank_address
        view.time.text = branch.time
    }

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val bank_type = itemView.findViewById(R.id.bank_type) as TextView
        val bank_name = itemView.findViewById(R.id.bank_name) as TextView
        val bank_address = itemView.findViewById(R.id.bank_address) as TextView
        val time = itemView.findViewById(R.id.time) as TextView
    }
}
