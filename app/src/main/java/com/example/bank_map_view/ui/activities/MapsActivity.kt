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
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.GridLayout.VERTICAL
import android.widget.Toast
import com.example.bank_branch_details.mvp.presenter.TouchPointListPresenter
import com.example.bank_branch_details.mvp.view.TouchPointListView
import com.example.bank_branch_details.network.DataImpl
import com.example.bank_branch_details.network.model.Access_ATM
import com.example.bank_branch_details.network.model.Access_Branch
import com.example.bank_map_view.network.model.Branch
import com.example.details_design.branch.BranchAdapter
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import kotlinx.android.synthetic.main.bank_list.*
import android.graphics.BitmapFactory.decodeResource as decodeResource1
import android.support.v4.view.MotionEventCompat
import com.example.bank_map_view.R
import kotlinx.android.synthetic.main.a.*
import kotlinx.android.synthetic.main.activity_maps.swipeRefresh

class MapsActivity : AppCompatActivity(), TouchPointListView, OnMapReadyCallback {

    private lateinit var presenter : TouchPointListPresenter

    private var googleMap: GoogleMap? = null

    private var markers : Marker? = null

    private var atmLatLng : LatLng? = null
    private var branchLatLng : LatLng? = null

    private lateinit var markerBranchList : List<Access_Branch>
    private lateinit var markerATMList : List<Access_ATM>

    private lateinit var recyclerview : RecyclerView
    private lateinit var branchAdapter : BranchAdapter

    private var branch : ArrayList<Branch>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.a)

        val behavior = BottomSheetBehavior.from(bottom_sheet)

        behavior.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback(){
            override fun onSlide(bottomSheet: View, state: Float) {
                Log.e("onSlideChanged","onSlideChaned"+state)
                behavior.isHideable = true
            }

            override fun onStateChanged(view: View, newState: Int) {

                when(newState){
                    BottomSheetBehavior.STATE_COLLAPSED -> {

                    }

                    BottomSheetBehavior.STATE_EXPANDED -> {

                    }

                    BottomSheetBehavior.STATE_DRAGGING -> {
                        //BottomSheet dialog
                        //BottomSheetMenu(applicationContext, branch!!).show()

                        //BottomSheet fragment
                        //recyclerview.adapter = branchAdapter

                        behavior.setState(BottomSheetBehavior.STATE_EXPANDED)
                    }

                    BottomSheetBehavior.STATE_HIDDEN -> {
                    }
                }
            }
        })

        behavior.peekHeight = 350
        behavior.isHideable = true

        presenter = TouchPointListPresenter(this)
        presenter.startLoadingTouchList()

        swipeRefresh.setOnRefreshListener {
            dismissLoading()
        }

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map_fragment) as SupportMapFragment
        mapFragment.getMapAsync(this)

        DataImpl.getInstance().getBranchDetail(value = "branchCode")

        branch_btn.setOnClickListener {
            branch_btn.setBackgroundColor(Color.BLUE)
        }
    }

    override fun showBranches(){

        branch = arrayListOf(
        Branch("NAMSANG", "No.3/10,National Road,3rd Quarter,NamSang Township,Shan State"),
        Branch("YGN-111 SANCHAUNG-PHAPONE STREET", "No.49,KYUNTAW ST,PHAPONE ST CORNER,KYUNTAW SOUTH QTR,SANCHAUNG PHAPONE ST,SANCHUANG TSP"),
        Branch("San Chaung Mini", "SANCHAUNG MINI BANK,NO.160/164 GROUND FLOOR,BAHO ROAD,SANCHAUNG."),
        Branch("Nation Mart Sat San", "SAT SAN NATION MART,NO.(315/317),UPPER PAZON TAUNG ROAD,MINGALAR TAUNG NYUNT,YANGON."))

        recyclerview = findViewById(R.id.branch_recyclerview)
        recyclerview.layoutManager = GridLayoutManager(this, 1, VERTICAL, false)
        branchAdapter = BranchAdapter(branch!!)
        recyclerview.adapter = branchAdapter
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
            markers = googleMap!!.addMarker(MarkerOptions().position(atmLatLng!!).title(markerATMList.get(index).Location_Name).icon(bitmapDescriptorFromVector(this, R.drawable.ic_atm_24dp)))
            markers!!.rotation = 20f
            markers!!.tag = markerATMList.get(index).Terminal_ID
        }

        googleMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(atmLatLng, 16f))

        markerBranchList = access_Branch
        for (index in 0 until markerBranchList!!.size){
            branchLatLng = LatLng(markerBranchList!!.get(index).Latitude, markerBranchList!!.get(index).Longitude)
            markers = googleMap!!.addMarker(MarkerOptions().position(branchLatLng!!).title(markerBranchList!!.get(index).Branch_Name).icon(bitmapDescriptorFromVector(this, R.drawable.ic_branch_24dp)))
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
                        val branchIntent = Intent(this@MapsActivity, BranchDetailsActivity::class.java)
                        branchIntent.putExtra("branchCode", markerBranchList!![index].Branch_Code)
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

    private fun bitmapDescriptorFromVector(context: Context, vectorResId : Int) : BitmapDescriptor{
        var vectorDrawable = ContextCompat.getDrawable(context, vectorResId)
        vectorDrawable!!.setBounds(0, 0, vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight)
        var bitmap = Bitmap.createBitmap(vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        var canvas = Canvas(bitmap)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
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
