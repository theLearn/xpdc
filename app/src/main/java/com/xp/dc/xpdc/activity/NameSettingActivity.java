package com.xp.dc.xpdc.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.example.hongcheng.common.util.ToastUtils;
import com.xp.dc.xpdc.R;
import org.jetbrains.annotations.NotNull;

public class NameSettingActivity extends AppCommonActivity implements View.OnClickListener {


    private EditText et_nickname;

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
        }
    }

    private void commitData(String nickname) {

    }

    @Override
    public int getMessageLayoutResId() {
        return 0;
    }
}
