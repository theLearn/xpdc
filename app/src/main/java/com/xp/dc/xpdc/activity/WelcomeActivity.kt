package com.xp.dc.xpdc.activity

import android.content.Intent
import android.os.Handler
import android.widget.ImageView
import com.example.hongcheng.common.base.BasicActivity
import com.xp.dc.xpdc.R
import kotlinx.android.synthetic.main.activity_welcome.*


class WelcomeActivity : BasicActivity() {

    override fun getLayoutResId(): Int {
        return R.layout.activity_welcome
    }

    override fun initView() {
        Handler().postDelayed({
            startActivity(Intent(this, MapActivity::class.java))
            finish()
        }, 2000)
        initData()
    }

    private fun initData() {
    }
}