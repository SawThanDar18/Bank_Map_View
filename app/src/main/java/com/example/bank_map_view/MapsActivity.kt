package com.example.bank_map_view

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.bank_branch_details.mvp.presenter.TouchPointListPresenter
import com.example.bank_branch_details.mvp.view.TouchPointListView
import com.example.bank_branch_details.network.DataImpl
import com.example.bank_branch_details.network.model.Access_ATM
import com.example.bank_branch_details.network.model.Access_Branch
import com.example.bank_map_view.network.model.Access_BranchCode
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_maps.*

class MapsActivity : AppCompatActivity(), TouchPointListView, OnMapReadyCallback {

    private lateinit var presenter : TouchPointListPresenter

    private var mMap: GoogleMap? = null
    private var placeMarker : Marker? = null

    private var currentLatLng : LatLng? = null
    private var atmLatLng : LatLng? = null
    private var branchLatLng : LatLng? = null

    private var markerBranchList : List<Access_Branch>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        presenter = TouchPointListPresenter(this)
        presenter.startLoadingTouchList()

        swipeRefresh.setOnRefreshListener {
            presenter.startLoadingTouchList()
        }

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map_fragment) as SupportMapFragment
        mapFragment.getMapAsync(this)

        DataImpl.getInstance().getBranchDetail()
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

    /*override fun showCurrentLocation(touchPointListResponse: TouchPointListResponse) {

        currentLatLng = LatLng(touchPointListResponse.access_TouchPointList!!.currentLat!!, touchPointListResponse.access_TouchPointList!!.currentLong!!)
        mMap!!.addMarker(MarkerOptions().position(currentLatLng!!).title("current").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)))
        mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12f))
    }*/

    override fun showPlaces(access_ATM: List<Access_ATM>, access_Branch: List<Access_Branch>) {
        var markerATMList = access_ATM
        for(index in 0..markerATMList.size-1){
            atmLatLng = LatLng(markerATMList.get(index).Latitude, markerATMList.get(index).Longitude)
            placeMarker = mMap!!.addMarker(MarkerOptions().position(atmLatLng!!).title(markerATMList.get(index).Location_Name).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)))
        }

        mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(atmLatLng, 16f))

        markerBranchList = access_Branch
        for (index in 0..markerBranchList!!.size-1){
            branchLatLng = LatLng(markerBranchList!!.get(index).Latitude, markerBranchList!!.get(index).Longitude)
            placeMarker = mMap!!.addMarker(MarkerOptions().position(branchLatLng!!).title(markerBranchList!!.get(index).Branch_Name).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)))
        }

        mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(branchLatLng, 16f))

    }

    override fun onMapReady(map: GoogleMap?) {
        mMap = map!!
        presenter.startLoadingTouchList()
        mMap!!.setOnMarkerClickListener(object : GoogleMap.OnMarkerClickListener {
            override fun onMarkerClick(marker: Marker?): Boolean {
                placeMarker = marker
                var indexNo : Int? = null
                for(index in 0 until markerBranchList!!.size-1){
                    var access_Branch = markerBranchList!!.get(index)
                    if((placeMarker!!.title).compareTo(access_Branch.Branch_Name.toString())>1){
                            indexNo = index
                    }
                }
                val intent = Intent(this@MapsActivity, DetailsActivity::class.java)
                intent.putExtra("branchCode", markerBranchList!![indexNo!!].Branch_Code)
                startActivity(intent)
                return true
            }
        })
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
