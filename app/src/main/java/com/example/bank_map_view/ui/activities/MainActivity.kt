package com.example.bank_map_view.ui.activities

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.design.widget.BottomSheetBehavior
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.*
import com.example.bank_branch_details.mvp.presenter.TouchPointListPresenter
import com.example.bank_branch_details.mvp.view.TouchPointListView
import com.example.bank_branch_details.network.DataImpl
import com.example.bank_branch_details.network.model.Access_ATM
import com.example.bank_branch_details.network.model.Access_Branch
import com.example.bank_map_view.R
import com.example.bank_map_view.mvp.presenter.CurrencyPresenter
import com.example.bank_map_view.mvp.presenter.ServicePresenter
import com.example.bank_map_view.mvp.view.CurrencyView
import com.example.bank_map_view.mvp.view.ServiceView
import com.example.bank_map_view.network.ItemClickListener
import com.example.bank_map_view.network.BranchItemClickListener
import com.example.bank_map_view.network.model.Access_Agent
import com.example.bank_map_view.network.model.Access_Merchant
import com.example.bank_map_view.network.model.Currency
import com.example.bank_map_view.network.response.CurrencyResponse
import com.example.bank_map_view.network.response.ServiceResponse
import com.example.details_design.branch.BranchAdapter
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import android.graphics.BitmapFactory.decodeResource as decodeResource1
import com.example.bank_map_view.ui.adapter.ATMAdapter
import com.example.bank_map_view.ui.adapter.AgentAdapter
import com.example.bank_map_view.ui.adapter.ServiceAdapter
import com.example.bank_map_view.ui.adapter.MerchantAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.bank_list.bottom_sheet
import kotlinx.android.synthetic.main.currency.*
import kotlinx.android.synthetic.main.currency.view.*

class MainActivity : AppCompatActivity(), TouchPointListView, CurrencyView, ServiceView, OnMapReadyCallback {

    private lateinit var presenter : TouchPointListPresenter
    private lateinit var currencyPresenter : CurrencyPresenter
    private lateinit var servicePresenter: ServicePresenter

    private lateinit var progressDialog : ProgressDialog

    private var behavior : BottomSheetBehavior<ConstraintLayout>? = null
    private  var currecy_behavior : BottomSheetBehavior<ConstraintLayout>? = null

    private var googleMap : GoogleMap? = null

    private var markers : Marker? = null

    private var atmLatLng : LatLng? = null
    private var branchLatLng : LatLng? = null
    private var agentLatLng : LatLng? = null
    private var merchantLatLng : LatLng? = null

    private lateinit var markerBranchList : ArrayList<Access_Branch>
    private lateinit var markerATMList : ArrayList<Access_ATM>
    private lateinit var markerAgentList : ArrayList<Access_Agent>
    private lateinit var markerMerchantList : ArrayList<Access_Merchant>

    private var recyclerview : RecyclerView? = null
    private var service_recyclerView : RecyclerView? = null

    private lateinit var branchAdapter : BranchAdapter
    private lateinit var atmAdapter: ATMAdapter
    private lateinit var agentAdapter: AgentAdapter
    private lateinit var merchantAdapter : MerchantAdapter
    private lateinit var serviceAdapter: ServiceAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        behavior = BottomSheetBehavior.from(bottom_sheet)
        behavior!!.peekHeight = 370
        behavior!!.isHideable = false

        currecy_behavior = BottomSheetBehavior.from(bottom_sheet_currency)
        currecy_behavior!!.peekHeight = 370
        currecy_behavior!!.isHideable = false

        /* //calculate screen size
         val display : Display = getWindowManager().getDefaultDisplay()
         val size = Point()
         display.getSize(size)
         val height : Int = size.y*/

        /*behavior!!.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback(){
            override fun onSlide(p0: View, p1: Float) {

            }

            override fun onStateChanged(view: View, newState: Int) {
                when(newState){

                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        bottom_sheet.cardView.visibility = View.GONE
                    }

                    BottomSheetBehavior.STATE_EXPANDED -> {
                        bottom_sheet.cardView.visibility = View.VISIBLE
                    }
                }
            }
        })*/

