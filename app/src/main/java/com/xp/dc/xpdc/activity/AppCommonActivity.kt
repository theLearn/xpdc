package com.xp.dc.xpdc.activity

import android.support.v7.app.ActionBar
import android.view.View
import com.example.hongcheng.common.base.CommonActivity
import com.example.hongcheng.common.util.ScreenUtils
import com.xp.dc.xpdc.R
import kotlinx.android.synthetic.main.layout_app_common_title.*


abstract class AppCommonActivity : CommonActivity() {
    override fun getTitleLayoutResId(): Int {
        return R.layout.layout_app_common_title
    }

    override fun initTitleView(view: View) {
        setSupportActionBar(tb_app_common)
        ScreenUtils.setWindowStatusBarColor(this, resources.getColor(R.color.white))
        ScreenUtils.setLightStatusBar(this, true)
        if (isNeedShowBack()) {
            tb_app_common.setNavigationIcon(R.drawable.back)
            tb_app_common.setNavigationOnClickListener { onBackPressed() }
        }
        val actionBar : ActionBar? = supportActionBar
        actionBar?.title = ""
        tv_app_common_title.setText(setToolbarTitle())
    }
}