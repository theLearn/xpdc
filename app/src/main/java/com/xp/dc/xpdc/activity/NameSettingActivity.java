package com.xp.dc.xpdc.activity;

import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.hongcheng.common.util.SPUtils;
import com.example.hongcheng.common.util.SpanUtils;
import com.example.hongcheng.common.util.ToastUtils;
import com.xp.dc.xpdc.R;
import com.xp.dc.xpdc.constants.Constants;
import org.jetbrains.annotations.NotNull;

public class NameSettingActivity extends AppCommonActivity implements View.OnClickListener {


    private EditText et_nickname;
    private ImageView iv_clear;

    @Override
    public boolean isNeedShowBack() {
        return true;
    }

    @Override
    public int setToolbarTitle() {
        return R.string.change_nick;
    }

    @Override
    public int getBodyLayoutResId() {
        return R.layout.activity_name_setting;
    }

    @Override
    public void initBodyView(@NotNull View view) {
        et_nickname = view.findViewById(R.id.et_nickname);
        iv_clear = findViewById(R.id.iv_clear);
        iv_clear.setOnClickListener(this);
        et_nickname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0) {
                    iv_clear.setVisibility(View.GONE);
                } else {
                    iv_clear.setVisibility(View.VISIBLE);
                }
            }
        });
        initData();
    }

    private void initData() {

    }

    @Override
    public void initTitleView(@NotNull View view) {
        super.initTitleView(view);
        TextView tv_app_common_right = view.findViewById(R.id.tv_app_common_right);
        tv_app_common_right.setVisibility(View.VISIBLE);
        tv_app_common_right.setText(R.string.complete);
        tv_app_common_right.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_app_common_right:
                String nickname = et_nickname.getText().toString().trim();
                if (TextUtils.isEmpty(nickname)) {
                    ToastUtils.show(this, "昵称不能为空");
                    return;
                }
                commitData(nickname);
                break;
            case R.id.iv_clear:
                break;
        }
    }

    private void commitData(String nickname) {
        Intent intent = new Intent();
        intent.putExtra(Constants.NICKNAME, nickname);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public int getMessageLayoutResId() {
        return 0;
    }
}
