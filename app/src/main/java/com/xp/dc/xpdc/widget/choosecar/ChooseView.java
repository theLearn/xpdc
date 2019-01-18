package com.xp.dc.xpdc.widget.choosecar;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.hongcheng.common.util.LoggerUtils;
import com.example.hongcheng.common.util.ToastUtils;
import com.example.hongcheng.common.view.DividerItemDecoration;
import com.xp.dc.xpdc.R;
import com.xp.dc.xpdc.bean.CarClassfyInfo;
import com.xp.dc.xpdc.bean.CarInfo;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ChooseView<T extends CarClassfyInfo> extends LinearLayout implements View.OnClickListener {
    private Context mContext;
    private View view;
    private RecyclerView rv_type;
    private RecyclerView rv_car;
    private List<T> carClassfyInfos = new ArrayList<>();
    private List<CarInfo> carInfos = new ArrayList<>();
    private ChooseAdapter<T> chooseAdapter;
    private CarHoriAdapter carAdapter;
    private boolean isOpen;//false表示未展开，true表示展开
    private ChooseAdapter<T> chooseVerAdapter;
    private RecyclerView rv_allcar;
    private LinearLayout ll_horizontal;
    private LinearLayout ll_vertical;
    private TextView tv_change;
    private TextView tv_call;
    private StatisticsListView mSl;
    private CarInfo chooseCarInfo;
    List<Toggle> toggleList = new ArrayList<>();
    private OnCallListener onCallListener;

    public void setOnCallListener(OnCallListener onCallListener) {
        this.onCallListener = onCallListener;
    }

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
        view = LayoutInflater.from(mContext).inflate(R.layout.layout_choose, null);
        initHoriView();
        initVeriView();
        addView(view);
    }

    private void initVeriView() {
        ll_vertical = (LinearLayout) view.findViewById(R.id.ll_vertical);
        chooseVerAdapter = new ChooseAdapter(mContext, carClassfyInfos);
        rv_allcar = view.findViewById(R.id.rv_allcar);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_allcar.setLayoutManager(linearLayoutManager);
        rv_allcar.setAdapter(chooseVerAdapter);
//        rv_allcar.getLayoutManager().smoothScrollToPosition(rv_allcar, null, 0);


//        chooseAdapter.setOnItemClickListener(new ChooseAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(int position) {
//                carInfos.clear();
//                carInfos.addAll(carClassfyInfos.get(position).getCarInfo());
//                carAdapter.notifyDataSetChanged();
//            }
//        });
    }

    private void initHoriView() {
        ll_horizontal = (LinearLayout) view.findViewById(R.id.ll_horizontal);
        chooseAdapter = new ChooseAdapter(mContext, carClassfyInfos);
        rv_type = view.findViewById(R.id.rv_type);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv_type.setLayoutManager(linearLayoutManager);
        rv_type.setAdapter(chooseAdapter);
        rv_type.getLayoutManager().smoothScrollToPosition(rv_type, null, 0);


        chooseAdapter.setOnItemClickListener(new ChooseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                carInfos.clear();
                carInfos.addAll(carClassfyInfos.get(position).getCarInfo());
                for (CarClassfyInfo carClassfyInfo : carClassfyInfos) {
                    for (CarInfo carInfo : carClassfyInfo.getCarInfo()) {
                        carInfo.setChecked(false);
                    }
                }
                carAdapter.notifyDataSetChanged();
                refreshSlView();
            }
        });


        //未展开的子view
        carAdapter = new CarHoriAdapter(mContext, carInfos);
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

        tv_change = view.findViewById(R.id.tv_change);
        tv_change.setOnClickListener(this);
        tv_call = view.findViewById(R.id.tv_call);
        tv_call.setOnClickListener(this);

        mSl = (StatisticsListView) view.findViewById(R.id.sl);
    }

    private void refreshSlView() {
        mSl.clear();
        toggleList.clear();
        for (int i = 0; i < carInfos.size(); i++) {
            final CarInfo carInfo = carInfos.get(i);
            final Toggle toggle = new Toggle(mContext);
            toggle.setType(carInfo.getCarName());
            toggle.setPosition(i);
            toggle.setTopTextSize(12);
            toggle.setButtonTextSize(12);
            toggle.setStrokeWidth(3);
            toggle.setInfo(carInfo);
            toggle.setTopText(carInfo.getCarName());
            toggle.setBottonText(carInfo.getPrice());
            toggle.setOn_text_res_id(carInfo.getCarIcon());
            toggle.setOff_text_res_id(carInfo.getCarIcon());
            toggle.setStyle(Toggle.Style.GREEN);
            toggle.setToggleOn(carInfo.isChecked());
            toggle.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toggle curToggle = null;
                    for (Toggle tog : toggleList) {
                        if (tog.isToggleOn()) {
                            curToggle = tog;
                            curToggle.setToggleOn(false);
                            break;
                        }
                    }
                    toggle.setToggleOn(true);

                }
            });
            mSl.addView(toggle);
            LayoutParams layoutParams = ((LayoutParams) toggle.getLayoutParams());
            if (carInfos.size() < 4) {
                layoutParams.width = ((Activity) mContext).getWindowManager().getDefaultDisplay().getWidth() / carInfos.size();
            } else {
                layoutParams.width = ((Activity) mContext).getWindowManager().getDefaultDisplay().getWidth() / 4;
            }
            toggle.setLayoutParams(layoutParams);
            toggleList.add(toggle);
        }
    }

    public void refreshData(List<T> data) {
        carClassfyInfos.clear();
        carClassfyInfos.addAll(data);
        chooseAdapter.notifyDataSetChanged();
        if (data.size() != 0) {
            data.get(0).setChecked(true);
            carInfos.clear();
            carInfos.addAll(data.get(0).getCarInfo());
            carAdapter.notifyDataSetChanged();
            refreshSlView();
        }
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
        chooseVerAdapter.notifyDataSetChanged();
        if (isOpen) {
//            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
//            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//            rv_type.setLayoutManager(linearLayoutManager);
//            rv_car.setVisibility(GONE);
            tv_change.setText("收缩");
            ll_horizontal.setVisibility(GONE);
            ll_vertical.setVisibility(VISIBLE);
        } else {
//            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
//            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
//            rv_type.setLayoutManager(linearLayoutManager);
//            rv_car.setVisibility(VISIBLE);
            tv_change.setText("展开");
            ll_horizontal.setVisibility(VISIBLE);
            ll_vertical.setVisibility(GONE);
            for (CarClassfyInfo carClassfyInfo : carClassfyInfos) {
                if (carClassfyInfo.isChecked()) {
                    carInfos.clear();
                    carInfos.addAll(carClassfyInfo.getCarInfo());
                    refreshSlView();
                    break;
                }
            }

        }
        chooseVerAdapter.setMode(mode);
        chooseAdapter.setMode(mode);
        carAdapter.setMode(mode);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_change:
                setMode(!isOpen);
                break;
            case R.id.tv_call:
                call();
                break;
        }
    }

    private void call() {
        CarInfo chooseCarInfo = null;
        for (CarClassfyInfo carClassfyInfo : carClassfyInfos) {
            for (CarInfo carInfo : carClassfyInfo.getCarInfo()) {
                if (carInfo.isChecked()) {
                    Log.d("chooseview", carInfo.getCarName() + carInfo.getPrice());
                    chooseCarInfo = carInfo;
                    break;
                }
            }
        }
        this.chooseCarInfo = chooseCarInfo;
        if (onCallListener != null) {
            onCallListener.onCall(chooseCarInfo);
        }
    }

    public CarInfo getChooseCarInfo() {
        return chooseCarInfo;
    }

    public interface OnCallListener {
        void onCall(CarInfo chooseCarInfo);
    }
}
