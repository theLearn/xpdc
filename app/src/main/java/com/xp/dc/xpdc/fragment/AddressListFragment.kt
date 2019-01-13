package com.xp.dc.xpdc.fragment


import android.app.Activity
import android.content.Intent
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener
import com.baidu.mapapi.search.sug.SuggestionSearch
import com.baidu.mapapi.search.sug.SuggestionSearchOption
import com.example.hongcheng.common.base.BaseListAdapter
import com.example.hongcheng.common.base.BasicFragment
import com.example.hongcheng.common.util.ScreenUtils
import com.example.hongcheng.common.view.DividerItemDecoration
import com.xp.dc.xpdc.R
import com.xp.dc.xpdc.activity.AddressSelectActivity
import com.xp.dc.xpdc.adapter.SearchAddressAdapter
import kotlinx.android.synthetic.main.fragment_address_list.*


class AddressListFragment : BasicFragment(), BaseListAdapter.OnItemClickListener {
    companion object {
        private const val RECOMMED: Int = 0
        private const val HISTORY: Int = 1
        private const val AIRPORT: Int = 2
        private const val TRAIN: Int = 3
        private const val BUS: Int = 4
        private const val PORT: Int = 5
    }

    private val keys = arrayListOf("", "", "机场", "火车站", "汽车站", "港口")
    private var type: Int = HISTORY
    private lateinit var mActivity: AddressSelectActivity
    private lateinit var addressAdapter: SearchAddressAdapter
    private var mSuggestionSearch : SuggestionSearch? = null

    override fun getLayoutResId(): Int {
        return R.layout.fragment_address_list
    }

    override fun initView() {
        val emptyView = LayoutInflater.from(mContext).inflate(R.layout.layout_list_nothing, rootView as ViewGroup, false)
        rv_address_info.layoutManager = LinearLayoutManager(rv_address_info.context)
        rv_address_info.itemAnimator = DefaultItemAnimator()
        rv_address_info.addItemDecoration(
            DividerItemDecoration(
                rv_address_info.context, DividerItemDecoration.HORIZONTAL_LIST, ScreenUtils.dp2px(rv_address_info.context, 1f),
                resources.getColor(R.color.line_gray)
            )
        )
        addressAdapter = SearchAddressAdapter()
        rv_address_info.adapter = addressAdapter
        rv_address_info.setEmptyView(emptyView)
        emptyView.visibility = View.GONE

        addressAdapter.onItemClickListener = this

        initData()
    }

    override fun onItemClick(position: Int) {
        val model = addressAdapter.getItem(position)
        val intent = Intent()
        intent.putExtra("selectLocation", model)
        mActivity.setResult(Activity.RESULT_OK, intent)
        mActivity.finish()
    }

    override fun onItemLongClick(position: Int) {
    }

    private fun initData() {
        type = arguments?.getInt("type", HISTORY)!!
        mActivity = activity as AddressSelectActivity
        if(type > HISTORY) {
            mSuggestionSearch = SuggestionSearch.newInstance()
            mSuggestionSearch?.setOnGetSuggestionResultListener(listener)

            mSuggestionSearch?.requestSuggestion(
                SuggestionSearchOption()
                    .city(mActivity.location!!.city)
                    .keyword(keys[type])
            )
        } else {
            addressAdapter.data = arrayListOf()
            addressAdapter.notifyDataSetChanged()
        }
    }

    private var listener: OnGetSuggestionResultListener = OnGetSuggestionResultListener {
        //处理sug检索结果
        addressAdapter.data = mActivity.getSearchInfo(keys[type], it.allSuggestions, true)
        addressAdapter.notifyDataSetChanged()
    }

    override fun onDestroy() {
        super.onDestroy()
        mSuggestionSearch?.destroy()
    }

}
