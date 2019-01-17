package com.xp.dc.xpdc.bean;

import java.util.List;

public class CarClassfyInfo {

    private String type;
    private int icon;
    private List<CarInfo> carInfo;

    private boolean isChecked;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<CarInfo> getCarInfo() {
        return carInfo;
    }

    public void setCarInfo(List<CarInfo> carInfo) {
        this.carInfo = carInfo;
    }
}
