package com.example.hongcheng.common.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import com.example.hongcheng.common.R;

public class LinearLayoutForRect extends LinearLayout
{
    private float scalse;

    public LinearLayoutForRect(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        initView(context, attrs);
    }

    public LinearLayoutForRect(Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public LinearLayoutForRect(Context context)
    {
        this(context, null);
    }

    private void initView(Context context, AttributeSet attrs) {
        //获取自定义属性。
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.LinearLayoutForRect);
        scalse = ta.getFloat(R.styleable.LinearLayoutForRect_ll_scale, 1);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        setMeasuredDimension(this.getMeasuredWidth(), (int) (scalse * this.getMeasuredWidth()));
    }
}
