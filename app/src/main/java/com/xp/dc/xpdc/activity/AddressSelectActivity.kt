package com.xp.dc.xpdc.activity

import android.app.Activity
import android.content.Intent
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.baidu.mapapi.search.core.PoiInfo
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener
import com.baidu.mapapi.search.sug.SuggestionResult
import com.baidu.mapapi.search.sug.SuggestionSearch
import com.baidu.mapapi.search.sug.SuggestionSearchOption
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
    private var reverseGeoCodeResult: ReverseGeoCodeResult? = null

    private lateinit var mSuggestionSearch: SuggestionSearch
    private var listener: OnGetSuggestionResultListener = OnGetSuggestionResultListener {
        //处理sug检索结果
        addressAdapter.data = getSearchInfo(svAddress.text, it.allSuggestions)
        addressAdapter.notifyDataSetChanged()
    }

    private fun getSearchInfo(
        key: String,
        list: List<SuggestionResult.SuggestionInfo>,
        isEndWith: Boolean = false
    ): MutableList<XPLocation> {
        val xpLocationList = arrayListOf<XPLocation>()
        for (info: SuggestionResult.SuggestionInfo in list) {
            if (info.pt == null) continue
            if (info.city != reverseGeoCodeResult?.addressDetail?.city) continue
            if (isEndWith && !info.key.endsWith(key)) continue
            val xpLocation = XPLocation()
            xpLocation.lat = info.pt.latitude
            xpLocation.lon = info.pt.longitude
            xpLocation.city = info.city
            xpLocation.address = info.key
            xpLocation.des = info.address
            xpLocationList.add(xpLocation)
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
        tv_city_show.text = reverseGeoCodeResult?.addressDetail?.city

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
                if (StringUtils.isEmpty(string)) {

                } else {
                    mSuggestionSearch.requestSuggestion(
                        SuggestionSearchOption()
                            .city(tv_city_show.text.toString())
                            .keyword(svAddress.text)
                    )
                }
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
        addressAdapter.data = getPoiListInfo()
        rv_address_search_list.adapter = addressAdapter
        addressAdapter.onItemClickListener = object : BaseListAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val model = addressAdapter.getItem(position)
                val intent = Intent()
                intent.putExtra("selectLocation", model)
                setResult(Activity.RESULT_OK, intent)
                finish()
            }

            override fun onItemLongClick(position: Int) {
            }
        }

        cs_city_search_list.setCurrentCity(reverseGeoCodeResult?.addressDetail?.city)
        cs_city_search_list.setOnCitySelectListener { cityName, isCurrentCity ->
            cs_city_search_list.visibility = View.GONE
            rv_address_search_list.visibility = View.VISIBLE
            ll_block_city_show.visibility = View.VISIBLE
            svCity.visibility = View.GONE
            tv_city_show.text = reverseGeoCodeResult?.addressDetail?.city
        }
    }

    private fun initData() {
        reverseGeoCodeResult = AppLocationUtils.getInstance().currentLocationInfo
        type = intent.getIntExtra("type", START)
        if (reverseGeoCodeResult == null) {
            finish()
            return
        }
        mSuggestionSearch = SuggestionSearch.newInstance()
        mSuggestionSearch.setOnGetSuggestionResultListener(listener)
    }

    private fun getPoiListInfo(): MutableList<XPLocation> {
        val xpLocationList = arrayListOf<XPLocation>()
        val poiList = reverseGeoCodeResult?.poiList
        poiList?.let {
            for (poi: PoiInfo in it) {
                val xpLocation = XPLocation()
                xpLocation.lat = poi.location.latitude
                xpLocation.lon = poi.location.longitude
                xpLocation.city = poi.city
                xpLocation.address = poi.name
                xpLocation.des = poi.address
                xpLocationList.add(xpLocation)
            }
        }

        return xpLocationList
    }

    override fun onDestroy() {
        super.onDestroy()
        mSuggestionSearch.destroy()
    }
}
