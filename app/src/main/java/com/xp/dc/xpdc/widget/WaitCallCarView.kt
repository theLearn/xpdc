package com.xp.dc.xpdc.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.xp.dc.xpdc.R
import kotlinx.android.synthetic.main.layout_call_car_wait.view.*

class WaitCallCarView : LinearLayout {

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
        setViewModel(WaitCallCarViewModel())
    }

    public fun setViewModel(viewModel: WaitCallCarViewModel) {
        tv_call_car_order_no.text = viewModel.orderNo
        tv_wait_car_type_num_tip.text =
                String.format(context.getString(R.string.call_car_wait_tip2), viewModel.carTypeNum)
        tv_wait_car_type_tip.text = viewModel.carType

        val options = RequestOptions()
            .placeholder(R.mipmap.icon_call_user_default)
            .error(R.mipmap.icon_call_user_default)
            .priority(Priority.HIGH)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .centerCrop()
            .transform(CircleCrop())
        Glide.with(iv_user_photo.context).applyDefaultRequestOptions(options).load(viewModel.userImgUrl)
            .transition(DrawableTransitionOptions.withCrossFade()).into(iv_user_photo)

    }

    data class WaitCallCarViewModel(
        var orderNo: String = "123456789",
        var userImgUrl: String = "",
        var carTypeNum: String = "1",
        var carType: String = "滴滴快车-经济型",
        var carImgUrl: String = "",
        var limitTime: Long = 10000
    )
}