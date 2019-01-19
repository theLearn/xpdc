package com.xp.dc.xpdc.location;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import com.baidu.mapapi.map.*;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.xp.dc.xpdc.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hxm on 2018/3/2.
 * Description: 自定义百度地图绘图操作的View
 * Function:
 * (1)添加或移除Marker、起点Marker、终点Marker;
 * (2)更新地图的中心位置;
 * (3)更新当前位置图标的位置;
 * (4)绘制路线轨迹;
 */

public class CustomMapView extends LinearLayout {
    private MapView mapView;
    BaiduMap mBaiduMap = null;
    List<Overlay> mOverlayList = null;

    private BitmapDescriptor curPosIcon;        // 当前位置图标
    private BitmapDescriptor startPosIcon;      // 当前位置图标
    private BitmapDescriptor startTempIcon;      // 当前位置图标
    private BitmapDescriptor endPosIcon;        // 当前位置图标

    private Marker startTempMarker;
    private Marker startMarker;
    private Marker endMarker;
    private Marker curMarker;
    private OnMapMarkerClickListener onMapMarkerClickListener;

    public CustomMapView(Context context) {
        this(context, null);
    }

    /**
     * 在XML文件中调用该自定义View时，此构造函数必须被重写(系统会调用该方法)，且必须继承父类的该方法，否则会产生异常
     *
     * @param context
     * @param attrs
     */
    public CustomMapView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomMapView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context, attrs, defStyleAttr);
    }

    /**
     * 初始化相关变量和地图
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    private void initialize(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        mOverlayList = new ArrayList<>();
        curPosIcon = BitmapDescriptorFactory.fromResource(R.mipmap.icon_location_arrow);
        startPosIcon = BitmapDescriptorFactory.fromResource(R.mipmap.icon_call_start);
        startTempIcon = BitmapDescriptorFactory.fromResource(R.mipmap.icon_location);
        endPosIcon = BitmapDescriptorFactory.fromResource(R.mipmap.icon_end_position);
        mapView = new MapView(context, new BaiduMapOptions().mapStatus(new MapStatus.Builder().overlook(1).zoom(17).build()).zoomControlsEnabled(false));
        mBaiduMap = mapView.getMap();
        this.addView(mapView); //在mapView创建完成后需要将其添加到当前视图中，否则地图将无法显示；
    }

    /**
     * 将所有Overlay 从 地图上消除
     */
    public final void removeFromMap() {
        if (mBaiduMap == null) {
            return;
        }
        for (Overlay marker : mOverlayList) {
            marker.remove();
        }

        mOverlayList.clear();
        mBaiduMap.clear();

        startTempMarker = null;
        startMarker = null;
        endMarker = null;
        curMarker = null;
    }

    /**
     * 缩放地图，使所有Overlay都在合适的视野内
     * <p>
     * 注： 该方法只对Marker类型的overlay有效
     * </p>
     *
     */
    public void zoomToSpan() {
        if (mBaiduMap == null) {
            return;
        }
        if (mOverlayList.size() > 0) {
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            for (Overlay overlay : mOverlayList) {
                // polyline 中的点可能太多，只按marker 缩放
                if (overlay instanceof Marker) {
                    builder.include(((Marker) overlay).getPosition());
                }
            }
            mBaiduMap.setMapStatus(MapStatusUpdateFactory
                    .newLatLngBounds(builder.build()));
        }
    }

    /**
     * 更新地图的中心位置
     *
     * @param mapCenterPos
     */
    public void updateMapCenter(LatLng mapCenterPos) {
        if (mBaiduMap == null || mapCenterPos == null) {
            return;
        }
        MapStatusUpdate msu = MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder()
                .target(mapCenterPos)
                .zoom(mBaiduMap.getMapStatus().zoom)
                .build());
        mBaiduMap.setMapStatus(msu);
    }

    /**
     * 更新当前图标的位置
     * @param curPos
     * @param direction 方向数据
     * @return
     */
    public Marker updateCurPosMarker(LatLng curPos, float direction) {
        if (curPos == null || mBaiduMap == null) {
            return null;
        }
        if (curMarker == null) {
            curMarker = addMarker(curPos, curPosIcon);
            if (curMarker != null) {
                curMarker.setRotate(360 - direction);
            }
        } else {
            curMarker.setPosition(curPos);
            curMarker.setRotate(360 - direction);
        }
        return curMarker;
    }

    public void drawSEToMap (LatLng start, LatLng end) {
        if (start == null || mBaiduMap == null) {
            return;
        }

        if(end == null) {
            if (endMarker != null) {
                endMarker.remove();
                mOverlayList.remove(endMarker);
            }

            if (startTempMarker == null) {
                startTempMarker = addMarker(start, startTempIcon);
            } else {
                startTempMarker.setPosition(start);
            }

            if (startMarker != null) {
                startMarker.remove();
                mOverlayList.remove(startMarker);
                startMarker = null;
            }
        } else {
            if (endMarker == null) {
                endMarker = addMarker(end, endPosIcon);
            } else {
                endMarker.setPosition(end);
            }

            if (startMarker == null) {
                startMarker = addMarker(start, startPosIcon);
            } else {
                startMarker.setPosition(start);
            }

            if (startTempMarker != null) {
                startTempMarker.remove();
                mOverlayList.remove(startTempMarker);
                startTempMarker = null;
            }
        }

    }

    /**
     * 添加标记Marker
     *
     * @param pos 起点经纬度所对应的LatLng
     */
    private Marker addMarker(LatLng pos, BitmapDescriptor markerIcon) {
        if (mBaiduMap == null || pos == null || markerIcon == null) {
            return null;
        }
        OverlayOptions option = new MarkerOptions()
                .position(pos)
                .icon(markerIcon)
                .zIndex((int) mBaiduMap.getMapStatus().zoom)
                .draggable(true)
                .anchor(0.5f, 0.5f);
        Marker marker = (Marker) mBaiduMap.addOverlay(option);
        mOverlayList.add(marker);
        zoomToSpan();
        return marker;
    }

    /**
     * 地图绘制完成监听接口
     */
    public interface OnMapStatusChangeFinishListener {
        void OnMapStatusChangeFinishListener(MapStatus mapStatus);
    }

    /**
     * 设置地图绘制完成监听事件
     *
     * @param listener
     */
    public void setOnMapStatusChangeFinishListener(final OnMapStatusChangeFinishListener listener) {
        if (mBaiduMap == null) {
            return;
        }
        mBaiduMap.setOnMapStatusChangeListener(new BaiduMap.OnMapStatusChangeListener() {
            @Override
            public void onMapStatusChangeStart(MapStatus mapStatus) {

            }

            @Override
            public void onMapStatusChangeStart(MapStatus mapStatus, int i) {

            }

            @Override
            public void onMapStatusChange(MapStatus mapStatus) {

            }

            @Override
            public void onMapStatusChangeFinish(MapStatus mapStatus) {
                if (listener != null) {
                    listener.OnMapStatusChangeFinishListener(mapStatus);
                }
            }
        });
    }


    /**
     * 弹出WindowInfo框
     *
     * @param view
     * @param latLng
     * @param i
     */
    public void showInfoWindow(View view, LatLng latLng, int i) {
        if (mBaiduMap != null) {
            mBaiduMap.hideInfoWindow();
            InfoWindow infoWindow = new InfoWindow(view, latLng, i);
            mBaiduMap.showInfoWindow(infoWindow);
        }
    }


    /**
     * MapView的相关的生命周期的方法,分别对应在Activity或Fragment的相对应的生命周期方法中调用
     */

    public void onResume() {
        if (mapView != null) {
            mapView.onResume();
        }
    }

    public void onPause() {
        if (mapView != null) {
            mapView.onPause();
        }
    }

    public void onDestroy() {
        if (mapView != null) {
            mapView.onDestroy();
        }
        //回收相关的地图覆盖物，防止内存泄漏
        if (curPosIcon != null) {
            curPosIcon.recycle();
            curPosIcon = null;
        }
        if (startTempIcon != null) {
            startTempIcon.recycle();
            startTempIcon = null;
        }

        if (endPosIcon != null) {
            endPosIcon.recycle();
            endPosIcon = null;
        }

        if (endPosIcon != null) {
            endPosIcon.recycle();
            endPosIcon = null;
        }
    }

    /**
     * 设置地图marker点击的事件监听
     */
    public void setOnMapMarkerClickListener(OnMapMarkerClickListener onMapMarkerClickListener) {
        this.onMapMarkerClickListener = onMapMarkerClickListener;
    }

    /**
     * 地图marker点击的事件监听接口
     */
    public interface OnMapMarkerClickListener {
        void onMapMarkerClick(Marker marker);
    }

}
