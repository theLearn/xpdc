package com.example.hongcheng.common.base

import android.support.v7.app.ActionBar
import android.view.View
import com.example.hongcheng.common.R
import com.example.hongcheng.common.util.ScreenUtils
import com.example.hongcheng.common.view.spinkit.SpriteFactory
import com.example.hongcheng.common.view.spinkit.Style
import com.example.hongcheng.common.view.spinkit.sprite.Sprite
import com.example.hongcheng.common.view.spinkit.sprite.SpriteContainer
import kotlinx.android.synthetic.main.layout_common_message.*
import kotlinx.android.synthetic.main.layout_common_title.*


abstract class CommonActivity : BaseActivity() {
    override fun getTitleLayoutResId(): Int {
        return R.layout.layout_common_title
    }

    override fun initTitleView(view: View) {
        setSupportActionBar(tb_common)
        ScreenUtils.setLightStatusBar(this, true)
        ScreenUtils.setWindowStatusBarColor(this, R.color.colorBase)
        if (isNeedShowBack()) {
            tb_common.setNavigationIcon(R.drawable.back)
            tb_common.setNavigationOnClickListener { onBackPressed() }
        }

        val actionBar: ActionBar? = supportActionBar
        actionBar?.setTitle(setToolbarTitle())
    }

    override fun getMessageLayoutResId(): Int {
        return R.layout.layout_common_message
    }

    override fun initMessageView(view: View) {
        val style: Style = Style.values()[7]
        val drawable: Sprite = SpriteFactory.create(style)
        (drawable as SpriteContainer).color = resources.getColor(R.color.colorBase)
        spin_kit.setIndeterminateDrawable(drawable)
    }

    abstract fun isNeedShowBack(): Boolean
    abstract fun setToolbarTitle(): Int
}