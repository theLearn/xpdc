package com.xp.dc.xpdc.activity

import android.view.View
import com.xp.dc.xpdc.R

class AddressSelectActivity : AppCommonActivity() {
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
        return R.layout.activity_address_select
    }

    override fun initBodyView(view: View) {
    }
}
