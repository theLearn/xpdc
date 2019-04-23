package com.xp.dc.xpdc.activity

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.view.View
import com.example.hongcheng.common.base.BasicActivity
import com.example.hongcheng.common.util.ImageLoadUtils
import com.xp.dc.xpdc.R
import kotlinx.android.synthetic.main.activity_user_center.*
import me.iwf.photopicker.PhotoPicker

class UserCenterActivity : BasicActivity(), View.OnClickListener {

    override fun initView() {
        findViewById<View>(R.id.back).setOnClickListener(this)
        findViewById<View>(R.id.rl_nickname).setOnClickListener(this)
        findViewById<View>(R.id.rl_sex).setOnClickListener(this)
        iv_head.setOnClickListener(this)
    }

    override fun getLayoutResId(): Int {
        return R.layout.activity_user_center
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.back -> finish()
            R.id.iv_head -> changeIcon()
            R.id.rl_nickname -> changeNick()
            R.id.rl_sex -> changeSex()
        }
    }

    private fun changeSex() {
        val sexs = arrayOf("男", "女")
        val dialog = AlertDialog.Builder(this)
                .setItems(sexs) { dialog, which ->
                    tv_sex!!.text = sexs[which]
                    dialog.dismiss()
                }.show()
    }

    private fun changeNick() {
        startActivityForResult(Intent(this, ModifyInfoActivity::class.java), NICK_NAME)
    }

    private fun changeIcon() {
        PhotoPicker.builder()
                .setPhotoCount(1)
                .setShowCamera(true)
                .setShowGif(true)
                .setPreviewEnabled(false)
                .start(this, PhotoPicker.REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == PhotoPicker.REQUEST_CODE) {
            if (data != null) {
                val photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS)
                if (photos != null && photos.size != 0) {
                    val path = photos[0]
                    ImageLoadUtils.bindImageUrlForCycle(iv_head!!, path, R.mipmap.ico_head)
                }
            }
        } else if (resultCode == Activity.RESULT_OK && requestCode == NICK_NAME) {
            tv_nickname!!.text = "哈哈"
        }
    }

    companion object {
        private val NICK_NAME = 101
    }
}
