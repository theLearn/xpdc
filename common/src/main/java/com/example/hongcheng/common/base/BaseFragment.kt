package com.example.hongcheng.common.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hongcheng.common.R

/**
 * Created by hongcheng on 17/5/29.
 */
abstract class BaseFragment : BasicFragment() {
    private var titleView: View? = null
    private var messageView: View? = null
    private var bodyView: View? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView: ViewGroup = inflater.inflate(getLayoutResId(), container, false) as ViewGroup
        titleView = getTitleView(inflater, rootView)
        titleView?.let { rootView.addView(it) }
        messageView = getMessageView(inflater, rootView)
        messageView?.let { rootView.addView(it) }
        bodyView = getBodyView(inflater, rootView)
        bodyView?.let { rootView.addView(it) }
        return rootView
    }

    override fun getLayoutResId(): Int {
        return R.layout.base_container
    }

    override fun initView() {
        titleView?.let { initTitleView(it) }
        messageView?.let { initMessageView(it) }
        bodyView?.let { initBodyView(it) }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)

        if (hidden) {
            onPause()
        } else {
            onResume()
        }
    }

    open fun getTitleView(inflater: LayoutInflater, container: ViewGroup?): View? {
        val resId: Int = getTitleLayoutResId()
        return if (resId > 0) inflater.inflate(resId, container, false) else null
    }

    open fun getMessageView(inflater: LayoutInflater, container: ViewGroup?): View? {
        val resId: Int = getMessageLayoutResId()
        return if (resId > 0) inflater.inflate(resId, container, false) else null
    }

    open fun getBodyView(inflater: LayoutInflater, container: ViewGroup?): View? {
        val resId: Int = getBodyLayoutResId()
        return if (resId > 0) inflater.inflate(resId, container, false) else null
    }

    abstract fun getTitleLayoutResId(): Int
    abstract fun getMessageLayoutResId(): Int
    abstract fun getBodyLayoutResId(): Int
    abstract fun initTitleView(view: View)
    abstract fun initMessageView(view: View)
    abstract fun initBodyView(view: View)

    protected fun setMessageViewVisible(isShow: Boolean) {
        messageView?.let { it.visibility = if (isShow) View.VISIBLE else View.GONE }
    }
}