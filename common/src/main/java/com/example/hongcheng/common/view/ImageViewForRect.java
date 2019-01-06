package com.example.hongcheng.common.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public class ImageViewForRect extends ImageView
{

    public ImageViewForRect(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    public ImageViewForRect(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public ImageViewForRect(Context context)
    {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        setMeasuredDimension(this.getMeasuredWidth(), this.getMeasuredWidth() * 3 / 4);
    }
}
