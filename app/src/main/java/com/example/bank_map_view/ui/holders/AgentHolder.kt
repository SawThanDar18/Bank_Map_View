package com.example.bank_map_view.ui.holders

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.bank_map_view.R
import com.example.bank_map_view.network.ItemClickListener
import com.example.bank_map_view.network.model.Access_Agent

class AgentHolder (itemView : View, private val context: Context,  val itemClickListener: ItemClickListener) : RecyclerView.ViewHolder(itemView){

    private val agent_name : TextView
    private val agent_address : TextView
    private val direction_iv : ImageView


    init {
        agent_name = itemView.findViewById(R.id.name)
        agent_address = itemView.findViewById(R.id.address_tv)
        direction_iv = itemView.findViewById(R.id.direction_iv)
    }

    fun index(access_Agent: Access_Agent) {

        agent_name.text = access_Agent.agent_name
        agent_address.text = access_Agent.agent_address

        direction_iv.setOnClickListener {
            val latitude = access_Agent.latitude
            val longitude = access_Agent.longitude
            val agent_name = access_Agent.agent_name

            val mapIntent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:<$latitude>,<$longitude>?q=<$latitude>,<$longitude>($agent_name)"))
            context.startActivity(mapIntent)
        }

        itemView.setOnClickListener {
            itemClickListener.onClicked(access_Agent.agent_name.toString(), access_Agent.agent_address.toString(), access_Agent.latitude!!.toDouble(), access_Agent.longitude!!.toDouble())
        }
    }
}