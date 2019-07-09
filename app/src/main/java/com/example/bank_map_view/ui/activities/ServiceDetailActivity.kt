package com.example.bank_map_view.ui.activities

import android.os.Bundle
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v7.app.AppCompatActivity
import android.webkit.WebView
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.bank_map_view.R
import com.example.bank_map_view.mvp.presenter.ServiceDetailPresenter
import com.example.bank_map_view.mvp.view.ServiceDetailView
import com.example.bank_map_view.network.response.ServiceResponse
import kotlinx.android.synthetic.main.activity_scrolling.*
import kotlinx.android.synthetic.main.service_detail_webview.*
import kotlinx.android.synthetic.main.service_detail_webview.swipeRefresh



class ServiceDetailActivity : AppCompatActivity(), ServiceDetailView {

    private lateinit var bundle: Bundle
    private lateinit var presenter : ServiceDetailPresenter

    private var value : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrolling)

        bundle = intent.extras
        value = bundle.getString("service_code")

        presenter = ServiceDetailPresenter(this)
        presenter.startLoadingServiceDetails(value!!)

        swipeRefresh.setOnRefreshListener {
            presenter.startLoadingServiceDetails(value!!)
        }

        back_iv.setOnClickListener {
            this.finish()
        }
    }

    override fun showServiceDetails(serviceResponse: ServiceResponse) {

        val serviceList = serviceResponse.service_List

        val service_title = findViewById<TextView>(R.id.title)
        val image_path = findViewById<ImageView>(R.id.image_path)
        val service_detail_webview = findViewById<WebView>(R.id.service_detail_webview)
        val detail_tv = findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout)

        for(index in 0 until serviceList!!.size) {
            if(serviceList[index].service_code.equals(value)) {

                service_title.text = serviceList[index].title
                Glide.with(applicationContext).load(serviceList[index].image_path).into(image_path)
                service_detail_webview.loadData(serviceList[index].service_detail, "text/html", null)
                detail_tv.title = "More Details about " + serviceList[index].title
            }
        }
    }

    override fun showPrompt(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
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
