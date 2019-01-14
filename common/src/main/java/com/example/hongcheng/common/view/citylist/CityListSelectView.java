package com.example.hongcheng.common.view.citylist;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import com.example.hongcheng.common.R;
import com.example.hongcheng.common.view.DividerItemDecoration;
import com.example.hongcheng.common.view.EmptyRecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CityListSelectView extends LinearLayout implements LetterListView.OnTouchingLetterChangedListener {

    private CityListAdapter mAdapter;

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

        EmptyRecyclerView mRecyclerView = findViewById(R.id.city_container);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(
                new DividerItemDecoration(getContext(), DividerItemDecoration.HORIZONTAL_LIST, 1,
                        getResources().getColor(R.color.gray_e6)));
        mAdapter = new CityListAdapter();
        mRecyclerView.setAdapter(mAdapter);

        LetterListView letter_container = findViewById(R.id.letter_container);
        letter_container.setOnTouchingLetterChangedListener(this);
    }

    public void load() {
        List<Pair<String, List<CityItem>>> source = new ArrayList<>();
        source.add(Pair.create("0", CityData.getHotCityList()));

        List<CityItem> list = CityData.getCityList();
        Collections.sort(list, comparator);
        String lastTitle = "";
        for (CityItem cityItem : list) {
            if (!source.isEmpty()) {
                lastTitle = source.get(source.size() - 1).first;
            }
            String currentTitle = cityItem.getPinyin().substring(0, 1);
            if (currentTitle.equals(lastTitle)) {
                source.get(source.size() - 1).second.add(cityItem);
            } else {
                List<CityItem> temp = new ArrayList<>();
                temp.add(cityItem);
                source.add(Pair.create(currentTitle, temp));
            }
        }
        mAdapter.setSource(source);
        mAdapter.notifyDataSetChanged();
    }

    /**
     * a-z排序
     */
    Comparator comparator = new Comparator<CityItem>() {
        @Override
        public int compare(CityItem lhs, CityItem rhs) {
            String a = lhs.getPinyin().substring(0, 1);
            String b = rhs.getPinyin().substring(0, 1);
            int flag = a.compareTo(b);
            if (flag == 0) {
                return a.compareTo(b);
            } else {
                return flag;
            }
        }
    };

    @Override
    public void onTouchingLetterChanged(String s) {

    }
}
