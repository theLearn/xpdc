package com.example.hongcheng.common.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class FrameLayoutForSquare extends FrameLayout
{

    public FrameLayoutForSquare(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    public FrameLayoutForSquare(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public FrameLayoutForSquare(Context context)
    {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        setMeasuredDimension(this.getMeasuredWidth(), this.getMeasuredWidth());
    }
}
