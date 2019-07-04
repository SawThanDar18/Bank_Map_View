package com.example.bank_map_view.ui.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import android.widget.Toast
import com.example.bank_map_view.R
import com.example.bank_map_view.mvp.presenter.MerchantPresenter
import com.example.bank_map_view.mvp.view.MerchantView
import kotlinx.android.synthetic.main.details.*

class MerchantDetailActivity: AppCompatActivity(), MerchantView {

    private lateinit var presenter : MerchantPresenter

    private var latitude : Double? = null
    private var longitude : Double? = null

    private var merchant_name : String? = null

    private lateinit var bundle : Bundle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.details)

        bundle = intent.extras

        presenter = MerchantPresenter(this)
        presenter.startLoadingMerchantDetails()

        swipeRefresh.setOnRefreshListener {
            dismissLoading()
        }

        refresh_iv.setOnClickListener {
            presenter.startLoadingMerchantDetails()
        }

        map_image.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:<$latitude>,<$longitude>?q=<$latitude>,<$longitude>($merchant_name)"))
            startActivity(intent)
        }

        back_press_iv.setOnClickListener {
            val intent = Intent(this@MerchantDetailActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun showPrompt(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
    }

    override fun showMerchantDetails() {

        val title = findViewById<TextView>(R.id.title)
        val merchant_title = findViewById<TextView>(R.id.title_tv)
        val merchant_address = findViewById<TextView>(R.id.address_tv)

        title.text = bundle!!.getString("Merchant_Name")
        merchant_title.text = bundle!!.getString("Merchant_Name")
        merchant_address.text = bundle!!.getString("Address")
    }

    override fun viewMap() {

        latitude = bundle!!.getDouble("Latitude")
        longitude = bundle!!.getDouble("Longitude")
        merchant_name = bundle!!.getString("Merchant_Name")
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

    override fun onResume() {
        super.onResume()
        presenter.onStart()
    }

    override fun onStop() {
        super.onStop()
        presenter.onStop()
    }

}