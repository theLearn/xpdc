package com.xp.dc.xpdc.activity

import android.content.Intent
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBar
import android.util.TypedValue
import android.view.MenuItem
import android.view.View
import com.baidu.mapapi.map.Marker
import com.baidu.mapapi.model.LatLng
import com.baidu.mapapi.search.geocode.*
import com.example.hongcheng.common.base.BasicActivity
import com.example.hongcheng.common.util.ScreenUtils
import com.example.hongcheng.common.util.StringUtils
import com.example.hongcheng.common.util.ViewUtils
import com.xp.dc.xpdc.R
import com.xp.dc.xpdc.application.BaseApplication
import com.xp.dc.xpdc.location.AppLocationUtils
import com.xp.dc.xpdc.location.XPLocation
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_map.*
import kotlinx.android.synthetic.main.layout_app_common_title.*


class MainActivity : BasicActivity(), View.OnClickListener, AppLocationUtils.XPLocationListener {
    companion object {
        private const val NOW: Int = 0
        private const val APPOINTMENT: Int = 1
    }

    private var CURRENT_STATE: Int = NOW

    private var lastPosition: XPLocation? = null
    private var curPosMark: Marker? = null          // 位置图标标记

    override fun getLayoutResId(): Int {
        return R.layout.activity_main
    }

    override fun initView() {
        setSupportActionBar(tb_app_common)
        ScreenUtils.setWindowStatusBarColor(this, resources.getColor(R.color.white))
        ScreenUtils.setLightStatusBar(this, true)
        val actionBar: ActionBar? = supportActionBar
        actionBar?.title = ""
        tv_app_common_right.visibility = View.VISIBLE
        actionBar?.setHomeAsUpIndicator(R.mipmap.icon_user)
        actionBar?.setDisplayHomeAsUpEnabled(true)

        tv_call_car_pre.setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimension(R.dimen.text_size_13))
        tv_call_car_pre.setBackgroundDrawable(null)

        nv_main.getHeaderView(0).findViewById<View>(R.id.ll_nv_main_head_edit).setOnClickListener(this)
        nv_main.getHeaderView(0).findViewById<View>(R.id.ll_nv_main_head_order).setOnClickListener(this)
        nv_main.getHeaderView(0).findViewById<View>(R.id.ll_nv_main_head_edit_pw).setOnClickListener(this)
        nv_main.getHeaderView(0).findViewById<View>(R.id.ll_nv_main_head_about).setOnClickListener(this)
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
            getAddressInfo(it.target)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> dl_main.openDrawer(GravityCompat.START)
            else -> {
            }
        }
        return true
    }

    override fun onLocateSuccess(xpLocation: XPLocation?) {
        xpLocation?.let {
            val ll = LatLng(it.lat, it.lon)
            curPosMark = mv_main.updateCurPosMarker(ll, it.direction)
            if (lastPosition == null) {
                mv_main.updateMapCenter(ll)
                mv_main.addStartMarker(ll)
                getAddressInfo(ll)
            }
            lastPosition = it
        }
    }

    override fun onLocateFailed() {
    }


    private fun getAddressInfo(latLng: LatLng) {
        val geoCoder = GeoCoder.newInstance()
        geoCoder.setOnGetGeoCodeResultListener(object : OnGetGeoCoderResultListener {
            override fun onGetGeoCodeResult(geoCodeResult: GeoCodeResult) {
            }

            override fun onGetReverseGeoCodeResult(reverseGeoCodeResult: ReverseGeoCodeResult) {
                val sb = StringBuilder()
                val poiList = reverseGeoCodeResult.poiList
                if (poiList != null && !poiList.isEmpty()) {
                    sb.append(poiList[0].name)
                } else if (!StringUtils.isEmpty(reverseGeoCodeResult.addressDetail.street)) {
                    sb.append(reverseGeoCodeResult.addressDetail.street)
                } else if (!StringUtils.isEmpty(reverseGeoCodeResult.addressDetail.town)) {
                    sb.append(reverseGeoCodeResult.addressDetail.district)
                        .append(reverseGeoCodeResult.addressDetail.town)
                } else {
                    sb.append(reverseGeoCodeResult.addressDetail.city)
                        .append(reverseGeoCodeResult.addressDetail.district)
                }
                tv_current_position.text = sb.toString()
            }
        })
        geoCoder.reverseGeoCode(ReverseGeoCodeOption().location(latLng))
    }

    override fun onClick(v: View?) {
        if (ViewUtils.isFastClick()) return
        when (v?.id) {
            R.id.ll_nv_main_head_edit
            -> {
                dl_main.closeDrawers()
            }
            R.id.ll_nv_main_head_order -> {
                startActivity(Intent(this, OrderShowActivity::class.java))
                dl_main.closeDrawers()
            }

            R.id.ll_nv_main_head_edit_pw -> {
                dl_main.closeDrawers()
            }

            R.id.ll_nv_main_head_about -> {
                dl_main.closeDrawers()
            }

            R.id.tv_app_common_right
            -> {
                startActivity(Intent(this, OrderShowActivity::class.java))
            }
            R.id.tv_call_car_now
            -> {
                if (CURRENT_STATE == APPOINTMENT) {
                    CURRENT_STATE = NOW
                    tv_call_car_now.setTextSize(
                        TypedValue.COMPLEX_UNIT_PX,
                        resources.getDimension(R.dimen.normal_text_size)
                    )
                    tv_call_car_now.setBackgroundResource(R.drawable.tv_gray_conner)
                    tv_call_car_pre.setTextSize(
                        TypedValue.COMPLEX_UNIT_PX,
                        resources.getDimension(R.dimen.text_size_13)
                    )
                    tv_call_car_pre.setBackgroundDrawable(null)
                }
            }
            R.id.tv_call_car_pre
            -> {
                if (CURRENT_STATE == NOW) {
                    CURRENT_STATE = APPOINTMENT
                    tv_call_car_pre.setTextSize(
                        TypedValue.COMPLEX_UNIT_PX,
                        resources.getDimension(R.dimen.normal_text_size)
                    )
                    tv_call_car_pre.setBackgroundResource(R.drawable.tv_gray_conner)
                    tv_call_car_now.setTextSize(
                        TypedValue.COMPLEX_UNIT_PX,
                        resources.getDimension(R.dimen.text_size_13)
                    )
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
                    getAddressInfo(ll)
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
