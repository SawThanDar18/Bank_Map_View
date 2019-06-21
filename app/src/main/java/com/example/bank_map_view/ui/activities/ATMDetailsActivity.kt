package com.example.bank_map_view.ui.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import android.widget.Toast
import com.example.bank_branch_details.network.model.Access_ATM
import com.example.bank_map_view.R
import com.example.bank_map_view.mvp.presenter.ATMPresenter
import com.example.bank_map_view.mvp.view.ATMView
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.atm_detail.*
import kotlinx.android.synthetic.main.branch_detail.map_image
import kotlinx.android.synthetic.main.branch_detail.refresh_iv
import kotlinx.android.synthetic.main.branch_detail.swipeRefresh

class ATMDetailsActivity : AppCompatActivity(), ATMView {

    private lateinit var presenter : ATMPresenter

    private var latitude : Double? = null
    private var longitude : Double? = null
    private var atm_name : String? = null

    private var markerATMList : List<Access_ATM>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.atm_detail)

        val bundle : Bundle? = intent.extras
        val locationName = bundle!!.getString("Location_Name")
        val address = bundle!!.getString("Address")
        val text : String = intent.getStringExtra("Location_Name")
        Toast.makeText(applicationContext, text, Toast.LENGTH_LONG).show()

        presenter = ATMPresenter(this)
        presenter.startLoadingATMDetails(locationName, address)

        swipeRefresh.setOnRefreshListener {
            presenter.startLoadingATMDetails(locationName, address)
        }

        refresh_iv.setOnClickListener {
            presenter.startLoadingATMDetails(locationName, address)
        }

        map_image.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:<$latitude>,<$longitude>?q=<$latitude>,<$longitude>($atm_name)"))
            startActivity(intent)
        }

        back_press_iv.setOnClickListener {
            val intent = Intent(this@ATMDetailsActivity, MapsActivity::class.java)
            startActivity(intent)
        }
    }

    override fun showPrompt(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
    }

    override fun showATMDetails(access_ATM : List<Access_ATM>) {
        markerATMList = access_ATM
        var indexNo : Int? = null

        val atm_title_tv = findViewById<TextView>(R.id.atm_title_tv)
        val atm_title = findViewById<TextView>(R.id.atm_title)
        val atm_address = findViewById<TextView>(R.id.atm_address)

        for(index in 0 until markerATMList!!.size){
            indexNo = index
        }

        atm_title_tv.text = markerATMList!![indexNo!!].Location_Name
        atm_title.text = markerATMList!![indexNo!!].Location_Name
        atm_address.text = markerATMList!![indexNo!!].Address
    }

    override fun viewMap(access_ATM: List<Access_ATM>) {
        markerATMList = access_ATM
        var indexNo : Int? = null

        for(index in 0 until markerATMList!!.size){
            indexNo = index
        }

        latitude = markerATMList!![indexNo!!].Latitude
        longitude = markerATMList!![indexNo!!].Longitude
        atm_name = markerATMList!![indexNo!!].Location_Name
    }

    override fun showLoading() {
        if (!swipeRefresh.isRefreshing) {
            swipeRefresh.isRefreshing = true
        }
    }

    override fun dismissLoading() {
        if (swipeRefresh.isRefreshing) {
            swipeRefresh.isRefreshing = false
        }
    }

    override fun onStart(){
        super.onStart()
        presenter.onStart()
    }

    override fun onStop() {
        super.onStop()
        presenter.onStop()
    }
}