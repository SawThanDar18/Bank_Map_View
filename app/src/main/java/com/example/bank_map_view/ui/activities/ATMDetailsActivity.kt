package com.example.bank_map_view.ui.activities

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.bank_branch_details.mvp.view.TouchPointListView
import com.example.bank_map_view.R
import com.example.bank_map_view.mvp.presenter.ATMPresenter
import com.example.bank_map_view.mvp.view.ATMView
import kotlinx.android.synthetic.main.bank_list.*
import kotlinx.android.synthetic.main.details.*
import kotlinx.android.synthetic.main.branch_detail.map_image
import kotlinx.android.synthetic.main.branch_detail.refresh_iv
import kotlinx.android.synthetic.main.branch_detail.swipeRefresh

class ATMDetailsActivity : AppCompatActivity(), ATMView {

    private lateinit var presenter : ATMPresenter

    private var latitude : Double? = null
    private var longitude : Double? = null

    private var atm_name : String? = null

    private lateinit var bundle : Bundle

    private lateinit var progressDialog : ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.details)

        bundle = intent.extras

        progressDialog =  ProgressDialog(this)
        progressDialog.setMessage("loading")
        progressDialog.setCancelable(false)

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
            this.finish()
        }
    }

    override fun showPrompt(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
    }

    override fun showATMDetails() {

        val type = findViewById<TextView>(R.id.type_tv)
        val atm_title_tv = findViewById<TextView>(R.id.title)
        val atm_title = findViewById<TextView>(R.id.title_tv)
        val atm_address = findViewById<TextView>(R.id.address_tv)

        type.text = bundle.getString("TouchPointType")
        atm_title_tv.text = bundle.getString("Location_Name")
        atm_title.text = bundle.getString("Location_Name")
        atm_address.text = bundle.getString("Address")
    }

    override fun viewMap() {

        latitude = bundle.getDouble("Latitude")
        longitude = bundle.getDouble("Longitude")
        atm_name = bundle.getString("Location_Name")
    }

    override fun showLoading() {

        progressDialog.show()
    }

    override fun dismissLoading() {

        progressDialog.dismiss()
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