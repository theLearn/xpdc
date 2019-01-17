package com.xp.dc.xpdc.widget.choosecar;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.example.hongcheng.common.view.DividerItemDecoration;
import com.xp.dc.xpdc.R;
import com.xp.dc.xpdc.bean.CarClassfyInfo;
import com.xp.dc.xpdc.bean.CarInfo;

import java.util.ArrayList;
import java.util.List;

public class ChooseView extends LinearLayout implements View.OnClickListener {
    private Context mContext;
    private View view;
    private RecyclerView rv_type;
    private RecyclerView rv_car;
    private List<CarClassfyInfo> carClassfyInfos = new ArrayList<>();
    private List<CarInfo> carInfos = new ArrayList<>();
    private ChooseAdapter chooseAdapter;
    private CarAdapter carAdapter;
    private boolean isOpen;//false表示未展开，true表示展开

    public ChooseView(Context context) {
        super(context);
        this.mContext = context;
        initView();
    }


    public ChooseView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initView();
    }

    public ChooseView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initView();
    }

    private void initView() {
        chooseAdapter = new ChooseAdapter(mContext, carClassfyInfos);
        view = LayoutInflater.from(mContext).inflate(R.layout.layout_choose, null);
        rv_type = view.findViewById(R.id.rv_type);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv_type.setLayoutManager(linearLayoutManager);
        rv_type.setAdapter(chooseAdapter);


        chooseAdapter.setOnItemClickListener(new ChooseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                carInfos.clear();
                carInfos.addAll(carClassfyInfos.get(position).getCarInfo());
                carAdapter.notifyDataSetChanged();
            }
        });

        //未展开的子view
        carAdapter = new CarAdapter(mContext, carInfos);
        rv_car = view.findViewById(R.id.rv_car);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(mContext);
        linearLayoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv_car.setLayoutManager(linearLayoutManager1);
        rv_car.setAdapter(carAdapter);
        rv_car.addItemDecoration(new SpaceItemDecoration(10));
        carAdapter.setOnItemClickListener(new ChooseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                CarInfo carInfo = carInfos.get(position);
                for (CarClassfyInfo classfyInfo : carClassfyInfos) {
                    for (CarInfo info : classfyInfo.getCarInfo()) {
                        if (carInfo.getId() != info.getId())
                            info.setChecked(false);
                    }
                }
                carAdapter.notifyDataSetChanged();
            }
        });

        view.findViewById(R.id.iv_change).setOnClickListener(this);
        view.findViewById(R.id.iv_call).setOnClickListener(this);

        addView(view);
    }

    public void refreshData(List<CarClassfyInfo> data) {
        carClassfyInfos.clear();
        if (data.size() != 0)
            data.get(0).setChecked(true);
        carClassfyInfos.addAll(data);
        chooseAdapter.notifyDataSetChanged();
    }

    public void setMode(boolean mode) {
        this.isOpen = mode;
        for (CarClassfyInfo classfyInfo : carClassfyInfos) {
            for (CarInfo info : classfyInfo.getCarInfo()) {
                info.setChecked(false);
            }
        }
        chooseAdapter.notifyDataSetChanged();
        carAdapter.notifyDataSetChanged();
        if (isOpen) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            rv_type.setLayoutManager(linearLayoutManager);
            rv_car.setVisibility(GONE);
        } else {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            rv_type.setLayoutManager(linearLayoutManager);
            rv_car.setVisibility(VISIBLE);
        }
        chooseAdapter.setMode(mode);
        carAdapter.setMode(mode);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_change:
                setMode(!isOpen);
                break;
            case R.id.iv_call:
                break;
        }
    }
}
