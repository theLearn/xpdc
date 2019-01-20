package com.xp.dc.xpdc.activity

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.example.hongcheng.common.base.BaseListAdapter
import com.example.hongcheng.common.base.BasicActivity
import com.example.hongcheng.common.util.ScreenUtils
import com.example.hongcheng.common.util.ViewUtils
import com.example.hongcheng.common.view.DividerItemDecoration
import com.example.hongcheng.common.view.DividerItemDecoration.HORIZONTAL_LIST
import com.example.hongcheng.common.view.searchview.ICallBack
import com.example.hongcheng.common.view.searchview.SearchView
import com.example.hongcheng.data.room.entity.HistoryAddressEntity
import com.xp.dc.xpdc.R
import com.xp.dc.xpdc.adapter.SearchAddressAdapter
import com.xp.dc.xpdc.location.AppLocationUtils
import com.xp.dc.xpdc.location.XPLocation
import com.xp.dc.xpdc.viewmodel.HistoryAddressViewModel
import com.xp.dc.xpdc.viewmodel.HistoryAddressViewModelFactory
import kotlinx.android.synthetic.main.layout_address_search_list.*
import kotlinx.android.synthetic.main.layout_address_search_title.*


class AddressSelectActivity : BasicActivity(), View.OnClickListener {
    companion object {
        public const val START: Int = 0
        public const val END: Int = 1
        public const val HOME: Int = 2
        public const val COMPANY: Int = 3
    }

    private lateinit var svCity: SearchView
    private lateinit var svAddress: SearchView

    private lateinit var addressAdapter: SearchAddressAdapter

    private var type: Int = START
    private lateinit var startLocation: XPLocation

    private lateinit var viewModel: HistoryAddressViewModel
    private var homeAddress : XPLocation? = null
    private var companyAddress : XPLocation? = null

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
            ll_search_list_block.visibility = View.GONE
            svCity.requestFocus()
        }

        tv_address_select_cancel.setOnClickListener {
            finish()
        }

        svCity = findViewById(R.id.sv_city)
        svCity.setOnSearchListener(object : ICallBack {
            override fun queryData(string: String?) {
                cs_city_search_list.setSearchSource(string)
            }

            override fun onFocusChange(hasFocus: Boolean) {
            }
        })

        svAddress = findViewById(R.id.sv_address)
        svAddress.setOnSearchListener(object : ICallBack {
            override fun queryData(string: String?) {
                viewModel.searchPoi(tv_city_show.text.toString(), svAddress.text)
            }

            override fun onFocusChange(hasFocus: Boolean) {
                if (hasFocus) {
                    cs_city_search_list.visibility = View.GONE
                    ll_search_list_block.visibility = View.VISIBLE
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
                completeSelect(model)
            }

            override fun onItemLongClick(position: Int) {
            }
        }

        cs_city_search_list.setCurrentCity(startLocation.city)
        cs_city_search_list.setOnCitySelectListener { cityName, isCurrentCity ->
            cs_city_search_list.visibility = View.GONE
            ll_search_list_block.visibility = View.VISIBLE
            ll_block_city_show.visibility = View.VISIBLE
            svCity.visibility = View.GONE
            tv_city_show.text = cityName
            viewModel.searchPoi(tv_city_show.text.toString(), svAddress.text)
        }

        if(type > END) ll_home_company_block.visibility = View.GONE
        ll_home_block.setOnClickListener(this)
        ll_company_block.setOnClickListener(this)
        fl_home_address_edit.setOnClickListener(this)
        fl_company_address_edit.setOnClickListener(this)

        viewModel.searchPoi(tv_city_show.text.toString(), svAddress.text)
    }

    private fun initData() {
        startLocation = AppLocationUtils.getInstance().startLocation
        type = intent.getIntExtra("type", START)
        if (startLocation == null) {
            finish()
            return
        }
        val factory = HistoryAddressViewModelFactory(this)
        viewModel = ViewModelProviders.of(this, factory).get(HistoryAddressViewModel::class.java)
        viewModel.initData()

        viewModel.poiList.observe(this, Observer { list ->
            list?.let {
                addressAdapter.data = it
                addressAdapter.notifyDataSetChanged()
            }
        })

        viewModel.all.observe(this, Observer { list ->
            list?.let {
                val xpList = arrayListOf<XPLocation>()
                for (info: HistoryAddressEntity in it) {
                    val xpLocation = XPLocation()
                    xpLocation.lat = info.lat
                    xpLocation.lon = info.lon
                    xpLocation.name = info.name
                    xpLocation.address = info.address
                    xpLocation.city = info.city
                    xpLocation.province = info.province
                    xpList.add(xpLocation)

                    if(info.isHome) {
                        tv_address_home.text = info.name
                        homeAddress = xpLocation
                    }
                    if(info.isCompany){
                        tv_address_company.text = info.name
                        companyAddress = xpLocation
                    }
                }
                addressAdapter.data = xpList
                addressAdapter.notifyDataSetChanged()
            }
        })
    }

    override fun onClick(v: View?) {
        if(ViewUtils.isFastClick()) return
        when (v?.id) {
            R.id.ll_home_block
            -> {
                homeAddress?.let {
                    completeSelect(it)
                }
            }
            R.id.ll_company_block
            -> {
                companyAddress?.let {
                    completeSelect(it)
                }
            }
            R.id.fl_home_address_edit
            -> {
                val homeIntent = Intent(this, AddressSelectActivity::class.java)
                homeIntent.putExtra("type", HOME)
                startActivity(homeIntent)
            }
            R.id.fl_company_address_edit
            -> {
                val companyIntent = Intent(this, AddressSelectActivity::class.java)
                companyIntent.putExtra("type", COMPANY)
                startActivity(companyIntent)
            }
            else -> {
            }
        }
    }

    private fun completeSelect(xpLocation: XPLocation) {
        val entity = convertAddress(xpLocation)
        when (type) {
            START
            -> {
                AppLocationUtils.getInstance().startLocation = xpLocation
            }
            END
            -> {
                AppLocationUtils.getInstance().endLocation = xpLocation
            }
            HOME
            -> {
                if(entity.isHome) {
                    setResult(Activity.RESULT_OK)
                    finish()
                    return
                }
                entity.isHome = true
            }
            COMPANY
            -> {
                if(entity.isCompany) {
                    setResult(Activity.RESULT_OK)
                    finish()
                    return
                }
                entity.isCompany = true
            }
            else -> {
            }
        }
        viewModel.insertSelectAddress(entity)
        setResult(Activity.RESULT_OK)
        finish()
    }

    private fun convertAddress(xpLocation: XPLocation): HistoryAddressEntity {
        val entity = HistoryAddressEntity()
        entity.lat = xpLocation.lat
        entity.lon = xpLocation.lon
        entity.name = xpLocation.name
        entity.address = xpLocation.address
        entity.city = xpLocation.city
        entity.province = xpLocation.province
        entity.userNo = "123456"
        homeAddress?.let {
            entity.isHome = it.lat == entity.lat && it.lon == entity.lon
        }
        companyAddress?.let {
            entity.isCompany = it.lat == entity.lat && it.lon == entity.lon
        }

        return entity
    }

    override fun onDestroy() {
        super.onDestroy()
        cs_city_search_list.onDestroy()
    }
}
