package com.example.hongcheng.common.base

import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * Created by hongcheng on 17/5/29.
 */
open class BaseBindingViewHolder<T : ViewDataBinding>(itemView : View) : RecyclerView.ViewHolder(itemView){
    var binding: T? = null
}