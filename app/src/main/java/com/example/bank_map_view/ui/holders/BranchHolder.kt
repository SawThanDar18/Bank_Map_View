package com.example.bank_map_view.ui.holders

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.bank_branch_details.network.model.Access_Branch
import com.example.bank_map_view.R
import com.example.bank_map_view.network.BranchItemClickListener

class BranchHolder (itemView : View, private val context: Context, private val branchItemClickListener: BranchItemClickListener) : RecyclerView.ViewHolder(itemView){

    private val branch_name : TextView
    private val branch_address : TextView
    private val call_iv : ImageView
    private val direction_iv : ImageView


    init {
        branch_name = itemView.findViewById(R.id.name)
        branch_address = itemView.findViewById(R.id.address_tv)
        call_iv = itemView.findViewById(R.id.call_iv)
        direction_iv = itemView.findViewById(R.id.direction_iv)
    }

    fun index(access_Branch: Access_Branch) {

        branch_name.text = access_Branch.Branch_Name
        branch_address.text = access_Branch.Address

        call_iv.setOnClickListener {

            val phone = access_Branch.Phone!!.split(",").toTypedArray()
            val callIntent = Intent(Intent.ACTION_VIEW, Uri.parse("tel:" + phone[0]))
            context.startActivity(callIntent)
        }

        direction_iv.setOnClickListener {
            val latitude = access_Branch.Latitude
            val longitude = access_Branch.Longitude
            val branch_name = access_Branch.Branch_Name

            val mapIntent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:<$latitude>,<$longitude>?q=<$latitude>,<$longitude>($branch_name)"))
            context.startActivity(mapIntent)
        }

        itemView.setOnClickListener {
            branchItemClickListener.onClicked(access_Branch.Branch_Code.toString())
        }
    }

}