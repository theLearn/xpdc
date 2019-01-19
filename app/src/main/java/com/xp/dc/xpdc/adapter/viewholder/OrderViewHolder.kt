package com.xp.dc.xpdc.adapter.viewholder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.xp.dc.xpdc.R

class OrderViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
    val tvCarType = itemView.findViewById<TextView>(R.id.tv_order_car_type)
    val tvState = itemView.findViewById<TextView>(R.id.tv_order_state)
    val tvTime = itemView.findViewById<TextView>(R.id.tv_order_time)
    val tvStartPosition = itemView.findViewById<TextView>(R.id.tv_order_start_position)
    val tvEndPosition = itemView.findViewById<TextView>(R.id.tv_order_end_position)
}