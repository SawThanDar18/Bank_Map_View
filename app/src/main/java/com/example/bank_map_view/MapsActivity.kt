package com.example.bank_map_view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.bank_branch_details.mvp.presenter.TouchPointListPresenter
import com.example.bank_branch_details.mvp.view.TouchPointListView
import com.example.bank_branch_details.network.model.Access_ATM
import com.example.bank_branch_details.network.model.Access_Branch
import com.example.bank_branch_details.network.response.TouchPointListResponse

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_maps.*

class MapsActivity : AppCompatActivity(), TouchPointListView, OnMapReadyCallback {

    private lateinit var presenter : TouchPointListPresenter

    private lateinit var mMap: GoogleMap

    private var currentLatLng : LatLng? = null
    private var atmLatLng : LatLng? = null
    private var branchLatLng : LatLng? = null

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

    override fun showCurrentLocation(touchPointListResponse: TouchPointListResponse) {

        currentLatLng = LatLng(touchPointListResponse.access_TouchPointList!!.currentLat!!, touchPointListResponse.access_TouchPointList!!.currentLong!!)
        mMap.addMarker(MarkerOptions().position(currentLatLng!!).title("current").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12f))
    }

    override fun showATMList(touchPointListResponse: TouchPointListResponse) {

        var markerATMList = ArrayList<Access_ATM>()
        for(index in 0..markerATMList.size-1){
            atmLatLng = LatLng(markerATMList.get(index).Latitude, markerATMList.get(index).Longitude)
            mMap!!.addMarker(MarkerOptions().position(atmLatLng!!).title(markerATMList.get(index).Address).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)))
        }

        mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(atmLatLng, 12f))
    }

    override fun showBranchList(touchPointListResponse: TouchPointListResponse) {

        var markerBranchList = ArrayList<Access_Branch>()
        for (index in 0..markerBranchList.size-1){
            branchLatLng = LatLng(markerBranchList.get(index).Latitude, markerBranchList.get(index).Longitude)
            mMap!!.addMarker(MarkerOptions().position(branchLatLng!!).title(markerBranchList.get(index).Address).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)))
        }

        mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(branchLatLng, 12f))
    }

    override fun onMapReady(map: GoogleMap?) {
        mMap = map!!
        presenter.startLoadingTouchList()
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
