package com.xp.dc.xpdc.adapter.viewholder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.xp.dc.xpdc.R

class SearchAddressViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
    val tvName = itemView.findViewById<TextView>(R.id.tv_search_address_name)
    val tvDes = itemView.findViewById<TextView>(R.id.tv_search_address_des)
    val ivType = itemView.findViewById<ImageView>(R.id.iv_search_address_type)
}