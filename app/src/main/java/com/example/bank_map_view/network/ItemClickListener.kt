package com.example.bank_map_view.network

interface ItemClickListener {

    fun onClicked(name : String, address : String, latitude : Double, Longitude : Double)
}