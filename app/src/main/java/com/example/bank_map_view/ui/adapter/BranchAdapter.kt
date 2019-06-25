package com.example.details_design.branch

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.bank_map_view.R

class BranchAdapter(val branchList : ArrayList<Branch>) : RecyclerView.Adapter<BranchAdapter.ViewHolder>(){

    override fun onCreateViewHolder(view: ViewGroup, position: Int): ViewHolder {
        val layout = LayoutInflater.from(view.context).inflate(R.layout.bank_list_item, view, false)
        return ViewHolder(layout)

    }

    override fun getItemCount(): Int {
        return branchList.size
    }

    override fun onBindViewHolder(view: ViewHolder, position: Int) {
        val branch : Branch = branchList[position]
        view.branch_type.text = branch.branch_type
        view.branch_name.text = branch.branch_name
        view.branch_address.text = branch.branch_address
        view.time.text = branch.time
    }

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val branch_type = itemView.findViewById(R.id.bank_type) as TextView
        val branch_name = itemView.findViewById(R.id.bank_name) as TextView
        val branch_address = itemView.findViewById(R.id.bank_address) as TextView
        val time = itemView.findViewById(R.id.time) as TextView
    }
}
