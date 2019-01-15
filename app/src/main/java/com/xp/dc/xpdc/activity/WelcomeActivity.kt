package com.xp.dc.xpdc.activity

import android.content.Intent
import com.example.hongcheng.common.base.BasicActivity
import com.example.hongcheng.common.util.ToastUtils
import com.example.hongcheng.common.view.citylist.CityData
import com.xp.dc.xpdc.R

class WelcomeActivity : BasicActivity() {

    override fun getLayoutResId(): Int {
        return R.layout.activity_welcome
    }

    override fun initView() {
        verifyPermissions()
    }

    override fun requestPermission(isSuccess: Boolean) {
        super.requestPermission(isSuccess)
        if(isSuccess) {
            CityData.getInstance().init()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        } else {
            ToastUtils.show(this, "本应用需要必要的权限才能使用！")
            finish()
        }
    }
}