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

    private var atmMarker : Marker? = null
    private var branchMarker : Marker? = null

    private var currentLatLng : LatLng? = null
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

    /*override fun showCurrentLocation(touchPointListResponse: TouchPointListResponse) {

        currentLatLng = LatLng(touchPointListResponse.access_TouchPointList!!.currentLat!!, touchPointListResponse.access_TouchPointList!!.currentLong!!)
        mMap!!.addMarker(MarkerOptions().position(currentLatLng!!).title("current").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)))
        mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12f))
    }*/

    override fun showPlaces(access_ATM: List<Access_ATM>, access_Branch: List<Access_Branch>) {
        markerATMList = access_ATM
        for(index in 0 until markerATMList.size){
            atmLatLng = LatLng(markerATMList.get(index).Latitude!!, markerATMList.get(index).Longitude!!)
            atmMarker = googleMap!!.addMarker(MarkerOptions().position(atmLatLng!!).title(markerATMList.get(index).Location_Name).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)))
            atmMarker!!.rotation = 20f
        }

        googleMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(atmLatLng, 16f))

        markerBranchList = access_Branch
        for (index in 0 until markerBranchList!!.size){
            branchLatLng = LatLng(markerBranchList!!.get(index).Latitude, markerBranchList!!.get(index).Longitude)
            branchMarker = googleMap!!.addMarker(MarkerOptions().position(branchLatLng!!).title(markerBranchList!!.get(index).Branch_Name).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)))
            branchMarker!!.rotation = -20f
        }

        googleMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(branchLatLng, 16f))
 }

    override fun onMapReady(map: GoogleMap?) {
        googleMap = map!!
        presenter.startLoadingTouchList()

        googleMap!!.setOnMarkerClickListener(object : GoogleMap.OnMarkerClickListener {
            override fun onMarkerClick(marker: Marker?): Boolean {

                branchMarker = marker
                var branchIndex: Int? = null
                for (index in 0 until markerBranchList!!.size) {
                    var access_Branch = markerBranchList!!.get(index)
                    if (branchMarker!!.title.compareTo(access_Branch.Branch_Name.toString()) > -1) {
                        branchIndex = index
                    }
                }
                val branchIntent = Intent(this@MapsActivity, BranchDetailsActivity::class.java)
                branchIntent.putExtra("branchCode", markerBranchList!![branchIndex!!].Branch_Code)
                startActivity(branchIntent)

                atmMarker = marker
                var atmIndex: Int? = null
                for (index in 0 until markerATMList!!.size) {
                    var access_ATM = markerATMList!!.get(index)
                    if (branchMarker!!.title.compareTo(access_ATM.Location_Name.toString()) > -1) {
                        atmIndex = index
                    }
                }
                val atmIntent = Intent(this@MapsActivity, ATMDetailsActivity::class.java)
                atmIntent.putExtra("Location_Name", markerATMList[atmIndex!!].Location_Name)
                atmIntent.putExtra("Address", markerATMList[atmIndex!!].Address)
                startActivity(atmIntent)
                BranchDetailsActivity().finish()


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

        /*atmMap!!.setOnMarkerClickListener(object : GoogleMap.OnMarkerClickListener{
            override fun onMarkerClick(marker: Marker?): Boolean {
                atmMarker = marker
                var atmIndex: Int? = null
                for (index in 0 until markerATMList!!.size) {
                    var access_ATM = markerATMList!!.get(index)
                    if (branchMarker!!.title.compareTo(access_ATM.Location_Name.toString()) > -1) {
                        atmIndex = index
                    }
                }
                val intent = Intent(this@MapsActivity, ATMDetailsActivity::class.java)
                intent.putExtra("Location_Name", markerATMList[atmIndex!!].Location_Name)
                intent.putExtra("Address", markerATMList[atmIndex!!].Address)
                startActivity(intent)
                return true
            }

        })*/
                    /*atmMarker = marker
                    var atmIndex: Int? = null
                    for (index in 0 until markerBranchList!!.size) {
                        var access_Branch = markerBranchList!!.get(index)
                        if (branchMarker!!.title.compareTo(access_Branch.Branch_Name.toString()) > -1) {
                            atmIndex = index
                        }
                    }
                    val intent = Intent(this@MapsActivity, ATMDetailsActivity::class.java)
                    intent.putExtra("Location_Name", markerATMList[atmIndex!!].Location_Name)
                    intent.putExtra("Address", markerATMList[atmIndex!!].Address)
                    startActivity(intent)
                }
                return true
            }
        })
    }*/


