package com.example.bank_map_view.ui.activities

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.GridLayout
import android.widget.Toast
import com.example.bank_branch_details.mvp.presenter.TouchPointListPresenter
import com.example.bank_branch_details.mvp.view.TouchPointListView
import com.example.bank_branch_details.network.DataImpl
import com.example.bank_branch_details.network.model.Access_ATM
import com.example.bank_branch_details.network.model.Access_Branch
import com.example.bank_map_view.R
import com.example.bank_map_view.network.ATMItemClickListener
import com.example.bank_map_view.network.BranchItemClickListener
import com.example.details_design.branch.BranchAdapter
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import android.graphics.BitmapFactory.decodeResource as decodeResource1
import com.example.bank_map_view.ui.adapter.ATMAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.bank_list.*

class MainActivity : AppCompatActivity(), TouchPointListView, OnMapReadyCallback {

    private lateinit var presenter : TouchPointListPresenter

    private var googleMap : GoogleMap? = null

    private var markers : Marker? = null

    private var atmLatLng : LatLng? = null
    private var branchLatLng : LatLng? = null

    private lateinit var markerBranchList : ArrayList<Access_Branch>
    private lateinit var markerATMList : ArrayList<Access_ATM>

    private lateinit var recyclerview : RecyclerView

    private lateinit var branchAdapter : BranchAdapter
    private lateinit var atmAdapter: ATMAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val behavior = BottomSheetBehavior.from(bottom_sheet)

        behavior.peekHeight = 370
        behavior.isHideable = false

