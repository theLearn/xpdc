package com.xp.dc.xpdc.activity

import android.app.Activity
import android.content.Intent
import android.os.Handler
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
import com.example.hongcheng.common.view.fragment.SelectPreTimeFragment
import com.example.hongcheng.common.view.spinkit.SpriteFactory
import com.example.hongcheng.common.view.spinkit.Style
import com.example.hongcheng.common.view.spinkit.sprite.Sprite
import com.example.hongcheng.common.view.spinkit.sprite.SpriteContainer
import com.xp.dc.xpdc.R
import com.xp.dc.xpdc.application.BaseApplication
import com.xp.dc.xpdc.fragment.LoginFragment
import com.xp.dc.xpdc.location.AppLocationUtils
import com.xp.dc.xpdc.location.XPLocation
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_map.*
import kotlinx.android.synthetic.main.layout_app_common_title.*


class MainActivity : BasicActivity(), View.OnClickListener, AppLocationUtils.XPLocationListener {
    companion object {
        private const val LOADING: Int = -1
        private const val NOW: Int = 0
        private const val APPOINTMENT: Int = 1
        private const val SELECT_CAR: Int = 2
        private const val WAIT_ORDER_ACCEPT: Int = 3
        private const val WAIT_CAR: Int = 4
        private const val START_REQUEST: Int = 100
        private const val END_REQUEST: Int = 200
    }

    private var CURRENT_STATE: Int = NOW

    private lateinit var geoCoder : GeoCoder
    private var lastPosition: XPLocation? = null
    private var curPosMark: Marker? = null          // 位置图标标记
    private var isLogin: Boolean = false

    private var mLoginDialog: LoginFragment? = null

    override fun getLayoutResId(): Int {
        return R.layout.activity_main
    }

