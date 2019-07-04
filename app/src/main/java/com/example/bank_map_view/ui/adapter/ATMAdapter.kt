package com.example.bank_map_view.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.bank_branch_details.network.model.Access_ATM
import com.example.bank_map_view.R
import com.example.bank_map_view.network.ItemClickListener
import com.example.bank_map_view.ui.holders.ATMHolder

class ATMAdapter (val context : Context, val itemClickListener: ItemClickListener) : RecyclerView.Adapter<ATMHolder>(){

    private var atmList : ArrayList<Access_ATM> = arrayListOf()

    override fun onCreateViewHolder(view: ViewGroup, position: Int): ATMHolder {
        val layout = LayoutInflater.from(view.context).inflate(R.layout.atm_list_item, view, false)
        return ATMHolder(layout, context, itemClickListener)

    }

    override fun getItemCount(): Int {
        return atmList!!.size
    }

    override fun onBindViewHolder(view: ATMHolder, position: Int) {

        view.index(atmList!![position])
    }

    fun setNewData(access_ATM : ArrayList<Access_ATM>){
        atmList = access_ATM
        notifyDataSetChanged()
    }
}
