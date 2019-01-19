package com.xp.dc.xpdc.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.xp.dc.xpdc.R;
import com.xp.dc.xpdc.bean.CarClassfyInfo;
import com.xp.dc.xpdc.bean.CarInfo;
import com.xp.dc.xpdc.widget.choosecar.BottomView;
import com.xp.dc.xpdc.widget.choosecar.ChooseView;
import com.xp.dc.xpdc.widget.choosecar.MyTabView;
import com.xp.dc.xpdc.widget.choosecar.Toggle;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class TestActivity extends AppCompatActivity {


    /**
     * 底部弹出view
     */
    BottomView bottomView;
    private ChooseView chooseView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        initView();
//        initData();
    }

    private void initView() {
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
        chooseView.setOnCallListener(new ChooseView.OnCallListener() {

            @Override
            public void onCall(List<CarInfo> chooseCarInfos) {
                for (CarInfo chooseCarInfo : chooseCarInfos) {
                    Log.i("TestActivity", chooseCarInfo.getCarName() + chooseCarInfo.getPrice());
                }

            }
        });
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
        carClassfyInfo.setCheckIcon(R.mipmap.ico_taxi_checked);
        carClassfyInfo.setIcon(R.mipmap.ic_taxi);
        List<CarInfo> carInfos = new ArrayList<>();
        CarInfo carInfo = new CarInfo();
        carInfo.setCarIcon(R.mipmap.ic_didi);
        carInfo.setCarName("出租车");
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
        carClassfyInfo.setCheckIcon(R.mipmap.ic_normal_car);
        carClassfyInfo.setIcon(R.mipmap.ico_normal_uncheck);
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
        carClassfyInfo.setCheckIcon(R.mipmap.ico_good_checked);
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
