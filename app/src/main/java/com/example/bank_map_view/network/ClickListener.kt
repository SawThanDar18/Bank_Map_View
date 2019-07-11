package com.example.bank_map_view.network

interface ClickListener {
    fun onClicked(type : String, name : String, address : String, latitude : Double, Longitude : Double)
}