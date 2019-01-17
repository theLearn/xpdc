package com.xp.dc.xpdc.widget.choosecar;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

/**
 * Created by hasee on 2018/3/20.
 */

public class StatisticsListView extends HorizontalScrollView {

    private LinearLayout mLinearLayout;

    @Override
    public void setHorizontalScrollBarEnabled(boolean horizontalScrollBarEnabled) {
        super.setHorizontalScrollBarEnabled(false);
    }

    public StatisticsListView(Context context) {
        super(context);
        init(context);
    }


    public LinearLayout getLinearLayout() {
        return mLinearLayout;
    }

    private void init(Context context) {
        mLinearLayout = new LinearLayout(context);
        addView(mLinearLayout);
        setHorizontalScrollBarEnabled(false);
    }

    public StatisticsListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public StatisticsListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void addView(Toggle view) {
        getLinearLayout().addView(view);
    }

    public void clear() {
        getLinearLayout().removeAllViews();
    }

    public void notifyDataSetChanged(){

    }


    //    private Context mContext;
//    private BaseStatisticsAdapter<T> mBaseStatisticsAdapter;
//
//    public StatisticsListView(Context context) {
//        super(context);
//        init(context);
//    }
//
//    public void setData(List<IStatisticInfo<T>> data) {
//        if (null != mBaseStatisticsAdapter)
//            mBaseStatisticsAdapter.setList(data);
//    }
//
//    public StatisticsListView(Context context, @Nullable AttributeSet attrs) {
//        super(context, attrs);
//        init(context);
//    }
//
//    public StatisticsListView(Context context, @Nullable AttributeSet attrs, int defStyle) {
//        super(context, attrs, defStyle);
//        init(context);
//    }
//
//    private void init(Context context) {
//        setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
//        mBaseStatisticsAdapter = new BaseStatisticsAdapter<T>(mContext);
//        setAdapter(mBaseStatisticsAdapter);
//    }
//
//    public void setOnToggleChangeListener(BaseStatisticsAdapter.OnToggleChangeListener onToggleChangeListener) {
//        mBaseStatisticsAdapter.setOnToggleChangeListener(onToggleChangeListener);
//    }
//
//    public void setAdapter(BaseStatisticsAdapter adapter) {
//        super.setAdapter(adapter);
//    }
}
