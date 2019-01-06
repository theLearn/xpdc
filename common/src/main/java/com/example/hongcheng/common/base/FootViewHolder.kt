package com.example.hongcheng.common.base

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.example.hongcheng.common.R


class FootViewHolder(private var view: View) : RecyclerView.ViewHolder(view) {
    var content: TextView
    var pb: View

    init {
        pb = view.findViewById(R.id.progressBar)
        content = view.findViewById(R.id.tv_load_more_tip)
    }
}