package com.example.bank_map_view.ui.activities

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.design.widget.BottomSheetBehavior
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.GridLayout
import android.widget.Toast
import com.example.bank_branch_details.network.model.Access_Branch
import com.example.bank_map_view.R
import com.example.bank_map_view.mvp.presenter.NearestCurrencyExchangePresenter
import com.example.bank_map_view.mvp.view.NearestCurrencyExchangeView
import com.example.bank_map_view.network.BranchItemClickListener
import com.example.bank_map_view.network.response.NearestCurrencyExchangeResponse
import com.example.details_design.branch.BranchAdapter
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import kotlinx.android.synthetic.main.bank_list.*
import kotlinx.android.synthetic.main.neaestexchange_activity.*

class NearestExchangeActivity : AppCompatActivity(), NearestCurrencyExchangeView, OnMapReadyCallback {

    private lateinit var presenter : NearestCurrencyExchangePresenter

    private lateinit var branchAdapter: BranchAdapter
    private lateinit var recyclerView: RecyclerView

    private lateinit var progressDialog : ProgressDialog

    private var behavior : BottomSheetBehavior<ConstraintLayout>? = null

    private var googleMap : GoogleMap? = null
    private var markers : Marker? = null
    private var branchLatLng : LatLng? = null
    private lateinit var markerBranchList : ArrayList<Access_Branch>

    private lateinit var bundle: Bundle

    private var value : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.neaestexchange_activity)

        behavior = BottomSheetBehavior.from(bottom_sheet)
        behavior!!.peekHeight = 370
        behavior!!.isHideable = false

        progressDialog =  ProgressDialog(this)
        progressDialog.setMessage("loading")
        progressDialog.setCancelable(false)

        bundle = intent!!.extras
        value = bundle.getString("serviceCode")

        presenter = NearestCurrencyExchangePresenter(this)
        presenter.startLoadingNearestExchangeDetails(value!!)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map_fragment) as SupportMapFragment
        mapFragment.getMapAsync(this)

        back_exchange_iv.setOnClickListener {
            this.finish()
        }

        refresh_exchange_iv.setOnClickListener {
            presenter.startLoadingNearestExchangeDetails(value!!)
        }
    }

    override fun showNearestCurrencyExchange(nearestCurrencyExchangeResponse: NearestCurrencyExchangeResponse) {

        recyclerView = findViewById(R.id.bank_recyclerview)
        branchAdapter = BranchAdapter(this, object : BranchItemClickListener {
            override fun onClicked(id: String) {
                val intent = Intent(applicationContext, BranchDetailsActivity::class.java)
                intent.putExtra("branchCode", id)
                startActivity(intent)
            }
        })

        var layoutManager = GridLayoutManager(this, 1, GridLayout.VERTICAL, false)
        recyclerView.setLayoutManager(layoutManager)
        branchAdapter.setNewData(nearestCurrencyExchangeResponse.access_Branch!!)
        recyclerView.adapter = branchAdapter
    }

    override fun showPlaces(nearestCurrencyExchangeResponse: NearestCurrencyExchangeResponse) {

        progressDialog.dismiss()
        markerBranchList = nearestCurrencyExchangeResponse.access_Branch!!
        if(markerBranchList.size>0) {
            for (index in 0 until markerBranchList.size) {
                branchLatLng = LatLng(markerBranchList.get(index).Latitude!!, markerBranchList.get(index).Longitude!!)
                markers = googleMap!!.addMarker(
                    MarkerOptions().position(branchLatLng!!).title(markerBranchList.get(index).Branch_Name)
                        .snippet(markerBranchList.get(index).TouchPointType)
                        .icon(bitmapDescriptorFromVector(this, R.drawable.ic_branch_24dp)))
                markers!!.rotation = -20f
                markers!!.tag = markerBranchList.get(index).Branch_Code
            }
            googleMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(branchLatLng, 13f))
        }
    }

    override fun onMapReady(map: GoogleMap?) {

        googleMap = map!!

        googleMap!!.setOnInfoWindowClickListener(object : GoogleMap.OnInfoWindowClickListener {
            override fun onInfoWindowClick(marker: Marker?) {

                if (markerBranchList.size > 0) {
                    for (index in 0 until markerBranchList.size) {
                        markers = marker
                        if (markers!!.tag == markerBranchList.get(index).Branch_Code) {
                            val branchIntent = Intent(this@NearestExchangeActivity, BranchDetailsActivity::class.java)
                            branchIntent.putExtra("branchCode", markerBranchList[index].Branch_Code)
                            startActivity(branchIntent)
                        }
                    }
                }
            }
        })
    }

    //for bitmap icon
    private fun bitmapDescriptorFromVector(context: Context, vectorResId : Int) : BitmapDescriptor {
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
        progressDialog.show()
    }

    override fun dismissLoading() {
        progressDialog.dismiss()
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
