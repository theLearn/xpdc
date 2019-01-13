package com.example.hongcheng.common.base

import android.support.v7.widget.RecyclerView
import com.example.hongcheng.common.constant.BaseConstants

/**
 * Created by hongcheng on 17/5/29.
 */
abstract class BaseListAdapter<T, K : RecyclerView.ViewHolder>() : RecyclerView.Adapter<K>(){
    var onItemClickListener : OnItemClickListener? = null
    var data : MutableList<T> = arrayListOf()
        set(value) {
            this.data.clear()
            this.data.addAll(value)
        }

    constructor(data : List<T>) : this()
    {
        this.data.clear()
        this.data.addAll(data)
    }

    fun addItem(t : T, isHead : Boolean = false)
    {
        if(isHead) data.add(0, t) else data.add(t)
        removeExceedItem(isHead)
    }

    fun addItem(list : List<T>, isHead : Boolean = false)
    {
        if(isHead) data.addAll(0, list) else data.addAll(list)
        removeExceedItem(isHead)
    }

    /**
     * 根据recyclerview的上限数移除超出的item
     * @param isHead
     */
    private fun removeExceedItem(isHead: Boolean) {
        if (BaseConstants.IS_LIMIT) {
            val exceedCount = data.size - BaseConstants.LIMIT_NUM
            if (exceedCount > 0) {
                for (i in 0..exceedCount - 1) {
                    val removeCount = if (isHead) data.size - 1 else 0
                    data.removeAt(removeCount)
                }
            }
        }
    }

    fun clear()
    {
        this.data.clear()
    }

    fun getItem(position: Int): T = data[position]

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: K, position: Int, payloads: MutableList<Any>) {
        super.onBindViewHolder(holder, position, payloads)
        holder.itemView.setOnClickListener {
            onItemClickListener?.onItemClick(position)
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
        fun onItemLongClick(position: Int)
    }
}