package com.xp.dc.xpdc.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.hongcheng.common.base.BaseListAdapter
import com.example.hongcheng.common.util.DateUtils
import com.xp.dc.xpdc.R
import com.xp.dc.xpdc.adapter.viewholder.OrderViewHolder
import com.xp.dc.xpdc.bean.OrderInfo

class OrderAdapter : BaseListAdapter<OrderInfo, OrderViewHolder>() {
    override fun onBaseCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        return OrderViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_order, parent, false))
    }

    override fun onBaseBindViewHolder(holder: OrderViewHolder, position: Int) {
        val model = data[position]
        holder.tvCarType.text = model.driverInfo?.carType
//        holder.tvState.text = model.address
        holder.tvTime.text = DateUtils.getTime(model.time)
        holder.tvStartPosition.text = model.startPosition.name
        holder.tvEndPosition.text = model.endPosition.name
    }
}