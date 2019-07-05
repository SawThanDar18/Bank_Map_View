package com.example.bank_map_view.ui.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import android.widget.Toast
import com.example.bank_map_view.R
import com.example.bank_map_view.mvp.presenter.AgentPresenter
import com.example.bank_map_view.mvp.view.AgentView
import kotlinx.android.synthetic.main.details.*

class AgentDetailActivity : AppCompatActivity(), AgentView {

    private lateinit var presenter : AgentPresenter

    private var latitude : Double? = null
    private var longitude : Double? = null

    private var agent_name : String? = null

    private lateinit var bundle : Bundle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.details)

        bundle = intent.extras

        presenter = AgentPresenter(this)
        presenter.startLoadingAgentDetails()

        swipeRefresh.setOnRefreshListener {
            dismissLoading()
        }

        refresh_iv.setOnClickListener {
            presenter.startLoadingAgentDetails()
        }

        map_image.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:<$latitude>,<$longitude>?q=<$latitude>,<$longitude>($agent_name)"))
            startActivity(intent)
        }

        back_press_iv.setOnClickListener {
            val intent = Intent(this@AgentDetailActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun showPrompt(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
    }

    override fun showAgentDetails() {

        val title = findViewById<TextView>(R.id.title)
        val agent_title = findViewById<TextView>(R.id.title_tv)
        val agent_address = findViewById<TextView>(R.id.address_tv)

        title.text = bundle!!.getString("Agent_Name")
        agent_title.text = bundle!!.getString("Agent_Name")
        agent_address.text = bundle!!.getString("Address")
    }

    override fun viewMap() {

        latitude = bundle!!.getDouble("Latitude")
        longitude = bundle!!.getDouble("Longitude")
        agent_name = bundle!!.getString("Agent_Name")
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