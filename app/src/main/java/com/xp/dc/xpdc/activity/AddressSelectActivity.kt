package com.xp.dc.xpdc.activity

import android.database.Cursor
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.ActionBar
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.ListView
import com.example.hongcheng.common.base.BaseActivity
import com.example.hongcheng.common.base.FragmentAdapter
import com.example.hongcheng.common.util.ScreenUtils
import com.example.hongcheng.common.view.searchview.ICallBack
import com.example.hongcheng.common.view.searchview.SearchView
import com.xp.dc.xpdc.R
import com.xp.dc.xpdc.fragment.AddressListFragment
import kotlinx.android.synthetic.main.activity_address_select.*

class AddressSelectActivity : BaseActivity() {
    private lateinit var svCity : SearchView
    private lateinit var svAddress : SearchView
    private lateinit var searchCityList : ListView
    private lateinit var searchAddressList : ListView

    private lateinit var mAdapter: FragmentAdapter
    private val fragments: MutableList<Fragment> = arrayListOf()
    private val titles: MutableList<String> = arrayListOf()

    override fun getTitleLayoutResId(): Int {
        return R.layout.layout_address_search_title
    }

    override fun getMessageLayoutResId(): Int {
        return R.layout.layout_address_search_list
    }

    override fun getBodyLayoutResId(): Int {
        return R.layout.activity_address_select
    }

    override fun initTitleView(view: View) {
        val toolbar = view.findViewById<Toolbar>(R.id.tb_search)
        setSupportActionBar(toolbar)
        ScreenUtils.setWindowStatusBarColor(this, resources.getColor(R.color.white))
        ScreenUtils.setLightStatusBar(this, true)
        toolbar.setNavigationIcon(R.drawable.back)
        toolbar.setNavigationOnClickListener { onBackPressed() }
        val actionBar : ActionBar? = supportActionBar
        actionBar?.title = ""

        svCity = view.findViewById(R.id.sv_city)
        svCity.setText("武汉")
        svCity.setOnClickSearch(object : ICallBack {
            override fun searchAction(string: String?) {
            }

            override fun queryResult(cursor: Cursor?) {
                if(cursor == null) return

                // 数据库中有搜索记录时，显示 "删除搜索记录"按钮
                setMessageViewVisible(cursor.count != 0)
            }
        })

        svAddress = view.findViewById(R.id.sv_address)
        svAddress.setOnClickSearch(object : ICallBack {
            override fun searchAction(string: String?) {
            }

            override fun queryResult(cursor: Cursor?) {
                if(cursor == null) return

                // 数据库中有搜索记录时，显示 "删除搜索记录"按钮
                setMessageViewVisible(cursor.count != 0)
            }
        })
    }

    override fun initMessageView(view: View) {
        searchCityList = view.findViewById(R.id.lv_city_search_list)
        searchAddressList = view.findViewById(R.id.lv_address_search_list)

        /**
         * 搜索记录列表（ListView）监听
         * 即当用户点击搜索历史里的字段后,会直接将结果当作搜索字段进行搜索
         */
//        searchList.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
//            // 获取用户点击列表里的文字,并自动填充到搜索框内
//            val textView = view.findViewById<View>(R.id.tv_search_content) as TextView
//            val name = textView.text.toString()
//            svTitle.setText(name)
//            setMessageViewVisible(false)
//        }

        view.setOnClickListener {
            setMessageViewVisible(false)
        }

        setMessageViewVisible(false)
    }

    override fun initBodyView(view: View) {
        titles.add(getString(R.string.history))
        titles.add(getString(R.string.airport))
        titles.add(getString(R.string.train_station))
        titles.add(getString(R.string.bus_station))
        titles.add(getString(R.string.port))

        for (i : Int in titles.indices) {
            val bundle = Bundle()
            bundle.putString("type", i.toString())
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
}
