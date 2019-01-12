package com.xp.dc.xpdc.activity

import android.graphics.Color
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.example.hongcheng.common.util.ScreenUtils
import com.xp.dc.xpdc.R
import kotlinx.android.synthetic.main.activity_order_show.*


class OrderShowActivity : AppCommonActivity(), SwipeRefreshLayout.OnRefreshListener {
    override fun isNeedShowBack(): Boolean {
        return true
    }
    override fun getMessageLayoutResId(): Int {
        return 0
    }

    override fun setToolbarTitle(): Int {
        return R.string.use_car_order
    }

    override fun getBodyLayoutResId(): Int {
        return R.layout.activity_order_show
    }

    override fun initBodyView(view: View) {
//        val emptyView = LayoutInflater.from(this).inflate(R.layout.layout_list_nothing, srl, false)
        rv_order.layoutManager = LinearLayoutManager(rv_order.context)
        rv_order.itemAnimator = DefaultItemAnimator()
//        mAdapter = MessageAdapter()
//        rv.setAdapter(mAdapter)
//        rv.setEmptyView(emptyView)
//        emptyView.setVisibility(View.GONE)

        // 设置加载更多监听
        rv_order.setLoadMoreListener { getData() }

        srl_order.setProgressBackgroundColorSchemeColor(Color.WHITE)
        srl_order.setColorSchemeResources(R.color.app_theme)
        srl_order.setProgressViewOffset(false, 0, ScreenUtils.dp2px(this, 30.0f))
        srl_order.setOnRefreshListener(this)
        srl_order.post{
            srl_order.isRefreshing = true
//            page = 0
//            dataList.clear()
//            getData()
        }
    }

    override fun onRefresh() {
//        page = 0
//        dataList.clear()
//        getData()
    }

    private fun getData() {

    }
}
