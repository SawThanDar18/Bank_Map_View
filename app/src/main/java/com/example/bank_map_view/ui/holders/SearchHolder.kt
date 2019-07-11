package com.example.bank_map_view.ui.holders

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.bank_map_view.R
import com.example.bank_map_view.network.BranchItemClickListener
import com.example.bank_map_view.network.ClickListener
import com.example.bank_map_view.network.ItemClickListener
import com.example.bank_map_view.network.model.Search_List

class SearchHolder (itemView : View, private val context: Context, val itemClickListener: ClickListener) : RecyclerView.ViewHolder(itemView){

    private val touchpointname : TextView
    private val name : TextView
    private val address : TextView
    private val call_iv : ImageView
    private val direction_iv : ImageView


    init {
        touchpointname = itemView.findViewById(R.id.touchpointname)
        name = itemView.findViewById(R.id.name)
        address = itemView.findViewById(R.id.address_tv)
        call_iv = itemView.findViewById(R.id.call_iv)
        direction_iv = itemView.findViewById(R.id.direction_iv)
    }

    fun index(search_List: Search_List) {

        touchpointname.text = search_List.touchPointType
        name.text = search_List.location_Name
        address.text = search_List.address

        call_iv.setOnClickListener {

            val phone = search_List.phone!!.split(",").toTypedArray()
            val callIntent = Intent(Intent.ACTION_VIEW, Uri.parse("tel:" + phone[0]))
            context.startActivity(callIntent)
        }

        direction_iv.setOnClickListener {
            val latitude = search_List.latitude
            val longitude = search_List.longitude
            val name = search_List.location_Name

            val mapIntent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:<$latitude>,<$longitude>?q=<$latitude>,<$longitude>($name)"))
            context.startActivity(mapIntent)
        }

        itemView.setOnClickListener {
            itemClickListener.onClicked(search_List.touchPointType.toString(),search_List.location_Name.toString(), search_List.address.toString(), search_List.latitude!!.toDouble(), search_List.longitude!!.toDouble())
        }
    }
}