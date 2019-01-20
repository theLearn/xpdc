package com.example.hongcheng.common.view.citylist;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.hongcheng.common.R;
import com.example.hongcheng.common.base.BaseListAdapter;
import com.example.hongcheng.common.view.DividerItemDecoration;
import com.example.hongcheng.common.view.EmptyRecyclerView;

public class CityListSelectView extends LinearLayout implements LetterListView.OnTouchingLetterChangedListener, View.OnClickListener {

    private Handler handler;
    private OverlayThread overlayThread;
    private WindowManager windowManager;
    private TextView overlay;
    private EmptyRecyclerView mRecyclerView;
    private CityListAdapter mAdapter;
    private TextView tvCurrentCity;
    private OnCitySelectListener listener;

    public CityListSelectView(Context context) {
        this(context, null);
    }

    public CityListSelectView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CityListSelectView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
        init();
    }

    private void initAttrs(Context context, AttributeSet attrs) {

    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_city_list, this);

        findViewById(R.id.ll_current_city_block).setOnClickListener(this);
        tvCurrentCity = findViewById(R.id.tv_city_current);

        View emptyView = LayoutInflater.from(getContext()).inflate(R.layout.layout_list_nothing, this, false);
        mRecyclerView = findViewById(R.id.city_container);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(
                new DividerItemDecoration(getContext(), DividerItemDecoration.HORIZONTAL_LIST, 1,
                        getResources().getColor(R.color.gray_e6)));
        mAdapter = new CityListAdapter();
        mAdapter.setSource(CityData.getInstance().getAllSource());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setEmptyView(emptyView);
        emptyView.setVisibility(View.GONE);

        mAdapter.setOnItemClickListener(new BaseListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if(listener != null) {
                    CityItem cityItem = mAdapter.getItemInfo(position);
                    if(cityItem != null) {
                        listener.onSelect(cityItem.getName(), false);
                    }
                }
            }

            @Override
            public void onItemLongClick(int position) {

            }
        });

        LetterListView letter_container = findViewById(R.id.letter_container);
        letter_container.setOnTouchingLetterChangedListener(this);
        initOverlay();
    }

    private void initOverlay() {
        handler = new Handler();
        overlayThread = new OverlayThread();
        LayoutInflater inflater = LayoutInflater.from(getContext());
        overlay = (TextView) inflater.inflate(R.layout.overlay, null);
        overlay.setVisibility(View.INVISIBLE);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                PixelFormat.TRANSLUCENT);
        windowManager = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);
        windowManager.addView(overlay, lp);
    }

    public void onDestroy() {
        windowManager.removeView(overlay);
    }

    @Override
    public void onTouchingLetterChanged(String s) {
        int index = mAdapter.getSelectIndex(s);
        mRecyclerView.scrollToPosition(index);
        overlay.setText(s);
        overlay.setVisibility(View.VISIBLE);
        handler.removeCallbacks(overlayThread);
        // Уoverlay
        handler.postDelayed(overlayThread, 1500);
    }

    // overlay
    private class OverlayThread implements Runnable {

        @Override
        public void run() {
            overlay.setVisibility(View.GONE);
        }

    }

    @Override
    public void onClick(View v) {
        if (R.id.ll_current_city_block  == v.getId()) {
            if(listener != null) {
                listener.onSelect(tvCurrentCity.getText().toString().trim(), true);
            }
        }
    }

    public void setCurrentCity(String cityName) {
        tvCurrentCity.setText(cityName);
    }

    public void setSearchSource(String searchKey) {
        mAdapter.setSource(CityData.getInstance().getSearchSource(searchKey));
        mAdapter.notifyDataSetChanged();
    }

    public void setOnCitySelectListener(OnCitySelectListener listener) {
        this.listener = listener;
    }

    public interface OnCitySelectListener{
        void onSelect(String cityName, boolean isCurrentCity);
    }
}
