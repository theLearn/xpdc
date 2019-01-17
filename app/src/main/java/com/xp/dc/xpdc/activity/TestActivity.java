package com.xp.dc.xpdc.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import com.example.hongcheng.common.util.ToastUtils;
import com.xp.dc.xpdc.R;
import com.xp.dc.xpdc.bean.CarClassfyInfo;
import com.xp.dc.xpdc.bean.CarInfo;
import com.xp.dc.xpdc.widget.choosecar.BottomView;
import com.xp.dc.xpdc.widget.choosecar.ChooseView;
import com.xp.dc.xpdc.widget.choosecar.MyTabView;
import com.xp.dc.xpdc.widget.choosecar.Toggle;

import java.util.ArrayList;
import java.util.List;

public class TestActivity extends AppCompatActivity {

    /**
     * 底部弹出view
     */
    BottomView bottomView;
    private LinearLayout ll_bottonlayout;
    private ChooseView chooseView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        initView();
        initData();
    }

    private void initView() {
        ll_bottonlayout = (LinearLayout) findViewById(R.id.ll_bottonlayout);
        initBottomView();
    }

    /**
     * 初始化底部弹出view
     *
     * @param
     * @return
     */
    public void initBottomView() {
        chooseView = findViewById(R.id.chooseview);
//        tabView.setOnTabChangeListener(new MyTabView.OnTabChangeListener() {
//            @Override
//            public void onTabSelected(int pos, String title, List<Toggle> toggleList) {
//                ToastUtils.show(TestActivity.this, carClassfyInfos.get(pos).getType());
//            }
//
//            @Override
//            public void onToggleSingle(Toggle openToggle, Toggle closeToggle) {
//                ToastUtils.show(TestActivity.this, openToggle.getType());
//            }
//        });
    }

    private int checkToggleRes;
    private String reclassify;

    public void toggleClick(Toggle toggle) {
        if (toggle != null) {
            String type = toggle.getType();
            if (toggle.isToggleOn()) {
                checkToggleRes = toggle.getOn_text_res_id();
                reclassify = toggle.getBottonText();
            } else {
            }
        }
    }


    List<CarClassfyInfo> carClassfyInfos;

    private void initData() {
        carClassfyInfos = new ArrayList<>();
        CarClassfyInfo carClassfyInfo = new CarClassfyInfo();
        carClassfyInfo.setType("出租车");
        carClassfyInfo.setIcon(R.mipmap.ic_taxi);
        List<CarInfo> carInfos = new ArrayList<>();
        CarInfo carInfo = new CarInfo();
        carInfo.setCarIcon(R.mipmap.ic_didi);
        carInfo.setCarName("滴滴快车");
        carInfo.setId(1);
        carInfo.setPrice("11.5元");
        carInfos.add(carInfo);
//        carInfo = new CarInfo();
//        carInfo.setCarIcon(R.mipmap.ic_caocao);
//        carInfo.setCarName("曹操");
//        carInfo.setPrice("13.5元");
//        carInfos.add(carInfo);
        carClassfyInfo.setCarInfo(carInfos);
        carClassfyInfos.add(carClassfyInfo);
        carClassfyInfo = new CarClassfyInfo();
        carClassfyInfo.setType("经济型");
        carClassfyInfo.setIcon(R.mipmap.ic_normal_car);
        carInfos = new ArrayList<>();
        carInfo = new CarInfo();
        carInfo.setCarIcon(R.mipmap.ic_didi);
        carInfo.setCarName("滴滴快车");
        carInfo.setPrice("12.5元");
        carInfo.setId(2);
        carInfos.add(carInfo);
        carInfo = new CarInfo();
        carInfo.setCarIcon(R.mipmap.ic_caocao);
        carInfo.setCarName("曹操专车");
        carInfo.setPrice("13.5元");
        carInfo.setId(3);
        carInfos.add(carInfo);
        carClassfyInfo.setCarInfo(carInfos);
        carClassfyInfos.add(carClassfyInfo);
        carClassfyInfo = new CarClassfyInfo();
        carClassfyInfo.setIcon(R.mipmap.ic_comfortable_car);
        carClassfyInfo.setType("舒适型");
        carInfos = new ArrayList<>();
//        carInfo = new CarInfo();
//        carInfo.setCarIcon(R.mipmap.ic_didi);
//        carInfo.setCarName("滴滴");
//        carInfo.setPrice("12.5元");
//        carInfos.add(carInfo);
        carInfo = new CarInfo();
        carInfo.setCarIcon(R.mipmap.ic_caocao);
        carInfo.setCarName("曹操专车");
        carInfo.setPrice("14.5元");
        carInfo.setId(4);
        carInfos.add(carInfo);
        carClassfyInfo.setCarInfo(carInfos);
        carClassfyInfos.add(carClassfyInfo);
        chooseView.refreshData(carClassfyInfos);
    }
}
