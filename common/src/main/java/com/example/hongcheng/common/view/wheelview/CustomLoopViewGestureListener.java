package com.example.hongcheng.common.view.wheelview;

import android.view.MotionEvent;


/**
 * 手势监听
 */
public final class CustomLoopViewGestureListener extends android.view.GestureDetector.SimpleOnGestureListener {

    private final CustomWheelView wheelView;


    public CustomLoopViewGestureListener(CustomWheelView wheelView) {
        this.wheelView = wheelView;
    }

    @Override
    public final boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        wheelView.scrollBy(velocityY);
        return true;
    }
}
