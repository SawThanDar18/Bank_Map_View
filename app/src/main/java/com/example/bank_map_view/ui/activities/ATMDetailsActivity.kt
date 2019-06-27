package com.example.bank_map_view.ui.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import android.widget.Toast
import com.example.bank_map_view.R
import com.example.bank_map_view.mvp.presenter.ATMPresenter
import com.example.bank_map_view.mvp.view.ATMView
import com.example.bank_map_view.ui.activities.branch.MainActivity
import kotlinx.android.synthetic.main.atm_detail.*
import kotlinx.android.synthetic.main.branch_detail.map_image
import kotlinx.android.synthetic.main.branch_detail.refresh_iv
import kotlinx.android.synthetic.main.branch_detail.swipeRefresh

class ATMDetailsActivity : AppCompatActivity(), ATMView {

    private lateinit var presenter : ATMPresenter

    private var latitude : Double? = null
    private var longitude : Double? = null

    private var atm_name : String? = null

    private lateinit var bundle : Bundle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.atm_detail)

        bundle = intent.extras

        presenter = ATMPresenter(this)
        presenter.startLoadingATMDetails()

        swipeRefresh.setOnRefreshListener {
            presenter.startLoadingATMDetails()
        }

        refresh_iv.setOnClickListener {
            presenter.startLoadingATMDetails()
        }

        map_image.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:<$latitude>,<$longitude>?q=<$latitude>,<$longitude>($atm_name)"))
            startActivity(intent)
        }

        back_press_iv.setOnClickListener {
            val intent = Intent(this@ATMDetailsActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun showPrompt(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
    }

    override fun showATMDetails() {

        val atm_title_tv = findViewById<TextView>(R.id.atm_title_tv)
        val atm_title = findViewById<TextView>(R.id.atm_title)
        val atm_address = findViewById<TextView>(R.id.atm_address)

        atm_title_tv.text = bundle!!.getString("Location_Name")
        atm_title.text = bundle!!.getString("Location_Name")
        atm_address.text = bundle!!.getString("Address")
    }

    override fun viewMap() {

        latitude = bundle!!.getDouble("Latitude")
        longitude = bundle!!.getDouble("Longitude")
        atm_name = bundle!!.getString("Location_Name")
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