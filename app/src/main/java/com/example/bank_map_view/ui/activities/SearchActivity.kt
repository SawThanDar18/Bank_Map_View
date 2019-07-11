package com.example.bank_map_view.ui.activities

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.EditText
import android.widget.Toast
import com.example.bank_map_view.R
import com.example.bank_map_view.mvp.presenter.SearchListPresenter
import com.example.bank_map_view.mvp.view.SearchListView
import com.example.bank_map_view.network.ClickListener
import com.example.bank_map_view.network.ItemClickListener
import com.example.bank_map_view.network.response.SearchResponse
import com.example.bank_map_view.ui.adapter.SearchAdapter
import kotlinx.android.synthetic.main.search_activity.*

class SearchActivity : AppCompatActivity(), SearchListView {

    private lateinit var searchListPresenter : SearchListPresenter

    private lateinit var searchAdapter : SearchAdapter

    private var recyclerview : RecyclerView? = null

    private lateinit var progressDialog : ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_activity)

        progressDialog =  ProgressDialog(this)
        progressDialog.setMessage("loading")
        progressDialog.setCancelable(false)

        searchListPresenter = SearchListPresenter(this)
        searchListPresenter.startLoadingSearchList(search_editText.text.toString())

        refresh.setOnClickListener {
            progressDialog.show()

            val value = search_editText.text.toString()
            searchListPresenter.startLoadingSearchList(value)

        }

        search_iv.setOnClickListener {
            progressDialog.show()

            val value = search_editText.text.toString()
            searchListPresenter.startLoadingSearchList(value)
        }

    }

    override fun showSearchList(searchResponse: SearchResponse) {

        progressDialog.dismiss()

        recyclerview = findViewById(R.id.recycler)
        searchAdapter = SearchAdapter(this, object : ClickListener{
            override fun onClicked(type : String, name: String, address: String, latitude: Double, Longitude: Double) {
                val intent = Intent(applicationContext, ATMDetailsActivity::class.java)
                intent.putExtra("TouchPointType", type)
                intent.putExtra("Location_Name",name)
                intent.putExtra("Address", address)
                intent.putExtra("Latitude", latitude)
                intent.putExtra("Longitude", Longitude)
                startActivity(intent)
            }
        })

        val layoutManager = GridLayoutManager(this, 1, GridLayout.VERTICAL, false)
        recyclerview!!.setLayoutManager(layoutManager)
        searchAdapter.setNewData(searchResponse.search_List!!)
        recyclerview!!.adapter = searchAdapter
    }

    override fun showPrompt(message: String) {

    }

    override fun showLoading() {
        //progressDialog.show()
    }

    override fun dismissLoading() {
        progressDialog.dismiss()
    }

    override fun onStart() {
        super.onStart()
        searchListPresenter.onStart()
    }

    override fun onResume() {
        super.onResume()
        searchListPresenter.startLoadingSearchList(search_editText.text.toString())
    }

    override fun onStop() {
        super.onStop()
        searchListPresenter.onStop()
    }
}