package com.xp.dc.xpdc.activity

import android.content.Intent
import android.os.Handler
import com.example.hongcheng.common.base.BasicActivity
import com.xp.dc.xpdc.R


class WelcomeActivity : BasicActivity() {

    override fun getLayoutResId(): Int {
        return R.layout.activity_welcome
    }

    override fun initView() {
        Handler().postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 2000)
        initData()
    }

    private fun initData() {
    }
}