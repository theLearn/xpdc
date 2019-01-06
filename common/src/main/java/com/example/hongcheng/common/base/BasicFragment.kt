package com.example.hongcheng.common.base

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by hongcheng on 17/5/29.
 */
abstract class BasicFragment : Fragment(), CommonUI {

    protected lateinit var mContext: Context
    protected lateinit var rootView: View

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(getLayoutResId(), container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    abstract fun initView()

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)

        if (hidden) {
            onPause()
        } else {
            onResume()
        }
    }

    override fun operateLoadingDialog(isOpen: Boolean) {
        if (mContext is CommonUI) (mContext as CommonUI).operateLoadingDialog(isOpen)
    }

    abstract fun getLayoutResId(): Int
}