        presenter = TouchPointListPresenter(this)
        presenter.startLoadingTouchList()

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map_fragment) as SupportMapFragment
        mapFragment.getMapAsync(this)

        DataImpl.getInstance().getBranchDetail(value = "branchCode")

        swipeRefresh.setOnRefreshListener {
            dismissLoading()
        }

        branch_btn.setOnClickListener {

            branch_btn.setTextColor(Color.WHITE)
            branch_btn.setBackgroundResource(R.drawable.selected_button_shape)

            atm_btn.setTextColor(Color.DKGRAY)
            atm_btn.setBackgroundResource(R.drawable.unselected_button_shape)

            merchant_btn.setTextColor(Color.DKGRAY)
            merchant_btn.setBackgroundResource(R.drawable.unselected_button_shape)

            agent_btn.setTextColor(Color.DKGRAY)
            agent_btn.setBackgroundResource(R.drawable.unselected_button_shape)

            //with change state
            /*branch_btn.setSelected(true)
            atm_btn.setSelected(false)
            agent_btn.setSelected(false)
            merchant_btn.setSelected(false)*/

            if(markerATMList.size>0) {
                googleMap!!.clear()
                for (index in 0 until markerBranchList!!.size) {
                    branchLatLng = LatLng(markerBranchList.get(index).Latitude!!, markerBranchList.get(index).Longitude!!)
                    markers = googleMap!!.addMarker(MarkerOptions().position(branchLatLng!!)
                                                                   .title(markerBranchList
                                                                   .get(index).Branch_Name)
                                                                   .icon(bitmapDescriptorFromVector(this, R.drawable.ic_branch_24dp)))

                    markers!!.rotation = -20f
                    markers!!.tag = markerBranchList.get(index).Branch_Code
                }
            }

            googleMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(branchLatLng, 16f))

            recyclerview.adapter = branchAdapter
        }

        atm_btn.setOnClickListener {

            atm_btn.setTextColor(Color.WHITE)
            atm_btn.setBackgroundResource(R.drawable.selected_button_shape)

            branch_btn.setTextColor(Color.DKGRAY)
            branch_btn.setBackgroundResource(R.drawable.unselected_button_shape)

            merchant_btn.setTextColor(Color.DKGRAY)
            merchant_btn.setBackgroundResource(R.drawable.unselected_button_shape)

            agent_btn.setTextColor(Color.DKGRAY)
            agent_btn.setBackgroundResource(R.drawable.unselected_button_shape)

            //with change state
            /*atm_btn.setSelected(true)
            branch_btn.setSelected(false)
            agent_btn.setSelected(false)
            merchant_btn.setSelected(false)*/

            if(markerBranchList.size>0){
                googleMap!!.clear()
            for(index in 0 until markerATMList!!.size){
                atmLatLng = LatLng(markerATMList!!.get(index).Latitude!!, markerATMList!!.get(index).Longitude!!)
                markers = googleMap!!.addMarker(MarkerOptions().position(atmLatLng!!)
                                                               .title(markerATMList!!
                                                               .get(index).Location_Name)
                                                               .icon(bitmapDescriptorFromVector(this, R.drawable.ic_atm_24dp)))

                markers!!.rotation = 20f
                markers!!.tag = markerATMList!!.get(index).Terminal_ID
                }
            }

                googleMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(atmLatLng, 16f))

            recyclerview.adapter = atmAdapter
        }

        merchant_btn.setOnClickListener {

            merchant_btn.setTextColor(Color.WHITE)
            merchant_btn.setBackgroundResource(R.drawable.selected_button_shape)

            branch_btn.setTextColor(Color.DKGRAY)
            branch_btn.setBackgroundResource(R.drawable.unselected_button_shape)

            atm_btn.setTextColor(Color.DKGRAY)
            atm_btn.setBackgroundResource(R.drawable.unselected_button_shape)

            agent_btn.setTextColor(Color.DKGRAY)
            agent_btn.setBackgroundResource(R.drawable.unselected_button_shape)

            //with change state
            /*merchant_btn.setSelected(true)
            branch_btn.setSelected(false)
            atm_btn.setSelected(false)
            agent_btn.setSelected(false)*/

            googleMap!!.clear()
            recyclerview.removeAllViewsInLayout()
        }

        agent_btn.setOnClickListener {

            agent_btn.setTextColor(Color.WHITE)
            agent_btn.setBackgroundResource(R.drawable.selected_button_shape)

            branch_btn.setTextColor(Color.DKGRAY)
            branch_btn.setBackgroundResource(R.drawable.unselected_button_shape)

            atm_btn.setTextColor(Color.DKGRAY)
            atm_btn.setBackgroundResource(R.drawable.unselected_button_shape)

            merchant_btn.setTextColor(Color.DKGRAY)
            merchant_btn.setBackgroundResource(R.drawable.unselected_button_shape)

            //with change state
            /*agent_btn.setSelected(true)
            branch_btn.setSelected(false)
            atm_btn.setSelected(false)
            merchant_btn.setSelected(false)*/

            googleMap!!.clear()
            recyclerview.removeAllViewsInLayout()
        }
    }

    override fun displayBranch(access_Branch: ArrayList<Access_Branch>) {

        recyclerview = findViewById(R.id.bank_recyclerview)
        branchAdapter = BranchAdapter(this, object : BranchItemClickListener{
            override fun onClicked(id: String) {
                val intent = Intent(applicationContext, BranchDetailsActivity::class.java)
                intent.putExtra("branchCode", id)
                startActivity(intent)
            }
        })

        var layoutManager = GridLayoutManager(this, 1, GridLayout.VERTICAL, false)
        recyclerview.setLayoutManager(layoutManager)
        branchAdapter.setNewData(access_Branch)
    }

    override fun displayATM(access_ATM: ArrayList<Access_ATM>) {

        recyclerview = findViewById(R.id.bank_recyclerview)
        atmAdapter = ATMAdapter(this, object : ATMItemClickListener{
            override fun onClicked(name: String, address: String, latitude: Double, Longitude: Double) {
                val intent = Intent(applicationContext, ATMDetailsActivity::class.java)
                intent.putExtra("Location_Name",name)
                intent.putExtra("Address", address)
                intent.putExtra("Latitude", latitude)
                intent.putExtra("Longitude", Longitude)
                startActivity(intent)
            }
        })

        var layoutManager = GridLayoutManager(this, 1, GridLayout.VERTICAL, false)
        recyclerview.setLayoutManager(layoutManager)
        atmAdapter.setNewData(access_ATM)
    }

    override fun showPlaces(access_ATM: ArrayList<Access_ATM>, access_Branch: ArrayList<Access_Branch>) {

        markerATMList = access_ATM
        for(index in 0 until markerATMList.size){
            atmLatLng = LatLng(markerATMList.get(index).Latitude!!, markerATMList.get(index).Longitude!!)
            markers = googleMap!!.addMarker(MarkerOptions().position(atmLatLng!!).title(markerATMList.get(index).Location_Name).icon(bitmapDescriptorFromVector(this, R.drawable.ic_atm_24dp)))
            markers!!.rotation = 20f
            markers!!.tag = markerATMList.get(index).Terminal_ID
        }

        googleMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(atmLatLng, 16f))

        markerBranchList = access_Branch
        for (index in 0 until markerBranchList.size){
            branchLatLng = LatLng(markerBranchList.get(index).Latitude!!, markerBranchList.get(index).Longitude!!)
            markers = googleMap!!.addMarker(MarkerOptions().position(branchLatLng!!).title(markerBranchList!!.get(index).Branch_Name).icon(bitmapDescriptorFromVector(this, R.drawable.ic_branch_24dp)))
            markers!!.rotation = -20f
            markers!!.tag = markerBranchList.get(index).Branch_Code
        }

        googleMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(branchLatLng, 16f))

        recyclerview = findViewById(R.id.bank_recyclerview)
        branchAdapter = BranchAdapter(this, object : BranchItemClickListener{
            override fun onClicked(id: String) {
                val intent = Intent(applicationContext, BranchDetailsActivity::class.java)
                intent.putExtra("branchCode", id)
                startActivity(intent)
            }

        })

        branchAdapter.setNewData(access_Branch)
        recyclerview.adapter = branchAdapter
        var layoutManager = GridLayoutManager(this, 1, GridLayout.VERTICAL, false)
        recyclerview.setLayoutManager(layoutManager)
    }

    override fun onMapReady(map: GoogleMap?) {
        googleMap = map!!
        presenter.startLoadingTouchList()

        googleMap!!.setOnInfoWindowClickListener(object : GoogleMap.OnInfoWindowClickListener {
            override fun onInfoWindowClick(marker: Marker?) {

                for (index in 0 until markerATMList!!.size) {
                    markers = marker
                    if (markers!!.tag == markerATMList!!.get(index).Terminal_ID) {
                        val atmIntent = Intent(this@MainActivity, ATMDetailsActivity::class.java)
                        atmIntent.putExtra("Location_Name", markerATMList[index].Location_Name)
                        atmIntent.putExtra("Address", markerATMList[index].Address)
                        atmIntent.putExtra("Latitude", markerATMList[index].Latitude)
                        atmIntent.putExtra("Longitude", markerATMList[index].Longitude)
                        startActivity(atmIntent)
                    }
                }

                for(index in 0 until markerBranchList.size){
                    markers = marker
                    if(markers!!.tag == markerBranchList.get(index).Branch_Code){
                        val branchIntent = Intent(this@MainActivity, BranchDetailsActivity::class.java)
                        branchIntent.putExtra("branchCode", markerBranchList!![index].Branch_Code)
                        startActivity(branchIntent)
                    }
                }
            }
        })
    }

    //for bitmap icon
    private fun bitmapDescriptorFromVector(context: Context, vectorResId : Int) : BitmapDescriptor{
        var vectorDrawable = ContextCompat.getDrawable(context, vectorResId)
        vectorDrawable!!.setBounds(0, 0, vectorDrawable.intrinsicWidth * 2, vectorDrawable.intrinsicHeight * 2)
        var bitmap = Bitmap.createBitmap(vectorDrawable.intrinsicWidth * 2, vectorDrawable.intrinsicHeight * 2, Bitmap.Config.ARGB_8888)
        var canvas = Canvas(bitmap)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
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

    override fun onStart() {
        super.onStart()
        presenter.onStart()
    }

    override fun onStop() {
        super.onStop()
        presenter.onStop()
    }
}
