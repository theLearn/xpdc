package com.xp.dc.xpdc.activity

import android.app.Activity
import android.text.TextUtils
import android.view.View
import com.example.hongcheng.common.util.ToastUtils
import com.example.hongcheng.common.util.ViewUtils
import com.xp.dc.xpdc.R
import kotlinx.android.synthetic.main.body_modify_info.*
import kotlinx.android.synthetic.main.layout_app_common_title.*

class ModifyInfoActivity  : AppCommonActivity(), View.OnClickListener {

    override fun isNeedShowBack(): Boolean {
        return true
    }

    override fun setToolbarTitle(): Int {
        return R.string.change_nick
    }

    override fun initTitleView(view: View) {
        super.initTitleView(view)
        tv_app_common_right.visibility = View.VISIBLE
        tv_app_common_right.setTextColor(resources.getColor(R.color.white))
        tv_app_common_right.setBackgroundResource(R.drawable.bg_theme_round)
        tv_app_common_right.setText(R.string.complete)
        tv_app_common_right.setOnClickListener(this)

    }

    override fun getMessageLayoutResId(): Int {
        return 0
    }

    override fun getBodyLayoutResId(): Int {
        return R.layout.body_modify_info
    }

    override fun initBodyView(view: View) {
    }

    override fun onClick(view: View?) {
        if (ViewUtils.isFastClick()) return
        when (view?.id) {
            R.id.tv_app_common_right -> {
                submit()
            }
            else -> {

            }
        }
    }

    private fun submit() {
        if(TextUtils.isEmpty(et_modify_info.text.toString().trim())) {
            ToastUtils.show(this, "请输入昵称")
            return
        }
        setResult(Activity.RESULT_OK)
        finish()
    }
}