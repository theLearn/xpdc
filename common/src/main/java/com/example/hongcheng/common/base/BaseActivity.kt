package com.example.hongcheng.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hongcheng.common.R

abstract class BaseActivity : BasicActivity() {
    private var titleView: View? = null
    private var messageView: View? = null
    private var bodyView: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflater: LayoutInflater = LayoutInflater.from(this)
        val rootView: ViewGroup = inflater.inflate(getLayoutResId(), null, false) as ViewGroup

        setContentView(rootView)
        titleView = getTitleView(inflater, rootView)
        titleView?.let {
            rootView.addView(it)
            initTitleView(it)
        }
        messageView = getMessageView(inflater, rootView)
        messageView?.let {
            rootView.addView(it)
            initMessageView(it)
        }
        bodyView = getBodyView(inflater, rootView)
        bodyView?.let {
            rootView.addView(it)
            initBodyView(it)
        }
    }

    override fun getLayoutResId(): Int {
        return R.layout.base_container
    }

    override fun initView() {
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