        currecy_behavior!!.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback(){
            override fun onSlide(p0: View, p1: Float) {

            }

            override fun onStateChanged(view: View, newState: Int) {
                when(newState){

                    BottomSheetBehavior.STATE_EXPANDED -> {
                        bottom_sheet_currency.cardView.visibility = View.VISIBLE
                    }

                    BottomSheetBehavior.STATE_DRAGGING -> {
                        bottom_sheet_currency.cardView.visibility = View.GONE
                    }
                }
            }
        })

        progressDialog =  ProgressDialog(this)
        progressDialog.setMessage("loading")
        progressDialog.setCancelable(false)

        presenter = TouchPointListPresenter(this)
        presenter.startLoadingTouchList()

        currencyPresenter = CurrencyPresenter(this)
        currencyPresenter.startLoadingCurrencyDetails()

        servicePresenter = ServicePresenter(this)
        servicePresenter.startLoadingServiceList()

        DataImpl.getInstance().getBranchDetail(value = "branchCode")

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map_fragment) as SupportMapFragment
        mapFragment.getMapAsync(this)


        //for activity_main layout
        val search_tv = findViewById<TextView>(R.id.search_tv)
        search_tv.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }

        val refresh = findViewById<ImageView>(R.id.refresh_iv)
        refresh.setOnClickListener {

            presenter.startLoadingTouchList()
            bottom_sheet_currency.visibility = View.VISIBLE

            behavior!!.state = BottomSheetBehavior.STATE_COLLAPSED
            currecy_behavior!!.state = BottomSheetBehavior.STATE_COLLAPSED

            branch_btn.setTextColor(Color.DKGRAY)
            branch_btn.setBackgroundResource(R.drawable.unselected_button_shape)

            atm_btn.setTextColor(Color.DKGRAY)
            atm_btn.setBackgroundResource(R.drawable.unselected_button_shape)

            merchant_btn.setTextColor(Color.DKGRAY)
            merchant_btn.setBackgroundResource(R.drawable.unselected_button_shape)

            agent_btn.setTextColor(Color.DKGRAY)
            agent_btn.setBackgroundResource(R.drawable.unselected_button_shape)
        }

        //for currency layout
        val search_tv_currency = findViewById<TextView>(R.id.search_currency)
        search_tv_currency.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }

        val refresh_iv_currency = findViewById<ImageView>(R.id.refresh_iv_currency)
        refresh_iv_currency.setOnClickListener {

            presenter.startLoadingTouchList()
            bottom_sheet_currency.visibility = View.VISIBLE
            bottom_sheet_currency.cardView.visibility = View.GONE

            currecy_behavior!!.state = BottomSheetBehavior.STATE_COLLAPSED
            behavior!!.state = BottomSheetBehavior.STATE_COLLAPSED

            branch_btn.setTextColor(Color.DKGRAY)
            branch_btn.setBackgroundResource(R.drawable.unselected_button_shape)

            atm_btn.setTextColor(Color.DKGRAY)
            atm_btn.setBackgroundResource(R.drawable.unselected_button_shape)

            merchant_btn.setTextColor(Color.DKGRAY)
            merchant_btn.setBackgroundResource(R.drawable.unselected_button_shape)

            agent_btn.setTextColor(Color.DKGRAY)
            agent_btn.setBackgroundResource(R.drawable.unselected_button_shape)

        }

        /*//for bank_list_layout
        val search_tv_list = findViewById<TextView>(R.id.search_tv_list)
        search_tv_list.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }

        val refresh_iv_list = findViewById<ImageView>(R.id.refresh_iv_list)
        refresh_iv_list.setOnClickListener {
            presenter.startLoadingTouchList()
            bottom_sheet_currency.visibility = View.VISIBLE

            currecy_behavior!!.state = BottomSheetBehavior.STATE_COLLAPSED
            behavior!!.state = BottomSheetBehavior.STATE_COLLAPSED

            branch_btn.setTextColor(Color.DKGRAY)
            branch_btn.setBackgroundResource(R.drawable.unselected_button_shape)

            atm_btn.setTextColor(Color.DKGRAY)
            atm_btn.setBackgroundResource(R.drawable.unselected_button_shape)

            merchant_btn.setTextColor(Color.DKGRAY)
            merchant_btn.setBackgroundResource(R.drawable.unselected_button_shape)

            agent_btn.setTextColor(Color.DKGRAY)
            agent_btn.setBackgroundResource(R.drawable.unselected_button_shape)
        }*/

        bottom_sheet_currency.constraint.setOnClickListener {
            val intent = Intent(this, NearestExchangeActivity::class.java)
            intent.putExtra("serviceCode", "Current_EUR_USD_SGD")
            startActivity(intent)
        }

        branch_btn.setOnClickListener {

            behavior!!.state = BottomSheetBehavior.STATE_COLLAPSED

            branch_btn.setTextColor(Color.WHITE)
            branch_btn.setBackgroundResource(R.drawable.selected_button_shape)

            atm_btn.setTextColor(Color.DKGRAY)
            atm_btn.setBackgroundResource(R.drawable.unselected_button_shape)

            merchant_btn.setTextColor(Color.DKGRAY)
            merchant_btn.setBackgroundResource(R.drawable.unselected_button_shape)

            agent_btn.setTextColor(Color.DKGRAY)
            agent_btn.setBackgroundResource(R.drawable.unselected_button_shape)

            if(markerATMList.size>0) {
                googleMap!!.clear()
                for (index in 0 until markerBranchList.size) {
                    branchLatLng = LatLng(markerBranchList.get(index).Latitude!!, markerBranchList.get(index).Longitude!!)
                    markers = googleMap!!.addMarker(MarkerOptions().position(branchLatLng!!)
                                                                   .title(markerBranchList
                                                                   .get(index).Branch_Name)
                                                                   .snippet(markerBranchList.get(index).TouchPointType)
                                                                   .icon(bitmapDescriptorFromVector(this, R.drawable.ic_branch_24dp)))

                    markers!!.rotation = -20f
                    markers!!.tag = markerBranchList.get(index).Branch_Code
                }
            }

            googleMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(branchLatLng, 14f))

            bottom_sheet_currency.visibility = View.GONE
            bottom_sheet.visibility = View.VISIBLE

            recyclerview!!.adapter = branchAdapter

        }

        atm_btn.setOnClickListener {

            behavior!!.state = BottomSheetBehavior.STATE_COLLAPSED

            atm_btn.setTextColor(Color.WHITE)
            atm_btn.setBackgroundResource(R.drawable.selected_button_shape)

            branch_btn.setTextColor(Color.DKGRAY)
            branch_btn.setBackgroundResource(R.drawable.unselected_button_shape)

            merchant_btn.setTextColor(Color.DKGRAY)
            merchant_btn.setBackgroundResource(R.drawable.unselected_button_shape)

            agent_btn.setTextColor(Color.DKGRAY)
            agent_btn.setBackgroundResource(R.drawable.unselected_button_shape)

            if(markerBranchList.size>0){
                googleMap!!.clear()
            for(index in 0 until markerATMList.size){
                atmLatLng = LatLng(markerATMList.get(index).Latitude!!, markerATMList.get(index).Longitude!!)
                markers = googleMap!!.addMarker(MarkerOptions().position(atmLatLng!!)
                                                               .title(markerATMList
                                                               .get(index).Location_Name)
                                                               .snippet(markerATMList.get(index).TouchPointType)
                                                               .icon(bitmapDescriptorFromVector(this, R.drawable.ic_atm_24dp)))

                markers!!.rotation = 20f
                markers!!.tag = markerATMList.get(index).Terminal_ID
                }
            }

            googleMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(atmLatLng, 14f))

            bottom_sheet_currency.visibility = View.GONE
            bottom_sheet.visibility = View.VISIBLE

            recyclerview!!.adapter = atmAdapter
        }

        merchant_btn.setOnClickListener {

            behavior!!.state = BottomSheetBehavior.STATE_COLLAPSED

            merchant_btn.setTextColor(Color.WHITE)
            merchant_btn.setBackgroundResource(R.drawable.selected_button_shape)

            branch_btn.setTextColor(Color.DKGRAY)
            branch_btn.setBackgroundResource(R.drawable.unselected_button_shape)

            atm_btn.setTextColor(Color.DKGRAY)
            atm_btn.setBackgroundResource(R.drawable.unselected_button_shape)

            agent_btn.setTextColor(Color.DKGRAY)
            agent_btn.setBackgroundResource(R.drawable.unselected_button_shape)

            googleMap!!.clear()

            bottom_sheet_currency.visibility = View.GONE
            bottom_sheet.visibility = View.VISIBLE

            recyclerview!!.adapter = merchantAdapter
        }

        agent_btn.setOnClickListener {

            behavior!!.state = BottomSheetBehavior.STATE_COLLAPSED

            agent_btn.setTextColor(Color.WHITE)
            agent_btn.setBackgroundResource(R.drawable.selected_button_shape)

            branch_btn.setTextColor(Color.DKGRAY)
            branch_btn.setBackgroundResource(R.drawable.unselected_button_shape)

            atm_btn.setTextColor(Color.DKGRAY)
            atm_btn.setBackgroundResource(R.drawable.unselected_button_shape)

            merchant_btn.setTextColor(Color.DKGRAY)
            merchant_btn.setBackgroundResource(R.drawable.unselected_button_shape)

            googleMap!!.clear()

            bottom_sheet_currency.visibility = View.GONE
            bottom_sheet.visibility = View.VISIBLE

            recyclerview!!.adapter = agentAdapter
        }
    }

    override fun displayBranch(access_Branch: ArrayList<Access_Branch>) {

        googleMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(branchLatLng, 14f))

        recyclerview = findViewById(R.id.bank_recyclerview)
        branchAdapter = BranchAdapter(this, object : BranchItemClickListener{
            override fun onClicked(id: String) {
                val intent = Intent(applicationContext, BranchDetailsActivity::class.java)
                intent.putExtra("branchCode", id)
                startActivity(intent)
            }
        })

        var layoutManager = GridLayoutManager(this, 1, GridLayout.VERTICAL, false)
        recyclerview!!.setLayoutManager(layoutManager)
        branchAdapter.setNewData(access_Branch)
    }

    override fun displayATM(access_ATM: ArrayList<Access_ATM>) {

        recyclerview = findViewById(R.id.bank_recyclerview)
        atmAdapter = ATMAdapter(this, object : ItemClickListener{
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
        recyclerview!!.setLayoutManager(layoutManager)
        atmAdapter.setNewData(access_ATM)
    }

    override fun displayAgent(access_Agent: ArrayList<Access_Agent>) {

        markerAgentList = access_Agent
        recyclerview = findViewById(R.id.bank_recyclerview)
        agentAdapter = AgentAdapter(this, object : ItemClickListener{
            override fun onClicked(name: String, address: String, latitude: Double, Longitude: Double) {
                val intent = Intent(applicationContext, AgentDetailActivity::class.java)
                intent.putExtra("Agent_Name",name)
                intent.putExtra("Address", address)
                intent.putExtra("Latitude", latitude)
                intent.putExtra("Longitude", Longitude)
                startActivity(intent)
            }
        })

        var layoutManager = GridLayoutManager(this, 1, GridLayout.VERTICAL, false)
        recyclerview!!.setLayoutManager(layoutManager)
        agentAdapter.setNewData(access_Agent)
    }

    override fun displayMerchant(access_Merchant: ArrayList<Access_Merchant>) {
        markerMerchantList = access_Merchant
        recyclerview = findViewById(R.id.bank_recyclerview)
        merchantAdapter = MerchantAdapter(this, object : ItemClickListener{
            override fun onClicked(name: String, address: String, latitude: Double, Longitude: Double) {
                val intent = Intent(applicationContext, MerchantDetailActivity::class.java)
                intent.putExtra("Merchant_Name",name)
                intent.putExtra("Address", address)
                intent.putExtra("Latitude", latitude)
                intent.putExtra("Longitude", Longitude)
                startActivity(intent)
            }
        })

        var layoutManager = GridLayoutManager(this, 1, GridLayout.VERTICAL, false)
        recyclerview!!.setLayoutManager(layoutManager)
        merchantAdapter.setNewData(access_Merchant)
    }

    override fun showPlaces(access_ATM: ArrayList<Access_ATM>, access_Branch: ArrayList<Access_Branch>, access_Agent: ArrayList<Access_Agent>, access_Merchant: ArrayList<Access_Merchant>) {

        progressDialog.dismiss()

        markerATMList = access_ATM
        if(markerATMList.size>0) {
            for (index in 0 until markerATMList.size) {
                atmLatLng = LatLng(markerATMList.get(index).Latitude!!, markerATMList.get(index).Longitude!!)
                markers = googleMap!!.addMarker(
                    MarkerOptions().position(atmLatLng!!).title(markerATMList.get(index).Location_Name).snippet(markerATMList.get(index).TouchPointType)
                        .icon(bitmapDescriptorFromVector(this, R.drawable.ic_atm_24dp))
                )
                markers!!.rotation = 20f
                markers!!.tag = markerATMList.get(index).Terminal_ID
            }
            googleMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(atmLatLng, 14f))
        }

        markerBranchList = access_Branch
        if(markerBranchList.size>0) {
            for (index in 0 until markerBranchList.size) {
                branchLatLng = LatLng(markerBranchList.get(index).Latitude!!, markerBranchList.get(index).Longitude!!)
                markers = googleMap!!.addMarker(
                    MarkerOptions().position(branchLatLng!!).title(markerBranchList.get(index).Branch_Name).snippet(markerBranchList.get(index).TouchPointType)
                        .icon(bitmapDescriptorFromVector(this, R.drawable.ic_branch_24dp))
                )
                markers!!.rotation = -20f
                markers!!.tag = markerBranchList.get(index).Branch_Code
            }
            googleMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(branchLatLng, 14f))
        }

        markerAgentList = access_Agent
        if(markerAgentList.size>0) {
            for (index in 0 until markerAgentList.size) {
                agentLatLng = LatLng(markerAgentList.get(index).latitude!!, markerAgentList.get(index).longitude!!)
                markers = googleMap!!.addMarker(
                    MarkerOptions().position(agentLatLng!!).title(markerAgentList.get(index).agent_name)
                        .icon(bitmapDescriptorFromVector(this, R.drawable.ic_agent_24dp)))
                markers!!.rotation = 20f
                markers!!.tag = markerAgentList.get(index).agent_id
            }
            googleMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(agentLatLng, 14f))
        }

        markerMerchantList = access_Merchant
        if(markerMerchantList.size>0) {
            for (index in 0 until markerMerchantList.size) {
                merchantLatLng = LatLng(markerMerchantList.get(index).latitude!!, markerMerchantList.get(index).longitude!!)
                markers = googleMap!!.addMarker(
                    MarkerOptions().position(merchantLatLng!!).title(markerMerchantList.get(index).merchant_name).icon(
                        bitmapDescriptorFromVector(this, R.drawable.ic_merchant_24dp)
                    )
                )
                markers!!.rotation = 20f
                markers!!.tag = markerMerchantList.get(index).merchant_id
            }
            googleMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(merchantLatLng, 14f))
        }

        bottom_sheet.visibility = View.GONE
    }

    override fun showCurrencyDetails(currencyResponse: CurrencyResponse) {

            var currencyDenominationList = arrayListOf<Currency>()
            for (currencyData in currencyResponse.currency!!) {
                val splitDenomination = currencyData.denomination!!.split(",")
                for (denomination in splitDenomination) {
                    if (denomination.contentEquals("100")) {
                        currencyDenominationList.add(currencyData)

                        for (index in 0 until currencyDenominationList.size) {
                            if (currencyDenominationList[index].currecyCode == "USD") {
                                buy_usd_tv.text = currencyDenominationList[index].buy_rate
                                sell_usd_tv.text = currencyDenominationList[index].sell_rate
                            }

                            if (currencyDenominationList[index].currecyCode == "SGD") {
                                buy_sgd_tv.text = currencyDenominationList[index].buy_rate
                                sell_sgd_tv.text = currencyDenominationList[index].sell_rate
                            }

                            if (currencyDenominationList[index].currecyCode == "EUR") {
                                buy_eur_tv.text = currencyDenominationList[index].buy_rate
                                sell_eur_tv.text = currencyDenominationList[index].sell_rate
                            }

                            if (currencyDenominationList[index].currecyCode == "THB") {
                                buy_thb_tv.text = currencyDenominationList[index].buy_rate
                                sell_thb_tv.text = currencyDenominationList[index].sell_rate
                            }
                        }
                    }
                }
            }
    }

    override fun showServiceList(serviceResponse: ServiceResponse) {


        service_recyclerView = findViewById(R.id.service_recycler)
        serviceAdapter = ServiceAdapter(this, object : BranchItemClickListener{
            override fun onClicked(id: String) {
                val intent = Intent(applicationContext, ServiceDetailActivity::class.java)
                intent.putExtra("service_code",id)
                startActivity(intent)
            }
        })

        var layoutManager = GridLayoutManager(this, 1, GridLayout.VERTICAL, false)
        service_recyclerView!!.setLayoutManager(layoutManager)
        serviceAdapter.setNewData(serviceResponse.service_List!!)
        service_recyclerView!!.adapter = serviceAdapter
    }

    override fun onMapReady(map: GoogleMap?) {

        googleMap = map!!

        googleMap!!.setOnInfoWindowClickListener(object : GoogleMap.OnInfoWindowClickListener {
            override fun onInfoWindowClick(marker: Marker?) {

                if(markerATMList.size>0) {
                    for (index in 0 until markerATMList.size) {
                        markers = marker
                        if (markers!!.tag == markerATMList.get(index).Terminal_ID) {
                            val atmIntent = Intent(this@MainActivity, ATMDetailsActivity::class.java)
                            atmIntent.putExtra("Location_Name", markerATMList[index].Location_Name)
                            atmIntent.putExtra("Address", markerATMList[index].Address)
                            atmIntent.putExtra("Latitude", markerATMList[index].Latitude)
                            atmIntent.putExtra("Longitude", markerATMList[index].Longitude)
                            startActivity(atmIntent)
                        }
                    }
                }

                if(markerBranchList.size>0) {
                    for (index in 0 until markerBranchList.size) {
                        markers = marker
                        if (markers!!.tag == markerBranchList.get(index).Branch_Code) {
                            val branchIntent = Intent(this@MainActivity, BranchDetailsActivity::class.java)
                            branchIntent.putExtra("branchCode", markerBranchList[index].Branch_Code)
                            startActivity(branchIntent)
                        }
                    }
                }

                if(markerAgentList.size>0) {
                    for (index in 0 until markerAgentList.size) {
                        markers = marker
                        if (markers!!.tag == markerAgentList.get(index).agent_id) {
                            val agentIntent = Intent(this@MainActivity, AgentDetailActivity::class.java)
                            agentIntent.putExtra("Agent_Name", markerAgentList[index].agent_name)
                            agentIntent.putExtra("Address", markerAgentList[index].agent_address)
                            agentIntent.putExtra("Latitude", markerAgentList[index].latitude)
                            agentIntent.putExtra("Longitude", markerAgentList[index].longitude)
                            startActivity(agentIntent)
                        }
                    }
                }

                if(markerMerchantList.size>0) {
                    for (index in 0 until markerMerchantList.size) {
                        markers = marker
                        if (markers!!.tag == markerMerchantList.get(index).merchant_id) {
                            val merchantIntent = Intent(this@MainActivity, MerchantDetailActivity::class.java)
                            merchantIntent.putExtra("Merchant_Name", markerMerchantList[index].merchant_name)
                            merchantIntent.putExtra("Address", markerMerchantList[index].merchant_address)
                            merchantIntent.putExtra("Latitude", markerMerchantList[index].latitude)
                            merchantIntent.putExtra("Longitude", markerMerchantList[index].longitude)
                            startActivity(merchantIntent)
                        }
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
        progressDialog.show()
    }

    override fun dismissLoading() {
        progressDialog.dismiss()
    }

    override fun onBackPressed() {

         if(bottom_sheet_currency.visibility == View.GONE){

            progressDialog.dismiss()

            bottom_sheet_currency.visibility = View.VISIBLE
            behavior!!.state = BottomSheetBehavior.STATE_COLLAPSED
            currecy_behavior!!.state = BottomSheetBehavior.STATE_COLLAPSED

            presenter.startLoadingTouchList()

            branch_btn.setTextColor(Color.DKGRAY)
            branch_btn.setBackgroundResource(R.drawable.unselected_button_shape)

            atm_btn.setTextColor(Color.DKGRAY)
            atm_btn.setBackgroundResource(R.drawable.unselected_button_shape)

            merchant_btn.setTextColor(Color.DKGRAY)
            merchant_btn.setBackgroundResource(R.drawable.unselected_button_shape)

            agent_btn.setTextColor(Color.DKGRAY)
            agent_btn.setBackgroundResource(R.drawable.unselected_button_shape)

        } else if(currecy_behavior!!.state == BottomSheetBehavior.STATE_EXPANDED) {
             bottom_sheet_currency.cardView.visibility = View.GONE
             currecy_behavior!!.state = BottomSheetBehavior.STATE_COLLAPSED

         } else if(currecy_behavior!!.state == BottomSheetBehavior.STATE_COLLAPSED) {
             super.onBackPressed()
                this.finish()
            }
        }

    override fun onStart() {
        super.onStart()
        presenter.onStart()
        currencyPresenter.onStart()
        servicePresenter.onStart()
    }

    override fun onStop() {
        super.onStop()
        presenter.onStop()
        currencyPresenter.onStop()
        servicePresenter.onStop()
    }
}
