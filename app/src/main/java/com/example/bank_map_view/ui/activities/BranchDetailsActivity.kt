package com.example.bank_map_view.ui.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.GridLayout
import android.widget.TextView
import android.widget.Toast
import com.example.bank_branch_details.ui.detail.adapter.RecyclerAdapter
import com.example.bank_map_view.R
import com.example.bank_map_view.mvp.presenter.BranchPresenter
import com.example.bank_map_view.mvp.view.BranchView
import com.example.bank_map_view.network.response.BranchCodeResponse
import kotlinx.android.synthetic.main.branch_detail.*

class BranchDetailsActivity : AppCompatActivity(), BranchView {

    private lateinit var presenter : BranchPresenter

    private lateinit var recyclerview : RecyclerView
    private lateinit var recyclerAdapter : RecyclerAdapter

    private var latitude : Double? = null
    private var longitude : Double? = null
    private var branch_name : String? = null

    private var phone : Array<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.branch_detail)

        val bundle : Bundle? = intent.extras
        val value = bundle!!.getString("branchCode")

        presenter = BranchPresenter(this)
        presenter.startLoadingBranchDetails(value)

        swipeRefresh.setOnRefreshListener {
            presenter.startLoadingBranchDetails(value)
        }

        refresh_iv.setOnClickListener {
            presenter.startLoadingBranchDetails(value)
        }

        call_image.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("tel:" + phone!![0]))
            startActivity(intent)
        }

        map_image.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:<$latitude>,<$longitude>?q=<$latitude>,<$longitude>($branch_name)"))
            startActivity(intent)
        }

        back_press_iv.setOnClickListener {
            val intent = Intent(this@BranchDetailsActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun showPrompt(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
    }

    override fun showBranchDetails(branchCodeResponse: BranchCodeResponse) {
        val branch_title_tv = findViewById<TextView>(R.id.title)
        val branch_title = findViewById<TextView>(R.id.branch_title)
        val branch_address = findViewById<TextView>(R.id.address_tv)
        val branch_phone = findViewById<TextView>(R.id.branch_phone)

        branch_title_tv.text = branchCodeResponse!!.access_BranchInfo!!.branch_name
        branch_title.text = branchCodeResponse!!.access_BranchInfo!!.branch_name
        branch_address.text = branchCodeResponse.access_BranchInfo!!.branch_address
        branch_phone.text = branchCodeResponse.access_BranchInfo!!.branch_phone

        recyclerview = findViewById(R.id.recyclerview)
        recyclerAdapter = RecyclerAdapter(branchCodeResponse.access_BranchServices!!, this)
        recyclerview.adapter = recyclerAdapter
        var layoutManager = GridLayoutManager(this, 1, GridLayout.VERTICAL, false)
        recyclerview.setLayoutManager(layoutManager)
    }

    override fun viewMap(branchCodeResponse: BranchCodeResponse) {

        latitude = branchCodeResponse.access_BranchInfo!!.latitude
        longitude = branchCodeResponse.access_BranchInfo!!.longitude
        branch_name = branchCodeResponse.access_BranchInfo!!.branch_name
    }

    override fun callBankPhone(branchCodeResponse: BranchCodeResponse) {
        phone = branchCodeResponse.access_BranchInfo!!.branch_phone!!.split(",").toTypedArray()
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
        presenter.startLoadingBranchDetails(value = "branchCode")
    }

    override fun onStop() {
        super.onStop()
        presenter.onStop()
    }
}