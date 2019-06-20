package com.example.bank_map_view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.GridLayout
import android.widget.TextView
import android.widget.Toast
import com.example.bank_branch_details.network.DataImpl
import com.example.bank_branch_details.ui.detail.adapter.RecyclerAdapter
import com.example.bank_map_view.mvp.presenter.BranchPresenter
import com.example.bank_map_view.mvp.view.BranchView
import com.example.bank_map_view.network.response.BranchCodeResponse
import kotlinx.android.synthetic.main.bank_branch_detail.*

class DetailsActivity : AppCompatActivity(), BranchView {

    private lateinit var presenter : BranchPresenter

    private lateinit var recyclerview : RecyclerView
    private lateinit var recyclerAdapter : RecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bank_branch_detail)

        val bundle : Bundle? = intent.extras
        val value = bundle!!.getString("branchCode")
        val text : String = intent.getStringExtra("branchCode")
        Toast.makeText(applicationContext, text, Toast.LENGTH_LONG).show()

        presenter = BranchPresenter(this)
        presenter.startLoadingBranchDetails()

        swipeRefresh.setOnRefreshListener {
            presenter.startLoadingBranchDetails()
        }

        refresh_iv.setOnClickListener {
            presenter.startLoadingBranchDetails()
        }
    }

    override fun showPrompt(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
    }

    override fun showBranchDetails(branchCodeResponse: BranchCodeResponse) {
        val branch_title = findViewById<TextView>(R.id.branch_title)
        val branch_address = findViewById<TextView>(R.id.branch_address)
        val branch_phone = findViewById<TextView>(R.id.branch_phone)

        branch_title.text = branchCodeResponse!!.access_BranchInfo!!.branch_name
        branch_address.text = branchCodeResponse.access_BranchInfo!!.branch_address
        branch_phone.text = branchCodeResponse.access_BranchInfo!!.branch_phone

        recyclerview = findViewById(R.id.recyclerview)
        recyclerAdapter = RecyclerAdapter(branchCodeResponse.access_BranchServices!!, this)
        recyclerview.adapter = recyclerAdapter
        var layoutManager = GridLayoutManager(this, 1, GridLayout.VERTICAL, false)
        recyclerview.setLayoutManager(layoutManager)
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