package com.xp.dc.xpdc.adapter.viewholder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.xp.dc.xpdc.R

class WaitCarTypeViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
    val tvType = itemView.findViewById<TextView>(R.id.tv_wait_car_type_tip)
    val tvTip = itemView.findViewById<TextView>(R.id.tv_wait_time_tip)
    val ivType = itemView.findViewById<ImageView>(R.id.iv_wait_car_type)
}