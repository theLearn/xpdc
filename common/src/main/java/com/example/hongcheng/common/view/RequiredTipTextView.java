package com.example.hongcheng.common.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.hongcheng.common.R;

public class RequiredTipTextView extends LinearLayout {
    private static final int BACK_MODE = 1;
    private static final int HEAD_MODE = 2;

    private TextView content;

    public RequiredTipTextView(Context context) {
        this(context, null);
    }

    public RequiredTipTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RequiredTipTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        if (attrs == null) {
            return;
        }
        //获取自定义属性。
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RequiredTipTextView);
        //获取字体大小,默认大小是12dp
        int fontSize = (int) ta.getDimension(R.styleable.RequiredTipTextView_requiredTextSize, 12);

        //星号大小,默认大小是5dp
        int width = (int) ta.getDimension(R.styleable.RequiredTipTextView_requiredWidth, 5);
        int height = (int) ta.getDimension(R.styleable.RequiredTipTextView_requiredHeight, 5);
        int space = ta.getDimensionPixelSize(R.styleable.RequiredTipTextView_requiredSpace, 5);

        //获取文字内容
        String text = ta.getString(R.styleable.RequiredTipTextView_requiredText);

        //获取文字颜色，默认颜色是BLUE
        int color = ta.getColor(R.styleable.RequiredTipTextView_requiredTextColor, Color.GRAY);

        int mode = ta.getInt(R.styleable.RequiredTipTextView_turn_mode, 1);
        ta.recycle();

        content = new TextView(context);
        content.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        content.setText(text);
        content.setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSize);
        content.setTextColor(color);
        content.setGravity(Gravity.CENTER);

        ImageView icon = new ImageView(context);
        icon.setLayoutParams(new LayoutParams(width, height));
        icon.setImageResource(R.drawable.icon_required);

        this.addView(content);
        if (BACK_MODE == mode) {
            content.setPadding(0, 0, space, 0);
            this.addView(icon);
        } else if (HEAD_MODE == mode) {
            content.setPadding(space, 0, 0, 0);
            this.addView(icon, 0);
        }
    }

    public void setText(String text) {
        content.setText(text);
    }

    public void setText(int resId) {
        content.setText(resId);
    }
}
