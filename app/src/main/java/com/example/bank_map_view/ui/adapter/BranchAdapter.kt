package com.example.details_design.branch

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.bank_branch_details.network.model.Access_Branch
import com.example.bank_map_view.R
import com.example.bank_map_view.network.BranchItemClickListener
import com.example.bank_map_view.ui.holders.BranchHolder

class BranchAdapter(val context: Context, val branchItemClickListener: BranchItemClickListener) : RecyclerView.Adapter<BranchHolder>(){

    private var branchList : ArrayList<Access_Branch> = arrayListOf()

    override fun onCreateViewHolder(view: ViewGroup, position: Int): BranchHolder {
        val layout = LayoutInflater.from(view.context).inflate(R.layout.branch_list_item, view, false)
        return BranchHolder(layout, context, branchItemClickListener)
    }

    override fun getItemCount(): Int {
        return branchList.size
    }

    override fun onBindViewHolder(view: BranchHolder, position: Int) {

        view.index(branchList[position])
    }

    fun setNewData(access_Branch: ArrayList<Access_Branch>){
        branchList = access_Branch
        notifyDataSetChanged()
    }
}