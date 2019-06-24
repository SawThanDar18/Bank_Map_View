package com.example.bank_map_view.ui.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.bank_branch_details.mvp.presenter.TouchPointListPresenter
import com.example.bank_branch_details.mvp.view.TouchPointListView
import com.example.bank_branch_details.network.DataImpl
import com.example.bank_branch_details.network.model.Access_ATM
import com.example.bank_branch_details.network.model.Access_Branch
import com.example.bank_map_view.R
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

    private var googleMap: GoogleMap? = null

    private var markers : Marker? = null

    private var atmLatLng : LatLng? = null
    private var branchLatLng : LatLng? = null

    private lateinit var markerBranchList : List<Access_Branch>
    private lateinit var markerATMList : List<Access_ATM>

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

        DataImpl.getInstance().getBranchDetail(value = "branchCode")
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

    override fun showPlaces(access_ATM: List<Access_ATM>, access_Branch: List<Access_Branch>) {
        markerATMList = access_ATM
        for(index in 0 until markerATMList.size){
            atmLatLng = LatLng(markerATMList.get(index).Latitude!!, markerATMList.get(index).Longitude!!)
            markers = googleMap!!.addMarker(MarkerOptions().position(atmLatLng!!).title(markerATMList.get(index).Location_Name).snippet(markerATMList.get(index).Terminal_ID).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)))
            markers!!.rotation = 20f
            markers!!.tag = markerATMList.get(index).Terminal_ID
        }

        googleMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(atmLatLng, 16f))

        markerBranchList = access_Branch
        for (index in 0 until markerBranchList!!.size){
            branchLatLng = LatLng(markerBranchList!!.get(index).Latitude, markerBranchList!!.get(index).Longitude)
            markers = googleMap!!.addMarker(MarkerOptions().position(branchLatLng!!).title(markerBranchList!!.get(index).Branch_Name).snippet(markerBranchList!!.get(index).Branch_Code).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)))
            markers!!.rotation = -20f
            markers!!.tag = markerBranchList.get(index).Branch_Code
        }

        googleMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(branchLatLng, 16f))
 }

    override fun onMapReady(map: GoogleMap?) {
        googleMap = map!!
        presenter.startLoadingTouchList()

        googleMap!!.setOnInfoWindowClickListener(object : GoogleMap.OnInfoWindowClickListener {
            override fun onInfoWindowClick(marker: Marker?) {

                markers = marker

                for(index in 0 until markerATMList.size){
                    markers = marker
                    if(markers!!.tag == markerATMList.get(index).Terminal_ID){
                        val atmIntent = Intent(this@MapsActivity, ATMDetailsActivity::class.java)
                        atmIntent.putExtra("Location_Name", markerATMList[index!!].Location_Name)
                        atmIntent.putExtra("Address", markerATMList[index!!].Address)
                        atmIntent.putExtra("Latitude", markerATMList[index!!].Latitude)
                        atmIntent.putExtra("Longitude", markerATMList[index!!].Longitude)
                        startActivity(atmIntent)
                    }
                }

                for(index in 0 until markerBranchList.size){
                    markers = marker
                    if(markers!!.tag == markerBranchList.get(index).Branch_Code){
                        val branchIntent = Intent(this@MapsActivity, BranchDetailsActivity::class.java)
                        branchIntent.putExtra("branchCode", markerBranchList!![index!!].Branch_Code)
                        startActivity(branchIntent)
                    }
                }
            }
        })

        googleMap!!.setOnMarkerClickListener(object : GoogleMap.OnMarkerClickListener {
            override fun onMarkerClick(marker: Marker?): Boolean {

                return false
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
