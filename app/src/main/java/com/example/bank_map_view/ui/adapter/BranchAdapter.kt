package com.example.details_design.branch

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.bank_branch_details.network.model.Access_Branch
import com.example.bank_map_view.R

class BranchAdapter(val context : Context, val branchList : ArrayList<Access_Branch>) : RecyclerView.Adapter<BranchAdapter.ViewHolder>(){

    override fun onCreateViewHolder(view: ViewGroup, position: Int): ViewHolder {
        val layout = LayoutInflater.from(view.context).inflate(R.layout.branch_list_item, view, false)
        return ViewHolder(layout)

    }

    override fun getItemCount(): Int {
        return branchList.size
    }

    override fun onBindViewHolder(view: ViewHolder, position: Int) {
        val access_Branch : Access_Branch = branchList[position]
        view.branch_name.text = access_Branch.Branch_Name
        view.branch_address.text = access_Branch.Address

        view.call_iv.setOnClickListener {

            val phone = branchList[position].Phone!!.split(",").toTypedArray()
            val callIntent = Intent(Intent.ACTION_VIEW, Uri.parse("tel:" + phone[0]))
            context.startActivity(callIntent)
        }

        view.direction_iv.setOnClickListener {
            val latitude = branchList[position].Latitude
            val longitude = branchList[position].Longitude
            val branch_name = branchList[position].Branch_Name

            val mapIntent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:<$latitude>,<$longitude>?q=<$latitude>,<$longitude>($branch_name)"))
            context.startActivity(mapIntent)
        }
    }

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val branch_name = itemView.findViewById(R.id.atm_name) as TextView
        val branch_address = itemView.findViewById(R.id.atm_address) as TextView
        val call_iv = itemView.findViewById(R.id.call_iv) as ImageView
        val direction_iv = itemView.findViewById(R.id.direction_iv) as ImageView
    }
}
