package com.xp.dc.xpdc.widget.choosecar;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.xp.dc.xpdc.R;
import com.xp.dc.xpdc.bean.CarClassfyInfo;
import com.xp.dc.xpdc.bean.CarInfo;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by wangwei on 2018/4/12.
 */

public class BottomView extends LinearLayout {
    private Activity mContext;
    private View view;
    View contentview;
    LinearLayout layoutContainer;
    MyTabView tabView;
    boolean isShow;
    boolean isShowHeadView = true;
    private BottomSheetBehavior bottomSheetBehavior;

    public void setShowHeadView(boolean showHeadView) {
        isShowHeadView = showHeadView;
        if (isShowHeadView) {
            tabView.setVisibility(VISIBLE);
        } else {
            tabView.setVisibility(GONE);
        }
    }

    public BottomView(Context context) {
        super(context);
        this.mContext = (Activity) context;
        initView();
    }

    public BottomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = (Activity) context;
        initView();
    }

    private void initView() {
        view = LayoutInflater.from(mContext).inflate(R.layout.layout_bottomview, null);
        layoutContainer = view.findViewById(R.id.layout_container);
        tabView = new MyTabView(mContext);
        layoutContainer.addView(tabView);
        addView(view);

    }






    public void refreshHeadView(List<CarClassfyInfo> firstList) {
        tabView.initContent(firstList);
    }

    public void setPeekHeight(int height) {
        bottomSheetBehavior.setPeekHeight(height);
    }

    public void expanded() {
        if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED ||
                bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_HIDDEN) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        }
    }

    public void collapsed() {
        isShow = true;
        if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED ||
                bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_HIDDEN) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
        if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_SETTLING) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
    }

    public void hide() {
        isShow = false;
        if (bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_HIDDEN) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        }
    }

    public void initBeHavor(BottomSheetBehavior behavior) {
        bottomSheetBehavior = behavior;
        bottomSheetBehavior.setHideable(true);
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

                // Check Logs to see how bottom sheets behaves
                switch (newState) {
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        if (null != onBottomUpListener) {
                            onBottomUpListener.onBottomUp();
                        }
                        Log.e("Bottom Sheet Behaviour", "STATE_COLLAPSED");
                        break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        Log.e("Bottom Sheet Behaviour", "STATE_DRAGGING");
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:
                        Log.e("Bottom Sheet Behaviour", "STATE_EXPANDED");
                        break;
                    case BottomSheetBehavior.STATE_HIDDEN:
                        if (null != onBottomUpListener) {
                            onBottomUpListener.onBottomDown();
                        }
                        Log.e("Bottom Sheet Behaviour", "STATE_HIDDEN");
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        Log.e("Bottom Sheet Behaviour", "STATE_SETTLING");
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
    }

    private void tvOverFlowed(final TextView textView) {
        ViewTreeObserver vto = textView.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @SuppressWarnings("deprecation")
            @Override
            public void onGlobalLayout() {
                textView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                textView.getHeight();
                double w0 = textView.getWidth();//控件宽度
                double w1 = textView.getPaint().measureText(textView.getText().toString());//文本宽度
                if (w1 >= w0) {
                    //需要换行
                    String[] ss = textView.getText().toString().split("-");
                    textView.setText(ss[0] + "-" + ss[1] + "-" + ss[2] + "-" + ss[3] + "\n" + ss[4]);
                }

            }
        });
    }


    public void addContentView(int resId) {
        if (null != contentview) {
            layoutContainer.removeView(contentview);
        }
        contentview = LayoutInflater.from(mContext).inflate(resId, null);
        layoutContainer.addView(contentview);
    }

    public void addContentView(View view) {
        if (null != contentview) {
            layoutContainer.removeView(contentview);
        }
        contentview = view;
        layoutContainer.addView(contentview);
    }

    public void removeContentView() {
        if (null != contentview) {
            layoutContainer.removeView(contentview);
        }
    }

    public void handIntentData(ArrayList<CarClassfyInfo> list) {
        this.list = list;
        refreshHeadView(list);
        removeContentView();
    }

    public void setToggleTopText(ArrayList<CarInfo> list) {
        tabView.setToggleTopText(list);
    }

    ArrayList<CarClassfyInfo> list;

    public ArrayList<CarClassfyInfo> getList() {
        return this.list;
    }

    public interface OnBottomUpListener {
        void onBottomUp();

        void onBottomDown();
    }

    OnBottomUpListener onBottomUpListener;

    public void setOnBottomUpListener(OnBottomUpListener onBottomUpListener) {
        this.onBottomUpListener = onBottomUpListener;
    }

    MyTabView.OnTabChangeListener mOnTabChangeListener;

    public void setOnTabChangeListener(MyTabView.OnTabChangeListener onTabChangeListener) {
        this.mOnTabChangeListener = onTabChangeListener;
        if (null != tabView)
            tabView.setOnTabChangeListener(mOnTabChangeListener);
    }
}
