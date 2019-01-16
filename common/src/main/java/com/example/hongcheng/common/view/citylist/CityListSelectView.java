package com.example.hongcheng.common.view.citylist;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.hongcheng.common.R;
import com.example.hongcheng.common.base.BaseListAdapter;
import com.example.hongcheng.common.view.DividerItemDecoration;
import com.example.hongcheng.common.view.EmptyRecyclerView;

public class CityListSelectView extends LinearLayout implements LetterListView.OnTouchingLetterChangedListener, View.OnClickListener {

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

        EmptyRecyclerView mRecyclerView = findViewById(R.id.city_container);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(
                new DividerItemDecoration(getContext(), DividerItemDecoration.HORIZONTAL_LIST, 1,
                        getResources().getColor(R.color.gray_e6)));
        mAdapter = new CityListAdapter();
        mAdapter.setSource(CityData.getInstance().getAllSource());
        mRecyclerView.setAdapter(mAdapter);

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
    }

    @Override
    public void onTouchingLetterChanged(String s) {

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

    public void setOnCitySelectListener(OnCitySelectListener listener) {
        this.listener = listener;
    }

    public interface OnCitySelectListener{
        void onSelect(String cityName, boolean isCurrentCity);
    }
}
