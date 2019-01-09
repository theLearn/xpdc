package com.example.hongcheng.common.base

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import com.example.hongcheng.common.view.fragment.LoadingFragment

abstract class BasicActivity : AppCompatActivity(), CommonUI {

    private var mLoadingDialog: LoadingFragment? = null
    protected var binding: ViewDataBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResId(), isNeedBind())
        initView()
    }

    private fun setContentView(layoutResID: Int, isNeedBind : Boolean) {
        if(isNeedBind) {
            binding = DataBindingUtil.setContentView(this, layoutResID)
        } else {
            setContentView(layoutResID)
        }
    }

    open fun isNeedBind() : Boolean = false

    abstract fun initView()
    abstract fun getLayoutResId(): Int

    override fun operateLoadingDialog(isOpen: Boolean) {
        if (mLoadingDialog == null) {
            mLoadingDialog = LoadingFragment()
        }

        mLoadingDialog?.let { if (isOpen) it.show(supportFragmentManager, "LoadingFragment") else it.dismiss() }
    }
}