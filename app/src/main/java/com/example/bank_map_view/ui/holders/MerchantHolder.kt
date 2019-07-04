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
import com.example.bank_map_view.network.model.Access_Merchant

class MerchantHolder (itemView : View, private val context: Context, val itemClickListener: ItemClickListener) : RecyclerView.ViewHolder(itemView){

    private val merchant_name : TextView
    private val merchant_address : TextView
    private val direction_iv : ImageView


    init {
        merchant_name = itemView.findViewById(R.id.merchant_name)
        merchant_address = itemView.findViewById(R.id.address_tv)
        direction_iv = itemView.findViewById(R.id.direction_iv)
    }

    fun index(access_Merchant: Access_Merchant) {

        merchant_name.text = access_Merchant.merchant_name
        merchant_address.text = access_Merchant.merchant_address

        direction_iv.setOnClickListener {
            val latitude = access_Merchant.latitude
            val longitude = access_Merchant.longitude
            val merchant_name = access_Merchant.merchant_name

            val mapIntent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:<$latitude>,<$longitude>?q=<$latitude>,<$longitude>($merchant_name)"))
            context.startActivity(mapIntent)
        }

        itemView.setOnClickListener {
            itemClickListener.onClicked(access_Merchant.merchant_name.toString(), access_Merchant.merchant_address.toString(), access_Merchant.latitude!!.toDouble(), access_Merchant.longitude!!.toDouble())
        }
    }
}