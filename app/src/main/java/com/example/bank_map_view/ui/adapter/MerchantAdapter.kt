package com.example.bank_map_view.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.bank_map_view.R
import com.example.bank_map_view.network.ItemClickListener
import com.example.bank_map_view.network.model.Access_Merchant
import com.example.bank_map_view.ui.holders.MerchantHolder

class MerchantAdapter (val context : Context, val itemClickListener: ItemClickListener) : RecyclerView.Adapter<MerchantHolder>() {

    private var access_Merchant : ArrayList<Access_Merchant> = arrayListOf()

    override fun onCreateViewHolder(view: ViewGroup, position: Int): MerchantHolder {
        val layout = LayoutInflater.from(view.context).inflate(R.layout.merchant_list_item, view, false)
        return MerchantHolder(layout, context, itemClickListener)

    }

    override fun getItemCount(): Int {
        return access_Merchant.size
    }

    override fun onBindViewHolder(view: MerchantHolder, position: Int) {

        view.index(access_Merchant!![position])
    }

    fun setNewData(merchantList: ArrayList<Access_Merchant>){
        access_Merchant = merchantList
        notifyDataSetChanged()
    }
}