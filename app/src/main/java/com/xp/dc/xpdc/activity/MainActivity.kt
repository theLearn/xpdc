package com.xp.dc.xpdc.activity

import android.content.Intent
import android.support.v7.app.ActionBar
import android.util.TypedValue
import android.view.View
import com.baidu.mapapi.map.Marker
import com.baidu.mapapi.model.LatLng
import com.baidu.mapapi.search.geocode.*
import com.example.hongcheng.common.base.BaseActivity
import com.example.hongcheng.common.util.ScreenUtils
import com.example.hongcheng.common.util.StringUtils
import com.example.hongcheng.common.util.ViewUtils
import com.xp.dc.xpdc.R
import com.xp.dc.xpdc.application.BaseApplication
import com.xp.dc.xpdc.location.AppLocationUtils
import com.xp.dc.xpdc.location.XPLocation
import kotlinx.android.synthetic.main.activity_map.*
import kotlinx.android.synthetic.main.layout_app_common_title.*


class MainActivity : BaseActivity(), View.OnClickListener, AppLocationUtils.XPLocationListener {

    companion object {
        private const val NOW : Int = 0
        private const val APPOINTMENT : Int = 1
    }

    private var CURRENT_STATE : Int = NOW

    private var lastPosition: XPLocation? = null
    private var curPosMark: Marker? = null          // 位置图标标记

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

        //设置地图状态更改完成的函数
        mv_main.setOnMapStatusChangeFinishListener {
            mv_main.addStartMarker(it.target)
            getPositioninfo(it.target)
        }
    }

    override fun onLocateSuccess(xpLocation: XPLocation?) {
        xpLocation?.let {
            val ll = LatLng(it.lat, it.lon)
            curPosMark = mv_main.updateCurPosMarker(ll, it.direction)
            if (lastPosition == null) {
                mv_main.updateMapCenter(ll)
                val positionStr = if (StringUtils.isEmpty(it.street)) {
                    it.address
                } else {
                    it.street
                }
                tv_current_position.text = positionStr + "附近"
                mv_main.addStartMarker(ll)
            }
            lastPosition = it
        }
    }

    override fun onLocateFailed() {
    }


    private fun getPositioninfo(latLng: LatLng) {
        val geoCoder = GeoCoder.newInstance()
        geoCoder.setOnGetGeoCodeResultListener(object : OnGetGeoCoderResultListener {
            override fun onGetGeoCodeResult(geoCodeResult: GeoCodeResult) {
            }

            override fun onGetReverseGeoCodeResult(reverseGeoCodeResult: ReverseGeoCodeResult) {
                val positionStr = if (StringUtils.isEmpty(reverseGeoCodeResult.addressDetail.street)) {
                    reverseGeoCodeResult.address
                } else {
                    reverseGeoCodeResult.addressDetail.street
                }
                tv_current_position.text = positionStr + "附近"
            }
        })
        geoCoder.reverseGeoCode(ReverseGeoCodeOption().location(latLng))
    }

    override fun onClick(v: View?) {
        if (ViewUtils.isFastClick()) return
        when (v?.id) {
            R.id.tv_app_common_right
            -> {
                startActivity(Intent(this, OrderShowActivity::class.java))
            }
            R.id.tv_call_car_now
            -> {
                if(CURRENT_STATE == APPOINTMENT) {
                    CURRENT_STATE = NOW
                    tv_call_car_now.setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimension(R.dimen.normal_text_size))
                    tv_call_car_now.setBackgroundResource(R.drawable.tv_gray_conner)
                    tv_call_car_pre.setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimension(R.dimen.text_size_13))
                    tv_call_car_pre.setBackgroundDrawable(null)
                }
            }
            R.id.tv_call_car_pre
            -> {
                if(CURRENT_STATE == NOW) {
                    CURRENT_STATE = APPOINTMENT
                    tv_call_car_pre.setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimension(R.dimen.normal_text_size))
                    tv_call_car_pre.setBackgroundResource(R.drawable.tv_gray_conner)
                    tv_call_car_now.setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimension(R.dimen.text_size_13))
                    tv_call_car_now.setBackgroundDrawable(null)
                }
            }
            R.id.ll_start_select
            -> {
                startActivity(Intent(this, AddressSelectActivity::class.java))
            }
            R.id.ll_destination_select
            -> {
                startActivity(Intent(this, AddressSelectActivity::class.java))
            }
            R.id.iv_map_reset
            -> {
                lastPosition?.let {
                    val ll = LatLng(it.lat, it.lon)
                    mv_main.updateMapCenter(ll)
                    curPosMark = mv_main.updateCurPosMarker(ll, it.direction)
                    mv_main.addStartMarker(ll)
                    tv_current_position.text = it.address
                }
            }
            else -> {
            }
        }

    }

    override fun onResume() {
        super.onResume()
        mv_main.onResume()
    }

    override fun onPause() {
        super.onPause()
        mv_main.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mv_main.onDestroy()
    }
}
