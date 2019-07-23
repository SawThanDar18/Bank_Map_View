package com.example.bank_map_view.ui.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.GridLayout
import com.example.bank_map_view.R
import com.example.bank_map_view.room.ServicesDatabase
import com.example.bank_map_view.ui.adapter.AvailableServiceAdapter

class AvailableServicesActivity : AppCompatActivity() {

    private lateinit var servicesDatabase: ServicesDatabase

    private lateinit var availableServiceAdapter: AvailableServiceAdapter
    private lateinit var recyclerView : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.available_service_activity)

        servicesDatabase = ServicesDatabase.getDatabase(this)
        servicesDatabase.getServicesDao().getServices()

        recyclerView= findViewById(R.id.recycler_services)
        var layoutManager = GridLayoutManager(this, 1, GridLayout.VERTICAL, false)
        recyclerView.setLayoutManager(layoutManager)
        recyclerView.adapter = availableServiceAdapter
        availableServiceAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(availableServiceAdapter);
    }
}