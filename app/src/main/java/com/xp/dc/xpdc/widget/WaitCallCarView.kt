package com.xp.dc.xpdc.widget

import android.content.Context
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.example.hongcheng.common.util.ViewUtils
import com.example.hongcheng.common.view.DividerItemDecoration
import com.xp.dc.xpdc.R
import com.xp.dc.xpdc.adapter.WaitCarTypeAdapter
import com.xp.dc.xpdc.bean.OrderInfo
import com.xp.dc.xpdc.bean.OrderState
import kotlinx.android.synthetic.main.layout_call_car_wait.view.*

class WaitCallCarView : LinearLayout, View.OnClickListener {
    public var onOrderActionListener: OnOrderActionListener? = null
    private var orderInfo: OrderInfo? = null
    private lateinit var mAdapter: WaitCarTypeAdapter

    constructor(context: Context) : this(context, null) {

    }

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0) {
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initAttrs(context, attrs)
        initView()
    }

    private fun initAttrs(context: Context, attrs: AttributeSet?) {

    }

    private fun initView() {
        LayoutInflater.from(context).inflate(R.layout.layout_call_car_wait, this)
        rv_wait_accept_block.layoutManager = LinearLayoutManager(rv_wait_accept_block.context)
        rv_wait_accept_block.itemAnimator = DefaultItemAnimator()
        rv_wait_accept_block.addItemDecoration(
            DividerItemDecoration(
                context, DividerItemDecoration.HORIZONTAL_LIST, 10,
                resources.getColor(R.color.transparent)
            )
        )
        mAdapter = WaitCarTypeAdapter()
        rv_wait_accept_block.adapter = mAdapter

        tv_wait_order_action.setOnClickListener(this)
        tv_wait_order_action_right.setOnClickListener(this)
    }

    public fun setViewModel(viewModel: OrderInfo) {
        orderInfo = viewModel
        tv_call_car_order_no.text = viewModel.orderNo
        rv_wait_accept_block.visibility = View.GONE
        ll_wait_car_block.visibility = View.GONE
        ll_order_making_block.visibility = View.GONE
        ll_pay_order_block.visibility = View.GONE
        ll_order_bottom.visibility = View.GONE
        line_order_bottom_h.visibility = View.GONE
        tv_wait_order_action_right.visibility = View.GONE
        var iconResId = R.mipmap.icon_call_user_default
        when (viewModel.state) {
            OrderState.DEFAULT
            -> {
                mAdapter.data = viewModel.carInfo
                rv_wait_accept_block.visibility = View.VISIBLE
                ll_order_bottom.visibility = View.VISIBLE
                tv_wait_order_action.setText(R.string.cancel_order)
                tv_wait_car_top.setText(R.string.call_car_wait_tip1)
                tv_wait_car_down.text =
                        String.format(context.getString(R.string.call_car_wait_tip2), viewModel.carInfo.size)
            }
            OrderState.WAIT_CAR
            -> {
                iconResId = R.mipmap.icon_driver_default
                ll_wait_car_block.visibility = View.VISIBLE
                ll_order_bottom.visibility = View.VISIBLE
                line_order_bottom_h.visibility = View.VISIBLE
                tv_wait_order_action_right.visibility = View.VISIBLE
                tv_wait_order_action.setText(R.string.cancel_order)
                tv_wait_car_top.text = viewModel.driverInfo?.carNo
                tv_wait_car_down.text = viewModel.driverInfo?.carType + " " + viewModel.driverInfo?.carName + " " +
                        viewModel.driverInfo?.name
            }
            OrderState.DRIVING
            -> {
                iconResId = R.mipmap.icon_driver_default
                ll_order_making_block.visibility = View.VISIBLE
                val str: String = "" + viewModel.driveTime + "分钟"
                tv_mileage_tip.text =
                        String.format(context.getString(R.string.order_state_make_tip), viewModel.mileage, str)
                tv_wait_car_top.text = viewModel.driverInfo?.carNo
                tv_wait_car_down.text = viewModel.driverInfo?.carType + " " + viewModel.driverInfo?.carName + " " +
                        viewModel.driverInfo?.name
            }
            OrderState.WAIT_PAY
            -> {
                iconResId = R.mipmap.icon_driver_default
                ll_pay_order_block.visibility = View.VISIBLE
                tv_wait_car_top.text = viewModel.driverInfo?.carNo
                tv_wait_car_down.text = viewModel.driverInfo?.carType + " " + viewModel.driverInfo?.carName + " " +
                        viewModel.driverInfo?.name
                tv_order_price.text = String.format(context.getString(R.string.pay_price), viewModel.price)
                ll_order_bottom.visibility = View.VISIBLE
                tv_wait_order_action.setText(R.string.to_evaluate)
            }
            OrderState.FINISH
            -> {
            }
            else -> {
            }
        }

        val options = RequestOptions()
            .placeholder(iconResId)
            .error(iconResId)
            .priority(Priority.HIGH)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .centerCrop()
            .transform(CircleCrop())
        Glide.with(iv_user_photo.context).applyDefaultRequestOptions(options).load(viewModel.driverInfo?.imgUrl)
            .transition(DrawableTransitionOptions.withCrossFade()).into(iv_user_photo)
    }

    override fun onClick(v: View?) {
        if (ViewUtils.isFastClick()) return
        orderInfo?.let {
            when (v?.id) {
                R.id.tv_wait_order_action
                -> {
                    if (OrderState.DEFAULT == it.state || OrderState.WAIT_CAR == it.state) {
                        onOrderActionListener?.cancel(it)
                    } else {
                        onOrderActionListener?.evaluate(it)
                    }
                }
                R.id.tv_wait_order_action_right
                -> {

                    onOrderActionListener?.contact(it)
                }
                else -> {
                }
            }
        }
    }

    public interface OnOrderActionListener {
        fun cancel(orderInfo: OrderInfo)
        fun evaluate(orderInfo: OrderInfo)
        fun contact(orderInfo: OrderInfo)
    }
}