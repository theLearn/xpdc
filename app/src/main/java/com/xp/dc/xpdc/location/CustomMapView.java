package com.xp.dc.xpdc.location;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import com.baidu.mapapi.map.*;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.overlayutil.OverlayManager;
import com.baidu.mapapi.utils.DistanceUtil;
import com.xp.dc.xpdc.R;

import java.util.ArrayList;
import java.util.Collections;
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
    private BaiduMap baiduMap;

    private BitmapDescriptor curPosIcon;        // 当前位置图标
    private BitmapDescriptor startPosIcon;      // 当前位置图标
    private BitmapDescriptor endPosIcon;        // 当前位置图标
    private Marker marker;
    private Marker startMarker;
    private Marker endMarker;
    private Marker curMarker;
    private int zoomLevel[] = {2000000, 1000000, 500000, 200000, 100000, 50000,
            25000, 20000, 10000, 5000, 2000, 1000, 500, 100, 50, 20, 0};
    private int mapZoom;
    private List<Overlay> routeOverlayList;
    private Overlay overlay;
    private OnMapMarkerClickListener onMapMarkerClickListener;
    private List<OverlayOptions> overlayOptionsList = null;

    public CustomMapView(Context context) {
        super(context);
        initialize(context, null, 0);
    }

    /**
     * 在XML文件中调用该自定义View时，此构造函数必须被重写(系统会调用该方法)，且必须继承父类的该方法，否则会产生异常
     *
     * @param context
     * @param attrs
     */
    public CustomMapView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initialize(context, attrs, 0);
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
        curPosIcon = BitmapDescriptorFactory.fromResource(R.mipmap.icon_location_arrow);
        startPosIcon = BitmapDescriptorFactory.fromResource(R.mipmap.icon_location);
        endPosIcon = BitmapDescriptorFactory.fromResource(R.mipmap.icon_location);
        mapZoom = 17;
        routeOverlayList = new ArrayList<>();
        overlayOptionsList = new ArrayList<>();
        mapView = new MapView(context, new BaiduMapOptions().mapStatus(new MapStatus.Builder().overlook(1).zoom(17).build()).zoomControlsEnabled(false));
        baiduMap = mapView.getMap();
        this.addView(mapView); //在mapView创建完成后需要将其添加到当前视图中，否则地图将无法显示；
    }

    /**
     * 添加标记Marker
     * @param pos 起点经纬度所对应的LatLng
     */
    public Marker addMarker(LatLng pos, BitmapDescriptor markerIcon, int zIndex, float v, float v1) {
        if (baiduMap == null || pos == null || markerIcon == null) {
            return null;
        }
        OverlayOptions option = new MarkerOptions()
                .position(pos)
                .icon(markerIcon)
                .zIndex(zIndex)
                .draggable(true)
                .anchor(v, v1);
        marker = (Marker) baiduMap.addOverlay(option);
        return marker;
    }

    /**
     * 添加标记Marker
     *
     * @param pos 起点经纬度所对应的LatLng
     */
    public Marker addMarkerWhithAnimate(LatLng pos, BitmapDescriptor markerIcon, int zIndex, float v, float v1) {
        if (baiduMap == null || pos == null || markerIcon == null) {
            return null;
        }
        OverlayOptions option = new MarkerOptions()
                .position(pos)
                .icon(markerIcon)
                .zIndex(zIndex)
                .draggable(false)
                .animateType(MarkerOptions.MarkerAnimateType.grow)
                .anchor(v, v1);
        marker = (Marker) baiduMap.addOverlay(option);
        return marker;
    }

    /**
     * 添加起点的标记Marker
     *
     * @param startPos 起点经纬度所对应的LatLng
     */
    public Marker addStartMarker(LatLng startPos) {
        if (baiduMap == null) {
            return null;
        }
        startMarker = addMarker(startPos, startPosIcon, (int) baiduMap.getMapStatus().zoom, 0.5f, 1.0f);
        return startMarker;
    }

    /**
     * 添加终点的标记Marker
     *
     * @param endPos
     */
    public Marker addEndMarker(LatLng endPos) {
        if (baiduMap == null) {
            return null;
        }
        endMarker = addMarker(endPos, endPosIcon, (int) baiduMap.getMapStatus().zoom, 0.5f, 1.0f);
        return endMarker;
    }

    /**
     * 移除标记Marker
     *
     * @param marker
     */
    public void removeMarker(Marker marker) {
        if (marker != null) {
            marker.remove();
        }
    }

    /**
     * 移除起点Marker
     */
    public void removeStartMarker() {
        removeMarker(startMarker);
    }

    /**
     * 移除终点Marker
     */
    public void removeEndMarker() {
        removeMarker(endMarker);
    }

    /**
     * 更新地图的中心位置
     *
     * @param mapCenterPos
     */
    public void updateMapCenter(LatLng mapCenterPos) {
        if (baiduMap == null || mapCenterPos == null) {
            return;
        }
        MapStatusUpdate msu = MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder()
                .target(mapCenterPos)
                .zoom(baiduMap.getMapStatus().zoom)
                .build());
        baiduMap.setMapStatus(msu);
    }

    /**
     * 根据轨迹在地图上的起点、中点、终点的位置关系设置地图的缩放级别并更新地图的中心位置
     *
     * @param mapStartPos
     * @param mapCenterPos
     * @param mapEndPos
     */
    public void updateMapCenter(LatLng mapStartPos, LatLng mapCenterPos, LatLng mapEndPos) {
        int distance01 = (int) DistanceUtil.getDistance(mapStartPos, mapEndPos);    //起点与终点的直线距离
        int distance02 = (int) DistanceUtil.getDistance(mapStartPos, mapCenterPos); //起点与中点的直线距离
        int distance03 = (int) DistanceUtil.getDistance(mapCenterPos, mapEndPos);   //中点与终点的直线距离
        int maxDistance = distance01;
        maxDistance = (maxDistance < distance02) ? distance02 : maxDistance;
        maxDistance = (maxDistance < distance03) ? distance03 : maxDistance;
        int i;
        for (i = 0; i < 17; i++) {
            if (zoomLevel[i] < maxDistance) {
                break;
            }
        }
        mapZoom = i + 6 > 17 ? 17 : i + 6;
        updateMapCenter(mapCenterPos);
    }


    /**
     * 更新当前图标的位置
     *
     * @param curPos
     * @return
     */
    public Marker updateCurPosMarker(LatLng curPos) {
        return updateCurPosMarkerNoDirection(curPos);
    }

    /**
     * 更新当前图标的位置
     *
     * @param curPos
     * @return
     */
    public Marker updateCurPosMarkerNoDirection(LatLng curPos) {
        if (curPos == null || baiduMap == null) {
            return null;
        }
        if (curMarker == null) {
            curMarker = addMarker(curPos, curPosIcon, (int) baiduMap.getMapStatus().zoom, 0.5f, 0.5f);
        } else {
            curMarker.setPosition(curPos);
        }
        return curMarker;
    }

    /**
     * 更新当前图标的位置
     *
     * @param curPos
     * @param direction 方向数据
     * @return
     */
    public Marker updateCurPosMarker(LatLng curPos, float direction) {
        if (curPos == null || baiduMap == null) {
            return null;
        }
        if (curMarker == null) {
            curMarker = addMarker(curPos, curPosIcon, (int) baiduMap.getMapStatus().zoom, 0.5f, 0.5f);
            if (curMarker != null) {
                curMarker.setRotate(360 - direction);
            }
        } else {
            curMarker.setPosition(curPos);
            curMarker.setRotate(360 - direction);
        }
        return curMarker;
    }

    /**
     * 移除当前位置的图标
     */
    public void removeCurPosMarker() {
        removeMarker(curMarker);
    }

    /**
     * 根据经纬度点的列表绘制轨迹
     *
     * @param pointList
     * @return
     */
    public Overlay drawRouteInMap(List<LatLng> pointList) {
        if (pointList == null || pointList.size() < 2 || baiduMap == null) {
            return null;
        }
        if (overlay != null) {
            overlay.remove();
        }
        OverlayOptions overlayOptions = new PolylineOptions()
                .width(8)
                .color(0xffff6600)
                .points(pointList);
        overlay = baiduMap.addOverlay(overlayOptions);
        return overlay;
    }


    /**
     * 清空地图上所有的轨迹线，仅供上报模块的路线采集清空上个任务信息使用
     */
    public void clerAllOverlay() {
        if (routeOverlayList != null && routeOverlayList.size() > 0) {
            for (Overlay overlay : routeOverlayList) {
                if (overlay != null) {
                    overlay.remove();
                }
            }
        }
    }

    /**
     * 从地图的其他地方定位到当前的定位位置
     *
     * @param curPosition
     */
    public void backToCurrentPosition(LatLng curPosition) {
        if (curPosition == null || baiduMap == null) {
            return;
        }
        MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newLatLng(curPosition);
        baiduMap.animateMapStatus(mapStatusUpdate);
    }

    /**
     * 地图marker点击的事件监听接口
     */
    public interface OnMapMarkerClickListener {
        void onMapMarkerClick(Marker marker);
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
        if (baiduMap == null) {
            return;
        }
        baiduMap.setOnMapStatusChangeListener(new BaiduMap.OnMapStatusChangeListener() {
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
     * 设置地图marker点击的事件监听
     */
    public void setOnMapMarkerClickListener(OnMapMarkerClickListener onMapMarkerClickListener) {
        this.onMapMarkerClickListener = onMapMarkerClickListener;
    }


    /**
     * 弹出WindowInfo框
     *
     * @param view
     * @param latLng
     * @param i
     */
    public void showInfoWindow(View view, LatLng latLng, int i) {
        if (baiduMap != null) {
            baiduMap.hideInfoWindow();
            InfoWindow infoWindow = new InfoWindow(view, latLng, i);
            baiduMap.showInfoWindow(infoWindow);
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
        if (startPosIcon != null) {
            startPosIcon.recycle();
            startPosIcon = null;
        }
        if (endPosIcon != null) {
            endPosIcon.recycle();
            endPosIcon = null;
        }
        if (overlayOptionsList != null) {
            overlayOptionsList.clear();
            overlayOptionsList = null;
        }
        if (routeOverlayList != null) {
            routeOverlayList.clear();
            routeOverlayList = null;
        }
    }

    public int getMapZoom(double maxLat, double minLat, double maxLon, double minLon) {
        int jl = (int) DistanceUtil.getDistance(new LatLng(maxLat, maxLon), new LatLng(minLat, minLon));
        int i;
        for (i = 0; i < 17; i++) {
            if (zoomLevel[i] < jl) {
                break;
            }
        }
        return i + 4;
    }

    /**
     * 在屏幕范围内显示所有的Marker
     *
     * @param posList
     */
    public void addAllMarkerToBaiduMap(List<LatLng> posList) {
        baiduMap.clear();
        overlayOptionsList.clear();
        if (posList == null || posList.size() == 0) {
            return;
        }
        List<Double> latList = new ArrayList<>();
        List<Double> lonList = new ArrayList<>();
        for (LatLng latlng : posList) {
            latList.add(latlng.latitude);
            lonList.add(latlng.longitude);
        }
        int zoom = getMapZoom(Collections.max(latList), Collections.min(latList), Collections.max(lonList), Collections.min(lonList));
        for (LatLng latLng : posList) {
            OverlayOptions ooA = new MarkerOptions().position(latLng)
                    .icon(startPosIcon) //无图标暂用中心图标代替
                    .zIndex(zoom)
                    .draggable(true)
                    .extraInfo(null); //是否携带数据，以后待定
            overlayOptionsList.add(ooA);
        }
        OverlayManager overlayManager = new OverlayManager(baiduMap) {
            @Override
            public List<OverlayOptions> getOverlayOptions() {
                return overlayOptionsList;
            }

            @Override
            public boolean onMarkerClick(Marker marker) {
                if (onMapMarkerClickListener != null) {
                    onMapMarkerClickListener.onMapMarkerClick(marker);
                }
                return true;
            }

            @Override
            public boolean onPolylineClick(Polyline polyline) {
                return false;
            }
        };
        overlayManager.addToMap();
        overlayManager.zoomToSpan();
    }

    /**
     * 展示特定经纬度的Marker列表
     *
     * @param latLngList    经纬度列表
     * @param drawableResId 该经纬度列表所添加的marker的图标
     * @return 所添加的maker列表
     */
    public List<Marker> showMark(List<LatLng> latLngList, int drawableResId) {
        if (baiduMap == null || latLngList == null || latLngList.size() == 0) {
            return null;
        }
        List<Marker> markerList = new ArrayList<>();
        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(drawableResId);
        for (LatLng latLng : latLngList) {
            if (latLng != null) {
                markerList.add(addMarker(latLng, icon, (int) baiduMap.getMapStatus().zoom, 0.5f, 1.0f));
            }
        }
        if (icon != null) {
            icon.recycle();
        }
        return markerList;
    }

    /**
     * 移除marker列表
     *
     * @param markerList
     */
    public void removeMarkerList(List<Marker> markerList) {
        if (markerList == null || markerList.size() == 0) {
            return;
        }
        for (Marker marker : markerList) {
            removeMarker(marker);
        }
    }

    /**
     * 在地图上移除轨迹
     *
     * @param overlay
     */
    public void removeOverlay(Overlay overlay) {
        if (overlay != null) {
            overlay.remove();
        }
    }

    /**
     * 移除所有的覆盖物和InfoWindow
     */
    public void removeAllOverlay() {
        baiduMap.clear();
    }

}
