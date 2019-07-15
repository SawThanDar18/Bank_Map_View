package com.example.bank_map_view.ui.activities

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Paint
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



class ServiceDetailActivity : AppCompatActivity(), ServiceDetailView {

    private lateinit var bundle: Bundle
    private lateinit var presenter : ServiceDetailPresenter

    private var value : String? = null

    private lateinit var progressDialog : ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrolling)

        bundle = intent.extras
        value = bundle.getString("service_code")

        progressDialog =  ProgressDialog(this)
        progressDialog.setMessage("loading")
        progressDialog.setCancelable(false)

        presenter = ServiceDetailPresenter(this)
        presenter.startLoadingServiceDetails(value!!)

        back_iv.setOnClickListener {
            this.finish()
        }

        exchange_constraintLayout.setOnClickListener {
            val intent = Intent(this, NearestExchangeActivity::class.java)
            startActivity(intent)
        }
    }

    override fun showServiceDetails(serviceResponse: ServiceResponse) {

        val serviceList = serviceResponse.service_List

        val image_path = findViewById<ImageView>(R.id.image_path)
        val service_detail_webview = findViewById<WebView>(R.id.service_detail_webview)
        val service_title = findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout)
        val detail_tv = findViewById<TextView>(R.id.detail_tv)

        for(index in 0 until serviceList!!.size) {
            if(serviceList[index].service_code.equals(value)) {

                Glide.with(applicationContext).load(serviceList[index].image_path).into(image_path)
                service_detail_webview.loadData(serviceList[index].service_detail, "text/html", null)
                service_title.title = serviceList[index].title

                detail_tv.text = "More Details about " + serviceList[index].title
                detail_tv.paintFlags = Paint.UNDERLINE_TEXT_FLAG
            }
        }
    }

    override fun showPrompt(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
    }

    override fun showLoading() {
        progressDialog.show()
    }

    override fun dismissLoading() {
        progressDialog.dismiss()
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
