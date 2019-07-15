package com.example.bank_map_view.ui.holders

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.bank_map_view.R
import com.example.bank_map_view.network.BranchItemClickListener
import com.example.bank_map_view.network.model.Service_List

class ServiceHolder (itemView : View, private val context: Context, val itemClickListener: BranchItemClickListener) : RecyclerView.ViewHolder(itemView){

    private val title : TextView
    private val description : TextView
    private val image_path : ImageView

    init {
        title = itemView.findViewById(R.id.service_title_tv)
        description = itemView.findViewById(R.id.service_desc_tv)
        image_path = itemView.findViewById(R.id.service_iv)
    }

    fun index(service_List: Service_List) {

        title.text = service_List.title
        description.text = service_List.description
        Glide.with(context).load(service_List.image_path).into(image_path)

        itemView.setOnClickListener{
            itemClickListener.onClicked(service_List.service_code!!)
        }
    }

}