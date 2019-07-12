package com.example.bank_map_view.ui.holders

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.bank_branch_details.network.model.Access_ATM
import com.example.bank_map_view.R
import com.example.bank_map_view.network.ItemClickListener

class ATMHolder (itemView : View, private val context: Context, private val itemClickListener: ItemClickListener) : RecyclerView.ViewHolder(itemView){

    private val atm_name : TextView
    private val atm_address : TextView
    private val atm_time : TextView
    private val direction_iv : ImageView


    init {
        atm_name = itemView.findViewById(R.id.name)
        atm_address = itemView.findViewById(R.id.address_tv)
        atm_time = itemView.findViewById(R.id.time_tv)
        direction_iv = itemView.findViewById(R.id.direction_iv)
        }

    fun index(access_ATM : Access_ATM) {

        atm_name.text = access_ATM.Location_Name
        atm_address.text = access_ATM.Address
        atm_time.text = "Open Now"

        direction_iv.setOnClickListener {
            val latitude = access_ATM.Latitude
            val longitude = access_ATM.Longitude
            val atm_name = access_ATM.Location_Name

            val mapIntent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:<$latitude>,<$longitude>?q=<$latitude>,<$longitude>($atm_name)"))
            context.startActivity(mapIntent)
        }

        itemView.setOnClickListener {
            itemClickListener.onClicked(access_ATM.Location_Name.toString(), access_ATM.Address.toString(), access_ATM.Latitude!!.toDouble(), access_ATM.Longitude!!.toDouble())
        }
    }
}