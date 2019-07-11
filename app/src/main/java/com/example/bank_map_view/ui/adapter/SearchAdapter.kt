package com.example.bank_map_view.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.bank_map_view.R
import com.example.bank_map_view.network.ClickListener
import com.example.bank_map_view.network.ItemClickListener
import com.example.bank_map_view.network.model.Search_List
import com.example.bank_map_view.ui.holders.SearchHolder

class SearchAdapter (val context: Context, val itemClickListener: ClickListener) : RecyclerView.Adapter<SearchHolder>(){

    private var search_List : ArrayList<Search_List> = arrayListOf()

    override fun onCreateViewHolder(view: ViewGroup, position: Int): SearchHolder {
        val layout = LayoutInflater.from(view.context).inflate(R.layout.search_list_item, view, false)
        return SearchHolder(layout, context, itemClickListener)
    }

    override fun getItemCount(): Int {
        return search_List.size
    }

    override fun onBindViewHolder(view: SearchHolder, position: Int) {

        view.index(search_List[position])
    }

    fun setNewData(searchList: ArrayList<Search_List>){
        search_List = searchList
        notifyDataSetChanged()
    }
}