package com.xp.dc.xpdc.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.example.hongcheng.common.util.ToastUtils;
import com.xp.dc.xpdc.R;
import org.jetbrains.annotations.NotNull;

public class PasswordActivity extends AppCommonActivity implements View.OnClickListener {

    private EditText et_tel_phone;
    private EditText et_code;
    private EditText et_pwd;
    private EditText et_pwd_again;

    @Override
    public boolean isNeedShowBack() {
        return true;
    }

    @Override
    public int setToolbarTitle() {
        return R.string.change_pwd;
    }

    @Override
    public int getBodyLayoutResId() {
        return R.layout.activity_change_pwd;
    }

    @Override
    public void initBodyView(@NotNull View view) {
        et_tel_phone = view.findViewById(R.id.et_tel_phone);
        et_code = view.findViewById(R.id.et_code);
        et_pwd = view.findViewById(R.id.et_pwd);
        et_pwd_again = view.findViewById(R.id.et_pwd_again);
        view.findViewById(R.id.tv_commit).setOnClickListener(this);
        view.findViewById(R.id.btn_send).setOnClickListener(this);
    }

    @Override
    public int getMessageLayoutResId() {
        return 0;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_commit:
                commitData();
                break;
            case R.id.btn_send:
                sendCode();
                break;
        }
    }

    private void sendCode() {
        String telphone = et_tel_phone.getText().toString().trim();
        if (TextUtils.isEmpty(telphone)){
            ToastUtils.show(this,"请输入电话号码");
            return;
        }
    }

    private void commitData() {
        String telphone = et_tel_phone.getText().toString().trim();
        String code = et_code.getText().toString().trim();
        String password = et_pwd.getText().toString().trim();
        String passwordConfirm = et_pwd_again.getText().toString().trim();
        if (TextUtils.isEmpty(telphone)||TextUtils.isEmpty(code)||TextUtils.isEmpty(password)||TextUtils.isEmpty(passwordConfirm)){
            ToastUtils.show(this,"信息未填写完整");
            return;
        }
        if (!password.equals(passwordConfirm)){
            ToastUtils.show(this,"两次密码不一致");
            return;
        }
    }
}
