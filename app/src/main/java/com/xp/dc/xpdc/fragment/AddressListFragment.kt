package com.xp.dc.xpdc.fragment


import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import com.example.hongcheng.common.base.BasicFragment
import com.xp.dc.xpdc.R
import kotlinx.android.synthetic.main.fragment_address_list.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


class AddressListFragment : BasicFragment() {

    override fun getLayoutResId(): Int {
        return R.layout.fragment_address_list
    }

    override fun initView() {
        val emptyView = LayoutInflater.from(mContext).inflate(R.layout.layout_list_nothing, rootView as ViewGroup, false)
        rv_address_info.layoutManager = LinearLayoutManager(rv_address_info.context)
        rv_address_info.itemAnimator = DefaultItemAnimator()
//        rv_address_info.adapter = mAdapter
        rv_address_info.setEmptyView(emptyView)
//        emptyView.visibility = View.GONE
    }

}
