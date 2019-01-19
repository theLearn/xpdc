package com.xp.dc.xpdc.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.hongcheng.common.base.BaseListAdapter
import com.xp.dc.xpdc.R
import com.xp.dc.xpdc.adapter.viewholder.WaitCarTypeViewHolder
import com.xp.dc.xpdc.bean.CallCarInfo

class WaitCarTypeAdapter : BaseListAdapter<CallCarInfo, WaitCarTypeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WaitCarTypeViewHolder {
        return WaitCarTypeViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_wait_car_info, parent, false))
    }

    override fun onBindViewHolder(holder: WaitCarTypeViewHolder, position: Int) {
        val model = data[position]
        holder.tvType.text = model.carType
    }
}