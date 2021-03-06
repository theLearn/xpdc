package com.xp.dc.xpdc.activity

import android.content.Intent
import android.graphics.Color
import android.os.Handler
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import com.example.hongcheng.common.base.BaseListAdapter
import com.example.hongcheng.common.util.ScreenUtils
import com.xp.dc.xpdc.R
import com.xp.dc.xpdc.adapter.OrderAdapter
import com.xp.dc.xpdc.bean.OrderInfo
import com.xp.dc.xpdc.viewmodel.OrderViewModel
import kotlinx.android.synthetic.main.activity_order_show.*


class OrderShowActivity : AppCommonActivity(), SwipeRefreshLayout.OnRefreshListener {
    private lateinit var mAdapter : OrderAdapter
    private var page : Int = 0
    private var dataList : MutableList<OrderInfo> = arrayListOf()

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
        val emptyView = LayoutInflater.from(this).inflate(R.layout.layout_list_nothing, srl_order, false)
        rv_order.layoutManager = LinearLayoutManager(rv_order.context)
        rv_order.itemAnimator = DefaultItemAnimator()
        mAdapter = OrderAdapter()
        rv_order.adapter = mAdapter
        rv_order.setEmptyView(emptyView)
        emptyView.visibility = View.GONE

        mAdapter.onItemClickListener = object : BaseListAdapter.OnItemClickListener{
            override fun onItemClick(position: Int) {
                val orderIntent = Intent(this@OrderShowActivity, OrderDetailActivity::class.java)
                orderIntent.putExtra("order", dataList[position])
                startActivity(orderIntent)
            }

            override fun onItemLongClick(position: Int) {
            }
        }

        // 设置加载更多监听
        rv_order.setLoadMoreListener { getData() }

        srl_order.setProgressBackgroundColorSchemeColor(Color.WHITE)
        srl_order.setColorSchemeResources(R.color.colorBase)
        srl_order.setProgressViewOffset(false, 0, ScreenUtils.dp2px(this, 30.0f))
        srl_order.setOnRefreshListener(this)
        srl_order.post{
            srl_order.isRefreshing = true
            page = 0
            dataList.clear()
            getData()
        }
    }

    override fun onRefresh() {
        page = 0
        dataList.clear()
        getData()
    }

    private fun getData() {
        Handler().postDelayed({
            for (i in 1..20) {
                dataList.add(OrderViewModel.order())
            }

            srl_order.isRefreshing = false
            mAdapter.data = dataList
            rv_order.enableLoadMore(dataList.size <= 40)
        }, 2000)
    }
}
