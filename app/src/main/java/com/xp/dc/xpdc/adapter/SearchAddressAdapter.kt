package com.xp.dc.xpdc.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.hongcheng.common.base.BaseListAdapter
import com.xp.dc.xpdc.R
import com.xp.dc.xpdc.adapter.viewholder.SearchAddressViewHolder
import com.xp.dc.xpdc.location.XPLocation

class SearchAddressAdapter : BaseListAdapter<XPLocation, SearchAddressViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchAddressViewHolder {
        return SearchAddressViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_search_address, parent, false))
    }

    override fun onBindViewHolder(holder: SearchAddressViewHolder, position: Int) {
        val model = data[position]
        holder.tvName.text = model.address
        holder.tvDes.text = model.des
    }
}