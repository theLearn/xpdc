package com.xp.dc.xpdc.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.*;
import android.widget.*;
import com.example.hongcheng.common.util.ToastUtils;
import com.xp.dc.xpdc.R;
import com.xp.dc.xpdc.widget.PayPwdEditText;

public class LoginFragment extends DialogFragment implements DialogInterface.OnKeyListener, View.OnClickListener {

    private Dialog dialogView;
    private EditText et_telNo;
    private CheckBox checkbox;
    private Button btn_next;
    private PayPwdEditText mPayPwdEditText;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // 使用不带Theme的构造器, 获得的dialog边框距离屏幕仍有几毫米的缝隙。
        dialogView = new Dialog(getContext(), R.style.Login_Dialog);
        dialogView.requestWindowFeature(Window.FEATURE_NO_TITLE); // 设置Content前设定
        dialogView.setContentView(R.layout.login_dialog);
        dialogView.setCanceledOnTouchOutside(false); // 外部点击取消
        // 设置宽度为屏宽, 靠近屏幕底部。
        final Window window = dialogView.getWindow();
        if (window != null) {
            window.setWindowAnimations(R.style.AnimTop);
            final WindowManager.LayoutParams lp = window.getAttributes();
            Point size = new Point();
            Display display = getActivity().getWindowManager().getDefaultDisplay();
            display.getSize(size);
            lp.gravity = Gravity.CENTER;
            lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(lp);
        }

        dialogView.setOnKeyListener(this);

        initView();
        return dialogView;
    }

    private void initView() {
        et_telNo = dialogView.findViewById(R.id.et_telNo);
        checkbox = dialogView.findViewById(R.id.checkbox);
        btn_next = dialogView.findViewById(R.id.btn_next);
        btn_next.setOnClickListener(this);
    }

    @Override
    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (dialogView.isShowing()) {
                dismiss();
            }
            return true;
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_next:
                toNext();
                break;
        }
    }

    private void toNext() {
        String telNo = et_telNo.getText().toString().trim();
        if (TextUtils.isEmpty(telNo)) {
            ToastUtils.show(getContext(), getString(R.string.inputNo));
            return;
        }
        if (!checkbox.isChecked()) {
            ToastUtils.show(getContext(), "请表明您已经阅读并接受会员注册协议和隐私政策");
            return;
        }
        dialogView.setContentView(R.layout.password_dialog);
        initPwdView();
    }

    private void initPwdView() {
        mPayPwdEditText = dialogView.findViewById(R.id.ppe_pwd);
        mPayPwdEditText.initStyle(R.drawable.edit_num_bg, 4, 10, R.color.white, R.color.text_black, 20);
        mPayPwdEditText.setFocus();
        mPayPwdEditText.setOnTextFinishListener(new PayPwdEditText.OnTextFinishListener() {
            @Override
            public void onFinish(String str) {//密码输入完后的回调
                if ("1111".equals(str)) {
                    ToastUtils.show(getContext(), "登录成功");
                    dismiss();
                } else {
                    Toast.makeText(getContext(), "密码错误", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
