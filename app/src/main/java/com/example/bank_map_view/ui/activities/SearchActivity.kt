package com.example.bank_map_view.ui.activities

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.bank_map_view.R
import com.example.bank_map_view.mvp.presenter.SearchListPresenter
import com.example.bank_map_view.mvp.view.SearchListView
import com.example.bank_map_view.network.ClickListener
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
        searchListPresenter.startLoadingSearchList(search.text.toString())

        refresh.setOnClickListener {

            progressDialog.show()

            val value = search.text.toString()
            searchListPresenter.startLoadingSearchList(value)

        }

        back_iv.setOnClickListener {

            progressDialog.show()
            this.finish()
        }

        search.setOnEditorActionListener(object : TextView.OnEditorActionListener{
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if(actionId == EditorInfo.IME_ACTION_SEARCH){

                    val value = search.text.toString()
                    searchListPresenter.startLoadingSearchList(value)

                    return true
                }
                return false
            }

        })

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
        Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
    }

    override fun showLoading() {

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
    }

    override fun onStop() {
        super.onStop()
        searchListPresenter.onStop()
    }
}