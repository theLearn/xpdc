package com.example.hongcheng.common.base

import android.support.v7.widget.RecyclerView


abstract class BaseLoadMoreAdapter<T, K : RecyclerView.ViewHolder> : BaseListAdapter<T, K>() {
    private val SHOW_FOOTER_NUM = 10
    protected val TYPE_ITEM = 0
    protected val TYPE_FOOTER = 1

    var isEnableLoadMore: Boolean = false

    override fun getItemCount(): Int {
        if (data.isEmpty()) {
            return 0
        }

        return if (isCanShowFoot()) data.size + 1 else data.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (isCanShowFoot() && position + 1 == itemCount) {
            TYPE_FOOTER
        } else {
            TYPE_ITEM
        }
    }

    fun isCanShowFoot(): Boolean {
        return data.size >= getShowFooterNum()
    }

    fun getShowFooterNum(): Int {
        return SHOW_FOOTER_NUM
    }
}