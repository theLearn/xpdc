package com.xp.dc.xpdc.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener
import com.baidu.mapapi.search.sug.SuggestionResult
import com.baidu.mapapi.search.sug.SuggestionSearch
import com.baidu.mapapi.search.sug.SuggestionSearchOption
import com.example.hongcheng.common.base.BaseActivity
import com.example.hongcheng.common.base.BaseListAdapter
import com.example.hongcheng.common.base.FragmentAdapter
import com.example.hongcheng.common.util.ScreenUtils
import com.example.hongcheng.common.util.StringUtils
import com.example.hongcheng.common.view.DividerItemDecoration
import com.example.hongcheng.common.view.DividerItemDecoration.HORIZONTAL_LIST
import com.example.hongcheng.common.view.searchview.ICallBack
import com.example.hongcheng.common.view.searchview.SearchView
import com.xp.dc.xpdc.R
import com.xp.dc.xpdc.adapter.SearchAddressAdapter
import com.xp.dc.xpdc.fragment.AddressListFragment
import com.xp.dc.xpdc.location.XPLocation
import kotlinx.android.synthetic.main.activity_address_select.*
import kotlinx.android.synthetic.main.layout_address_search_list.*


class AddressSelectActivity : BaseActivity() {
    companion object {
        public const val START: Int = 0
        public const val END: Int = 1
    }

    private lateinit var svCity: SearchView
    private lateinit var svAddress: SearchView

    private lateinit var addressAdapter: SearchAddressAdapter

    private lateinit var mAdapter: FragmentAdapter
    private val fragments: MutableList<Fragment> = arrayListOf()
    private val titles: MutableList<String> = arrayListOf()

    public var location: XPLocation? = null
    private var type: Int = START

    private lateinit var mSuggestionSearch: SuggestionSearch
    private var listener: OnGetSuggestionResultListener = OnGetSuggestionResultListener {
        //处理sug检索结果
        addressAdapter.data = getSearchInfo(svAddress.text, it.allSuggestions)
        addressAdapter.notifyDataSetChanged()
        setMessageViewVisible(true)
    }

    public fun getSearchInfo(
        key: String,
        list: List<SuggestionResult.SuggestionInfo>,
        isEndWith: Boolean = false
    ): MutableList<XPLocation> {
        val xpLocationList = arrayListOf<XPLocation>()
        for (info: SuggestionResult.SuggestionInfo in list) {
            if (info.pt == null) continue
            if (info.city != location?.city) continue
            if (isEndWith && !info.key.endsWith(key)) continue
            val xpLocation = XPLocation()
            xpLocation.lat = info.pt.latitude
            xpLocation.lon = info.pt.longitude
            xpLocation.city = info.city
            xpLocation.address = info.key
            xpLocationList.add(xpLocation)
        }

        return xpLocationList
    }

    override fun getTitleLayoutResId(): Int {
        return R.layout.layout_address_search_title
    }

    override fun getMessageLayoutResId(): Int {
        return R.layout.layout_address_search_list
    }

    override fun getBodyLayoutResId(): Int {
        return R.layout.activity_address_select
    }

    private fun initData() {
        location = intent.getParcelableExtra("location")
        type = intent.getIntExtra("type", START)
        if (location == null) {
            finish()
            return
        }
        mSuggestionSearch = SuggestionSearch.newInstance()
        mSuggestionSearch.setOnGetSuggestionResultListener(listener)
    }

    override fun initTitleView(view: View) {
        initData()

        ScreenUtils.setWindowStatusBarColor(this, resources.getColor(R.color.white))
        ScreenUtils.setLightStatusBar(this, true)

        svCity = view.findViewById(R.id.sv_city)
        svCity.text = location?.city
        svCity.setOnSearchListener(object : ICallBack {
            override fun queryData(string: String?) {
            }

            override fun onFocusChange(hasFocus: Boolean) {
                if (hasFocus) {
                    cs_city_search_list.visibility = View.VISIBLE
                    rv_address_search_list.visibility = View.GONE
                }
            }
        })

        svAddress = view.findViewById(R.id.sv_address)
        svAddress.setOnSearchListener(object : ICallBack {
            override fun queryData(string: String?) {
                if (StringUtils.isEmpty(string)) {
                    setMessageViewVisible(false)
                } else {
                    mSuggestionSearch.requestSuggestion(
                        SuggestionSearchOption()
                            .city(svCity.text)
                            .keyword(svAddress.text)
                    )
                }
            }

            override fun onFocusChange(hasFocus: Boolean) {
                if (hasFocus) {
                    cs_city_search_list.visibility = View.GONE
                    rv_address_search_list.visibility = View.VISIBLE
                }
            }
        })
    }

    override fun initMessageView(view: View) {
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
                val intent = Intent()
                intent.putExtra("selectLocation", model)
                setResult(Activity.RESULT_OK, intent)
                finish()
            }

            override fun onItemLongClick(position: Int) {
            }
        }

        cs_city_search_list.load()

//        view.setOnClickListener {
//            setMessageViewVisible(false)
//        }
    }

    override fun initBodyView(view: View) {
        if (type == END) {
            titles.add(getString(R.string.history))
        } else {
            titles.add(getString(R.string.recommend))
        }

        titles.add(getString(R.string.airport))
        titles.add(getString(R.string.train_station))
        titles.add(getString(R.string.bus_station))
        titles.add(getString(R.string.port))

        for (i: Int in titles.indices) {
            val bundle = Bundle()
            if (0 == i && type == START) {
                bundle.putInt("type", 0)
            } else {
                bundle.putInt("type", i + 1)
            }

            val fragment = AddressListFragment()
            fragment.arguments = bundle
            fragments.add(fragment)
        }

        mAdapter = FragmentAdapter(supportFragmentManager)
        changeShows(titles, fragments)
    }

    private fun changeShows(titles: List<String>, fragments: List<Fragment>) {
        if (titles.isEmpty() || fragments.isEmpty()) return

        vp_address_select.removeAllViews()
        tabs_address_select.removeAllTabs()

        for (title: String in titles) {
            tabs_address_select.addTab(tabs_address_select.newTab().setText(title))
        }

        mAdapter.mTitles = titles
        mAdapter.mFragments = fragments
        vp_address_select.adapter = mAdapter
        if (titles.size > 1) {
            vp_address_select.offscreenPageLimit = titles.size - 1
            tabs_address_select.setupWithViewPager(vp_address_select)
        } else {
            tabs_address_select.visibility = View.GONE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mSuggestionSearch.destroy()
    }
}
