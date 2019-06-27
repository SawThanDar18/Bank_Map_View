package com.example.bank_map_view.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.bank_branch_details.network.model.Access_ATM
import com.example.bank_map_view.R

class ATMAdapter (val context : Context, val atmList : ArrayList<Access_ATM>) : RecyclerView.Adapter<ATMAdapter.ViewHolder>(){

    override fun onCreateViewHolder(view: ViewGroup, position: Int): ViewHolder {
        val layout = LayoutInflater.from(view.context).inflate(R.layout.bank_list_item, view, false)
        return ViewHolder(layout)

    }

    override fun getItemCount(): Int {
        return atmList.size
    }

    override fun onBindViewHolder(view: ViewHolder, position: Int) {
        val access_ATM : Access_ATM = atmList[position]
        view.atm_name.text = access_ATM.Location_Name
        view.atm_address.text = access_ATM.Address
        view.atm_time.text = "24-Hour"
    }

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val atm_name = itemView.findViewById(R.id.bank_name) as TextView
        val atm_address = itemView.findViewById(R.id.bank_address) as TextView
        val atm_time = itemView.findViewById(R.id.time) as TextView
    }
}
