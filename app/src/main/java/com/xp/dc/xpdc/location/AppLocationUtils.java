package com.xp.dc.xpdc.location;

import android.content.Context;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

import java.util.ArrayList;
import java.util.List;

public class AppLocationUtils {
    private static AppLocationUtils instance = null;

    private List<XPLocationListener> locationListenerList;
    private List<XPLocationListener> locationListenerOnceList;
    private LocationClient locationClient;
    private MyBDLocationListener myBDLocationListener;
    private LocationClientOption option;
    private Context context;

    private XPLocation startLocation;
    private XPLocation endLocation;

    private AppLocationUtils(){

    }

    public static AppLocationUtils getInstance(){
        if(instance == null){
            synchronized (AppLocationUtils.class){
                if(instance == null){
                    instance = new AppLocationUtils();
                }
            }
        }

        return instance;
    }

    public void init(Context context) {
        this.context = context;
        locationListenerList = new ArrayList<>();
        locationListenerOnceList = new ArrayList<>();
        myBDLocationListener = new MyBDLocationListener();
        option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setCoorType("bd09ll");
        option.setScanSpan(5000);               //可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.disableCache(true);
        option.setIsNeedAddress(true);          //可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);                //可选，默认false,设置是否使用gps
        option.setLocationNotify(false);        //可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true); //可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);  //可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);     //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);  //可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);     //可选，默认false，设置是否需要过滤GPS仿真结果，默认需要locationClient = new LocationClient(context);
        locationClient = new LocationClient(context.getApplicationContext());
        locationClient.setLocOption(option);
        locationClient.registerLocationListener(myBDLocationListener);
        locationClient.start();
    }

    public LocationClient getLocationClient() {
        return locationClient;
    }

    public void startLocate(XPLocationListener locationListener) {
        if(!locationListenerList.contains(locationListener)) {
            locationListenerList.add(locationListener);
        }

        if(locationClient != null) {
            if(locationClient.isStarted()){
                locationClient.requestLocation();
            }else{
                locationClient.start();
            }
        }
    }

    public void startLocateOnce(XPLocationListener locationListener) {
        if(!locationListenerOnceList.contains(locationListener)) {
            locationListenerOnceList.add(locationListener);
        }

        if(locationClient != null) {
            if(locationClient.isStarted()){
                locationClient.requestLocation();
            }else{
                locationClient.start();
            }
        }
    }

    public void removeListener(XPLocationListener locationListener) {
        if(locationListenerList.contains(locationListener)) {
            locationListenerList.remove(locationListener);
        }
    }

    /**
     * Description:     百度的位置监听
     */
    public class MyBDLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            if (bdLocation != null && !("4.9E-324".equals(String.valueOf(bdLocation.getLatitude())))) {
                XPLocation tempLocation = new XPLocation();
                tempLocation.setLat(bdLocation.getLatitude());
                tempLocation.setLon(bdLocation.getLongitude());
                if ("4.9E-324".equals(String.valueOf(bdLocation.getAltitude()))) {
                    tempLocation.setAlt(0);
                } else {
                    tempLocation.setAlt(bdLocation.getAltitude());
                }
                tempLocation.setLocType(bdLocation.getLocType());
                tempLocation.setSpeed(bdLocation.getSpeed());
                tempLocation.setDirection(bdLocation.getDirection());
                tempLocation.setAddress(bdLocation.getAddrStr());
                tempLocation.setProvince(bdLocation.getProvince());
                tempLocation.setCity(bdLocation.getCity());
                tempLocation.setDistrict(bdLocation.getDistrict());
                tempLocation.setStreet(bdLocation.getStreet());
                if(tempLocation.getCity() != null && !"null".equals(tempLocation.getCity()) && !"".equals(tempLocation.getCity())){
                    tempLocation.setAddress(tempLocation.getCity() + tempLocation.getDistrict() + tempLocation.getStreet());
                    tempLocation.setName(tempLocation.getAddress());
                }else{
                    tempLocation.setAddress("经度：" + tempLocation.getLon() + ", 纬度：" + tempLocation.getLat());
                    tempLocation.setName(tempLocation.getAddress());
                }

                tempLocation.setPosDate(System.currentTimeMillis() + "");

                for (XPLocationListener listener : locationListenerList) {
                    listener.onLocateSuccess(tempLocation);
                }

                for (XPLocationListener listenerOnce : locationListenerOnceList) {
                    listenerOnce.onLocateSuccess(tempLocation);
                }
            }else{
                for (XPLocationListener listenerOnce : locationListenerOnceList) {
                    listenerOnce.onLocateFailed();
                }
            }

            locationListenerOnceList.clear();
        }
    }

    public XPLocation getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(XPLocation startLocation) {
        this.startLocation = startLocation;
    }

    public XPLocation getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(XPLocation endLocation) {
        this.endLocation = endLocation;
    }

    public interface XPLocationListener {
        void onLocateSuccess(XPLocation xpLocation);
        void onLocateFailed();
    }
}
