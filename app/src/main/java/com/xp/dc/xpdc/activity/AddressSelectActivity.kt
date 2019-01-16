package com.xp.dc.xpdc.activity

import android.app.Activity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.baidu.mapapi.search.core.PoiInfo
import com.baidu.mapapi.search.poi.*
import com.example.hongcheng.common.base.BaseListAdapter
import com.example.hongcheng.common.base.BasicActivity
import com.example.hongcheng.common.util.ScreenUtils
import com.example.hongcheng.common.util.StringUtils
import com.example.hongcheng.common.view.DividerItemDecoration
import com.example.hongcheng.common.view.DividerItemDecoration.HORIZONTAL_LIST
import com.example.hongcheng.common.view.searchview.ICallBack
import com.example.hongcheng.common.view.searchview.SearchView
import com.xp.dc.xpdc.R
import com.xp.dc.xpdc.adapter.SearchAddressAdapter
import com.xp.dc.xpdc.location.AppLocationUtils
import com.xp.dc.xpdc.location.XPLocation
import kotlinx.android.synthetic.main.layout_address_search_list.*
import kotlinx.android.synthetic.main.layout_address_search_title.*


class AddressSelectActivity : BasicActivity() {
    companion object {
        public const val START: Int = 0
        public const val END: Int = 1
    }

    private lateinit var svCity: SearchView
    private lateinit var svAddress: SearchView

    private lateinit var addressAdapter: SearchAddressAdapter

    private var type: Int = START
    private lateinit var startLocation: XPLocation

    private lateinit var poiSearch: PoiSearch

    private var listener: OnGetPoiSearchResultListener = object : OnGetPoiSearchResultListener {
        override fun onGetPoiResult(poiResult: PoiResult) {
            addressAdapter.data = getPoiListInfo(poiResult.allPoi)
            addressAdapter.notifyDataSetChanged()
        }

        override fun onGetPoiDetailResult(poiDetailSearchResult: PoiDetailSearchResult) {

        }

        override fun onGetPoiIndoorResult(poiIndoorResult: PoiIndoorResult) {

        }

        //废弃
        override fun onGetPoiDetailResult(poiDetailResult: PoiDetailResult) {

        }
    }

    private fun getPoiListInfo(poiList: List<PoiInfo>?): MutableList<XPLocation> {
        val xpLocationList = arrayListOf<XPLocation>()
        poiList?.let {
            for (poi: PoiInfo in it) {
                val xpLocation = XPLocation()
                xpLocation.lat = poi.location.latitude
                xpLocation.lon = poi.location.longitude
                xpLocation.province = poi.province
                xpLocation.city = poi.city
                xpLocation.address = poi.address
                xpLocation.name = poi.name
                xpLocationList.add(xpLocation)
            }
        }

        return xpLocationList
    }

    override fun getLayoutResId(): Int {
        return R.layout.activity_address_select
    }

    override fun initView() {
        initData()

        ScreenUtils.setWindowStatusBarColor(this, resources.getColor(R.color.white))
        ScreenUtils.setLightStatusBar(this, true)
        tv_city_show.text = startLocation.city

        ll_block_city_show.setOnClickListener {
            ll_block_city_show.visibility = View.GONE
            svCity.visibility = View.VISIBLE
            cs_city_search_list.visibility = View.VISIBLE
            rv_address_search_list.visibility = View.GONE
            svCity.requestFocus()
        }

        tv_address_select_cancel.setOnClickListener {
            finish()
        }

        svCity = findViewById(R.id.sv_city)
        svCity.setOnSearchListener(object : ICallBack {
            override fun queryData(string: String?) {
            }

            override fun onFocusChange(hasFocus: Boolean) {
            }
        })

        svAddress = findViewById(R.id.sv_address)
        svAddress.setOnSearchListener(object : ICallBack {
            override fun queryData(string: String?) {
                searchPoi()
            }

            override fun onFocusChange(hasFocus: Boolean) {
                if (hasFocus) {
                    cs_city_search_list.visibility = View.GONE
                    rv_address_search_list.visibility = View.VISIBLE
                    ll_block_city_show.visibility = View.VISIBLE
                    svCity.visibility = View.GONE
                }
            }
        })

        rv_address_search_list.layoutManager = LinearLayoutManager(rv_address_search_list.context)
        rv_address_search_list.itemAnimator = DefaultItemAnimator()
        rv_address_search_list.addItemDecoration(
            DividerItemDecoration(
                this, HORIZONTAL_LIST, 1,
                resources.getColor(R.color.line_gray)
            )
        )
        addressAdapter = SearchAddressAdapter()
        rv_address_search_list.adapter = addressAdapter
        addressAdapter.onItemClickListener = object : BaseListAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val model = addressAdapter.getItem(position)
                when (type) {
                    START
                    -> {
                        AppLocationUtils.getInstance().startLocation = model
                    }
                    END
                    -> {
                        AppLocationUtils.getInstance().endLocation = model
                    }
                    else -> {
                    }
                }
                setResult(Activity.RESULT_OK)
                finish()
            }

            override fun onItemLongClick(position: Int) {
            }
        }

        cs_city_search_list.setCurrentCity(startLocation.city)
        cs_city_search_list.setOnCitySelectListener { cityName, isCurrentCity ->
            cs_city_search_list.visibility = View.GONE
            rv_address_search_list.visibility = View.VISIBLE
            ll_block_city_show.visibility = View.VISIBLE
            svCity.visibility = View.GONE
            tv_city_show.text = cityName
            searchPoi()
        }

        searchPoi()
    }

    private fun initData() {
        startLocation = AppLocationUtils.getInstance().startLocation
        type = intent.getIntExtra("type", START)
        if (startLocation == null) {
            finish()
            return
        }
        poiSearch = PoiSearch.newInstance()
        poiSearch.setOnGetPoiSearchResultListener(listener)
    }

    private fun searchPoi() {
        var key = svAddress.text
        if (StringUtils.isEmpty(key)) key = "火车站"
        poiSearch.searchInCity(
            PoiCitySearchOption()
                .city(tv_city_show.text.toString()) //必填
                .keyword(key) //必填
                .pageCapacity(20)
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        poiSearch.destroy()
    }
}