    override fun initView() {
        geoCoder = GeoCoder.newInstance()
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

        val style: Style = Style.values()[7]
        val drawable: Sprite = SpriteFactory.create(style)
        (drawable as SpriteContainer).color = resources.getColor(R.color.colorBase)
        sk_call_query_load.setIndeterminateDrawable(drawable)

        AppLocationUtils.getInstance().init(BaseApplication.getInstance())
        AppLocationUtils.getInstance().startLocate(this)

        //设置地图状态更改完成的函数
        mv_main.setOnMapStatusChangeFinishListener {
            if(CURRENT_STATE == NOW || CURRENT_STATE == APPOINTMENT) {
                mv_main.drawSEToMap(it.target, null)
                getAddressInfo(it.target)
            }
        }

        cc_main.setOnCallListener {
            order()
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
                mv_main.drawSEToMap(ll, null)
                getAddressInfo(ll)
            }
            lastPosition = it
        }
    }

    override fun onLocateFailed() {
    }


    private fun getAddressInfo(latLng: LatLng) {
        geoCoder = GeoCoder.newInstance()
        geoCoder.setOnGetGeoCodeResultListener(object : OnGetGeoCoderResultListener {
            override fun onGetGeoCodeResult(geoCodeResult: GeoCodeResult) {
            }

            override fun onGetReverseGeoCodeResult(reverseGeoCodeResult : ReverseGeoCodeResult) {
                if(StringUtils.isEmpty(reverseGeoCodeResult.addressDetail?.city)) return

                val sb = StringBuilder()
                val poiList = reverseGeoCodeResult.poiList
                if (poiList != null && !poiList.isEmpty()) {
                    sb.append(poiList[0].name)
                } else if (!StringUtils.isEmpty(reverseGeoCodeResult.addressDetail?.street)) {
                    sb.append(reverseGeoCodeResult.addressDetail.street)
                } else if (!StringUtils.isEmpty(reverseGeoCodeResult.addressDetail?.town)) {
                    sb.append(reverseGeoCodeResult.addressDetail?.district)
                        .append(reverseGeoCodeResult.addressDetail?.town)
                } else {
                    sb.append(reverseGeoCodeResult.addressDetail?.city)
                        .append(reverseGeoCodeResult.addressDetail?.district)
                }
                tv_current_position.text = sb.toString()

                val xpLocation = XPLocation()
                xpLocation.lat = latLng.latitude
                xpLocation.lon = latLng.longitude
                xpLocation.province = reverseGeoCodeResult.addressDetail?.province
                xpLocation.city = reverseGeoCodeResult.addressDetail?.city
                xpLocation.district = reverseGeoCodeResult.addressDetail?.district
                xpLocation.street = reverseGeoCodeResult.addressDetail?.street
                xpLocation.address = reverseGeoCodeResult.address
                xpLocation.name = reverseGeoCodeResult.address
                AppLocationUtils.getInstance().startLocation = xpLocation
            }
        })
        geoCoder.reverseGeoCode(ReverseGeoCodeOption().location(latLng))
    }

    override fun onClick(v: View?) {
        if (ViewUtils.isFastClick()) return
        when (v?.id) {
            R.id.ll_nv_main_head_edit
            -> {
                startActivity(Intent(this, UserCenterActivity::class.java))
                dl_main.closeDrawers()
            }
            R.id.ll_nv_main_head_order -> {
                startActivity(Intent(this, OrderShowActivity::class.java))
                dl_main.closeDrawers()
            }

            R.id.ll_nv_main_head_edit_pw -> {
                startActivity(Intent(this, PasswordActivity::class.java))
                dl_main.closeDrawers()
            }

            R.id.ll_nv_main_head_about -> {
                dl_main.closeDrawers()
            }

            R.id.tv_app_common_right
            -> {
                if (isLogin)
                    startActivity(Intent(this, OrderShowActivity::class.java))
                else {
                    if (mLoginDialog == null) {
                        mLoginDialog = LoginFragment()
                    }
                    mLoginDialog?.show(supportFragmentManager, "LoginFragment")
                }
            }
            R.id.tv_call_car_now
            -> {
                changeView(NOW)
            }
            R.id.tv_call_car_pre
            -> {
                val selectPreTimeFragment = SelectPreTimeFragment()
                selectPreTimeFragment.setOnClickListener(object : SelectPreTimeFragment.OnSelectListener {
                    override fun onSure(time: Long, timeStr : String): Boolean {
                        changeView(APPOINTMENT)
                        tv_call_car_pre.text = timeStr
                        return false
                    }

                    override fun onCancel(): Boolean {
                        return false
                    }
                })

                selectPreTimeFragment.show(supportFragmentManager, "selectPreTimeFragment")
            }
            R.id.ll_start_select
            -> {
                val startIntent = Intent(this, AddressSelectActivity::class.java)
                startIntent.putExtra("type", AddressSelectActivity.START)
                startActivityForResult(startIntent, START_REQUEST)
            }
            R.id.ll_destination_select
            -> {
                val endIntent = Intent(this, AddressSelectActivity::class.java)
                endIntent.putExtra("type", AddressSelectActivity.END)
                startActivityForResult(endIntent, END_REQUEST)
            }
            R.id.iv_map_reset
            -> {
                resetMap()
            }
            else -> {
            }
        }
    }

    private fun resetMap() {
        lastPosition?.let {
            val ll = LatLng(it.lat, it.lon)
            curPosMark = mv_main.updateCurPosMarker(ll, it.direction)
            mv_main.updateMapCenter(ll)
            if(CURRENT_STATE == NOW || CURRENT_STATE == APPOINTMENT) {
                mv_main.drawSEToMap(ll, null)
                getAddressInfo(ll)
            }
        }
    }

    private fun changeView(state : Int) {
        if(CURRENT_STATE == state) return
        sk_call_query_load.visibility = View.GONE
        cc_main.visibility = View.GONE
        wc_main.visibility = View.GONE
        ll_block_select_position.visibility = View.GONE
        when (state) {
            NOW
            -> {
                ll_block_select_position.visibility = View.VISIBLE
                tv_call_car_now.setTextSize(
                    TypedValue.COMPLEX_UNIT_PX,
                    resources.getDimension(R.dimen.normal_text_size)
                )
                tv_call_car_now.setTextColor(resources.getColor(R.color.colorBase))
                tv_call_car_pre.setTextColor(resources.getColor(R.color.text_gray_call))
                tv_call_car_now.setBackgroundResource(R.drawable.tv_gray_conner)
                tv_call_car_pre.setTextSize(
                    TypedValue.COMPLEX_UNIT_PX,
                    resources.getDimension(R.dimen.text_size_13)
                )
                tv_call_car_pre.setBackgroundDrawable(null)
                tv_call_car_pre.setText(R.string.call_car_pre)
            }
            APPOINTMENT
            -> {
                ll_block_select_position.visibility = View.VISIBLE
                tv_call_car_pre.setTextSize(
                    TypedValue.COMPLEX_UNIT_PX,
                    resources.getDimension(R.dimen.normal_text_size)
                )
                tv_call_car_pre.setTextColor(resources.getColor(R.color.colorBase))
                tv_call_car_now.setTextColor(resources.getColor(R.color.text_gray_call))
                tv_call_car_pre.setBackgroundResource(R.drawable.tv_gray_conner)
                tv_call_car_now.setTextSize(
                    TypedValue.COMPLEX_UNIT_PX,
                    resources.getDimension(R.dimen.text_size_13)
                )
                tv_call_car_now.setBackgroundDrawable(null)
            }
            SELECT_CAR
            -> {
                showPointInMap()
                tv_app_common_title.setText(R.string.main_title_call_confirm)
                cc_main.visibility = View.VISIBLE
            }
            WAIT_ORDER_ACCEPT
            -> {
                tv_app_common_title.setText(R.string.main_title_calling)
                wc_main.visibility = View.VISIBLE
            }
            WAIT_CAR
            -> {
            }
            LOADING
            -> {
                sk_call_query_load.visibility = View.VISIBLE
            }
            else -> {
            }
        }

        CURRENT_STATE = state
    }

    private fun showPointInMap() {
        val startPosition = AppLocationUtils.getInstance().startLocation
        val endPosition = AppLocationUtils.getInstance().endLocation
        mv_main.drawSEToMap(LatLng(startPosition.lat, startPosition.lon), LatLng(endPosition.lat, endPosition.lon))
    }

    private fun calculate() {
        changeView(LOADING)
        Handler().postDelayed({ changeView(SELECT_CAR) }, 2000)
    }

    private fun order() {
        changeView(LOADING)
        Handler().postDelayed({ changeView(WAIT_ORDER_ACCEPT) }, 2000)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (Activity.RESULT_OK == resultCode) {
            when (requestCode) {
                START_REQUEST
                -> {
                    val startLocation = AppLocationUtils.getInstance().startLocation
                    tv_current_position.text = startLocation.name
                    mv_main.drawSEToMap(LatLng(startLocation.lat, startLocation.lon), null)
                }
                END_REQUEST
                -> {
                    calculate()
                }
                else -> {
                }
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
        geoCoder.destroy()
    }

    override fun onBackPressed() {
        when (CURRENT_STATE) {
            SELECT_CAR
            -> {
                changeView(NOW)
                resetMap()
            }
            else -> {
                moveTaskToBack(true)
            }
        }
    }
}
