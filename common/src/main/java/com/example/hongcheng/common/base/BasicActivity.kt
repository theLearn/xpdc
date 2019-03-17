package com.example.hongcheng.common.base

import android.Manifest
import android.content.pm.PackageManager
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import com.example.hongcheng.common.rx.RxUtils
import com.example.hongcheng.common.util.LoggerUtils
import com.example.hongcheng.common.view.fragment.LoadingFragment
import io.reactivex.disposables.CompositeDisposable
import java.lang.Exception

abstract class BasicActivity : AppCompatActivity(), CommonUI {

    protected lateinit var compositeDisposable: CompositeDisposable

    private var mLoadingDialog: LoadingFragment? = null
    protected var binding: ViewDataBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        compositeDisposable = RxUtils.getCompositeDisposable(this.javaClass)
        setContentView(getLayoutResId(), isNeedBind())
        initView()
    }

    override fun onDestroy() {
        super.onDestroy()
        RxUtils.unsubscribe(compositeDisposable)
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

        mLoadingDialog?.let {
            val isShow = it.dialog !=null && it.dialog.isShowing
            try {
                if(!isOpen) {
                    it.dismiss()
                } else if(!it.isAdded && !isShow) {
                    it.show(supportFragmentManager, "LoadingFragment")
                }
            } catch (e : Exception) {
                it.dismiss()
                LoggerUtils.error("exception", e.message)
            }
        }
    }

    private val REQUEST_PERMISSIONS_NEED= 1
    private val PERMISSIONS_NEED = arrayListOf<String>(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.READ_PHONE_STATE,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE)
    private val needPermissions = arrayListOf<String>()
    private val needRequestPermission = arrayListOf<String>()

    open fun getNeedPermission() : ArrayList<String>?
    {
        return null
    }

    fun verifyPermissions() {
        needPermissions.addAll(PERMISSIONS_NEED)
        getNeedPermission()?.let { needPermissions.addAll(it) }

        for (item in needPermissions)
        {
            val state = ActivityCompat.checkSelfPermission(this, item)
            if (state != PackageManager.PERMISSION_GRANTED){
                needRequestPermission.add(item)
            }
        }

        if (0 < needRequestPermission.size) {
            ActivityCompat.requestPermissions(this, needRequestPermission.toTypedArray(), REQUEST_PERMISSIONS_NEED)
        } else {
            requestPermission(true)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        needPermissions.let{
            for(item in it)
            {
                // Check if we have write permission
                val state = ActivityCompat.checkSelfPermission(this, item)
                if (state != PackageManager.PERMISSION_GRANTED){
                    requestPermission(false)
                    return
                }
            }
            requestPermission(true)
        }
    }

    open fun requestPermission(isSuccess : Boolean){}
}