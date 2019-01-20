package com.xp.dc.xpdc.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.view.LayoutInflater
import android.view.View
import com.baidu.mapapi.model.LatLng
import com.example.hongcheng.common.util.ViewUtils
import com.example.hongcheng.common.view.spinkit.SpriteFactory
import com.example.hongcheng.common.view.spinkit.Style
import com.example.hongcheng.common.view.spinkit.sprite.Sprite
import com.example.hongcheng.common.view.spinkit.sprite.SpriteContainer
import com.xp.dc.xpdc.R
import com.xp.dc.xpdc.bean.OrderInfo
import com.xp.dc.xpdc.bean.OrderState
import com.xp.dc.xpdc.viewmodel.OrderViewModel
import com.xp.dc.xpdc.viewmodel.OrderViewModelFactory
import com.xp.dc.xpdc.widget.WaitCallCarView
import kotlinx.android.synthetic.main.body_order_detail.*
import kotlinx.android.synthetic.main.layout_app_common_title.*


class OrderDetailActivity : AppCommonActivity(), View.OnClickListener,
    WaitCallCarView.OnOrderActionListener {

    private lateinit var order : OrderInfo

    private lateinit var viewModel: OrderViewModel

    override fun isNeedShowBack(): Boolean {
        return true
    }
    override fun getMessageLayoutResId(): Int {
        return 0
    }

    override fun setToolbarTitle(): Int {
        return R.string.main_title_calling
    }

    override fun getBodyLayoutResId(): Int {
        return R.layout.body_order_detail
    }

    override fun initBodyView(view: View) {
        iv_reset_order_detail.setOnClickListener(this)

        val style: Style = Style.values()[7]
        val drawable: Sprite = SpriteFactory.create(style)
        (drawable as SpriteContainer).color = resources.getColor(R.color.colorBase)
        sk_call_query_load_order_detail.setIndeterminateDrawable(drawable)

        wc_order_detail.onOrderActionListener = this
        order = intent.getParcelableExtra("order")
        wc_order_detail.setViewModel(order)
        showPointInMap()

        val overlay = LayoutInflater.from(this).inflate(R.layout.overlay_wait_order_accept, wc_order_detail, false)
        mv_order_detail.showInfoWindow(overlay, LatLng(order.startPosition.lat, order.startPosition.lon), -47)

        val factory = OrderViewModelFactory(this)
        viewModel = ViewModelProviders.of(this, factory).get(OrderViewModel::class.java)
        viewModel.order.observe(this, Observer { it ->
            it?.let {
                wc_order_detail.setViewModel(it)
                order = it
                when(it.state) {
                    OrderState.DEFAULT
                    -> {
                        tv_app_common_title.setText(R.string.main_title_calling)
                    }
                    OrderState.WAIT_CAR
                    -> {
                        mv_order_detail.hideInfoWindow()
                        tv_app_common_title.setText(R.string.main_title_wait_car)
                    }
                    OrderState.DRIVING
                    -> {
                        tv_app_common_title.setText(R.string.main_title_driving)
                    }
                    OrderState.WAIT_PAY
                    -> {
                        tv_app_common_title.setText(R.string.main_title_order_finish)
                    }
                    OrderState.FINISH
                    -> {
                    }
                    else -> {
                    }
                }
            }
        })

        viewModel.accept()
    }

    override fun onClick(v: View?) {
        if (ViewUtils.isFastClick()) return
        when (v?.id) {
            R.id.iv_reset_order_detail
            -> {
            }
            else -> {
            }
        }
    }

    private fun showPointInMap() {
        val startPosition = order.startPosition
        val endPosition = order.endPosition
        mv_order_detail.drawSEToMap(LatLng(startPosition.lat, startPosition.lon), LatLng(endPosition.lat, endPosition.lon))
    }


    override fun cancel(orderInfo: OrderInfo) {
    }

    override fun evaluate(orderInfo: OrderInfo) {
    }

    override fun contact(orderInfo: OrderInfo) {
    }

    override fun onResume() {
        super.onResume()
        mv_order_detail.onResume()
    }

    override fun onPause() {
        super.onPause()
        mv_order_detail.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mv_order_detail.onDestroy()
    }
}
