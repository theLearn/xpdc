package com.xp.dc.xpdc.activity

import android.support.v7.app.ActionBar
import android.util.TypedValue
import android.view.View
import com.baidu.mapapi.map.Marker
import com.baidu.mapapi.model.LatLng
import com.example.hongcheng.common.base.BaseActivity
import com.example.hongcheng.common.util.ScreenUtils
import com.example.hongcheng.common.util.ViewUtils
import com.xp.dc.xpdc.R
import com.xp.dc.xpdc.application.BaseApplication
import com.xp.dc.xpdc.location.AppLocationUtils
import com.xp.dc.xpdc.location.XPLocation
import com.xp.dc.xpdc.location.XPLocationOrientationListener
import kotlinx.android.synthetic.main.activity_map.*
import kotlinx.android.synthetic.main.layout_app_common_title.*


class MainActivity : BaseActivity(), View.OnClickListener, AppLocationUtils.XPLocationListener {

    private var centerLatLng: LatLng? = null
    private var curPosMark: Marker? = null          // 位置图标标记
    private var orientationListener: XPLocationOrientationListener? = null          // 位置图标标记
    // 传感器记录的当前方向
    private var mDirection: Float = 0.toFloat()

    override fun getTitleLayoutResId(): Int {
        return R.layout.layout_app_common_title
    }

    override fun initTitleView(view: View) {
        setSupportActionBar(tb_app_common)
        ScreenUtils.setWindowStatusBarColor(this, resources.getColor(R.color.white))
        ScreenUtils.setLightStatusBar(this, true)
        tb_app_common.setNavigationIcon(R.mipmap.icon_user)
        tb_app_common.setNavigationOnClickListener { }
        val actionBar: ActionBar? = supportActionBar
        actionBar?.title = ""
        tv_app_common_right.visibility = View.VISIBLE
    }

    override fun getMessageLayoutResId(): Int {
        return 0
    }

    override fun initMessageView(view: View) {
    }

    override fun getBodyLayoutResId(): Int {
        return R.layout.activity_map
    }


    override fun initBodyView(view: View) {
        tv_call_car_pre.setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimension(R.dimen.text_size_13))
        tv_call_car_pre.setBackgroundDrawable(null)

        tv_app_common_right.setOnClickListener(this)
        tv_call_car_now.setOnClickListener(this)
        tv_call_car_pre.setOnClickListener(this)
        ll_start_select.setOnClickListener(this)
        ll_destination_select.setOnClickListener(this)
        iv_map_reset.setOnClickListener(this)

        AppLocationUtils.getInstance().init(BaseApplication.getInstance())
        AppLocationUtils.getInstance().startLocate(this)

        //设置位置传感器的监听事件
        orientationListener = XPLocationOrientationListener(this)
        orientationListener?.setOnOrientationListener { direction ->
            mDirection = direction
            curPosMark?.rotate = 360 - mDirection
        }
        //设置地图状态更改完成的函数
        mv_main.setOnMapStatusChangeFinishListener { }
    }

    override fun onLocateSuccess(xpLocation: XPLocation?) {
        xpLocation?.let {
            centerLatLng = LatLng(it.lat, it.lon)
            mv_main.updateMapCenter(centerLatLng)
            curPosMark = mv_main.updateCurPosMarker(centerLatLng, mDirection)
        }
    }

    override fun onLocateFailed() {
    }

    override fun onClick(v: View?) {
        if (ViewUtils.isFastClick()) return
        when (v?.id) {
            R.id.tv_app_common_right
            -> {
            }
            R.id.tv_call_car_now
            -> {
            }
            R.id.tv_call_car_pre
            -> {
            }
            R.id.ll_start_select
            -> {
            }
            R.id.ll_destination_select
            -> {
            }
            R.id.iv_map_reset
            -> {
                if (centerLatLng != null) {
                    mv_main.updateMapCenter(centerLatLng)
                    curPosMark = mv_main.updateCurPosMarker(centerLatLng, mDirection)
                }
            }
            else -> {
            }
        }

    }

    override fun onResume() {
        super.onResume()
        mv_main.onResume()
        orientationListener?.start()
    }

    override fun onPause() {
        super.onPause()
        mv_main.onPause()
        orientationListener?.stop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mv_main.onDestroy()
    }
}
