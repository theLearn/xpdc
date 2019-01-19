package com.xp.dc.xpdc.viewmodel

import android.arch.lifecycle.MutableLiveData
import com.baidu.mapapi.search.core.PoiInfo
import com.baidu.mapapi.search.poi.*
import com.example.hongcheng.common.base.BaseViewModel
import com.example.hongcheng.common.base.CommonUI
import com.example.hongcheng.common.util.LoggerUtils
import com.example.hongcheng.common.util.StringUtils
import com.example.hongcheng.data.retrofit.ActionException
import com.example.hongcheng.data.retrofit.BaseFlowableSubscriber
import com.example.hongcheng.data.room.DBInit
import com.example.hongcheng.data.room.RoomClient
import com.example.hongcheng.data.room.entity.HistoryAddressEntity
import com.xp.dc.xpdc.location.XPLocation
import io.reactivex.Flowable
import io.reactivex.FlowableOnSubscribe


class HistoryAddressViewModel(private var ui: CommonUI?) : BaseViewModel(ui) {
    val poiList = MutableLiveData<ArrayList<XPLocation>>()
    val all = MutableLiveData<ArrayList<HistoryAddressEntity>>()

    fun getAll() {
        val data = arrayListOf<HistoryAddressEntity>()

        val source : Flowable<List<HistoryAddressEntity>>? = DBInit.getInstance().getAppDatabase()?.getHistoryAddressDao()?.getAllInfoByUserNo("123456")
        if(source != null) {
            compositeDisposable.add(RoomClient.getInstance().map(source, object : BaseFlowableSubscriber<List<HistoryAddressEntity>>() {
                override fun onBaseNext(t: List<HistoryAddressEntity>?) {
                    if (t != null && !t.isEmpty()) {
                        data.clear()
                        for (entity : HistoryAddressEntity in t) {
                            data.add(entity)
                        }
                    }

                    all.postValue(data)
                }

                override fun onError(e: ActionException?) {
                    all.postValue(data)
                }
            }))
        } else {
            all.postValue(data)
        }
    }

    fun insertAddress(cards: List<HistoryAddressEntity>) {
        compositeDisposable.add(RoomClient.getInstance().create(FlowableOnSubscribe {
            val result = DBInit.getInstance().getAppDatabase()?.getHistoryAddressDao()?.insertOrReplace(cards)
            if (result != null) {
                it.onNext(result)
            }
            it.onComplete()
        }, object : BaseFlowableSubscriber<List<Long>>() {
            override fun onBaseNext(t: List<Long>?) {
                if (t != null && !t.isEmpty()) {
                    LoggerUtils.error("insert", t.toString())
                }
            }

            override fun onError(e: ActionException?) {
                LoggerUtils.error("insert", e.toString())
            }
        }))
    }

    fun insertSelectAddress(entity: HistoryAddressEntity) {
        if (entity.isHome) DBInit.getInstance().getAppDatabase()?.getHistoryAddressDao()?.updateHomeAddress(false, "123456")
        if (entity.isCompany) DBInit.getInstance().getAppDatabase()?.getHistoryAddressDao()?.updateCompanyAddress(false, "123456")
        val list = arrayListOf<HistoryAddressEntity>()
        list.add(entity)
        insertAddress(list)
    }

    private lateinit var poiSearch: PoiSearch
    private var listener: OnGetPoiSearchResultListener = object : OnGetPoiSearchResultListener {
        override fun onGetPoiResult(poiResult: PoiResult) {
            poiList.postValue(getPoiListInfo(poiResult.allPoi))
        }

        override fun onGetPoiDetailResult(poiDetailSearchResult: PoiDetailSearchResult) {

        }

        override fun onGetPoiIndoorResult(poiIndoorResult: PoiIndoorResult) {

        }

        //废弃
        override fun onGetPoiDetailResult(poiDetailResult: PoiDetailResult) {

        }
    }

    private fun getPoiListInfo(poiList: List<PoiInfo>?): ArrayList<XPLocation>? {
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


    public fun initData() {
        poiSearch = PoiSearch.newInstance()
        poiSearch.setOnGetPoiSearchResultListener(listener)
    }

    public fun searchPoi(city : String, key : String) {
        if(StringUtils.isEmpty(key)) {
            getAll()
        } else {
            poiSearch.searchInCity(
                PoiCitySearchOption()
                    .city(city) //必填
                    .keyword(key) //必填
                    .pageCapacity(20)
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        poiSearch.destroy()
    }
}