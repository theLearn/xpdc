package com.xp.dc.xpdc.activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.baidu.mapapi.map.MapView;
import com.example.hongcheng.common.base.BaseActivity;
import com.example.hongcheng.common.base.BasicActivity;
import com.example.hongcheng.common.base.CommonActivity;
import com.xp.dc.xpdc.R;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MapActivity extends CommonActivity {
    private MapView mMapView;


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }


    @Override
    public void initBodyView(@NotNull View view) {
        mMapView = view.findViewById(R.id.bmapView);
        initData();
    }

    private void initData() {
        setMessageViewVisible(false);
    }


    @Override
    public boolean isNeedShowBack() {
        return false;
    }

    @Override
    public int setToolbarTitle() {
        return R.string.map_main;
    }

    @Override
    public int getBodyLayoutResId() {
        return R.layout.activity_map;
    }

}
