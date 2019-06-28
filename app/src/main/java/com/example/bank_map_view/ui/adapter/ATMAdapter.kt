package com.example.bank_map_view.ui.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.bank_branch_details.network.model.Access_ATM
import com.example.bank_map_view.R

class ATMAdapter (val context : Context, val atmList : ArrayList<Access_ATM>) : RecyclerView.Adapter<ATMAdapter.ViewHolder>(){

    override fun onCreateViewHolder(view: ViewGroup, position: Int): ViewHolder {
        val layout = LayoutInflater.from(view.context).inflate(R.layout.atm_list_item, view, false)
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

        view.direction_iv.setOnClickListener {
            val latitude = atmList[position].Latitude
            val longitude = atmList[position].Longitude
            val atm_name = atmList[position].Location_Name

            val mapIntent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:<$latitude>,<$longitude>?q=<$latitude>,<$longitude>($atm_name)"))
            context.startActivity(mapIntent)
        }
    }

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val atm_name = itemView.findViewById(R.id.atm_name) as TextView
        val atm_address = itemView.findViewById(R.id.atm_address) as TextView
        val atm_time = itemView.findViewById(R.id.time) as TextView
        val direction_iv = itemView.findViewById(R.id.direction_iv) as ImageView
    }
}
