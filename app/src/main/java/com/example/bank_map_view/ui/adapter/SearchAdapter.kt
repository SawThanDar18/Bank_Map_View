package com.example.bank_map_view.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import com.example.bank_map_view.R
import com.example.bank_map_view.network.ClickListener
import com.example.bank_map_view.network.model.Search_List
import com.example.bank_map_view.ui.holders.SearchHolder

class SearchAdapter (val context: Context, val itemClickListener: ClickListener) : RecyclerView.Adapter<SearchHolder>(){

    private var search_List : ArrayList<Search_List> = arrayListOf()
    //private var search : MutableList<Search_List>? = null

    override fun onCreateViewHolder(view: ViewGroup, position: Int): SearchHolder {
        val layout = LayoutInflater.from(view.context).inflate(R.layout.search_list_item, view, false)
        return SearchHolder(layout, context, itemClickListener)
    }

    override fun getItemCount(): Int {
        return search_List.size
    }

    /*override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charString = charSequence.toString()
                if (charString.isEmpty()) {
                    search_List = search as ArrayList<Search_List>
                } else {
                    val filteredList = ArrayList<Search_List>()
                    for (row in search!!) {

                        if (row.location_Name!!.toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row)
                        }
                    }

                    search_List = filteredList
                }

                val filterResults = FilterResults()
                filterResults.values = search_List
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: Filter.FilterResults) {
                search_List = filterResults.values as ArrayList<Search_List>
                notifyDataSetChanged()
            }
        }
    }*/

    override fun onBindViewHolder(view: SearchHolder, position: Int) {

        view.index(search_List[position])
    }

    fun setNewData(searchList: ArrayList<Search_List>){
        search_List = searchList
        notifyDataSetChanged()
    }
}