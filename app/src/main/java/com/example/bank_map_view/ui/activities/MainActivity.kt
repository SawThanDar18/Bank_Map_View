package com.example.bank_map_view.ui.activities

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.v4.content.ContextCompat
import android.support.v4.content.res.ResourcesCompat
import android.support.v4.graphics.drawable.DrawableCompat
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.GridLayout
import android.widget.LinearLayout
import android.widget.Toast
import com.example.bank_branch_details.mvp.presenter.TouchPointListPresenter
import com.example.bank_branch_details.mvp.view.TouchPointListView
import com.example.bank_branch_details.network.DataImpl
import com.example.bank_branch_details.network.model.Access_ATM
import com.example.bank_branch_details.network.model.Access_Branch
import com.example.bank_map_view.R
import com.example.details_design.branch.BranchAdapter
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import android.graphics.BitmapFactory.decodeResource as decodeResource1
import com.example.bank_map_view.ui.adapter.ATMAdapter
import kotlinx.android.synthetic.main.activity_main.*



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

    private lateinit var layout : LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        layout = findViewById(R.id.bottom_sheet)
        val behavior = BottomSheetBehavior.from(layout)

        behavior.peekHeight = 400
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
            branch_btn.setBackgroundColor(resources.getColor(R.color.branch_color))
            atm_btn.setTextColor(Color.DKGRAY)
            atm_btn.setBackgroundColor(Color.WHITE)
            atm_btn.setBackgroundResource(R.drawable.button_shape)

            if(markerATMList.size>0) {
                googleMap!!.clear()
            for (index in 0 until markerBranchList!!.size) {
                    branchLatLng = LatLng(markerBranchList.get(index).Latitude!!, markerBranchList.get(index).Longitude!!)
                    markers = googleMap!!.addMarker(MarkerOptions().position(branchLatLng!!).title(markerBranchList!!.get(index).Branch_Name).icon(bitmapDescriptorFromVector(this, R.drawable.ic_branch_24dp)))
                    markers!!.rotation = -20f
                    markers!!.tag = markerBranchList.get(index).Branch_Code
                }
            }

            googleMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(branchLatLng, 16f))

            recyclerview = findViewById(R.id.bank_recyclerview)
            branchAdapter = BranchAdapter(this, markerBranchList!!)
            recyclerview.adapter = branchAdapter
            var layoutManager = GridLayoutManager(this, 1, GridLayout.VERTICAL, false)
            recyclerview.setLayoutManager(layoutManager)
        }

        atm_btn.setOnClickListener {

            atm_btn.setTextColor(Color.WHITE)
            atm_btn.setBackgroundColor(resources.getColor(R.color.atm_color))
            branch_btn.setTextColor(Color.DKGRAY)
            branch_btn.setBackgroundColor(Color.WHITE)
            branch_btn.setBackgroundResource(R.drawable.button_shape)

            if(markerBranchList.size>0){
                googleMap!!.clear()
            for(index in 0 until markerATMList!!.size){
                atmLatLng = LatLng(markerATMList!!.get(index).Latitude!!, markerATMList!!.get(index).Longitude!!)
                markers = googleMap!!.addMarker(MarkerOptions().position(atmLatLng!!).title(markerATMList!!.get(index).Location_Name).icon(bitmapDescriptorFromVector(this, R.drawable.ic_atm_24dp)))
                markers!!.rotation = 20f
                markers!!.tag = markerATMList!!.get(index).Terminal_ID
                }
            }

                googleMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(atmLatLng, 16f))

            recyclerview = findViewById(R.id.bank_recyclerview)
            atmAdapter = ATMAdapter(this, markerATMList!!)
            recyclerview.adapter = atmAdapter
            var layoutManager = GridLayoutManager(this, 1, GridLayout.VERTICAL, false)
            recyclerview.setLayoutManager(layoutManager)
        }
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
        branchAdapter = BranchAdapter(this, markerBranchList!!)
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
                        atmIntent.putExtra("Location_Name", markerATMList!![index].Location_Name)
                        atmIntent.putExtra("Address", markerATMList!![index].Address)
                        atmIntent.putExtra("Latitude", markerATMList!![index].Latitude)
                        atmIntent.putExtra("Longitude", markerATMList!![index].Longitude)
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
        vectorDrawable!!.setBounds(0, 0, vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight)
        var bitmap = Bitmap.createBitmap(vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
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

    override fun onStart(){
        super.onStart()
        presenter.onStart()
    }

    override fun onStop() {
        super.onStop()
        presenter.onStop()
    }




    override fun showBranchPlaces(access_Branch: ArrayList<Access_Branch>) {

        /*  markerBranchList = access_Branch
          for (index in 0 until markerBranchList!!.size) {
              branchLatLng = LatLng(markerBranchList.get(index).Latitude!!, markerBranchList.get(index).Longitude!!)
              branchMarkers = googleMap!!.addMarker(
                  MarkerOptions().position(branchLatLng!!).title(markerBranchList!!.get(index).Branch_Name).icon(
                      bitmapDescriptorFromVector(this, R.drawable.ic_branch_24dp)
                  )
              )
              branchMarkers!!.rotation = -20f
              branchMarkers!!.tag = markerBranchList.get(index).Branch_Code
          }

          val branch_name = findViewById<TextView>(R.id.bank_name)
          val branch_address = findViewById<TextView>(R.id.bank_address)

          for (index in 0 until markerBranchList.size) {
              branch_name.text = markerBranchList[index].Branch_Name
              branch_address.text = markerBranchList[index].Address
          }

          recyclerview = findViewById(R.id.bank_recyclerview)
          branchAdapter = BranchAdapter(this, markerBranchList!!)
          recyclerview.adapter = branchAdapter
          var layoutManager = GridLayoutManager(this, 1, GridLayout.VERTICAL, false)
          recyclerview.setLayoutManager(layoutManager)*/
    }

    override fun showATMPlaces(access_ATM: ArrayList<Access_ATM>) {
        /*markerATMList = access_ATM
        for(index in 0 until markerATMList!!.size){
            atmLatLng = LatLng(markerATMList!!.get(index).Latitude!!, markerATMList!!.get(index).Longitude!!)
            atmMarkers = googleMap!!.addMarker(MarkerOptions().position(atmLatLng!!).title(markerATMList!!.get(index).Location_Name).icon(bitmapDescriptorFromVector(this, R.drawable.ic_atm_24dp)))
            atmMarkers!!.rotation = 20f
            atmMarkers!!.tag = markerATMList!!.get(index).Terminal_ID
        }

       if(markerBranchList.size > 0){
            markerBranchList.get(0)
           googleMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(atmLatLng, 16f))
        }

        val atm_name = findViewById<TextView>(R.id.bank_name)
        val atm_address = findViewById<TextView>(R.id.bank_address)

        for(index in 0 until markerATMList.size){
            atm_name.text = markerATMList[index].Location_Name
            atm_address.text = markerATMList[index].Address
        }

        recyclerview = findViewById(R.id.bank_recyclerview)
        atmAdapter = ATMAdapter(this, markerATMList)
        recyclerview.adapter = atmAdapter
        var layoutManager = GridLayoutManager(this, 1, GridLayout.VERTICAL, false)
        recyclerview.setLayoutManager(layoutManager)*/

    }
}
