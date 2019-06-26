package com.example.details_design.branch

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.bank_branch_details.network.model.Access_Branch
import com.example.bank_map_view.R
import com.example.bank_map_view.network.model.Branch

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
        view.branch_name.text = branch.name
        view.branch_address.text = branch.address
    }

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val branch_name = itemView.findViewById(R.id.branch_name) as TextView
        val branch_address = itemView.findViewById(R.id.branch_address) as TextView
    }
}
