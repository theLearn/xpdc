package com.xp.dc.xpdc.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.hongcheng.common.base.BasicActivity;
import com.example.hongcheng.common.util.ImageLoadUtils;
import com.xp.dc.xpdc.R;
import com.xp.dc.xpdc.constants.Constants;
import me.iwf.photopicker.PhotoPicker;

import java.util.ArrayList;

public class UserCenterActivity extends BasicActivity implements View.OnClickListener {

    private static final int NICK_NAME = 101;
    private static final int TEL_NO = 102;
    private ImageView iv_head;
    private TextView tv_nickname;
    private TextView tv_sex;
    private TextView tv_tel;

    @Override
    public void initView() {
        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.rl_nickname).setOnClickListener(this);
        findViewById(R.id.rl_sex).setOnClickListener(this);
        findViewById(R.id.rl_telNo).setOnClickListener(this);
        iv_head = findViewById(R.id.iv_head);
        iv_head.setOnClickListener(this);
        tv_nickname = (TextView) findViewById(R.id.tv_nickname);
        tv_sex = (TextView) findViewById(R.id.tv_sex);
        tv_tel = (TextView) findViewById(R.id.tv_tel);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_user_center;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.iv_head:
                changeIcon();
                break;
            case R.id.rl_nickname:
                changeNick();
                break;
            case R.id.rl_sex:
                changeSex();
                break;
            case R.id.rl_telNo:
                changeTelNo();
                break;
        }
    }

    private void changeTelNo() {
        startActivityForResult(new Intent(this, TelPhoneActivity.class), TEL_NO);
    }

    private void changeSex() {
        final String[] sexs = {"男", "女"};
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setItems(sexs, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tv_sex.setText(sexs[which]);
                        dialog.dismiss();
                    }
                }).show();
    }

    private void changeNick() {
        startActivityForResult(new Intent(this, NameSettingActivity.class), NICK_NAME);
    }

    private void changeIcon() {
        PhotoPicker.builder()
                .setPhotoCount(1)
                .setShowCamera(true)
                .setShowGif(true)
                .setPreviewEnabled(false)
                .start(this, PhotoPicker.REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == PhotoPicker.REQUEST_CODE) {
            if (data != null) {
                ArrayList<String> photos =
                        data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);

                if (photos != null && photos.size() != 0) {
                    String path = photos.get(0);
                    Bitmap bitmap = BitmapFactory.decodeFile(path);
                    Bitmap roundBitmap = ImageLoadUtils.toRoundBitmap(bitmap);
                    Glide.with(this).load(roundBitmap).into(iv_head);
                }
            }
        } else if (resultCode == RESULT_OK && requestCode == NICK_NAME) {
            if (data != null) {
                tv_nickname.setText(data.getStringExtra(Constants.NICKNAME));
            }
        } else if (resultCode == RESULT_OK && requestCode == TEL_NO) {
            if (data != null) {
                tv_tel.setText(data.getStringExtra(Constants.TEL_NO));
            }
        }
    }
}
