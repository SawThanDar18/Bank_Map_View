package com.example.bank_map_view.ui.activities

import android.content.Context
import android.support.design.widget.BottomSheetDialog
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import com.example.bank_map_view.R
import com.example.details_design.branch.Branch
import com.example.details_design.branch.BranchAdapter
import kotlinx.android.synthetic.main.bank_list.view.*

class BottomSheetMenu(private val context: Context, private val items: ArrayList<Branch>) {

    private val bottomSheetDialog: BottomSheetDialog = BottomSheetDialog(context)

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.bank_list, null)
        bottomSheetDialog.setContentView(view)
        with(view) {
            branch_recyclerview.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            branch_recyclerview.adapter = BranchAdapter(items)
        }
    }

    fun show() {
        bottomSheetDialog.show()
    }
}