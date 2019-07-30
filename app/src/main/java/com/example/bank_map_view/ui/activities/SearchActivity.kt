package com.example.bank_map_view.ui.activities

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import com.example.bank_map_view.R
import com.example.bank_map_view.mvp.presenter.SearchListPresenter
import com.example.bank_map_view.mvp.view.SearchListView
import com.example.bank_map_view.network.BranchItemClickListener
import com.example.bank_map_view.network.ClickListener
import com.example.bank_map_view.network.model.Recent
import com.example.bank_map_view.network.model.Service_List
import com.example.bank_map_view.network.response.SearchResponse
import com.example.bank_map_view.room.RecentDatabase
import com.example.bank_map_view.room.ServicesDatabase
import com.example.bank_map_view.ui.adapter.AvailableServiceAdapter
import com.example.bank_map_view.ui.adapter.RecentAdapter
import com.example.bank_map_view.ui.adapter.SearchAdapter
import kotlinx.android.synthetic.main.search_activity.*

class SearchActivity : AppCompatActivity(), SearchListView {

    private lateinit var searchListPresenter : SearchListPresenter

    private lateinit var searchAdapter : SearchAdapter
    private lateinit var availableServiceAdapter: AvailableServiceAdapter
    private lateinit var recentAdapter: RecentAdapter

    private lateinit var servicesDatabase : ServicesDatabase
    private lateinit var recentDatabase: RecentDatabase

    private var recyclerview : RecyclerView? = null
    private var roomRecyclerview : RecyclerView? = null
    private var recentRecyclerview : RecyclerView? = null

    private lateinit var progressDialog : ProgressDialog

    private var value : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_activity)

        progressDialog =  ProgressDialog(this)
        progressDialog.setMessage("loading")
        progressDialog.setCancelable(false)

        recyclerview = findViewById(R.id.recycler)
        roomRecyclerview = findViewById(R.id.recycler_service)
        recentRecyclerview = findViewById(R.id.recent_recyclerview)

        recyclerview!!.visibility = View.INVISIBLE

        available_tv.visibility = View.VISIBLE
        roomRecyclerview!!.visibility = View.VISIBLE

        recent_tv.visibility = View.VISIBLE
        recentRecyclerview!!.visibility = View.VISIBLE

        searchListPresenter = SearchListPresenter(this)
        searchListPresenter.startLoadingSearchList(search.text.toString())

        refresh.setOnClickListener {

            progressDialog.show()

            value = search.text.toString()
            searchListPresenter.startLoadingSearchList(value!!)

        }

        back_iv.setOnClickListener {

            progressDialog.show()
            this.finish()
        }

        search.setOnEditorActionListener(object : TextView.OnEditorActionListener{
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if(actionId == EditorInfo.IME_ACTION_SEARCH){

                    recyclerview!!.visibility = View.VISIBLE

                    available_tv!!.visibility = View.INVISIBLE
                    roomRecyclerview!!.visibility = View.INVISIBLE

                    recent_tv.visibility = View.INVISIBLE
                    recentRecyclerview!!.visibility = View.INVISIBLE

                    var value = search.text.toString().trim()
                    searchListPresenter.startLoadingSearchList(value)

                    var recent = Recent(value)
                    var recentDatabase = RecentDatabase.getDatabase(this@SearchActivity)
                    var addRecent = recentDatabase.getRecentDao().addRecentWord(recent)

                    return true
                }

                return false
            }

        })

        search.setOnKeyListener { v, keyCode, event ->
            if(keyCode == KeyEvent.KEYCODE_DEL){

                recyclerview!!.visibility = View.INVISIBLE

                available_tv!!.visibility = View.VISIBLE
                roomRecyclerview!!.visibility = View.VISIBLE

                recent_tv.visibility = View.VISIBLE

                recentDatabase = RecentDatabase.getDatabase(this)
                var getRecent = recentDatabase.getRecentDao().getRecentWords()

                recentRecyclerview = findViewById(R.id.recent_recyclerview)
                recentAdapter = RecentAdapter(getRecent as ArrayList<Recent>, this, object : BranchItemClickListener{
                    override fun onClicked(id: String) {
                        var code = id
                        search.setText(code)

                        searchListPresenter.startLoadingSearchList(code)
                        recyclerview!!.visibility = View.VISIBLE
                        roomRecyclerview!!.visibility = View.INVISIBLE
                        recent_tv.visibility = View.INVISIBLE
                        recentRecyclerview!!.visibility = View.INVISIBLE
                    }

                })

                var layoutManager = GridLayoutManager(this, 1, android.widget.GridLayout.VERTICAL, false)
                recentRecyclerview!!.setLayoutManager(layoutManager)
                recentRecyclerview!!.adapter = recentAdapter
            }

            return@setOnKeyListener false
        }

        search.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searchByKeyword(s.toString())
            }

        })

    }

    private fun searchByKeyword(keyword : String) {

        var service = arrayListOf<Service_List>()
        servicesDatabase = ServicesDatabase.getDatabase(this)
        var getServices = servicesDatabase.getServicesDao().getServices()

        for(services in getServices){
            if(services.title!!.toLowerCase().contains(keyword.toLowerCase())){
                service.add(services)
            }
        }

        availableServiceAdapter = AvailableServiceAdapter(this, service, object : BranchItemClickListener {
            override fun onClicked(id: String) {
                val intent = Intent(applicationContext, ServiceDetailActivity::class.java)
                intent.putExtra("service_code",id)
                startActivity(intent)
            }
        })
        var layoutManager = GridLayoutManager(this, 1, android.widget.GridLayout.VERTICAL, false)
        roomRecyclerview!!.setLayoutManager(layoutManager)
        roomRecyclerview!!.adapter = availableServiceAdapter
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

    override fun retrieveFromDB() {

        servicesDatabase = ServicesDatabase.getDatabase(this)
        var services : List<Service_List> = servicesDatabase.getServicesDao().getServices()

        roomRecyclerview = findViewById(R.id.recycler_service)
        availableServiceAdapter = AvailableServiceAdapter(this, services as ArrayList<Service_List>, object : BranchItemClickListener {
            override fun onClicked(id: String) {
                val intent = Intent(applicationContext, ServiceDetailActivity::class.java)
                intent.putExtra("service_code",id)
                startActivity(intent)
            }
        })
        var layoutManager = GridLayoutManager(this, 1, android.widget.GridLayout.VERTICAL, false)
        roomRecyclerview!!.setLayoutManager(layoutManager)
        roomRecyclerview!!.adapter = availableServiceAdapter

        recentDatabase = RecentDatabase.getDatabase(this)
        var getRecent = recentDatabase.getRecentDao().getRecentWords()

        if(getRecent.isNotEmpty()){
            recentRecyclerview = findViewById(R.id.recent_recyclerview)
            recentAdapter = RecentAdapter(getRecent as ArrayList<Recent>, this, object : BranchItemClickListener{
                override fun onClicked(id: String) {
                    var code = id
                    search.setText(code)
                    searchListPresenter.startLoadingSearchList(code)
                    recyclerview!!.visibility = View.VISIBLE
                    roomRecyclerview!!.visibility = View.INVISIBLE
                    recent_tv.visibility = View.INVISIBLE
                    recentRecyclerview!!.visibility = View.INVISIBLE
                }

            })

            var recentLayoutManager = GridLayoutManager(this, 1, android.widget.GridLayout.VERTICAL, false)
            recentRecyclerview!!.setLayoutManager(recentLayoutManager)
            recentRecyclerview!!.adapter = recentAdapter
        }
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

    override fun onStop() {
        super.onStop()
        searchListPresenter.onStop()
    }
}