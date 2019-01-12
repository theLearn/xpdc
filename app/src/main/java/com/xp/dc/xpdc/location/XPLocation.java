package com.xp.dc.xpdc.location;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Yunhao on 2017/10/16.
 * Description：定位信息实体类 根据 BDLocation设计
 */

public class XPLocation implements Parcelable {
    private double lon;     // 纬度
    private double lat;     // 经度
    private double alt;     // 高度
    private int accuracy;   // 精确度
    private double speed;   // 速度
    private double direction;   // 方向
    private int locType;    // 类型
    private int satellites; // 卫星数量
    private String province;    // 省份
    private String city;        // 城市
    private String district;    // 区
    private String address;     // 详细地址，如果地址为空，可以用省份+城市代替
    private String posDate;     // 定位时间
    private int posFlag;    // 位置标识 0 其他；1起点；2中间点；3终点

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getAlt() {
        return alt;
    }

    public void setAlt(double alt) {
        this.alt = alt;
    }

    public int getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(int accuracy) {
        this.accuracy = accuracy;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getDirection() {
        return direction;
    }

    public void setDirection(double direction) {
        this.direction = direction;
    }

    public int getLocType() {
        return locType;
    }

    public void setLocType(int locType) {
        this.locType = locType;
    }

    public int getSatellites() {
        return satellites;
    }

    public void setSatellites(int satellites) {
        this.satellites = satellites;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPosDate() {
        return posDate;
    }

    public void setPosDate(String posDate) {
        this.posDate = posDate;
    }

    public void setPosFlag(int posFlag){
        this.posFlag = posFlag;
    }

    public int getPosFlag(){
        return posFlag;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(this.lon);
        dest.writeDouble(this.lat);
        dest.writeDouble(this.alt);
        dest.writeInt(this.accuracy);
        dest.writeDouble(this.speed);
        dest.writeDouble(this.direction);
        dest.writeInt(this.locType);
        dest.writeInt(this.satellites);
        dest.writeString(this.province);
        dest.writeString(this.city);
        dest.writeString(this.district);
        dest.writeString(this.address);
        dest.writeString(this.posDate);
        dest.writeInt(this.posFlag);
    }

    public XPLocation() {
    }

    protected XPLocation(Parcel in) {
        this.lon = in.readDouble();
        this.lat = in.readDouble();
        this.alt = in.readDouble();
        this.accuracy = in.readInt();
        this.speed = in.readDouble();
        this.direction = in.readDouble();
        this.locType = in.readInt();
        this.satellites = in.readInt();
        this.province = in.readString();
        this.city = in.readString();
        this.district = in.readString();
        this.address = in.readString();
        this.posDate = in.readString();
        this.posFlag = in.readInt();
    }

    public static final Parcelable.Creator<XPLocation> CREATOR = new Parcelable.Creator<XPLocation>() {
        @Override
        public XPLocation createFromParcel(Parcel source) {
            return new XPLocation(source);
        }

        @Override
        public XPLocation[] newArray(int size) {
            return new XPLocation[size];
        }
    };
}
